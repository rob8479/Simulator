package application;

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
	 * @return Returns a rectangle showing the bounds of this wall.
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
	
	
}
