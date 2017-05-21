package gui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RuleController{

	public Stage window = new Stage();
	
	public void rules() throws IOException{
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Regler");
		window.setWidth(600);
		window.setHeight(400);
		window.setOnCloseRequest(e -> closeProgram());
		Pane mainPane = (Pane) FXMLLoader.load(RuleController.class.getResource("/Rules.fxml"));
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
	}
	public void closeProgram() {
		window.close();
	}

}
