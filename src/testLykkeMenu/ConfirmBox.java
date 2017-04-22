package testLykkeMenu;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ConfirmBox {
	
public	boolean answer;
public	Stage window = new Stage();
public Font font = new Font("Tw Cen MT", 24 );
	
	public boolean display(String title, String message){

		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		window.setOnCloseRequest(e -> closeProgram());
		
		Label label = new Label();
		label.setFont(font);
		label.setText(message);
		
		Button buttonOk = new Button("Okej");
		buttonOk.setFont(font);
		
		buttonOk.setOnAction(e-> {
			closeProgram();
		
		});
	
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, buttonOk);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}
	
	public void closeProgram(){
		System.out.println("Kommer att stänga fönstret");
		window.close();
	}


}
