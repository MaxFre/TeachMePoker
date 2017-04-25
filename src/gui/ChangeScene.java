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



  public void prepGame() throws IOException {
    System.out.println("preLoad: " + fmController);
    FXMLLoader loaderFM = new FXMLLoader(FMController.class.getResource("FirstMenu.fxml"));
    Pane rootMenu = loaderFM.load();
    fmController = loaderFM.getController();
    FXMLLoader loaderSS =
        new FXMLLoader(SettingsController.class.getResource("GameSettingMenu.fxml"));
    Pane rootNewGame = loaderSS.load();
    settingsController = loaderSS.getController();

    FXMLLoader loaderGS = new FXMLLoader(GameController.class.getResource("GameState.fxml"));
    Pane root2 = loaderGS.load();
    gameController = loaderGS.getController();
    System.out.println(gameController);



    sceneMenu = new Scene(rootMenu);
    sceneNewGame = new Scene(rootNewGame);
    sceneGameState = new Scene(root2);

    gameController.setChangeScene(this);
    settingsController.setChangeScene(this);
    fmController.setChangeScene(this);


  }


  public void switchScenetoSetting() throws IOException {
    Main.window.setScene(sceneNewGame);


  }


  public void switchScenetoGame() throws IOException {

    gameController.setUsername(settingsController.getName());
    Main.window.setScene(sceneGameState);



  }


  public Scene firstScene() throws IOException {
    System.out.println("CS: " + this);
    System.out.println("CS: " + settingsController);
    System.out.println("CS: " + gameController);
    System.out.println("CS: " + fmController);
    return sceneMenu;
  }

}
