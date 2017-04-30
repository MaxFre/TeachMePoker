package gui;

import java.nio.file.Paths;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Main extends Application {
  public static Stage window;
  public ChangeScene cs = new ChangeScene();



  public void start(Stage primaryStage) throws Exception {
    cs.prepGame();

    window = primaryStage;
    window.setTitle("TeachMePoker");
    window.setResizable(true);
    window.setOnCloseRequest(e -> closeProgram());
    
    window.setScene(cs.firstScene());
    window.show();
  
    
   
  }

  public static void main(String[] args) {
	  launch(args);

  }
  
  public void closeProgram(){
		window.close();
	}
}
