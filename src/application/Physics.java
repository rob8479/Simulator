package application;


/**
 * 
 * @author Robert Sadler
 * The Physics for the simulator is all controlled within in.
 * 
 * The Forces implemented here are :
 * 
 *	Collisions
 *	Checking valid movements - i.e. You can only move from a terrain tile that is one above or below that terrain.
 *	Friction
 *  Get Schwifty 
 *		
 */
public class Physics {

	public Physics() {
		// TODO Auto-generated constructor stub
	}
	

	public void testPushBox(Obstacle a, Obstacle b) {
		b.setVelocityX(a.getVelocityX());
		b.setVelocityY(a.getVelocityY());
	}
	


	
}
