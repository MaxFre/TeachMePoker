package testLykkeMenu;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChangeScene {
	
	Scene sceneNewGame;
	Scene sceneGameState;

	public void switchScene(Stage window) throws IOException{
		Pane rootNewGame = FXMLLoader.load(getClass().getResource("GameSettingMenu.fxml"));
		window.setTitle("TeachMePoker - inställningar");
		window.setResizable(true);
		sceneNewGame = new Scene(rootNewGame, 1366, 768);
		window.setScene(sceneNewGame);
		window.centerOnScreen();
        window.show();
	} 
        
	public void switchToGame(Stage window) throws IOException{
    	Pane root2 = FXMLLoader.load(getClass().getResource("PokerPlan2.fxml"));
		window.setTitle("TeachMePoker");
	//	primary.setScene(new Scene(root,800,600));
		sceneGameState = new Scene(root2, 1366, 768);
		window.setScene(sceneGameState);
		window.centerOnScreen();
		window.show();
	}

	
	

	
}
