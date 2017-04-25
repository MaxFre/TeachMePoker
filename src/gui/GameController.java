package gui;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import deck.Card;
import deck.Deck;
import hand.Hand;
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
	private ChangeScene changeScene;
	private int powerBarValue = 0;
	private Image image;
	private ArrayList<String> cardsIcon = new ArrayList<String>();
	private Hand hand;
	private Deck deck;
	private int bet;
	private int potValue = 2000; // just for testing, its coming from controller
	private ImageView imgCardOne = new ImageView();
	private ImageView imgCard2 = new ImageView();
	private ImageView imgPowerBar = new ImageView();
	private FileHandler pot;

	public void initialize() throws Exception {

	}

	public void exitGame() {

		System.exit(0);
	}

	public void setChangeScene(ChangeScene sceneChanger) {
		this.changeScene = sceneChanger;
	}

	public void playerCheck() {
		System.out.println("Player checked");
	}

	public void playerFold() {
		System.out.println("Player folded");
		lbPlayerAction.setText("fold");
	}

	public void PlayerCall() {
		System.out.println("Player call");
	}

	public void playerRaise() {
		System.out.println("Player raised");
	}

	public void newGame() {
		System.out.println("new game");
	}

	public void saveGame() {
		try {
			pot = new FileHandler(potValue);
			pot.savePot();
			System.out.println("pot from GUI:" + pot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sliderChange() {
		int val = (int) slider.getValue();
		// ****************** if we like to show the bet value in the text field ******************
		// field.setText(""+val);
		// slider.setValue((int)(INIT_VALUE));
		// field.setText(new Integer((int)(INIT_VALUE)).toString());
		// field.textProperty().bindBidirectional(slider.valueProperty(),
		// NumberFormat.getNumberInstance());
		// ****************************************************************************************
	}

	// this method uses only for testing Hand and deck as a demo
	// Okbox method starts when you click the Raise button and shows the player
	// cards, help, advise and power bar
	public void okBox() {
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
		String temp2 = String.valueOf(temp);
		String temp4 = String.valueOf(temp3);
		char A = cards.get(0).getCardSuit().charAt(0);
		char B = cards.get(1).getCardSuit().charAt(0);
		temp2 += String.valueOf(A);
		temp4 += String.valueOf(B);

		System.out.println(temp2);
		System.out.println(temp4);
		for (int i = 0; i < test.length; i++) {
			if (test[i].equals(temp2)) {
				temp2 += "O";
			}
			if (test[i].equals(temp4)) {
				temp4 += "O";
			}
		}
		String cardOne = "resources/images/" + temp2 + ".png";
		String cardTwo = "resources/images/" + temp4 + ".png";
		String powerBarWeakHand = "resources/images/weakHand.png";
		String powerBarMediumWeakHand = "resources/images/mediumWeakHand.png";
		String powerBarMediumStrongHand = "resources/images/mediumStrongHand.png";
		String powerBarStrongHand = "resources/images/StrongHand.png";

		System.out.println("temp2 - " + temp2);
		System.out.println("temp4 - " + temp4);
		// XXXXXXXXXXXXXXXXXXXXXXXXXXX END XXXXXXXXXXXXXXXXXXXXX

		bet = 0;
		try {

			bet = (int) (slider.getValue());
			if (potValue >= bet) {
				lbPlayerAction.setText("" + bet + " §");
				int NewPotValue = potValue - bet;
				System.out.println("" + NewPotValue);

				potValue = NewPotValue;
				lbPotValue.setText("" + NewPotValue + " §");
				slider.setMax(NewPotValue);
				if (bet == potValue) {
					lbPlayerAction.setText("ALL IN");
					lbPotValue.setText("" + 0 + " §");
					slider.setMax(NewPotValue);
					showAllIn();
				}
				String helpText = hand.theHelp();
				helpLabel.setText(helpText);
				String adviceText = hand.theAdvice();
				adviceLabel.setText("Råd: \n" + adviceText);

				powerBarValue = hand.toPowerBar();
				if (powerBarValue == 1) {
					image = new Image(Paths.get(powerBarWeakHand).toUri().toString(), 120, 166, false, false);
					imgPowerBar = new ImageView(image);
					powerBarArea.getChildren().add(imgPowerBar);
					imgPowerBar.setX(30);
					imgPowerBar.setY(15);
				}
				if (powerBarValue == 2) {
					image = new Image(Paths.get(powerBarMediumWeakHand).toUri().toString(), 120, 166, false, false);
					imgPowerBar = new ImageView(image);
					powerBarArea.getChildren().add(imgPowerBar);
					imgPowerBar.setX(30);
					imgPowerBar.setY(15);
				}
				if (powerBarValue == 3) {
					image = new Image(Paths.get(powerBarMediumStrongHand).toUri().toString(), 120, 166, false, false);
					imgPowerBar = new ImageView(image);
					powerBarArea.getChildren().add(imgPowerBar);
					imgPowerBar.setX(30);
					imgPowerBar.setY(15);
				}
				if (powerBarValue == 4) {
					image = new Image(Paths.get(powerBarStrongHand).toUri().toString(), 120, 166, false, false);
					imgPowerBar = new ImageView(image);
					powerBarArea.getChildren().add(imgPowerBar);
					imgPowerBar.setX(30);
					imgPowerBar.setY(15);

				}

				image = new Image(Paths.get(cardOne).toUri().toString(), 120, 166, false, false);
				imgCardOne = new ImageView(image);
				playerCardsArea.getChildren().add(imgCardOne);
				imgCardOne.setX(0);
				imgCardOne.setY(0);
				image = new Image(Paths.get(cardTwo).toUri().toString(), 120, 166, false, false);
				imgCard2 = new ImageView(image);
				playerCardsArea.getChildren().add(imgCard2);
				imgCard2.setX(105);
				imgCard2.setY(0);
			} else {
				lbPotValue.setText("0.0");
				showAllIn();
				slider.setDisable(true);
			}
		} catch (Exception e) {
		}
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

	public double setPotValue() {
		System.out.println(potValue);
		return potValue;
	}

	// restart all the images
	public void nextRound() {

		image = null;
		imgCardOne.setImage(null);
		imgCard2.setImage(null);
		imgPowerBar.setImage(null);
		System.gc();
	}

	public void setUsername(String name) {
		userName.setText(name);

	}

	public void showAllIn() {
		lbAllIn.setVisible(true);
	}

}
