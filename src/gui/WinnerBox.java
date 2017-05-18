package gui;


import java.nio.file.Paths;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Box that shows the winner of round. 
 * @author Lykke Levin 
 * version 0.2
 *
 */
public class WinnerBox {

	public boolean answer = false;
	public Stage window = new Stage();
	public Font font = new Font("Tw Cen MT", 18);
	private ImageView back = new ImageView(Paths.get("resources/images/background.png").toUri().toString());
	
	
	public boolean displayWinner(String title, String message, int nr) {
		
		String aiWin = new String("Rundan vanns av " + message);
		String playerWin = new String("Grattis " + message + ", du vann den här rundan!");
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setWidth(400);
		window.setHeight(200);
		window.setOnCloseRequest(e -> closeProgram());

		Pane pane = new Pane();

		Label messageText = new Label();
		messageText.setFont(font);
		messageText.setTextFill(Color.WHITE);
		messageText.setWrapText(true);
//		messageText.setTextAlignment(TextAlignment.CENTER);
		if(nr == 1){
			messageText.setText(playerWin);
			System.out.println("*******LYKKE PLAYER WIN********");
		} else if(nr == 2){
			messageText.setText(aiWin);
			System.out.println("*******LYKKE AI WIN********");
		}

		Button buttonOk = new Button("Okidoki");
		buttonOk.setFont(font);

		buttonOk.setOnAction(e -> {
			answer = true;
			closeProgram();
		});

		back.setFitHeight(window.getHeight());
		back.setFitWidth(window.getWidth());
		messageText.setPrefSize(200, 100);
		messageText.setLayoutX(100);
		messageText.setLayoutY(10);
		buttonOk.setLayoutX(171);
		buttonOk.setLayoutY(123);

		pane.getChildren().addAll(back, messageText, buttonOk);

		Scene scene = new Scene(pane);
		window.setScene(scene);
		window.showAndWait();
		return answer;
		
	}

	public void closeProgram() {
		window.close();
	}

}
