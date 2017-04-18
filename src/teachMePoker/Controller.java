package teachMePoker;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class Controller implements Initializable {
	@FXML
    private TextField field;
    @FXML
    private Slider slider;
    @FXML
    private Label label;
    @FXML
    private TextField potValueField;
    @FXML
    private Button okBox;
    @FXML
    private ImageView arrow;
    
    
    
    private double bet;
    private double potValue=2000;  // just for test
	private static final double INIT_VALUE=50;
	
private FileHandler pot;
    public void exitGame(){
        System.exit(0);
    }
    public void playerCheck(){
        System.out.println("Player checked");
    }
    public void playerFold(){
        System.out.println("Player folded");
        label.setText("fold");
    }
    public void PlayerBet(){
        System.out.println("Player bets");
    }
    public void playerRaise(){
        System.out.println("Player raised");
    }
    
    public void newGame(){
    	System.out.println("new game");
    }
    
    public void saveGame(){
    	try {
    	//	System.out.println(potValue);  // test for potvalue amount
    		pot= new FileHandler(potValue);
			pot.savePot();
			System.out.println("pot from GUI:"+pot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {  //slidebar functions for betting amount
		// TODO Auto-generated method stub
		slider.setValue(Math.round(INIT_VALUE));
		field.setText(new Double(Math.round(INIT_VALUE)).toString());
		field.textProperty().bindBidirectional(slider.valueProperty(), NumberFormat.getNumberInstance());
	}
	// player accepts the odds from slider
	public void okBox(){
		bet=0;
		try{
		if (potValue>0.1){
		bet= Math.round(slider.getValue());
		double NewPotValue=potValue-bet;
		System.out.println(""+NewPotValue);
		label.setText(""+bet+" §");
		potValue= NewPotValue;
		potValueField.setText(""+NewPotValue+" §");
		slider.setMax(NewPotValue);
		
	//	changePosition(); //extra
		arrow.setLayoutX(271);
		arrow.setLayoutY(57);
		}
		else {
			potValueField.setText("0.0");
			field.setText("0.0");			
			field.setDisable(true);
			slider.setDisable(true);
			}
		}
		catch(Exception e){ 
			
		//	okBox.setVisible(false);
			okBox.setDisable(true);  // something wrong ???
		}
	}
	
	
	public void deactiveButton(){ //extra function (just in case)
		okBox.setVisible(false);
		
	}
	public void soundSetting(){
		System.out.println("Sound Setting");
	}
	
	public void helpState(){
		System.out.println("Go to Help section");
	}
	
	public void mainState(){
		System.out.println("Go to Main section");
	}
	
	public double setPotValue(){
		System.out.println(potValue);
		return potValue;
	}
	
	
    
}
