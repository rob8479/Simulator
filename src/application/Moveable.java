package application;

import javafx.scene.image.Image;

/**
 * 
 * @author Robert Sadler
 * A moveable is an obstacle that can increase or decrease it's velocity
 *
 */
public class Moveable extends Obstacle {

	public Moveable(Image image, double positionX, double positionY,double angle) {
		super(image, positionX, positionY,angle);
	}
	
	/**
	 * 
	 * @param rotation - New Desired Rotation
	 * Rotate the obstacle to the desired rotation.
	 */
    public void changeRotation(double rotation) {
    	angle = rotation;
    }
    
    /**
     * 
     * @param newVelocity - New Desired Velocity in m/s
     * Set's the Velocity of the object equal to the given param.
     */
    public void setVelocity(double newVelocity) {
    	velocity = newVelocity * 100;
    }
    
    /**
     * 
     * @return The current velocity of the object. 
     */
    public double getVelocity() {
    	return this.velocity;
    }
}
