package application;
import javafx.scene.image.Image;
/**
 * 
 * @author Robert Sadler
 * Similar to Moveable, except this class is directly driveable on keyboard input. The rendering is slightly different, 
 * as Driveable objects can rotate on the spot.
 */
public class Driveable extends Obstacle {

	private Sonar sonar;
	private boolean drive;
	
	
	public Driveable(Image image, double positionX, double positionY, double angle, Sonar sonar,double mass) {
		super(image, positionX, positionY, angle,mass);
		// TODO Auto-generated constructor stub
		this.sonar = sonar;
		drive = false;
	}
	
	/**
	 * 
	 * @param elapsedTime  - The current Time since the last frame
	 * Update Function called from the main simulation loop which figures out the current position of the obstacle.
	 * Note: Only movable objects can be given velocity, so velocity is controlled within that class and not here.
	 */
	public void update(double elapsedTime) {
		
		if(drive) {
			velocityX = (Math.cos(Math.toRadians(angle - 90)) * velocity);
			velocityY = (Math.sin(Math.toRadians(angle - 90)) * velocity);
		}
		
		
		positionX += velocityX  * elapsedTime;	
		positionY += velocityY  * elapsedTime;
		/**
		 * Because the top left corner is 0,0 - as apposed to the convential bottom left, the whole thing needs shifting by -90 degrees
		 */
		this.scanSonar();
	}
	/**
	 * 
	 * @param rotation - New Desired Rotation
	 * Rotate the obstacle to the desired rotation.
	 */
    public void setRotation(double rotation) {
    	angle = rotation;
    }
    

    
    public double[] scanSonar() {
    	//Calculate the position of the Sonar
    	double centreX = this.positionX + image.getWidth()/2;
    	double centreY = this.positionY + image.getHeight()/2;
    	
    	double sonarPosX = centreX + ((image.getHeight()/2 - 20) * Math.cos(Math.toRadians(angle - 90)));
    	double sonarPosY = centreY + ((image.getHeight()/2 - 20) * Math.sin(Math.toRadians(angle - 90)));
    	sonar.setPositionX(sonarPosX);
    	sonar.setPositionY(sonarPosY);
    	sonar.setOrientation(angle);
    	//Scan the sonar
    	return sonar.scan(this);
    	
    }
    
    
    public void drive(double newVelocity) {
    	this.velocity = newVelocity * 100;
    	drive = true;
    }
    
    public void disableDrive() {
    	this.drive = false;
    }
    
    
    
}
