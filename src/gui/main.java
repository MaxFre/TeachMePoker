package gui;

import java.nio.file.Paths;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Main extends Application {
  public static Stage window;
  ChangeScene cs = new ChangeScene();

  public void start(Stage primaryStage) throws Exception {
    cs.prepGame();
    
    window = primaryStage;
    window.setTitle("TeachMePoker");
    window.setResizable(true);
    
    window.setScene(cs.firstScene());
    window.show();
    
   
  }

  public static void main(String[] args) {
	    Media m = new Media(Paths.get("resources/sounds/cool_struttin'.mp3").toUri().toString());
	    new MediaPlayer(m).play();
	    
	    
	    launch(args);

  }
}
