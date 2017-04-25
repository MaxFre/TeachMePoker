package testLykkeMenu;

import javafx.fxml.FXML;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
	@FXML
	private Label lblAiInfo;
	@FXML
	private Label lblPotInfo;
	@FXML
	private Label lblTutorialInfo;

	Stage window;
	Scene sceneSetting;
	Main main;
	ChangeScene changeScene;

	public void initialize() throws Exception {
		changeScene = new ChangeScene();
	}

	public void NewGameClicked() throws Exception {
		Stage stage = (Stage) ivNewGame.getScene().getWindow();
		changeScene.switchScene(stage);

	}

	public void LoadGameClicked() {
		System.out.println("LoadGame");
	}

	public void tfNameInputChange() {
		String name = tfNameInput.getText();
		System.out.println(name);
	}

	public void aiSliderChange() {
		Double val = aiSlider.getValue();
		Integer value = val.intValue();
		System.out.println("Slider moved to " + value);
	}

	public void potSliderChange() {
		System.out.println("Slider moved");
	}

	public void cbOnClicked() {
		if (cbOff.isSelected()) {
			cbOff.setSelected(false);
			cbOn.setSelected(true);

		}

		System.out.println("Tutorial On");
	}

	public void cbOffClicked() {
		if (cbOn.isSelected()) {
			cbOn.setSelected(false);
			cbOff.setSelected(true);

		}
		System.out.println("Tutorial Off");
	}

	public void startGame() {
		if(!tfNameInput.getText().isEmpty()){
			
		
		if (cbOn.isSelected()) {
			System.out.println("Tutorial ska visas");
		}
		System.out.println("Spel startas!");
		}else if(tfNameInput.getText().isEmpty()){
		System.out.println("Du måste välja ett användarnamn");
	}
	}

	public void ivQuestionAiHoovered() {
		lblAiInfo.setVisible(true);
		ivQuestionAi.setOnMouseExited(e-> lblAiInfo.setVisible(false));
		
	}

	public void ivQuestionPotHoovered() {
		lblPotInfo.setVisible(true);
		ivQuestionPot.setOnMouseExited(e-> lblPotInfo.setVisible(false));

	}

	public void ivQuestionTutorialHoovered() {
		lblTutorialInfo.setVisible(true);
		ivQuestionTutorial.setOnMouseExited(e-> lblTutorialInfo.setVisible(false));
	}
	
	

}
