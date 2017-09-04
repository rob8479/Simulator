package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * 
 * @author Robert Sadler
 * Similar to Moveable, except this class is directly driveable on keyboard input. The rendering is slightly different, 
 * as Driveable objects can rotate on the spot.
 */
public class Driveable extends Obstacle {

	private double velocity;
	private Sonar sonar;
	
	
	public Driveable(Image image, double positionX, double positionY, double angle, Sonar sonar) {
		super(image, positionX, positionY, angle);
		// TODO Auto-generated constructor stub
		this.sonar = sonar;
	}
	
	/**
	 * 
	 * @param elapsedTime  - The current Time since the last frame
	 * Update Function called from the main simulation loop which figures out the current position of the obstacle.
	 * Note: Only movable objects can be given velocity, so velocity is controlled within that class and not here.
	 */
	public void update(double elapsedTime) {
		velocityX = (Math.cos(Math.toRadians(angle - 90)) * velocity) * elapsedTime;
		velocityY = (Math.sin(Math.toRadians(angle - 90)) * velocity) * elapsedTime;
		positionX += velocityX;	
		positionY += velocityY;
		/**
		 * Because the top left corner is 0,0 - as apposed to the convential bottom left, the whole thing needs shifting by -90 degrees
		 */
		this.scanSonar();
	}
	/**
	 * 
	 * @param rotation - New Desired Rotation
	 * Rotate the obstacle to the desired rotation.
	 */
    public void setRotation(double rotation) {
    	angle = rotation;
    }
    
    /**
     * 
     * @param newVelocity - New Desired Velocity in m/s
     * Set's the Velocity in the x axis of the object equal to the given param.
     */
    public void setVelocity(double newVelocity) {
    	velocity = newVelocity * 100;
    }
    
    
    /**
     * @return The velocity of the robot
     */
    public double getVelocity() {
    	return this.velocity;
    }
    
    public double[] scanSonar() {
    	//Calculate the position of the Sonar
    	double centreX = this.positionX + image.getWidth()/2;
    	double centreY = this.positionY + image.getHeight()/2;
    	
    	double sonarPosX = centreX + ((image.getHeight()/2) * Math.cos(Math.toRadians(angle - 90)));
    	double sonarPosY = centreY + ((image.getHeight()/2) * Math.sin(Math.toRadians(angle - 90)));
    	sonar.setPositionX(sonarPosX);
    	sonar.setPositionY(sonarPosY);
    	sonar.setOrientation(angle);
    	//Scan the sonar
    	return sonar.scan(true,this);
    	
    }
    
    
}
