package teachMePoker;

import java.io.IOException;
import hand.Hand;

import java.net.URL;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Icon;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import deck.Card;
import deck.Deck;
public class Controller implements Initializable {
	@FXML
    private TextField field;
    @FXML
    private Slider slider;
    @FXML
    private Label label;
    @FXML
    private Label potValueLabel;

    @FXML
    private Button okBox;
    @FXML
    private ImageView arrow;
    
    @FXML
    private ImageView cardOne;
        
    @FXML
    private Pane pane;
    
    @FXML
    private Button next;
    
    @FXML
    private Label adviceLabel;

    @FXML
    private Label helpLabel;
    
    @FXML
    private Label userName;



    private Image image;
    private ArrayList<String> cardsIcon= new ArrayList<String>();
    private Hand hand;
    private Deck deck;
    private double bet;
    private double potValue=2000;  // just for test
	private static final double INIT_VALUE=50;
	ImageView imgView=new ImageView();
	ImageView imgView2= new ImageView();
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
		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<String> highligtedCards = new ArrayList<String>();
		String[] test;
		
		highligtedCards.clear();
		cards.clear();
		deck = new Deck();
		deck.shuffle();
		cards.add(deck.getCard());
		cards.add(deck.getCard());
		hand = new Hand(cards);
		
		
		
		highligtedCards = hand.sendToHighlight();

		test = new String[highligtedCards.size()];
		
		for(int i = 0; i<test.length; i++){
			String firstTemp = highligtedCards.get(i);
			String[] splitter = firstTemp.split(",");
			test[i] = splitter[0] + splitter[1];
		}
		
		

		int temp=(cards.get(0).getCardValue());
		int temp3=(cards.get(1).getCardValue());
		String temp2= String.valueOf(temp);
		String temp4= String.valueOf(temp3);
		char A = cards.get(0).getCardSuit().charAt(0);
		char B = cards.get(1).getCardSuit().charAt(0);
		temp2 += String.valueOf(A);
		temp4 += String.valueOf(B);
		
		System.out.println(temp2);
		System.out.println(temp4);
		for(int i = 0; i<test.length; i++){
			if(test[i].equals(temp2)){
				temp2 += "O";
			}
			if(test[i].equals(temp4)){
				temp4 += "O";
			}
		}
//		String cardOne= "resources/images/"+temp2+".png";
		String cardOne= "resources/images/"+temp2+".png";
		String cardTwo= "resources/images/"+temp4+".png";
		
		System.out.println("temp2 - " + temp2);
		System.out.println("temp4 - " + temp4);
		
		

		bet=0;
		try{
//			image = null;
//			imgView.setImage(null);
//			imgView2.setImage(null);
//			System.gc();
		if (potValue>0.1){
		bet= Math.round(slider.getValue());
		double NewPotValue=potValue-bet;
		System.out.println(""+NewPotValue);
		label.setText(""+bet+" §");
		potValue= NewPotValue;
		potValueLabel.setText(""+NewPotValue+" §");
		slider.setMax(NewPotValue);
		
	//	changePosition(); //extra
		arrow.setLayoutX(437);
		arrow.setLayoutY(66);
		
	//	help.setText("ses");
		String helpText = hand.theHelp();
		helpLabel.setText(helpText);
		String adviceText = hand.theAdvice();
		adviceLabel.setText("Råd: \n"+adviceText);
		
		System.out.println("temp "+ temp2);
		System.out.println("temp "+ temp4);
		
// test transfer card
//		 image  = new Image(getClass().getResourceAsStream(cardOne),80,116,false,false);
		 image  = new Image(Paths.get(cardOne).toUri().toString(),80,116,false,false);
//		Image newimg = image.getScaledInstance(120, 120, 120);
		 imgView = new ImageView(image);
		pane.getChildren().add(imgView);
		imgView.setX(0);
		imgView.setY(0);
		
//		 image  = new Image(getClass().getResourceAsStream(cardTwo),80,116, false,false);
		 image  = new Image(Paths.get(cardTwo).toUri().toString(),80,116,false,false);
		 imgView2 = new ImageView(image);
		pane.getChildren().add(imgView2);
		imgView2.setX(70);
		imgView2.setY(0);
		}
		else {
			potValueLabel.setText("0.0");
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
		System.out.println("Go to Rules section");
	}
	
	public void mainState(){
		System.out.println("Go to Main section");
	}
	
	public double setPotValue(){
		System.out.println(potValue);
		return potValue;
	}
	
	public void nextRound(){
		
		image = null;
		imgView.setImage(null);
		imgView2.setImage(null);
		System.gc();
	}
	
	
    
}
