package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
/**
 * 
 * @author Robert Sadler
 * Controlling Class for the whole Simulation. Responsible for drawing graphics, checking collisions and then calling the Physics engine.
 */

public class Control implements Initializable{

	@FXML private Canvas zoomedMap ; 
    private GraphicsContext gc ;
    
    private Terrain terrain; //The Map Terrain
    private ArrayList<Obstacle> obstacles; //All obstacles , moveable and nonemoveable
    
    private Driveable robot;
    
	/**
	 * Launch of the program.
	 * Currently set at generate a random Terrain.
	 * 
	 * In the future, we will change this that we load and save terrains.
	 * 
	 * One feature I thought we could do is a random terrain generation based
	 * on perlin noise. So we get "realistic" random terrains
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		gc = zoomedMap.getGraphicsContext2D();
		obstacles = new ArrayList<Obstacle>();
		terrain = new Terrain(50, 50, 100); //50 by 50 m terrain. 100 pixels, 1 pixel = 1 cm.
		terrain.generateRandomTerrain();
		//Load the Images		
		this.mainSimulationLoop();
    }
	
	/**
     * The main simulation loop. 
     * Responsible for drawing, checking physics, and then asking AI agents for their decision
     */
	public void mainSimulationLoop() {
		/*
		 * This is from the google doc.
		 * 
		 * 
			while(experimentNotOver() && !IterationLimit() && !PausedEnded()) {
				propogatePhysics();
				displayGUI(); // update the GUI
	
				Foreach (av in avatar) {  // everyone thinks at the same time
					av.sendNN(terrain.getSignals());
					av.think();
				}
	
				Foreach (av in avatar) { // everyone moves at the same time
					while(command == NULL && attempt<limit) command = av.getCommand();
						If (command != NULL) {
							terrain.execute(command, av);
							recordStateChange(outputFileName);
						}
				}
			}
		 */
				
		
		Image Robot = new Image(getClass().getResource("Robot.png").toExternalForm());
		//Creates the new thread
		//Note to self, Don't use "this." as this is not the class is anymore, but the seperate thread
		//i.e. You can do drawTerrain(); but not this.drawTerrain();
		robot = new Driveable(Robot, 0, 0, 0);
		obstacles.add(robot);

		new AnimationTimer() {
			long lastNanoTime = System.nanoTime();
			@Override
			public void handle(long currentNanoTime) {
				// Timing. The difference between the last frame and the current frame, in seconds
				double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
				lastNanoTime = currentNanoTime;

				drawTerrain(); //Draw the Terrain
				checkCollisions(); //Check the collisions
				updatePositions(elapsedTime);
				drawObstacles(); 	//Then Draw the position of all obstacles
			}			
		}.start();
	}
	
	/**
	 * Draw the Terrain on the canvas
	 * Reads in each node of the terrain, and reads the value for it's elevation.
	 * Blue | Yellow | And then Green's getting darker
	 * Water|  Sand  | Various degrees of Grass
	 */
	public void drawTerrain() {
		for(int i = 0; i < this.terrain.getGridX(); i++) {
			for(int j =0; j < this.terrain.getGridY(); j++) {
				switch(this.terrain.getNode(i, j).getTerrainheight()) {
				case 0:
					gc.setFill(Color.CORNFLOWERBLUE);
					break;
				case 1:
					gc.setFill(Color.LIGHTGOLDENRODYELLOW);
					break;
				case 2: 
					gc.setFill(Color.LIGHTGREEN);
					break;
				case 3:
					gc.setFill(Color.GREENYELLOW);
					break;
				case 4:
					gc.setFill(Color.GREEN);
					break;
				case 5:
					gc.setFill(Color.DARKGREEN);
					break;
					default:
						gc.setFill(Color.WHITE);
						break;
				}
				float diameter = terrain.getNodeDiameter();
				gc.fillRect(i * diameter, j * diameter , diameter,diameter );
			}
		}	
	}
	
	/**
	 * Draw all of the obstacles onto the terrain
	 */
	public void drawObstacles() {
		//For Each Obstacle in the list, render it
		for(Obstacle o : obstacles) {
			o.render(gc);
		}
	}
	
	/**
	 * Check if any collision has occurred
	 */
	public void checkCollisions() {
		//For each Obstacle o in the list, check every other object that it is not o, l to see if it intersects
		for(Obstacle o: obstacles) {
			for(Obstacle l: obstacles) {
				if(!o.equals(l)) {
					if(o.intersects(l)) {
						System.out.println("COLLISION");
						//Collision Handle
						//Need a check to make sure we don't have this transative relation
						//I.e. A Collides B , Does not then check B Collides A
					}
				}
			}
		}
		
	}
	
	/**
	 * Calculate the position of the Obstacles
	 */
	public void updatePositions(double time) {
		for(Obstacle o : obstacles) {
			o.update(time);;
		}
	}
	
	/**
	 * 
	 * @param e - The key that has been pressed
	 * This is purely for testing purposes to have a robot that can be driven.
	 * 
	 * W - Increase Velocity
	 * A - Turn to the Left
	 * S - Decrease Velocity
	 * D - Turn to the right
	 * 
	 */
	public void buttonPressed(KeyEvent e) {
		//Forward
		if(e.getCode().toString().equals("W")) {
			robot.setVelocity(+ 1);
		}
		
		//Left
		if(e.getCode().toString().equals("A")){
			robot.setRotation(robot.getRotation() - 3);
		}
		
		//Backwards
		if(e.getCode().toString().equals("S")) {
			robot.setVelocity(- 1);
		}
		
		//Right
		if(e.getCode().toString().equals("D")){
			robot.setRotation(robot.getRotation() + 3);
		}
	}
	


}
