package teachMePoker;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MenuGUI extends Application {

	private Stage window;
	private VBox btnMenu;
	private BorderPane borderPane;
	private Button btnNewGame;
	private Button btnLoadGame;
	private Image image;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("TeachMePoker");

		Text txtLogga = new Text("TeachMePoker");
		Text txtBtnNew = new Text("Nytt spel");
		txtBtnNew.setFill(Color.WHITE);

		btnNewGame = new Button("Nytt spel");
		btnNewGame.setTextFill(Color.WHITE);

		btnLoadGame = new Button("Ladda spel");
		btnLoadGame.setTextFill(Color.WHITE);

		btnNewGame.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		btnLoadGame.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		// Label lblLogga = new Label(txtLogga);
		Font font = new Font("DINNextRoundedLTPro-Regular.otf", 25);
		// lblLogga.setFont(font);
		txtLogga.setFont(font);

		btnNewGame.setOnMouseMoved(e -> btnNewGame.requestFocus());
		btnNewGame.setOnMouseMoved(e -> changeColor());
		btnNewGame.setOnMouseExited(e->btnNewGame.setTextFill(Color.WHITE));
		btnNewGame.setOnAction(e -> System.out.println("Nytt spel klickat på"));
		
		btnLoadGame.setOnMouseMoved(e->btnLoadGame.requestFocus());
		btnLoadGame.setOnMouseMoved(e -> changeColor());
		btnLoadGame.setOnMouseExited(e->btnLoadGame.setTextFill(Color.WHITE));
		btnLoadGame.setOnAction(e->System.out.println("Ladda sparat spel klickat"));

		btnMenu = new VBox(20);
		btnMenu.getChildren().addAll(btnNewGame, btnLoadGame);

		borderPane = new BorderPane();
		btnMenu.setAlignment(Pos.BOTTOM_CENTER);
		// lblLogga.setAlignment(Pos.CENTER);
		txtLogga.setTextAlignment(TextAlignment.CENTER);
		borderPane.setBottom(btnMenu);
		borderPane.setCenter(txtLogga);
		// borderPane.setCenter(lblLogga);

		borderPane.setPadding(new Insets(0, 10, 160, 10));

		// BackgroundImage bcImage = new BackgroundImage(new
		// Image("gubbe.jpg",32,32,false,true), null, null, null, null);
		// borderPane.setBackground(new Background(bcImage));

		Scene scene = new Scene(borderPane, 600, 600);

		// window.setFullScreen(true);
		window.setScene(scene);
		window.show();

	}

	public void changeColor() {
		if(btnNewGame.isFocused()){
		btnNewGame.setTextFill(Color.AQUA);
		}
		else if(btnLoadGame.isFocused()){
			btnLoadGame.setTextFill(Color.HOTPINK);
		}
	}
}
