package gui;

import java.io.IOException;


import filehandler.FileHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller for FXML-doc FirstMenu.fxml. 
 * @author Lykke Levin
 * @version 1.0
 *
 */

public class FMController {

//	private FileHandler fileHandler;
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

	}

	public void NewGameClicked() throws Exception {

		changeScene.switchScenetoSetting();

	}

	/*LoadGameClicked() is currently a non-functional method. For now, it only tests effect sounds.
	 * It will later be used to load saved game files.  
	 */
	public void LoadGameClicked() throws IOException {
		// fileHandler = new FileHandler();
		// String pot = fileHandler.loadPot();
		// System.out.println(fileHandler.loadPot());

		System.out.println("LoadGame");
		sound = new Sound();
		sound.shuffleSound();

	}

}
