package gui;

import java.io.IOException;
import controller.SPController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * Class that handles the switching of scenes in the main window and the gui controll class that
 * manages that scene.
 * 
 * @author Lykke Levin & Rikard Almgren
 * @version 1.0
 *
 */

public class ChangeScene {
  
  Pane rootMenu;
  Pane rootNewGame;
  Pane root2;
  Scene bestScene;
  private FMController fmController;
  private SettingsController settingsController;
  private GameController gameController;

  public void prepGame() throws IOException, InstantiationException, IllegalAccessException {
    Sound.class.newInstance().playBackgroundMusic();
    FXMLLoader loaderFM = new FXMLLoader(FMController.class.getResource("/FirstMenu.fxml"));
    this.rootMenu = loaderFM.load();
    this.fmController = loaderFM.getController();
    FXMLLoader loaderSS =
        new FXMLLoader(SettingsController.class.getResource("/GameSettingMenu.fxml"));
    this.rootNewGame = loaderSS.load();
    this.settingsController = loaderSS.getController();

    FXMLLoader loaderGS = new FXMLLoader(GameController.class.getResource("/GameState.fxml"));
    this.root2 = loaderGS.load();
    this.gameController = loaderGS.getController();

    this.bestScene = new Scene(rootMenu);

    gameController.setChangeScene(this);
    settingsController.setChangeScene(this);
    fmController.setChangeScene(this);

  }

  public void switchScenetoSetting() throws IOException {
    Main.window.getScene().setRoot(rootNewGame);
  }

  public void switchScenetoGame() throws IOException {
    
    Main.window.getScene().setRoot(root2);
    gameController.setUsername(settingsController.getName());
    Sound.mp.setVolume(0.3);

  }

  public Scene firstScene() throws IOException {
    return bestScene;
  }

  public void switchToMainMenu() throws IOException, InstantiationException, IllegalAccessException {
    prepGame();
    Main.window.setScene(bestScene);
  }

  public void setSPController(SPController spController) {
    gameController.setSPController(spController);
  }

}
