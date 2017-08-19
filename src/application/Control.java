package application;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private Physics physics;
    
    
    private Boolean isRunning;
    
    //Entity Statistics
    private Obstacle selectedEntity;
    @FXML private TextField xPos;
    @FXML private TextField yPos;
    @FXML private TextField velocity;
    @FXML private TextField elevation;
    @FXML private TextField surface;
    
    //Simulation Statistics
    @FXML private TextField fps;
    @FXML private TextField wind;
    @FXML private TextField windDirection;
    @FXML private TextField time;
    @FXML private TextField sun;
    @FXML private TextField weather;
    @FXML private TextField entity;
    
    //Simulation Control Buttons
    @FXML private Button start;
    @FXML private Button pause;
    @FXML private Button end;
       
    //Terrain Configuration
    @FXML private Tab terrainConfig;
    @FXML private Button robotButton;
    @FXML private Button moveableBoxButton;
    @FXML private Button fixedObstacleButton;
    @FXML private Button removeObstacleButton;
    @FXML private Button waterButton;
    @FXML private Button concreteButton;
    @FXML private Button sandButton;
    @FXML private Button carpetButton;
    
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
		physics = new Physics(); //Initialise the physic's engine
		terrain.generateRandomTerrain();
		isRunning = false;
		pause.setDisable(true);
		end.setDisable(true);
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
		
		zoomedMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent me) {
	        	if(terrainConfig.isSelected()) {
	        		//We are drawing on the terrain, and need to alter the terrain
	        		
	        	} else {
	        		//We are in config mode. So we are looking to see if the mouse has clicked on an entity
		            for(Obstacle o: obstacles) {
		            	if(o.selectedEntity(me.getX(),me.getY())) {
		            		selectedEntity = o;
		            		break;
		            	}
		            }
	        	}
	        }
	    });
				
		
		Image Robot = new Image(getClass().getResource("Robot.png").toExternalForm());
		Image Box = new Image(getClass().getResource("Box.png").toExternalForm());
		//Creates the new thread
		//Note to self, Don't use "this." as this is not the class is anymore, but the seperate thread
		//i.e. You can do drawTerrain(); but not this.drawTerrain();
		robot = new Driveable(Robot, 0, 0, 0);
		obstacles.add(robot);
		Moveable boxxy = new Moveable(Box,500,500,0);
		obstacles.add(boxxy);
		
		Moveable boxxy2 = new Moveable(Box,300,300,0);
		obstacles.add(boxxy2);
		
		Moveable boxxy3 = new Moveable(Box,100,100,0);
		obstacles.add(boxxy3);
		
		Moveable boxxy4 = new Moveable(Box,200,200,0);
		obstacles.add(boxxy4);
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		new AnimationTimer() {
			long lastNanoTime = System.nanoTime();
				@Override
				public void handle(long currentNanoTime) {
					// Timing. The difference between the last frame and the current frame, in seconds
					if(isRunning) {
					double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
					lastNanoTime = currentNanoTime;
					
					//If we are in the terrainConfig, pause the simulation
					if(terrainConfig.isSelected()) {
						pauseSimulation();
					}			
					
					//Entity Stats
					if(selectedEntity != null) {
						xPos.setText(selectedEntity.getXPosition() + "");					
						yPos.setText(selectedEntity.getYPosition() + "");
						//Pyth. for Velocity X + Velocity Y for the total velocity
						/** THIS MAY BE WRONG **/
						double totalVelocity = Math.sqrt((selectedEntity.getVelocityX() * selectedEntity.getVelocityX()) + (selectedEntity.getVelocityY() * selectedEntity.getVelocityY()));
						velocity.setText(df.format(totalVelocity) + " m/s");					
					}
					
					//Simulation Stats Print
					double currentFps = 1/elapsedTime;
					fps.setText(df.format(currentFps));
					entity.setText(obstacles.size() + "");
					
					//Main Loop
					drawTerrain(); //Draw the Terrain
					checkCollisions(); //Check the collisions
					updatePositions(elapsedTime);
					drawObstacles(); 	//Then Draw the position of all obstacles
				}	
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
				if(!o.equals(l) ) {
					if(o.intersects(l)) {
						//System.out.println("COLLISION");
						//Collision Handle
						//Need a check to make sure we don't have this transative relation
						//I.e. A Collides B , Does not then check B Collides A
						physics.testPushBox(o, l);
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
	
	
	/**
	 * Starts the simulation running
	 */
	public void startSimulation() {
		start.setDisable(true);
		pause.setDisable(false);
		end.setDisable(false);
		this.isRunning = true;
	}
	
	/**
	 * Pauses the simulation
	 */
	public void pauseSimulation() {
		start.setDisable(false);
		pause.setDisable(true);
		end.setDisable(false);
		this.isRunning = false;
	}
	
	/**
	 * End the simulation
	 */
	public void endSimulation() {
		start.setDisable(false);
		pause.setDisable(false);
		end.setDisable(true);
		this.isRunning = false;
	}
	


}
