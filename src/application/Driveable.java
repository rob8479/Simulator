package application;

import javafx.scene.image.Image;
/**
 * 
 * @author Robert Sadler
 * Similar to Moveable, except this class is directly driveable on keyboard input. The rendering is slightly different, 
 * as Driveable objects can rotate on the spot.
 */
public class Driveable extends Obstacle {

	private double velocity;
	
	public Driveable(Image image, double positionX, double positionY, double angle) {
		super(image, positionX, positionY, angle);
		// TODO Auto-generated constructor stub
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
    
    
}
