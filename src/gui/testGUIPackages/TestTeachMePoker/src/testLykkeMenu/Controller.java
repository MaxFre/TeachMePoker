package testLykkeMenu;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Controller {
	
	@FXML
	private Button btnNewGame;
	@FXML
	private TextField tfNameInput;
	@FXML
	private Slider aiSlider;
	@FXML
	private Slider potSlider;
	@FXML 
	private ToggleButton tbShowTutorial;
	

	Stage window;
	Scene sceneNewGame;
	
	
//	public Controller(){
//		this.main = main;
//	}


	public void btnNewGameClicked() throws IOException{
		window = new Stage();
		Parent rootNewGame = FXMLLoader.load(getClass().getResource("NewGameMenu.fxml"));
		sceneNewGame = new Scene(rootNewGame, 1151, 694);
		window.setScene(sceneNewGame);
		window.show();
		System.out.println("Clicked button");
	}
	
	public void btnLoadGameClicked(){
		
	}
	
	public void tfNameInputChange(){
		System.out.println("cool");
	}
	
	public void aiSliderChange(){
		System.out.println("Slider moved");
	}
	
	public void potSliderChange(){
		System.out.println("Slider moved");
	}
	
	public void tbChanged(){
		System.out.println("Toggle button changed");
	}
}
