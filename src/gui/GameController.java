package gui;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import aiClass.Ai;
import controller.SPController;
import deck.Card;
import deck.Deck;
import hand.Hand;
import filehandler.FileHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GameController {


	@FXML
	private ImageView btCheck;
	@FXML
	private ImageView btCall;
	@FXML
	private ImageView btFold;
	@FXML
	private ImageView btRaise;
	@FXML
	private Slider slider;
	@FXML
	private Label lbPlayerAction;
	@FXML
	private Label lbPotValue;
	@FXML
	private Label lbAllIn;
	@FXML
	private Pane powerBarArea;
	@FXML
	private ImageView cardOne;
	@FXML
	private Pane playerCardsArea;
	@FXML
	private Label adviceLabel;
	@FXML
	private Label helpLabel;
	@FXML
	private Label userName;
	@FXML
	private Pane tabelCardArea;
	
	@FXML
	private ImageView imgPlayerOneCards;
	@FXML
	private ImageView imgPlayerTwoCards;
	@FXML
	private ImageView imgPlayerThreeCards;
	@FXML
	private ImageView imgPlayerFourCards;
	@FXML
	private ImageView imgPlayerFiveCards;
	
	@FXML
	private Label labelPlayerOneName;
	@FXML
	private Label labelPlayerTwoName;
	@FXML
	private Label labelPlayerThreeName;
	@FXML
	private Label labelPlayerFourName;
	@FXML
	private Label labelPlayerFiveName;
	
	@FXML
	private Label labelPlayerOnePot;
	@FXML
	private Label labelPlayerTwoPot;
	@FXML
	private Label labelPlayerThreePot;
	@FXML
	private Label labelPlayerFourPot;
	@FXML
	private Label labelPlayerFivePot;

	@FXML
	private Label labelPlayerOneAction;
	@FXML
	private Label labelPlayerTwoAction;
	@FXML
	private Label labelPlayerThreeAction;
	@FXML
	private Label labelPlayerFourAction;
	@FXML
	private Label labelPlayerFiveAction;

	private ChangeScene changeScene;
	private int powerBarValue = 0;
	private Image image;
	private ArrayList<String> cardsIcon = new ArrayList<String>();
	private Hand hand;
	private Deck deck;
	private int bet;
	private int tablePotValue = 2000; // just for testing, its coming from controller
	private int playerPot = 0;
	private int alreadyPaid = 0;
	private int highCard;
	private ImageView imgCardOne = new ImageView();
	private ImageView imgCard2 = new ImageView();
	private ImageView imgCard3 = new ImageView();
	private ImageView imgCard4 = new ImageView();
	private ImageView imgCard5 = new ImageView();
	private ImageView imgCard6 = new ImageView();
	private ImageView imgCard7 = new ImageView();
	private ImageView imgPowerBar = new ImageView();
	private FileHandler pot;
	private SPController spController;
	private boolean playerMadeDecision = false;
	private String decision;
	private Card card1;
	private Card card2;
	private int handStrength;
	private int sliderValue;
	private LinkedList<Ai> aiPlayers;
	private Label [][] collectionOfLabelsAi;
	private ImageView [] collectionOfCardsAi;

	public void initialize() throws Exception {
	
		this.collectionOfLabelsAi = new Label [][]  {
				{labelPlayerOneName, labelPlayerOnePot, labelPlayerOneAction},
				{labelPlayerTwoName, labelPlayerTwoPot, labelPlayerTwoAction},
				{labelPlayerThreeName, labelPlayerThreePot, labelPlayerThreeAction},
				{labelPlayerFourName, labelPlayerFourPot, labelPlayerFourAction},
				{labelPlayerFiveName, labelPlayerFivePot, labelPlayerFiveAction}
		};
		
		this.collectionOfCardsAi = new ImageView [] {
				imgPlayerOneCards,
				imgPlayerTwoCards,
				imgPlayerThreeCards,
				imgPlayerFourCards,
				imgPlayerFiveCards
		};
		
	}
	
	public void setShowUIAiBar(int position){
		collectionOfLabelsAi[position][0].setVisible(true);
		collectionOfLabelsAi[position][1].setVisible(true);
		collectionOfLabelsAi[position][2].setVisible(true);
		
		collectionOfCardsAi[position].setVisible(true);
	}
	
	public void setLabelUIAiBarName(int position, String name){
		collectionOfLabelsAi[position][0].setText(name);
	}
	
	public void setLabelUIAiBarPot(int position, String pot){
		collectionOfLabelsAi[position][1].setText(pot);
	}
	
	public void setLabelUIAiBarAction(int position, String action){
		collectionOfLabelsAi[position][2].setText(action);
	}
	
	public void setUIAiFolded(int position, boolean folded){	
		String resource = "resources/images/";
		
		Image showCards = new Image(Paths.get(resource + "aiBarWithCards.png").toUri().toString(), 122, 158, true, true); 
		Image foldCards = new Image(Paths.get(resource + "aiBarWithoutCards.png").toUri().toString(), 122, 158, true, true);  

		if(folded == true){
			collectionOfCardsAi[position].setImage(showCards);

		}else{
			collectionOfCardsAi[position].setImage(foldCards);
		}
	}
	
	public void exitGame() {

		System.exit(0);
	}

	public void setSPController(SPController spController) {
		this.spController = spController;
		spController.setGameController(this);
	}

	public void setChangeScene(ChangeScene sceneChanger) {
		this.changeScene = sceneChanger;
	}

	public void playerCheck() {
		disableButtons();
		System.out.println("Player checked");
		//okBox();
		this.decision = "check";
		lbPlayerAction.setText("Check");
		playerMadeDecision = true;
		updatePlayerValues(decision);
		// TODO
	}

	public void playerFold() {
		disableButtons();
		System.out.println("Player folded");
		this.decision = "fold";
		lbPlayerAction.setText("Fold");
		//okBox();
		playerMadeDecision = true;
		updatePlayerValues(decision);
		// TODO
	}

	public void playerCall() {
		disableButtons();
		System.out.println("Player call");
		this.playerPot -=(spController.getCurrentMaxBet()-alreadyPaid);
		this.alreadyPaid = spController.getCurrentMaxBet();
		this.decision = "call," + Integer.toString(alreadyPaid);
		//okBox();
		playerMadeDecision = true;
		updatePlayerValues(decision);

		// TODO
	}
	
	public void updatePlayerValues(String action){
		lbPotValue.setText(Integer.toString(playerPot) + " §");
		lbPlayerAction.setText(action + " §");
		setSliderValues();

	}
	
	public void playerRaise() {
		disableButtons();
		this.playerPot -= (int) slider.getValue();
		
		this.decision = "raise," +  ((int) slider.getValue() + alreadyPaid);
		this.alreadyPaid = ((int) slider.getValue() + alreadyPaid);
		//okBox();
		playerMadeDecision = true;
		updatePlayerValues(decision);
		try {
			tablePotValue = spController.getPotSize();
			bet = (int) (slider.getValue());
			if (tablePotValue >= bet) {
				lbPlayerAction.setText("" + bet + " §");
				lbPotValue.setText("" + playerPot + " §");
				//   slider.setMax(NewPotValue);
				if (bet == tablePotValue) {
					lbPlayerAction.setText("ALL IN");
					lbPotValue.setText("" + 0 + " §");
					slider.setMax(tablePotValue + bet);
					showAllIn();
				}
			} else {
				lbPotValue.setText("0.0");
				showAllIn();
				slider.setDisable(true);
			}
		} catch (Exception e) {
		}
		// TODO
	}

	public void newGame() {
		System.out.println("new game");
	}

	public void saveGame() {
		System.out.println("Saved Game");
		// try {
		// pot = new FileHandler(tablePotValue);
		// pot.savePot();
		// System.out.println("pot from GUI:" + pot);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public void setSliderValues(){
		slider.setMax(playerPot);
		if ((spController.getCurrentMaxBet()-alreadyPaid)+(spController.getCurrentMaxBet() *0.25) <= playerPot){
			slider.setMin(spController.getCurrentMaxBet()*1.25);	
		}else{
			slider.setMin(0);
		}
		
	}

	public void sliderChange() {
		int val = 0;
		val = (int) slider.getValue();
		this.sliderValue = val;

		// ****************** if we like to show the bet value in the text field ******************
		// field.setText(""+val);
		// slider.setValue((int)(INIT_VALUE));
		// field.setText(new Integer((int)(INIT_VALUE)).toString());
		// field.textProperty().bindBidirectional(slider.valueProperty(),
		// NumberFormat.getNumberInstance());
		// ****************************************************************************************
	}

	// this method uses only for testing Hand and deck as a demo
	// Okbox method starts when you click the w button and shows the player
	// cards, help, advise and power bar

	public void okBox() {
		alreadyPaid = 0;
		nextRound(); // method that restart all the images.
		// get Cards from deck
		// XXXXXXXXXXXXXXXXXXXXXXX This code is a testing for hand and deck XXXXXXXXXXXXXXXXXXXXXXXXXXX
		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<String> highligtedCards = new ArrayList<String>();
		String[] test;

		highligtedCards.clear();
		cards.clear();
		deck = new Deck();
		deck.shuffle();
		cards.add(deck.getCard());
		cards.add(deck.getCard());
		cards.add(deck.getCard());
		cards.add(deck.getCard());
		cards.add(deck.getCard());
		cards.add(deck.getCard());
		cards.add(deck.getCard());
		hand = new Hand(cards);

		highligtedCards = hand.sendToHighlight();

		test = new String[highligtedCards.size()];

		for (int i = 0; i < test.length; i++) {
			String firstTemp = highligtedCards.get(i);
			String[] splitter = firstTemp.split(",");
			test[i] = splitter[0] + splitter[1];
		}

		int temp = (cards.get(0).getCardValue());
		int temp3 = (cards.get(1).getCardValue());
		int temp5 = (cards.get(2).getCardValue());
		int temp7 = (cards.get(3).getCardValue());
		int temp9 = (cards.get(4).getCardValue());
		int temp11 = (cards.get(5).getCardValue());
		int temp13 = (cards.get(6).getCardValue());
		String temp2 = String.valueOf(temp);
		String temp4 = String.valueOf(temp3);
		String temp6 = String.valueOf(temp5);
		String temp8 = String.valueOf(temp7);
		String temp10 = String.valueOf(temp9);
		String temp12 = String.valueOf(temp11);
		String temp14 = String.valueOf(temp13);
		char A = cards.get(0).getCardSuit().charAt(0);
		char B = cards.get(1).getCardSuit().charAt(0);
		char C = cards.get(2).getCardSuit().charAt(0);
		char D = cards.get(3).getCardSuit().charAt(0);
		char E = cards.get(4).getCardSuit().charAt(0);
		char F = cards.get(5).getCardSuit().charAt(0);
		char G = cards.get(6).getCardSuit().charAt(0);
		temp2 += String.valueOf(A);
		temp4 += String.valueOf(B);
		temp6 += String.valueOf(C);
		temp8 += String.valueOf(D);
		temp10 += String.valueOf(E);
		temp12 += String.valueOf(F);
		temp14 += String.valueOf(G);

		System.out.println(temp2);
		System.out.println(temp4);
		System.out.println(temp6);
		System.out.println(temp8);
		System.out.println(temp10);
		for (int i = 0; i < test.length; i++) {
			if (test[i].equals(temp2)) {
				temp2 += "O";
			}
			if (test[i].equals(temp4)) {
				temp4 += "O";
			}
			if (test[i].equals(temp6)) {
				temp6 += "O";
			}
			if (test[i].equals(temp8)) {
				temp8 += "O";
			}
			if (test[i].equals(temp10)) {
				temp10 += "O";
			}
			if (test[i].equals(temp12)) {
				temp12 += "O";
			}
			if (test[i].equals(temp14)) {
				temp14 += "O";
			}
		}
		String cardOne = "resources/images/" + temp2 + ".png";
		String cardTwo = "resources/images/" + temp4 + ".png";
		String cardThree = "resources/images/" + temp6 + ".png";
		String cardFour = "resources/images/" + temp8 + ".png";
		String cardFive = "resources/images/" + temp10 + ".png";
		String cardSix = "resources/images/" + temp12 + ".png";
		String cardSeven = "resources/images/" + temp14 + ".png";
		String powerBarWeakHand = "resources/images/weakHand.png";
		String powerBarMediumWeakHand = "resources/images/mediumWeakHand.png";
		String powerBarMediumStrongHand = "resources/images/mediumStrongHand.png";
		String powerBarStrongHand = "resources/images/StrongHand.png";

		System.out.println("temp2 - " + temp2);
		System.out.println("temp4 - " + temp4);
		// XXXXXXXXXXXXXXXXXXXXXXXXXXX END XXXXXXXXXXXXXXXXXXXXX

		bet = 0;

		String helpText = hand.theHelp();
		helpLabel.setText(helpText);
		String adviceText = hand.theAdvice();
		adviceLabel.setText("Råd: \n" + adviceText);

		powerBarValue = hand.toPowerBar();
		if (powerBarValue == 1) {
			image = new Image(Paths.get(powerBarWeakHand).toUri().toString(), 120, 166, true, true);
			imgPowerBar = new ImageView(image);
			powerBarArea.getChildren().add(imgPowerBar);
			imgPowerBar.setX(30);
			imgPowerBar.setY(15);
		}
		if (powerBarValue == 2) {
			image =
					new Image(Paths.get(powerBarMediumWeakHand).toUri().toString(), 120, 166, true, true);
			imgPowerBar = new ImageView(image);
			powerBarArea.getChildren().add(imgPowerBar);
			imgPowerBar.setX(30);
			imgPowerBar.setY(15);
		}
		if (powerBarValue == 3) {
			image = new Image(Paths.get(powerBarMediumStrongHand).toUri().toString(), 120, 166, true,
					true);
			imgPowerBar = new ImageView(image);
			powerBarArea.getChildren().add(imgPowerBar);
			imgPowerBar.setX(30);
			imgPowerBar.setY(15);
		}
		if (powerBarValue == 4) {
			image = new Image(Paths.get(powerBarStrongHand).toUri().toString(), 120, 166, true, true);
			imgPowerBar = new ImageView(image);
			powerBarArea.getChildren().add(imgPowerBar);
			imgPowerBar.setX(30);
			imgPowerBar.setY(15);

		}

		image = new Image(Paths.get(cardOne).toUri().toString(), 120, 166, true, true);
		imgCardOne = new ImageView(image);
		playerCardsArea.getChildren().add(imgCardOne);
		imgCardOne.setX(0);
		imgCardOne.setY(0);
		image = new Image(Paths.get(cardTwo).toUri().toString(), 120, 166, true, true);
		imgCard2 = new ImageView(image);
		playerCardsArea.getChildren().add(imgCard2);
		imgCard2.setX(105);
		imgCard2.setY(0);

		image = new Image(Paths.get(cardThree).toUri().toString(), 120, 166, true, true);
		imgCard3 = new ImageView(image);
		tabelCardArea.getChildren().add(imgCard3);
		imgCard3.setX(0);
		imgCard3.setY(0);
		image = new Image(Paths.get(cardFour).toUri().toString(), 120, 166, true, true);
		imgCard4 = new ImageView(image);
		tabelCardArea.getChildren().add(imgCard4);
		imgCard4.setX(105);
		imgCard4.setY(0);
		image = new Image(Paths.get(cardFive).toUri().toString(), 120, 166, true, true);
		imgCard5 = new ImageView(image);
		tabelCardArea.getChildren().add(imgCard5);
		imgCard5.setX(205);
		imgCard5.setY(0);
		image = new Image(Paths.get(cardSix).toUri().toString(), 120, 166, true, true);
		imgCard6 = new ImageView(image);
		tabelCardArea.getChildren().add(imgCard6);
		imgCard6.setX(305);
		imgCard6.setY(0);
		image = new Image(Paths.get(cardSeven).toUri().toString(), 120, 166, true, true);
		imgCard7 = new ImageView(image);
		tabelCardArea.getChildren().add(imgCard7);
		imgCard7.setX(405);
		imgCard7.setY(0);



	}

	public void soundSetting() {
		System.out.println("Sound Setting");
	}

	public void rulesState() {
		System.out.println("Go to Rules section");
	}

	public void mainState() {
		System.out.println("Go to Main section");
	}

	public double getPotValue() {
		System.out.println(tablePotValue);
		return tablePotValue;
	}

	// restart all the images
	public void nextRound() {

		image = null;
		imgCardOne.setImage(null);
		imgCard2.setImage(null);
		imgCard3.setImage(null);
		imgCard4.setImage(null);
		imgCard5.setImage(null);
		imgCard6.setImage(null);
		imgCard7.setImage(null);
		imgPowerBar.setImage(null);
		tabelCardArea.requestLayout();

	}

	public void setUsername(String name) {
		userName.setText(name);

	}

	public void showAllIn() {
		lbAllIn.setVisible(true);
	}

	public void setStartingHand(Card card1, Card card2) {
		nextRound();
		
		System.out.println(this);
		this.card1 = card1;
		this.card2 = card2;
		highCard = card1.getCardValue();
		if (card2.getCardValue() > highCard) {
			highCard = card2.getCardValue();
		}
		String cardOne =
				"resources/images/" + card1.getCardValue() + card1.getCardSuit().charAt(0) + ".png";
		String cardTwo =
				"resources/images/" + card2.getCardValue() + card2.getCardSuit().charAt(0) + ".png";
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(card1);
		cards.add(card2);
		this.hand = new Hand(cards);

		Platform.runLater(() -> {

			image = new Image(Paths.get(cardOne).toUri().toString(), 120, 166, true, true);
			imgCardOne = new ImageView(image);
			playerCardsArea.getChildren().add(imgCardOne);
			imgCardOne.setX(0);
			imgCardOne.setY(0);
			image = new Image(Paths.get(cardTwo).toUri().toString(), 120, 166, true, true);
			imgCard2 = new ImageView(image);
			playerCardsArea.getChildren().add(imgCard2);
			imgCard2.setX(105);
			imgCard2.setY(0);
		});

		handHelp();


		///VEDRANA HELPER FIND
		updatePlayerValues("");

	}

	public void playerSmallBlind(int i) {

		this.alreadyPaid += i;
		System.out.println("Player paid small blind(" + i + ")");
		// TODO set smallBlindIcon at Player

	}

	public void playerBigBlind(int i) {

		this.alreadyPaid += i;
		System.out.println("Player paid big blind(" + i + ")");
		// TODO set bigBlindIcon at player
	}

	public int getPlayerAlreadyPaid(){
		return this.alreadyPaid;
	}

	public void handHelp() {
		String powerBarWeakHand = "resources/images/weakHand.png";
		String powerBarMediumWeakHand = "resources/images/mediumWeakHand.png";
		String powerBarMediumStrongHand = "resources/images/mediumStrongHand.png";
		String powerBarStrongHand = "resources/images/StrongHand.png";

		Platform.runLater(() -> {

			String helpText = hand.theHelp();
			helpLabel.setText(helpText);
			String adviceText = hand.theAdvice();
			adviceLabel.setText("Råd: \n" + adviceText);

			powerBarValue = hand.toPowerBar();
			if (powerBarValue == 1) {
				image = new Image(Paths.get(powerBarWeakHand).toUri().toString(), 120, 166, true, true);
				imgPowerBar = new ImageView(image);
				powerBarArea.getChildren().add(imgPowerBar);
				imgPowerBar.setX(30);
				imgPowerBar.setY(15);
			} else if (powerBarValue == 2) {
				image =
						new Image(Paths.get(powerBarMediumWeakHand).toUri().toString(), 120, 166, true, true);
				imgPowerBar = new ImageView(image);
				powerBarArea.getChildren().add(imgPowerBar);
				imgPowerBar.setX(30);
				imgPowerBar.setY(15);
			} else if (powerBarValue == 3) {
				image = new Image(Paths.get(powerBarMediumStrongHand).toUri().toString(), 120, 166, true, true);
				imgPowerBar = new ImageView(image);
				powerBarArea.getChildren().add(imgPowerBar);
				imgPowerBar.setX(30);
				imgPowerBar.setY(15);
			} else if (powerBarValue == 4) {
				image = new Image(Paths.get(powerBarStrongHand).toUri().toString(), 120, 166, true, true);
				imgPowerBar = new ImageView(image);
				powerBarArea.getChildren().add(imgPowerBar);
				imgPowerBar.setX(30);
				imgPowerBar.setY(15);

			}
		});
	}

	public String getPlayerDecision() {
		return decision;
	}

	public String askForPlayerDecision() {
		enableButtons();
		playerMadeDecision = false;
		while (!playerMadeDecision) {
			try {
				spController.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return decision;

	}

	public void playerReset(String resetDecision) {

		decision = resetDecision;
		alreadyPaid = 0;
	}

	public void setPlayerPot(int newValue) {

		this.playerPot += newValue;
	}


	public void disableButtons() {
		btCall.setVisible(false);
		btRaise.setVisible(false);
		btRaise.setVisible(false);
	}

	private void enableButtons() {
		btCall.setVisible(true);
		btRaise.setVisible(true);
		btRaise.setVisible(true);

	}

	public int getHandStrength() {
		return handStrength;

	}

	public void setButtonCallCheck(boolean choice){
		if (choice){
			btCheck.setVisible(true);
			btCheck.setDisable(false);
			btCall.setVisible(false);
			btCall.setDisable(true);	
		}else{
			btCheck.setVisible(false);
			btCheck.setDisable(true);	
			btCall.setVisible(true);
			btCall.setDisable(false);

		} 
	}



	public int getPlayerPot() {
		return playerPot;
	}

	public void setAiPlayers(LinkedList<Ai> aiPlayers) {
		this.aiPlayers = aiPlayers;
		
		int totalAI = aiPlayers.size();
		if(totalAI == 1){
			setShowUIAiBar(0);
		}else if (totalAI == 3){
			setShowUIAiBar(0);
			setShowUIAiBar(2);
			setShowUIAiBar(3);
		}else if (totalAI == 5){
			//JOptionPane.showMessageDialog(null,"aaaa");
			setShowUIAiBar(0);
			setShowUIAiBar(1);
			setShowUIAiBar(2);
			setShowUIAiBar(3);
			setShowUIAiBar(4);
		}
		//TODO set AI players in GUI
	}
	
	public void setPlayerAtTable(){
		
	}
	
	
	public void aiAction(int currentAI, String decision) {
		// TODO AiPlayer matching currentPlayer does decision
		Ai ai = aiPlayers.get(currentAI);
		if(decision == "fold"){
			setUIAiFolded(currentAI, false);
		}else{
			setUIAiFolded(currentAI, true);
		}
		if( (currentAI == 0 && aiPlayers.size() == 3 ) || aiPlayers.size() == 5){
			Platform.runLater(new Runnable() {
				private volatile boolean shutdown;
			    @Override
			    public void run() {					
			    	while (!shutdown) {
			    	System.out.println("Loop... Thread!");
					setLabelUIAiBarName(currentAI, ai.getName() );
					setLabelUIAiBarPot(currentAI, Integer.toString(ai.aiPot()) );	
					setLabelUIAiBarAction(currentAI, decision);		
					shutdown = true;
					
			    	}
			    }
			});	
		}else{
			Platform.runLater(new Runnable() {
			    private volatile boolean shutdown;
			    @Override
			    public void run() {
			    	System.out.println("Loop... Thread!");
			        while (!shutdown) {	  
					setLabelUIAiBarName(currentAI+1, ai.getName() );
					setLabelUIAiBarPot(currentAI+1, Integer.toString(ai.aiPot()) );	
					setLabelUIAiBarAction(currentAI+1, decision);
					shutdown = true;
					
			        }
			    }
			});
		}
	}
	
	
	
	
}
