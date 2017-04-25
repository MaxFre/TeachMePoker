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
  FMController fmController;
  SettingsController settingsController;
  GameController gameController;


  public void switchScenetoSetting() throws IOException {

    FXMLLoader loader =
        new FXMLLoader(SettingsController.class.getResource("GameSettingMenu.fxml"));
    Pane rootNewGame = loader.load();
    settingsController = loader.getController();
    sceneNewGame = new Scene(rootNewGame);
    settingsController.setChangeScene(this);
    Main.window.setScene(sceneNewGame);


  }


  public void switchScenetoGame() throws IOException {

    FXMLLoader loader = new FXMLLoader(GameController.class.getResource("GameState.fxml"));

    Pane root2 = loader.load();
    sceneGameState = new Scene(root2);
    gameController = loader.getController();
    gameController.setUsername(settingsController.getName());
    Main.window.setScene(sceneGameState);
    gameController.setChangeScene(this);

  }


  public Scene firstScene() throws IOException {

    FXMLLoader loader = new FXMLLoader(FMController.class.getResource("FirstMenu.fxml"));
    Pane rootMenu = loader.load();
    sceneMenu = new Scene(rootMenu);
    fmController = (FMController) loader.getController();
    fmController.setChangeScene(this);

    return sceneMenu;
  }

}
