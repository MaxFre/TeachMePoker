<<<<<<< HEAD
package gui;
=======
package javaFxTotarial;
>>>>>>> 9a43ba73166b936b25ad8d507ee7e570c345ef39

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {

//	public void start(Stage primary) throws Exception{
//		Parent root = FXMLLoader.load(getClass().getResource("PokerDemo1.fxml"));
//		primary.setTitle("TeachMePoker");
//		primary.setScene(new Scene(root,800,600));
//		primary.show();
		
		public void start(Stage primary) throws Exception{
			Parent root = FXMLLoader.load(getClass().getResource("PokerDemo2.fxml"));
			primary.setTitle("TeachMePoker");
			primary.setScene(new Scene(root,800,600));
			primary.show();
	}
	public static void main(String[] args){
		launch(args);
	}
}
