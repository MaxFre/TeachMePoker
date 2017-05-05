package gui;

import java.io.IOException;
import controller.SPController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller for FXML-doc GameSettingMenu.fxml
 * @author Lykke Levin
 * @version 1.0
 *
 */
public class SettingsController {
  private SPController spController;

  private ChangeScene changeScene;
  private Sound sound;
  private ConfirmBox confirmBox;
  private String name;
  private int aiValue;
  private int potValue;

  @FXML
  private TextField tfNameInput;
  @FXML
  private Slider aiSlider;
  @FXML
  private Slider potSlider;
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
  @FXML
  private ImageView ivBack;


  public void initialize() throws Exception {
	  
  }

  public void tfNameInputChange() {
    this.name = tfNameInput.getText();
  }

  public void setChangeScene(ChangeScene sceneChanger) {

    this.changeScene = sceneChanger;
  }

  public void aiSliderChange() {
    Double val = aiSlider.getValue();
    aiValue = val.intValue();

    System.out.println("Slider moved to " + aiValue);

  }

  public void potSliderChange() {

    Double val = potSlider.getValue();
    potValue = val.intValue();

    System.out.println("Slider moved to " + potValue);
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

    potSliderChange();
    aiSliderChange();
    if (!tfNameInput.getText().isEmpty()) {
      name = tfNameInput.getText();
      spController = new SPController();
      changeScene.setSPController(spController);
      


      if (cbOn.isSelected()) {
        System.out.println("Tutorial ska visas");
      }
      changeScene.switchScenetoGame();
      System.out.println("Spel startas!");
      spController.startGame(aiValue, potValue, name);
    } else if (tfNameInput.getText().isEmpty()) {
      confirmBox = new ConfirmBox();
      boolean result =
          confirmBox.display("Varning", "Du måste välja ett användarnamn för att starta spelet");

      System.out.println("Du måste välja ett användarnamn");
      System.out.println(result);

    }

  }

  public void ivQuestionAiHovered() {

    lblAiInfo.setVisible(true);
    ivQuestionAi.setOnMouseExited(e -> lblAiInfo.setVisible(false));

  }

  public void ivQuestionPotHovered() {

    lblPotInfo.setVisible(true);
    ivQuestionPot.setOnMouseExited(e -> lblPotInfo.setVisible(false));

  }

  public void ivQuestionTutorialHovered() {

    lblTutorialInfo.setVisible(true);
    ivQuestionTutorial.setOnMouseExited(e -> lblTutorialInfo.setVisible(false));
  }

  public void back() {
    Main.window.setScene(changeScene.sceneMenu);
  }

  public String getName() {
    return name;
  }

}
