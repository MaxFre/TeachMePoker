package gui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main method to start the program.
 * 
 * @author Lykke Levin
 * @version 1.0
 *
 */

public class Main extends Application {
  public static Stage window;
  public ChangeScene cs = new ChangeScene();



  public void start(Stage primaryStage) throws Exception {
    cs.prepGame();

    window = primaryStage;
    window.setTitle("TeachMePooker");
    window.setResizable(true);
    window.setOnCloseRequest(e -> closeProgram());

    window.setScene(cs.firstScene());
    window.show();



  }

  public static void main(String[] args) {
    launch(args);

  }

  public void closeProgram() {
    window.close();
  }
}
