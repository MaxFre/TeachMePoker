package testLykkeMenu;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	Stage window;
	Scene sceneMenu;
	Scene sceneNewGame;

	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		Pane rootMenu = FXMLLoader.load(getClass().getResource("FirstMenu.fxml"));
		window.setTitle("TeachMePoker - MainMenu");
		window.setResizable(true);
		sceneMenu = new Scene(rootMenu, 1366, 768);

		window.setScene(sceneMenu);
		window.centerOnScreen();
		window.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
