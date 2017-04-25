package testLykkeMenu;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChangeScene {
	
	Scene sceneNewGame;

	public void switchScene(Stage window) throws IOException{
		Pane rootNewGame = FXMLLoader.load(getClass().getResource("GameSettingMenu.fxml"));
		window.setTitle("TeachMePoker - inställningar");
		window.setResizable(true);
		sceneNewGame = new Scene(rootNewGame, 1366, 768);
		window.setScene(sceneNewGame);
		window.centerOnScreen();
        window.show();

	}

	
}
