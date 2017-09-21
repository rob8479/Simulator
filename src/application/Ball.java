package application;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * 
 * @author Robert Sadler
 * Ball is an obstacle that rolls. A ball is affected more by ramps and is more likely to bounce of an object.
 * An object that can roll acts different to stay a box, which is what a Moveable Obstacle would be.
 */
public class Ball extends Obstacle {
	
	private double diameter;
	private Color colour;
	
	public Ball(double positionX, double positionY, double angle, double mass, double diameter, Image dontMatter,Color colour) {
		super(dontMatter, positionX, positionY, angle, mass);
		this.width = diameter;
		this.height = diameter;
		this.diameter = diameter;
		this.colour = colour;
	}
	
	
	@Override
	/**
	 * Draws a circle representing the ball onto the canvas. A line is also drawn from the centre 
	 * in the direction it is facing to show direction of travel.
	 */
	public void render(GraphicsContext gc) {
		gc.setFill(colour);
		gc.fillOval(positionX, positionY,this.diameter,this.diameter);
				
		//DEBUGGING
		double centreX = this.positionX + this.diameter/2;
		double centreY = this.positionY + this.diameter/2;
		double directionPointx = this.positionX + (this.diameter/2) * Math.cos(Math.toRadians(this.angle));
		double directionPointy = this.positionY + (this.diameter/2) * Math.sin(Math.toRadians(this.angle));
		gc.strokeLine(centreX, centreY, directionPointx, directionPointy);
		
	}
	
	
	/**
	 * 
	 * @param o - The Obstacle which we want to see is intersecting with this obstacle
	 * @return True if the obstacle is within the bounds of this object, else false.
	 */
	@Override
	public boolean intersects(Obstacle o) {
		//return o.getBoundary().intersects(this.getBoundary());
		Ellipse2D.Double hitBox = new Ellipse2D.Double(this.positionX, this.positionY, this.diameter, this.diameter);
		Area area = new Area(hitBox);
		area.intersect(new Area (o.getHitBox()));
		return !area.isEmpty();
		
	}
	
	/**
	 * Similar to intersect with Obstacle, except with walls
	 * TODO - Change Wall to have the super Obstacle
	 * @param w - The wall that we are checking if it collides with
	 * @return
	 */
	@Override
	public boolean intersectsWall(Wall w) {
		Ellipse2D.Double hitBox = new Ellipse2D.Double(this.positionX, this.positionY, this.diameter, this.diameter);
		Area area = new Area(hitBox);
		area.intersect(new Area (w.getHitBox()));
		return !area.isEmpty();
	}
	
		
	
	@Override
	/**
	 *  Returns true if the selected point is within the bounds of the shape.
	 *  
	 *  Different to the normal selectedEntity method of the super Obstacle, as the shape is not square.
	 *  Instead uses equation of a circle as it is faster than using the Ellipse Method
	 */
	public boolean selectedEntity(double x, double y) {
		//Ellipse2D.Double hitBox = new Ellipse2D.Double(this.positionX, this.positionY, this.diameter, this.diameter);
		//Area area = new Area(hitBox);
		//return area.contains(x, y);
		
		//Optimisation - Use an equal of a circle to see if the point is in it
		
		double centreX = this.positionX + (this.diameter / 2);
		double centreY = this.positionY + (this.diameter / 2);
		return ((x - centreX) * (x - centreX) + (y - centreY) * (y - centreY)) < ((this.diameter/2) * (this.diameter/2));
	}
	
	
}
