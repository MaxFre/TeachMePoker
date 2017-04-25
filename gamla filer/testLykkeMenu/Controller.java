package testLykkeMenu;

import java.io.IOException;

import controller.SPController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class Controller {
  private SPController spController = new SPController();
  private ConfirmBox confirmBox;

  @FXML
  private Button btnNewGame;
  @FXML
  private TextField tfNameInput;
  @FXML
  private Slider aiSlider;
  @FXML
  private Slider potSlider;
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

  ChangeScene changeScene;
  Sound sound;
  

  public void initialize() throws Exception {
    changeScene = new ChangeScene();
  }

  public void NewGameClicked() throws Exception {
    changeScene.switchScenetoSetting();

  }

  public void LoadGameClicked() {
    System.out.println("LoadGame");
    sound = new Sound();
    sound.testPlaySound();
 
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
	  Double val = potSlider.getValue();
	  int value = val.intValue();
    System.out.println("Slider moved to " + value);
  }

  public void cbOnClicked() {
	 
    if (cbOff.isSelected()) {
      cbOff.setSelected(false);
      cbOff.setDisable(false);
      cbOn.setSelected(true);
      cbOn.setDisable(true);

    }

    System.out.println("Tutorial On");
  }

  public void cbOffClicked() {
    if (cbOn.isSelected()) {
      cbOn.setSelected(false);
      cbOn.setDisable(false);
      cbOff.setSelected(true);
      cbOff.setDisable(true);

    }
    System.out.println("Tutorial Off");
  }

  public void startGame() throws IOException {
    if (!tfNameInput.getText().isEmpty()) {
        changeScene.switchScenetoGame();
        
    	
//      spcontroller.startGame((int) aiSlider.getValue(), (int) potSlider.getValue(),
//          tfNameInput.getText());

      if (cbOn.isSelected()) {
        System.out.println("Tutorial ska visas");
      }
      System.out.println("Spel startas!");
    } else if (tfNameInput.getText().isEmpty()) {
    	confirmBox = new ConfirmBox();
		boolean result = confirmBox.display("Varning", "Du måste välja ett användarnamn för att starta spelet");
    	
      System.out.println("Du måste välja ett användarnamn");
      System.out.println(result);
    }
  }

  public void ivQuestionAiHoovered() {
    lblAiInfo.setVisible(true);
    ivQuestionAi.setOnMouseExited(e -> lblAiInfo.setVisible(false));

  }

  public void ivQuestionPotHoovered() {
    lblPotInfo.setVisible(true);
    ivQuestionPot.setOnMouseExited(e -> lblPotInfo.setVisible(false));

  }

  public void ivQuestionTutorialHoovered() {
    lblTutorialInfo.setVisible(true);
    ivQuestionTutorial.setOnMouseExited(e -> lblTutorialInfo.setVisible(false));
  }



}
