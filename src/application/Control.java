package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		gc = img.getGraphicsContext2D();
		terrain = new Terrain(100, 100, 50);
		terrain.generateRandomTerrain();
		this.drawTerrain();
	}
	/**
	 * Draw the Terrain on the canvas
	 */
	public void drawTerrain() {
		for(int i = 0; i < this.terrain.getGridX(); i++) {
			for(int j =0; j < this.terrain.getGridY(); j++) {
				switch(this.terrain.getNode(i, j).getTerrainheight()) {
				case 0:
					gc.setFill(Color.BLUE);
					break;
				case 1:
					gc.setFill(Color.YELLOW);
					break;
				case 2: 
					gc.setFill(Color.GREEN);
					break;
				case 3:
					gc.setFill(Color.RED);
					break;
				case 4:
					gc.setFill(Color.BLACK);
					break;
				case 5:
					gc.setFill(Color.PINK);
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
