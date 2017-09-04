package application;

import javafx.scene.paint.Color;

/**
 * 
 * @author Robert Sadler
 * Sonar is an instrument attached to the robots. The Sonar can be called to give information about what it can currently "see".
 * The sonar scans from the minimumAngle up the maximumAngle, and will return an array of values. These values are the distance until an 
 * obstacle (in cm) is away from the sonar for that set increment. If increments is set to 100, there will be 100 increments. 
 * If there is no obstacle, the value will be returned as -1.
 * 
 */
public class Sonar {
	//Variables of the sonar
	private double positionX;
	private double positionY;
	private double orientation;
	private double range;
	private double minAngle;
	private double maxAngle;
	private int sensitivity;
	private int increments;
	private Control currentSimulation;
	
	/**
	 * 
	 * @param currentSimulation
	 * @param positionX
	 * @param positionY
	 * @param orientation
	 * @param range
	 * @param minAngle
	 * @param maxAngle
	 * @param sensitivity - The higher, the more accurate. The amount of tests per distance
	 * @param increments
	 */
	public Sonar(Control currentSimulation, double positionX, double positionY, double orientation, double range, double minAngle, double maxAngle, int sensitivity, int increments) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.orientation = orientation;
		this.range = range;
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
		this.sensitivity = sensitivity;
		this.increments = increments;
		this.currentSimulation = currentSimulation;
	}

	/**
	 * @return the orientation
	 */
	public double getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return the minAngle
	 */
	public double getMinAngle() {
		return minAngle;
	}

	/**
	 * @param minAngle the minAngle to set
	 */
	public void setMinAngle(double minAngle) {
		this.minAngle = minAngle;
	}

	/**
	 * @return the maxAngle
	 */
	public double getMaxAngle() {
		return maxAngle;
	}

	/**
	 * @param maxAngle the maxAngle to set
	 */
	public void setMaxAngle(double maxAngle) {
		this.maxAngle = maxAngle;
	}
	
	
	/**
	 * @return the positionX
	 */
	public double getPositionX() {
		return positionX;
	}

	/**
	 * @param positionX the positionX to set
	 */
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	/**
	 * @return the positionY
	 */
	public double getPositionY() {
		return positionY;
	}

	/**
	 * @param positionY the positionY to set
	 */
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	/**
	 * Scans the surroundings from the sonar, and adds the distance of when it hits an obstacle, or when it hits a wall.
	 * @return an array of length increments, containing a list of distances to an object
	 */
	public double[] scan(boolean drawScans) {
		/*	   							Orientation
		 * 									|		 
		 * 									|
		 * 									|
		 *  Orientation - MinAngle	________|________ Orientation + MaxAngle
		 * 								Position
		 */
		
		//The scans to return
		double scans[] = new double[increments];
		//Each angle starting from the minimum up until the maximum angle
		double angleIncrements = (this.minAngle + this.maxAngle)/this.increments;
		double minimum = (this.orientation - this.minAngle);
		//double maximum = (this.orientation + this.maxAngle);
		double rangeIncrements = this.range / this.sensitivity;
		
		//Main Loop, for each angle within the range
		for(int i = 0; i < this.increments; i++) {
			//Inner loop, for each sensitivity, extending in distance from the sonar to see if an object is there
			double currentAngle = minimum + (i * angleIncrements) - 90;
			for(int j = 0; j < this.sensitivity; j++) {
				//Get the scan point, relative the centre of the sonar. Based on distance and angle
				double scanPointX = this.positionX + (rangeIncrements * j) * Math.cos(Math.toRadians(currentAngle));
				double scanPointY = this.positionY + (rangeIncrements * j) * Math.sin(Math.toRadians(currentAngle));
				//Check if it hit's an obstacle
				if(this.currentSimulation.scanHit(scanPointX, scanPointY)) {
					//If it has, return that distance
					scans[i] = rangeIncrements * j;
					break;
				}
				
				//If the scans are being asked to draw a line to the point
				if(drawScans) {
					this.currentSimulation.getGraphicsContext().strokeLine(this.positionX, this.positionY, scanPointX, scanPointY);
				}
				//If no point is found, set the scan point's Distance as -1
				scans[i] = -1;
			}
		}
			
		return scans;
	}
	
	

}
