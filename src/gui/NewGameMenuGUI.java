package teachMePoker;

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Slider;

public class NewGameMenuGUI extends Application {
	private Stage window;
	
	private Label lblName;
	private Label lblAI;
	private Label lblPot;
	private Label lblAIinfo;
	private Label lblPotinfo;
	private Label lblPotValue;
	private Label lblAIValue;

	private Scene scene;
	
	private TextField tfNameInput;
	private BorderPane borderPane;
	private GridPane grid;
	private Slider sliderPot;
	private Slider sliderAI;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		

		grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(50);
		grid.setHgap(90);
		
		//Name label
		lblName = new Label("Namn:");
		grid.setConstraints(lblName, 0, 0);
		
		//Name input
		tfNameInput = new TextField();
		grid.setConstraints(tfNameInput, 1, 0);
		
		//AI label
		lblAI = new Label("Antal AI-spelare:");
		grid.setConstraints(lblAI, 0, 1);
		
		//Pot label
		lblPot = new Label("Pottstorlek:");
		grid.setConstraints(lblPot, 0, 3);
		
		
		
		//Slider Potsize
		sliderPot = new Slider();
		sliderPot.setMin(200);
		sliderPot.setMax(1000);
		sliderPot.setValue(500);
		sliderPot.setShowTickLabels(true);
//		slider.setShowTickMarks(true);
		sliderPot.setMajorTickUnit(50);
		sliderPot.setMinorTickCount(100);
		sliderPot.setBlockIncrement(10);
		
		grid.setConstraints(sliderPot, 1, 3);
		
		//Label Chosen value Pot;
		lblPotValue = new Label("Storlek:");
		grid.setConstraints(lblPotValue, 0, 4);

		//Label for showing the inputvalue of the Potslider
		lblPotinfo = new Label(Double.toString(sliderPot.getValue()));
		sliderPot.setOnMouseDragged(e -> lblPotinfo.textProperty().setValue(
				String.valueOf((Double)sliderPot.getValue())));

		//setOnDragDetected
		grid.setConstraints(lblPotinfo, 1, 4);
		
		//Slider number of AI-players
		sliderAI = new Slider();
		sliderAI.setMin(1);
		sliderAI.setMax(7);
		sliderAI.setValue(5);
		sliderAI.setShowTickLabels(true);
		sliderAI.setMajorTickUnit(2);
		sliderAI.setMinorTickCount(1);
		sliderAI.setBlockIncrement(1);
		
	
		grid.setConstraints(sliderAI, 1, 1);
		
		//Label Chosen no of AI:
		lblAIValue = new Label("Antal:");
		grid.setConstraints(lblAIValue, 0, 2);
		
		//Label for showing the inputvalue of the AISlider
		lblAIinfo = new Label(Double.toString(sliderAI.getValue()));
		sliderAI.setOnMouseDragged(e -> lblAIinfo.textProperty().setValue(
				String.valueOf((Double)sliderAI.getValue())));
		
	
				
			
		grid.setConstraints(lblAIinfo, 1, 2);
		
		grid.getChildren().addAll(lblName, tfNameInput, lblAI, sliderAI, lblPot, sliderPot, lblPotValue, lblPotinfo, lblAIValue, lblAIinfo);

		grid.setAlignment(Pos.CENTER);
		
		
		borderPane = new BorderPane();

	
		borderPane.setCenter(grid);
		
		scene = new Scene(borderPane,600,600);
		window.setScene(scene);
		window.show();
		
	}

}
