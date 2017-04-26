package gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class FMController {

  private ChangeScene changeScene;
  private Sound sound;
  @FXML
  private TextField tfNameInput;
  @FXML
  private ImageView ivNewGame;
  @FXML
  private ImageView ivLoadGame;

  public void initialize() throws Exception {

  }

  public void setChangeScene(ChangeScene sceneChanger) {
    this.changeScene = sceneChanger;
    System.out.println("SCS: " + changeScene);
    System.out.println("SCS: " + this);

  }

  public void NewGameClicked() throws Exception {
    System.out.println("NG: " + changeScene);
    System.out.println("NG: " + this);

    changeScene.switchScenetoSetting();

  }


  public void LoadGameClicked() {

    System.out.println("LoadGame");
    sound = new Sound();
    sound.testPlaySound();

  }

}
