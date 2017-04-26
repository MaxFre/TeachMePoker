package gui;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import controller.SPController;
import deck.Card;
import deck.Deck;
import hand.Hand;
import filehandler.FileHandler;
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
  @FXML
  private Pane tabelCardArea;

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

  public void initialize() throws Exception {

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
    playerMadeDecision = true;
    // TODO
  }

  public void playerFold() {
    disableButtons();
    System.out.println("Player folded");
    this.decision = "fold";
    lbPlayerAction.setText("fold");
    playerMadeDecision = true;
    // TODO
  }

  public void PlayerCall() {
    disableButtons();
    System.out.println("Player call");
    playerMadeDecision = true;
    // TODO
  }

  public void playerRaise() {
    disableButtons();
    System.out.println("Player raised");
    playerMadeDecision = true;
    // TODO
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
          image = new Image(Paths.get(powerBarWeakHand).toUri().toString(), 120, 166, true, true);
          imgPowerBar = new ImageView(image);
          powerBarArea.getChildren().add(imgPowerBar);
          imgPowerBar.setX(30);
          imgPowerBar.setY(15);
        }
        if (powerBarValue == 2) {
          image = new Image(Paths.get(powerBarMediumWeakHand).toUri().toString(), 120, 166, true, true);
          imgPowerBar = new ImageView(image);
          powerBarArea.getChildren().add(imgPowerBar);
          imgPowerBar.setX(30);
          imgPowerBar.setY(15);
        }
        if (powerBarValue == 3) {
          image = new Image(Paths.get(powerBarMediumStrongHand).toUri().toString(), 120, 166, true, true);
          imgPowerBar = new ImageView(image);
          powerBarArea.getChildren().add(imgPowerBar);
          imgPowerBar.setX(30);
          imgPowerBar.setY(15);
        }
        if (powerBarValue == 4) {
          image =
              new Image(Paths.get(powerBarStrongHand).toUri().toString(), 120, 166, true, true);
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

  public void setStartingHand() {

  }

  public void handHelp() {

  }

  public String getPlayerDecision() {
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

}
