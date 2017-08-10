package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * 
 * @author Robert Sadler
 *
 */

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));	
			primaryStage.setTitle("Simulator v1.0");
			primaryStage.setScene(new Scene(root,1280,720));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		/*
		Terrain test = new Terrain(10,10, 10);
		test.generateRandomTerrain();
		test.printTerrain();
		*/
		launch(args);
	}
}
