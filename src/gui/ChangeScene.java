package gui;

import java.io.IOException;

import controller.SPController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Class that handles the switching of scenes in the main window and the gui controll class that manages that scene. 
 * @author Lykke Levin & Rikard Almgren
 * @version 1.0
 *
 */

public class ChangeScene {

	Scene sceneNewGame;
	Scene sceneGameState;
	Scene sceneMenu;
	private FMController fmController;
	private SettingsController settingsController;
	private GameController gameController;

	public void prepGame() throws IOException, InstantiationException, IllegalAccessException {
		Sound.class.newInstance().playBackgroundMusic();
		FXMLLoader loaderFM = new FXMLLoader(FMController.class.getResource("FirstMenu.fxml"));
		Pane rootMenu = loaderFM.load();
		fmController = loaderFM.getController();
		FXMLLoader loaderSS = new FXMLLoader(SettingsController.class.getResource("GameSettingMenu.fxml"));
		Pane rootNewGame = loaderSS.load();
		settingsController = loaderSS.getController();

		FXMLLoader loaderGS = new FXMLLoader(GameController.class.getResource("GameState.fxml"));
		Pane root2 = loaderGS.load();
		gameController = loaderGS.getController();

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
		return sceneMenu;
	}

	public void setSPController(SPController spController) {
		gameController.setSPController(spController);
	}

}
