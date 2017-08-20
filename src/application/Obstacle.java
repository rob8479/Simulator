package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

/**
 * 
 * @author Robert Sadler
 * Any object in the world which is not considered to be the terrain i.e. the ground is considered an obstacle.
 * All obstacles have a position, a size, and general methods to do with collision handling.
 * This is the super class, which each other lower class implementing their own behaviour.
 */
public class Obstacle {
	
	private Image image;
	protected double positionX;
	protected double positionY;
	private double width; //x
	private double height; //y
	protected double angle;//Direction we are facing
	protected double velocityX;
	protected double velocityY;
	
	/**
	 * 
	 * @param image			- Image for the obstacle
	 * @param positionX		- x Co-ord in the world
	 * @param positionY		- Y Co-ord in the world
	 * @param width			- width of the obstacle
	 * @param height		- height of the obstacle
	 */
	public Obstacle (Image image, double positionX, double positionY, double angle) {
		this.image = image;
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.angle = angle;
		this.velocityX = 0;
		this.velocityY = 0;
	}
	
	/**
	 * 
	 * @param elapsedTime  - The current Time since the last frame
	 * Update Function called from the main simulation loop which figures out the current position of the obstacle.
	 * Note: Only movable objects can be given velocity, so velocity is controlled within that class and not here.
	 */
	public void update(double elapsedTime) {
		//Position is equal to the Old Position, plus the velocity in that direction
		positionX += (velocityX * elapsedTime);
		positionY += (velocityY * elapsedTime);
		
		//positionX += (Math.cos(Math.toRadians(angle - 90)) * velocity) * elapsedTime;	
		//positionY += (Math.sin(Math.toRadians(angle - 90)) * velocity) * elapsedTime;
		/**
		 * Because the top left corner is 0,0 - as apposed to the convential bottom left, the whole thing needs shifting by -90 degrees
		 */
		
		
		//COME BACK TO - This assumes there is no friction in the world
		velocityX = 0;
		velocityY = 0;
	}
	
	/**
	 * 
	 * @param gc - The current drawing environment
	 * Called by the main simulation loop to draw the obstacle at it's current position
	 */
	public void render(GraphicsContext gc) {
		//gc.drawImage(image, positionX, positionY);
		
		//Draw HitBox - For Debugging only
		Rectangle2D bounds = this.getBoundary();
		gc.fillRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
		
		
		this.drawRotatedImage(gc, image, angle, positionX, positionY);
	}
	
	/**
	 * 
	 * @return Returns a rectangle showing the bounds of this obstacle.
	 * 
	 * THIS NEEDS CHANGING
	 */
	public Rectangle2D getBoundary() {
		return new Rectangle2D(positionX, positionY, width, height);
	}
	
	/**
	 * 
	 * @param x - X-Cordinate for click
	 * @param y - Y-Cordinate for click
	 * @return - Returns true if the mouse position clicks on this entity, else false.
	 */
	public boolean selectedEntity(double x, double y) {
		return this.getBoundary().contains(x, y);
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
    
    /**
     * 
     * @return Returns the x position of this obstacle
     */
    public double getXPosition() {
    	return this.positionX;
    }
    
    /**
     * 
     * @return Returns the y position of this obstacle
      */
    public double getYPosition() {
    	return this.positionY;
    }
    
    /**
     * @return The angle which this object is facing
     */
    public double getRotation() {
    	return this.angle;
    }
    /**
     * 
     * @param gc 	- The Graphics Context
     * @param angle	- Angle of rotation
     * @param px	- X Pivot point of rotation 
     * @param py	- Y Pivot point of rotation
     */
    private void rotate(GraphicsContext gc, double angle, double px, double py) {
    	Rotate r = new Rotate(angle,px,py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }
    
    /**
     * Draws an image on a graphics context.
     *
     * The image is drawn at (tlpx, tlpy) rotated by angle pivoted around the point:
     *   (tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2)
     *
     * @param gc the graphics context the image is to be drawn on.
     * @param angle the angle of rotation.
     * @param tlpx the top left x co-ordinate where the image will be plotted (in canvas co-ordinates).
     * @param tlpy the top left y co-ordinate where the image will be plotted (in canvas co-ordinates).
     */
    private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        gc.save(); // saves the current state on stack, including the current transform
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy);
        gc.restore(); // back to original state (before rotation)
    }
    
    /**
     * 
     * @return The current velocity of the object in the X axis.
     */
    public double getVelocityX() {
    	return this.velocityX;
    }
    
    /**
     * 
     * @return The current velocity of the object in the Y axis.
     */
    public double getVelocityY() {
    	return this.velocityY;
    }
    
    /**
     * 
     * @param newVelocity - New Desired Velocity in m/s
     * Set's the Velocity in the x axis of the object equal to the given param.
     */
    public void setVelocityX(double newVelocity) {
    	velocityX = newVelocity * 100;
    }
    
    /**
     * 
     * @param newVelocity - New Desired Velocity in m/s
     * Set's the Velocity in the y axis of the object equal to the given param.
     */
	public void setVelocityY(double newVelocity) {
    	velocityY = newVelocity * 100;
	}
}
