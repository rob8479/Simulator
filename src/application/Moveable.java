package application;

import javafx.scene.image.Image;

/**
 * 
 * @author Robert Sadler
 * A moveable is an obstacle that can increase or decrease it's velocity - i.e. , when it's pushed
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
    

    

}
