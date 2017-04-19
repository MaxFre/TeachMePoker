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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
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
	@FXML
	private ImageView ivNewGame;
	@FXML
	private ImageView ivLoadGame;
	@FXML
	private CheckBox cbOn;
	@FXML
	private CheckBox cbOff;
	@FXML
	private ImageView ivStartGame;
	@FXML
	private ImageView ivQuestionAi;
	@FXML
	private ImageView ivQuestionPot;
	@FXML
	private ImageView ivQuestionTutorial;

	Stage window;
	Scene sceneSetting;
	Main main;
	ChangeScene changescene;

	public void initialize() throws Exception {
		changescene = new ChangeScene();
	}

	public void NewGameClicked() throws Exception {
		Stage stage = (Stage) ivNewGame.getScene().getWindow();
		changescene.switchScene(stage);

	}

	public void LoadGameClicked() {
		System.out.println("LoadGame");
	}

	public void tfNameInputChange() {
		String name = tfNameInput.getText();
		System.out.println(name);
	}

	public void aiSliderChange() {
		Double value = aiSlider.getValue();
		System.out.println("Slider moved" + value);
	}

	public void potSliderChange() {
		System.out.println("Slider moved");
	}

	public void cbOnClicked() {
		System.out.println("Tutorial On");
	}

	public void cbOffClicked() {
		System.out.println("Tutorial Off");
	}

	public void startGame() {
		System.out.println("Spel startas!");
	}
	
	public void ivQuestionAiHoovered(){
		System.out.println("fet");
	}
	
	public void ivQuestionPotHoovered(){
		
	}
	
	public void ivQuestionTutorialHoovered(){
		
	}
}
