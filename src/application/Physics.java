package application;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

/**
 * 
 * @author Robert Sadler
 * The Physics for the simulator is all controlled within in.
 * 	
 */
public class Physics {
	
	private ArrayList<Obstacle> obstacles;
	private Terrain terrain;
	private GraphicsContext gc;
	
	public Physics(ArrayList<Obstacle> obstacles, Terrain terrain, GraphicsContext gc) {
		// TODO Auto-generated constructor stub
		this.obstacles = obstacles;
		this.terrain = terrain;
		this.gc = gc;
	}
	
	/**
	 * Method called by the simulator, that excutes all physics for the object.
	 */
	public void propogatePhysics() {
		this.checkCollisions();
		this.friction();
	}
	
	
	/**
	 * Loops through all obstacles, and see if any of them intersect the other
	 */
	private void checkCollisions() {
		//Check vs. Walls first
		for(Obstacle o: obstacles) {
			//Optimisation: No point checking objects which are already stationary
			if(o.getVelocityX() != 0 || o.getVelocityY() != 0) {
				//Get the walls for that node
				ArrayList<Wall> walls = terrain.getWallsOfNode(o.positionX, o.positionY,gc);
				//Check that any of the objects are in contact with the wall
				for(Wall w: walls) {
					if(o.intersectsWall(w)) {
						this.handleMoveableWall(o, w);
					}
				}
			}
		}
		
		//Now Check for Obstacles vs. Obstacles
		for(Obstacle o: obstacles) {
			for(Obstacle l: obstacles) {
				//Only check objects within a 100cm radius of each other
				//TODO Update so it works based off the centres of obstacles - And based on the size of objects (Circle to circle detection)
				if(Math.abs(o.getVelocityX()) > 0.2 || Math.abs(o.getVelocityY()) > 0.2) {
					if(!o.equals(l) && (o.getDistance(o.getXPosition(), o.getYPosition(), l.getXPosition(), l.getYPosition()) < 100)) {
						if(o.intersects(l)) {
							//Collision Handle 
							//Need a check to make sure we don't have this transative relation
							//I.e. A Collides B , Does not then check B Collides A
							this.handleObstacleObstacle(o, l);
						}
					}
				}
			}
		}	
	}

	
	/**
	 * 
	 * @param o Obstacle
	 * @param w Wall
	 * 
	 * Similar to Obstacle vs. Obstacle, except a Wall has infinite mass, and no velocity.
	 */
	private void handleMoveableWall(Obstacle a, Wall w) {
				
		double rvx = 0 - a.getVelocityX();
		double rvy = 0 - a.getVelocityY();

		//Get the centre of the Wall
		double wallX = terrain.getCentreOfNode(a.getCentreX());
		double wallY = terrain.getCentreOfNode(a.getCentreY());
		
		switch(w.getPosition()) {
		case "North":
			wallY -= terrain.getNodeDiameter();
			break;
		case "South":
			wallY += terrain.getNodeDiameter();
			break;
		case "West":
			wallX -= terrain.getNodeDiameter();
			break;
		case "East":
			wallX += terrain.getNodeDiameter();
			break;
		}
		
		/*VECTOR*/double collisionNormalX = a.getCentreX() - wallX;
		/*VECTOR*/double collisionNormalY = a.getCentreY() - wallY;
		
		if(Values.DEBUG) {
			gc.strokeLine(a.getCentreX(), a.getCentreY(), wallX, wallY);

		}

		double length = Math.sqrt(collisionNormalX * collisionNormalX + collisionNormalY * collisionNormalY);
		/*VECTOR*/collisionNormalX = collisionNormalX / length;
		/*VECTOR*/collisionNormalY = collisionNormalY / length;
		
		double velAlongNormal = (rvx * collisionNormalX + rvy * collisionNormalY);
		
		//Only do collision resolution of the objects are moving towards each other
		if(velAlongNormal < 0) {
			return;
		}
		
		//Coffeficent of how bouncy it is
		//0 is Perfectly inelastic, 1 is perfectly elastic
		//i.e. 0 is pushing, 1 is bouncing off
		double e = 1;
		
		double j = -(1 + e) * velAlongNormal;
		j /= 1 / a.mass + 1/10000000;
		
		double impulseX = j * collisionNormalX;
		double impulseY = j * collisionNormalY;
				
		double x = a.getVelocityX() - (1 / a.getMass() * impulseX);
		double y = a.getVelocityY() - (1 / a.getMass() * impulseY);
		
		a.setVelocityX(a.getVelocityX() - (1 / a.getMass() * impulseX));
		a.setVelocityY(a.getVelocityY() - (1 / a.getMass() * impulseY));	
		
		//Change Robot (Different to just setting VelX and VelY);
		if(a.toString().contains("Driveable")) {
			Driveable temp = (Driveable)a;
			temp.disableDrive();
		}
		
	}
	
	/**
	 * 
	 * @param a First Obstacle
	 * @param b Second Obstacle
	 * 
	 * Uses impulse based system. Impulse is based on the parameter e which is a constant that can be defined. It defines the bounciness.
	 * The size of the collision is also correlated with the mass.
	 */
	private void handleObstacleObstacle(Obstacle a, Obstacle b) {
		
		double rvx = b.getVelocityX() - a.getVelocityX();
		double rvy = b.getVelocityY() - a.getVelocityY();
		
		/*VECTOR*/double collisionNormalX = a.getCentreX() - b.getCentreX();
		/*VECTOR*/double collisionNormalY = a.getCentreY() - b.getCentreY();
		
		double length = Math.sqrt(collisionNormalX * collisionNormalX + collisionNormalY * collisionNormalY);
		/*VECTOR*/collisionNormalX = collisionNormalX / length;
		/*VECTOR*/collisionNormalY = collisionNormalY / length;
		
		double velAlongNormal = (rvx * collisionNormalX + rvy * collisionNormalY);
		
		//Only do collision resolution of the objects are moving towards each other
		if(velAlongNormal < 0) {
			return;
		}
		
		//Coffeficent of how bouncy it is
		//0 is Perfectly inelastic, 1 is perfectly elastic
		//i.e. 0 is pushing, 1 is bouncing off
		double e = 1;
		
		double j = -(1 + e) * velAlongNormal;
		j /= 1 / a.mass + 1/b.mass;
		
		double impulseX = j * collisionNormalX;
		double impulseY = j * collisionNormalY;
						
		a.setVelocityX(a.getVelocityX() - (1 / a.getMass() * impulseX));
		a.setVelocityY(a.getVelocityY() - (1 / a.getMass() * impulseY));
				
		b.setVelocityX(b.getVelocityX() + (1 / b.getMass() * impulseX));
		b.setVelocityY(b.getVelocityY() + (1 / b.getMass() * impulseY));	
		
		//Change Robot (Different to just setting VelX and VelY);
		if(a.toString().contains("Driveable")) {
			Driveable temp = (Driveable)a;
			temp.disableDrive();
		}
	}
	
	private void friction() {
		
		for(Obstacle o : obstacles) {
			o.setVelocityX(o.getVelocityX() * 0.99);
			o.setVelocityY(o.getVelocityY() * 0.99);
		}
	}
	

}
