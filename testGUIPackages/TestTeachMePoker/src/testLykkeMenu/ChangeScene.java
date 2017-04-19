package testLykkeMenu;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChangeScene {

	public void switchScene(Stage window) throws IOException{
		Pane rootNewGame = rootNewGame = FXMLLoader.load(getClass().getResource("NewGameMenu.fxml"));
		Scene sceneNewGame = new Scene(rootNewGame, 1366, 768);
		window.setScene(sceneNewGame);
        window.show();

	}

	
}
