package testLykkeMenu;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
	Stage window; 
	Scene sceneMenu;
	Scene sceneNewGame;

	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		
		Parent rootMenu = FXMLLoader.load(getClass().getResource("FirstMenu.fxml"));

		
		window.setTitle("TeachMePoker - MainMenu");
		window.centerOnScreen();
//		window.setFullScreen(true);
		sceneMenu = new Scene(rootMenu, 1100, 640);
	
//		window.sizeToScene();
		window.setScene(sceneMenu);

		window.show();
	}

	
	public static void main(String[] args){
		launch(args);
	}


}
