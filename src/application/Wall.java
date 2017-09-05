package application;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

import javafx.geometry.Rectangle2D;

public class Wall {
	
	private double positionX;
	private double positionY;
	private double width;
	private double height;
	
	/**
	 * 
	 * @param positionX - ToprightPosition
	 * @param positionY - ToprightPosition
	 * @param width - width of the wall 
	 * @param height - height of the wall
	 */
	public Wall(double positionX, double positionY, double width, double height) {
		// TODO Auto-generated constructor stub
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 
	 * @return Returns a rectangle showing the bounds of this wall. This is used for Sonar and is far more efficent than the hitbox
	 * that is required for collision detection.
	 * 
	 */
	public Rectangle2D getBoundary() {
		return new Rectangle2D(positionX, positionY, width, height);
	}
	
	
	/**
	 * 
	 * @param x - X-Cordinate for sonar
	 * @param y - Y-Cordinate for sonar
	 * @return - Returns true if the sonar point is within the wall
	 */
	public boolean sonarIntersects(double x, double y) {
		return this.getBoundary().contains(x, y);
	}
	
	
	/**
	 * 
	 * @param o - The Obstacle which we want to see is intersecting with this wall
	 * @return True if the obstacle is within the bounds of this wall, else false.
	 */
	public boolean intersects(Obstacle o) {
		//return o.getBoundary().intersects(this.getBoundary());
		Path2D hitBox = this.getHitBox();
		Area area = new Area(hitBox);
		area.intersect(new Area (o.getHitBox()));
		return !area.isEmpty();
	}
	
	
	/**
	 * 
	 * @return The hitbox is slightly different to the boundary. The Hitbox is a more accurate representation, as it will rotate with 
	 * the object. The Bounding box does not. This needs to be accurate to achieve accurate physics.
	 */
	public Path2D getHitBox() {
		Path2D hitBox = new Path2D.Double();
		//Creates an area by drawing a line through all of the corners (think turtle bot)
		hitBox.moveTo(positionX, positionY);
		hitBox.lineTo(positionX + width, positionY);
		hitBox.lineTo(positionX + width, positionY + height);
		hitBox.lineTo(positionX, positionY + height);
		hitBox.closePath();
		
		return hitBox;
	}
	
}
