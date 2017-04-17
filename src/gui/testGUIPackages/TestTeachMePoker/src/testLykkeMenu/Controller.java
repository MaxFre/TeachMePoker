package testLykkeMenu;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Controller {
	private Button btnNewGame;
	private Main main;
	Stage window;
	Scene sceneNewGame;
	
	
//	public Controller(){
//		this.main = main;
//	}


	public void btnNewGameClicked() throws IOException{
		window = new Stage();
		Parent rootNewGame = FXMLLoader.load(getClass().getResource("NewGameMenu.fxml"));
		sceneNewGame = new Scene(rootNewGame, 1100, 640);
		window.setScene(sceneNewGame);
		window.show();
		System.out.println("Clicked button");
	}
}
