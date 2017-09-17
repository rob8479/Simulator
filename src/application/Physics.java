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
	
	
	public void checkCollisions() {
		//Check vs. Walls first
		for(Obstacle o: obstacles) {
			//Optimisation: No point checking objects which are already stationary
			if(o.getVelocityX() != 0 || o.getVelocityY() != 0) {
				//Get the walls for that node
				ArrayList<Wall> walls = terrain.getWallsOfNode(o.positionX, o.positionY,gc);
				//Check that any of the objects are in contact with the wall
				for(Wall w: walls) {
					if(o.toString().contains("Ball")){
							Ball temp = (Ball)o;
							if(temp.intersectsWall(w)) {
								//temp.reverseCollision();
								this.handleBallWall(temp,w);
							}
					} else {
						if(o.intersectsWall(w)) {
							this.handleMoveableWall(o, w);
						}
					}
					
					
				}
			
			}
				
		}
		
		//Now Check for Obstacles vs. Obstacles
		for(Obstacle o: obstacles) {
			for(Obstacle l: obstacles) {
				//Only check objects within a 100cm radius of each other
				//TODO Update so it works based off the centres of obstacles
				if(!o.equals(l) && (o.getDistance(o.getXPosition(), o.getYPosition(), l.getXPosition(), l.getYPosition()) < 100)) {
					if(o.intersects(l)) {
						//Collision Handle 
						//Need a check to make sure we don't have this transative relation
						//I.e. A Collides B , Does not then check B Collides A
						this.handleObstacleObstacle(o, l);
						//Undo the collisions so they don't just keep colliding over and over
						o.reverseCollision();
						l.reverseCollision();
					}
				}
			}
		}
		
	}

	
	/**
	 * 
	 * @param o - The ball that collided with the wall
	 * @param w - The wall that the ball collided with
	 * 
	 * A ball bouncing against a Wall is an elastic collision - i.e. , it will bounce
	 */
	private void handleBallWall(Ball b, Wall w) {
		
	}
	
	private void handleMoveableWall(Obstacle o, Wall w) {
		o.setVelocity(0);
	}
	
	
	private void handleObstacleObstacle(Obstacle a, Obstacle b) {
		
		double rvx = b.getVelocityX() - a.getVelocityX();
		double rvy = b.getVelocityY() - a.getVelocityY();
		
		/*VECTOR*/double collisionNormalX = a.getCentreX() - b.getCentreX();
		/*VECTOR*/double collisionNormalY = a.getCentreY() - b.getCentreY();
		
		double length = Math.sqrt(collisionNormalX * collisionNormalX + collisionNormalY * collisionNormalY);
		/*VECTOR*/collisionNormalX = collisionNormalX / length;
		/*VECTOR*/collisionNormalY = collisionNormalY / length;
		
		double velAlongNormal = (rvx * collisionNormalX + rvy * collisionNormalY);
		
		double e = 1;
		
		double j = -(1 + e) * velAlongNormal;
		j /= 1 / a.mass + 1/b.mass;
		
		double impulseX = j * collisionNormalX;
		double impulseY = j * collisionNormalY;
		
		
		a.setVelocityX(a.getVelocityX() - (1 / a.getMass() * impulseX));
		a.setVelocityY(a.getVelocityY() - (1 / a.getMass() * impulseY));
		
		
		b.setVelocityX(b.getVelocityX() + (1 / b.getMass() * impulseX));
		b.setVelocityY(b.getVelocityY() + (1 / b.getMass() * impulseY));
		
		
	}
	
}
