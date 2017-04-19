package testLykkeMenu;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;


public class Controller{
	
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
	@FXML
	private ImageView ivNewGame;
	@FXML
	private ImageView ivLoadGame;
	

	Stage window;
	Scene sceneSetting;
	Main main;
	ChangeScene changescene;
	
	
	 public void initialize() throws Exception {
	        changescene = new ChangeScene();
	    }



//	public void btnNewGameClicked() throws Exception{
//		window = new Stage();
//		Parent rootNewGame = FXMLLoader.load(getClass().getResource("NewGameMenu.fxml"));
////		sceneNewGame = new Scene(rootNewGame, 1151, 694);
//		sceneNewGame = new Scene(rootNewGame);
//		window.setScene(sceneNewGame);
//		window.show();
//		closeWindow();
//		System.out.println("Clicked button");
//	}
	
	public void NewGameClicked() throws Exception{
		Stage stage = (Stage) ivNewGame.getScene().getWindow();
        changescene.switchScene(stage);
	
	}
	
	public void LoadGameClicked(){
		System.out.println("LoadGame");
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
