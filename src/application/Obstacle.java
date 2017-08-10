package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * 
 * @author Robert Sadler
 * Any object in the world which is not considered to be the terrain i.e. the ground is considered an obstacle.
 * All obstacles have a position, a size, and general methods to do with collision handling.
 * This is the super class, which each other lower class implementing their own behaviour.
 */
public class Obstacle {
	
	private Image image;
	private double positionX;
	private double positionY;
	private double width; //x
	private double height; //y
	public double velocityX;
	public double velocityY;
	//private double rotation;
	
	/**
	 * 
	 * @param image			- Image for the obstacle
	 * @param positionX		- x Co-ord in the world
	 * @param positionY		- Y Co-ord in the world
	 * @param width			- width of the obstacle
	 * @param height		- height of the obstacle
	 */
	public Obstacle (Image image, double positionX, double positionY, double width, double height) {
		this.image = image;
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.velocityX = 0;
		this.velocityY = 0;
	}
	
	/**
	 * 
	 * @param time  - The current Time
	 * Update Function called from the main simulation loop which figures out the current position of the obstacle.
	 * Note: Only movable objects can be given velocity, so velocity is controlled within that class and not here.
	 */
	public void update(double time) {
		positionX += velocityX * time;
		positionY += velocityY * time;
	}
	
	/**
	 * 
	 * @param gc - The current drawing environment
	 * Called by the main simulation loop to draw the obstacle at it's current position
	 */
	public void render(GraphicsContext gc) {
		gc.drawImage(image, positionX, positionY);
	}
	
	/**
	 * 
	 * @return Returns a rectangle showing the bounds of this obstacle.
	 */
	public Rectangle2D getBoundary() {
		return new Rectangle2D(positionX, positionY, width, height);
	}
	
	/**
	 * 
	 * @param o - The Obstacle which we want to see is intersecting with this obstacle
	 * @return True if the obstacle is within the bounds of this object, else false.
	 */
	public boolean intersects(Obstacle o) {
		return o.getBoundary().intersects(this.getBoundary());
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * Set the position of the obstacle to the co-ordinates passed in.
	 */
    public void setPosition(double x, double y)
    {
        positionX = x;
        positionY = y;
    }
}
