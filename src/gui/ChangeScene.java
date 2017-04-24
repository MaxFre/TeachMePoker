package gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ChangeScene {
	
	Scene sceneNewGame;
	Scene sceneGameState;
	Scene sceneMenu;

	public void switchScenetoSetting() throws IOException{
		Pane rootNewGame = FXMLLoader.load(getClass().getResource("GameSettingMenu.fxml"));
		sceneNewGame = new Scene(rootNewGame);
		Main.window.setScene(sceneNewGame);
	
	} 
        
	public void switchScenetoGame() throws IOException{
		Pane root2 = FXMLLoader.load(getClass().getResource("GameState.fxml"));
		sceneGameState = new Scene(root2);
		Main.window.setScene(sceneGameState);
	}


	public Scene firstScene() throws IOException{
		Pane rootMenu = FXMLLoader.load(getClass().getResource("FirstMenu.fxml"));
		 return sceneMenu = new Scene(rootMenu);
	}

}
