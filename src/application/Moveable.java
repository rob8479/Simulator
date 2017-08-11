package application;

import javafx.scene.image.Image;

/**
 * 
 * @author Robert Sadler
 * A moveable is an obstacle that can increase or decrease it's velocity
 *
 */
public class Moveable extends Obstacle {

	public Moveable(Image image, double positionX, double positionY, double width, double height) {
		super(image, positionX, positionY, width, height);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
    public void addVelocity(double x, double y)
    {
        velocityX += x;
        velocityY += y;
    }

}
