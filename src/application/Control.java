package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
/**
 * 
 * @author Robert Sadler
 *
 */

public class Control implements Initializable{

	@FXML private Canvas img ;
    private GraphicsContext gc ;
    
    private Terrain terrain; //The Map Terrain
    
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
		gc = img.getGraphicsContext2D();
		terrain = new Terrain(100, 100, 50);
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
		
		
		//Creates the new thread
		//Note to self, Don't use "this." as this is not the class is anymore, but the seperate thread
		//i.e. You can do drawTerrain(); but not this.drawTerrain();
		new AnimationTimer() {
			int i = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				drawTerrain();
				gc.setFill(Color.HOTPINK);
				gc.fillRect(i, 40 , 100,100);
				i++;
			}			
		}.start();
	}
	
	/**
	 * Draw the Terrain on the canvas
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
				float radius = terrain.getGridRadius();
				gc.fillRect(i * radius, j * radius , radius,radius );
			}
		}
		
	}


}
