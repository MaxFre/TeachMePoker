package gui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import aiClass.Ai;
import controller.SPController;
import deck.Card;
import hand.Hand;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
  private Label raiseLabel;
  @FXML
  private Pane tabelCardArea;
  @FXML
  private AnchorPane AnchorPaneAll;

  @FXML
  private ImageView imgRoundStatus;
  @FXML
  private Pane paneRounds;

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

  @FXML
  private ImageView imgCard1;
  @FXML
  private ImageView imgCard2;
  @FXML
  private ImageView imgCard3;
  @FXML
  private ImageView imgCard4;
  @FXML
  private ImageView imgCard5;
  @FXML
  private ImageView imgCard6;
  @FXML
  private ImageView imgCard7;

  @FXML
  private ImageView ivBigBlind;
  @FXML
  private ImageView ivSmallBlind;
  @FXML
  private ImageView ivDealer;
  @FXML
  private TitledPane tpHandRanking;
  @FXML
  public ImageView ivSound;
  @FXML
  public MenuItem miNewGame;
  @FXML
  public MenuItem miClose;
  @FXML
  public MenuItem miSettings;
  @FXML
  public MenuItem miAbout;


  private ConfirmBox confirmBox;
  private ChangeScene changeScene;
  private int powerBarValue = 0;
  private Image image;
  private ArrayList<Card> cards = new ArrayList<Card>();
  private Hand hand;
  private int tablePotValue = 2000; // just for testing, its coming from controller
  private int playerPot = 0;
  private int alreadyPaid = 0;
  private ImageView imgPowerBar = new ImageView();
  private SPController spController;
  private boolean playerMadeDecision = false;
  private boolean isReady = false;
  private String decision;
  private Card card1;
  private Card card2;
  private int handStrength;
  private LinkedList<Ai> aiPlayers;
  private Label[][] collectionOfLabelsAi;
  private ImageView[] collectionOfCardsAi;
  private ImageView[] collectionOfCardsTable;
  private int[][] aiPositions;
  private int highCard;
  private int prevPlayerActive;
  private Sound sound = new Sound();


  public void initialize() throws Exception {

    // Groups together labels for each AI-position.
    this.collectionOfLabelsAi =
        new Label[][] {{labelPlayerOneName, labelPlayerOnePot, labelPlayerOneAction},
            {labelPlayerTwoName, labelPlayerTwoPot, labelPlayerTwoAction},
            {labelPlayerThreeName, labelPlayerThreePot, labelPlayerThreeAction},
            {labelPlayerFourName, labelPlayerFourPot, labelPlayerFourAction},
            {labelPlayerFiveName, labelPlayerFivePot, labelPlayerFiveAction}};

    // Placeholders for the AI (based on their position). Shows their cardbacks/no cards or
    // highlighted cards (AI-frame).
    this.collectionOfCardsAi = new ImageView[] {imgPlayerOneCards, imgPlayerTwoCards,
        imgPlayerThreeCards, imgPlayerFourCards, imgPlayerFiveCards};

    // Used to place AI-players into the right position depending on the chosen number of AI:s.
    this.aiPositions = new int[][] {{2}, {0, 2, 4}, {0, 1, 2, 3, 4, 5}};

    // Table cards placeholders.
    this.collectionOfCardsTable =
        new ImageView[] {imgCard3, imgCard4, imgCard5, imgCard6, imgCard7};

    // Used by method: inactivateAllAiCardGlows and aiAction.
    this.prevPlayerActive = -1;
  }


  /**
   * Used to show labels and AI-frame.
   * 
   * @param position Position on the screen (0-4).
   */
  public void setShowUIAiBar(int position) {
    collectionOfLabelsAi[position][0].setVisible(true);
    collectionOfLabelsAi[position][1].setVisible(true);
    collectionOfLabelsAi[position][2].setVisible(true);
    collectionOfCardsAi[position].setVisible(true);
  }


  /**
   * Used to change AI-label "name" based on position.
   * 
   * @param position Position on the screen (0-4).
   * @param name The label for the AI's name.
   */
  public void setLabelUIAiBarName(int position, String name) {
    collectionOfLabelsAi[position][0].setText(name);
  }


  /**
   * Used to change AI-label "pot" based on position.
   * 
   * @param Position Position on the screen (0-4).
   * @param name The label for the AI's pot.
   */
  public void setLabelUIAiBarPot(int position, String pot) {
    collectionOfLabelsAi[position][1].setText(pot);
  }


  /**
   * Used to change AI-label "action" based on position.
   * 
   * @param Position Position on the screen (0-4).
   * @param name The label for the AI's action.
   */
  public void setLabelUIAiBarAction(int position, String action) {
    collectionOfLabelsAi[position][2].setText(action);
  }


  /**
   * Changes the AI-frame based on position and state.
   * 
   * @param position Position Position on the screen (0-4).
   * @param state The state can either be inactive (folded/lost), idle (waiting for it's turn),
   *        active (currently it's turn).
   */
  public void setUIAiStatus(int position, String state) {

    String resource = "resources/images/"; // 122, 158
    Image hideCards = new Image(Paths.get(resource + "aiBarWithoutCards.png").toUri().toString(),
        122, 158, true, true);
    Image showCards = new Image(Paths.get(resource + "aiBarWithCards.png").toUri().toString(), 122,
        158, true, true);
    Image showActiveCards =
        new Image(Paths.get(resource + "aiBarWithCardsCurrentPlayer.png").toUri().toString(), 122,
            158, true, true);

    if (state == "inactive") {
      collectionOfCardsAi[position].setImage(hideCards);
    } else if (state == "idle") {
      collectionOfCardsAi[position].setImage(showCards);
    } else if (state == "active") {
      collectionOfCardsAi[position].setImage(showActiveCards);
    }
  }


  public void setSPController(SPController spController) {

    this.spController = spController;
    spController.setGameController(this);
  }


  public void setChangeScene(ChangeScene sceneChanger) {

    this.changeScene = sceneChanger;
  }


  /**
   * Disables all buttons and shows player-frame's action as check.
   */
  public void playerCheck() {
    disableButtons();
    System.out.println("Player checked");
    this.decision = "check";
    lbPlayerAction.setText("check");
    playerMadeDecision = true;
    updatePlayerValues("Check");
    sound.checkSound.play();

  }


  /**
   * Disables all buttons and shows player-frame's action as fold.
   */
  public void playerFold() {

    disableButtons();
    System.out.println("Player folded");
    this.decision = "fold";
    lbPlayerAction.setText("fold");
    playerMadeDecision = true;
    updatePlayerValues("Fold");
    sound.cardFold();
  }


  /**
   * Disables all buttons and shows player-frame's action as call, and the called amount. Calculates
   * and withdraws amount from player-pot.
   */
  public void playerCall() {

    disableButtons();
    System.out.println("Player call");
    this.playerPot -= (spController.getCurrentMaxBet() - alreadyPaid); // Player's pot - (Current
                                                                       // maxbet - already paid
                                                                       // (prev rounds)) = THE
                                                                       // PLAYER'S POT
    this.alreadyPaid += (spController.getCurrentMaxBet() - alreadyPaid); // Already paid + (Current
                                                                         // maxbet - already paid) =
                                                                         // WHAT THE PLAYER HAS
                                                                         // ALREADY PAID
    this.decision = "call," + Integer.toString(alreadyPaid);
    playerMadeDecision = true;
    sound.chipSingle();
    updatePlayerValues("Call, §" + Integer.toString(alreadyPaid));

  }


  /**
   * Disables all buttons and shows player-frame's action as raise, and the raised amount.
   * Calculates and withdraws amount from player-pot and adjusts already paid.
   */
  public void playerRaise() {

    disableButtons();
    int calcWithdraw = 0;

    if (spController.getCurrentMaxBet() != alreadyPaid) { // If the player hasn't matched the
                                                          // current maxbet
      calcWithdraw = spController.getCurrentMaxBet() - alreadyPaid; // Calculates how much the
                                                                    // player has to pay to match it
    }

    int raisedBet = (int) (slider.getValue());
    this.playerPot -= raisedBet + calcWithdraw; // The player's pot - (raised amount + the amount
                                                // the player has to match(if the player has to
                                                // match)) = THE PLAYER'S POT
    this.decision = "raise," + (raisedBet + spController.getCurrentMaxBet()); // Chosen raised
                                                                              // amount
    this.alreadyPaid += raisedBet + calcWithdraw; // Already paid + (raised amount + the amount the
                                                  // player has to match(if the player has to
                                                  // match)) = WHAT THE PLAYER HAS ALREADY PAID
    playerMadeDecision = true;
    sound.chipMulti();

    updatePlayerValues("Raise, §" + raisedBet);

    try {
      if (playerPot == 0) { // Checks if the player has gone all in.
        updatePlayerValues("All-In, §" + raisedBet);
        slider.setDisable(true);
        showAllIn();
        disableButtons();
      } else {
        updatePlayerValues("Raise, §" + raisedBet);
      }
    } catch (Exception e) {
    }
  }


  /**
   * Updates player-frame's labels (action and player pot) based on action.
   * 
   * @param action Call, Check, Raise or Fold
   */
  public void updatePlayerValues(String action) {

    lbPotValue.setText("§" + Integer.toString(playerPot));
    lbPlayerAction.setText(action);
    setSliderValues();
  }


  // public void newGame() {
  // // TODO Necessary?
  // System.out.println("new game");
  // }

  public void saveGame() {

    System.out.println("Saved Game");
    // TODO försök lista ut hur fan den ska spara.

    // try {
    // pot = new FileHandler(Integer.toString(playerPot));
    // pot.savePot();
    // System.out.println("pot from GUI:" + pot);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
  }


  /**
   * Sets the slider's min and max values based on the player-pot. Sets minimum sliderValue based on
   * BigBlind.
   */
  public void setSliderValues() {

    int calcWithdraw = 0;
    if (spController.getCurrentMaxBet() != alreadyPaid) { // If the player hasn't matched the
                                                          // current maxbet
      calcWithdraw = spController.getCurrentMaxBet() - alreadyPaid; // Calculates how much the
                                                                    // player has to pay to match it
    }

    slider.setMax(playerPot - calcWithdraw);
    if (spController.getBigBlind() <= playerPot) { // Sets minimum value required in order to raise.
      slider.setMin(spController.getBigBlind());
    } else {
      slider.setMin(0);
    }

    if ((slider.getMax() - slider.getMin()) > 4) {
      slider.setMajorTickUnit((slider.getMax() - slider.getMin()) / 4);
    } else {
      slider.setMajorTickUnit(25);
    }
    slider.setMinorTickCount(4);
  }


  /**
   * Triggers when the player uses the slider to choose raise amount.
   */
  public void sliderChange() {
    slider.valueProperty().addListener(e -> {
      raiseLabel.setText(String.valueOf((int) slider.getValue()));

    });
  }


  public void soundSetting() {

    // TODO Av-mutea, lägg till effektljud. Ny ruta med settings?
    if (sound.audio.getVolume() == 1.0) {
      sound.audio.setVolume(0);
      System.out.println("Volym : 0");
    } else if (sound.audio.getVolume() == 0.0) {
      sound.audio.setVolume(1);
      System.out.println("Volym : 1");
    }
    // sound.stopSound();



    System.out.println("Sound Setting");
  }


  public void rulesState() {

    // TODO Rules State?
    System.out.println("Go to Rules section");
  }


  public void mainState() {

    // TODO Return to main menu
    System.out.println("Go to Main section");
  }


  public double getPotValue() {

    // TODO Future use to show total table pot.
    System.out.println(tablePotValue);
    return tablePotValue;
  }


  /**
   * Sets the player's name.
   * 
   * @param name Used to sets the players name on the UI.
   */
  public void setUsername(String name) {
    userName.setText(name);
  }


  /**
   * Set Allin label visible
   */
  public void showAllIn() {
    lbAllIn.setVisible(true);
  }

  /**
   * Set Allin label deactive
   */
  public void hideAllIn() {
    lbAllIn.setVisible(false);
  }

  /**
   * Set slider active
   */
  public void activeSlider() {
    slider.setDisable(false);
  }


  /**
   * Clears AI action and updates the new and current AI-pot at the end of the round.
   * 
   * @param ai Which AI to update values for.
   */
  public void endOfRound(int ai) {

    Platform.runLater(new Runnable() {
      private volatile boolean shutdown;

      @Override
      public void run() {

        while (!shutdown) {
          setLabelUIAiBarPot(ai, Integer.toString(aiPlayers.get(ai).aiPot()));
          setLabelUIAiBarAction(ai, "");
          shutdown = true;
        }
      }
    });
  }


  /**
   * Sets the starting hand pre-flop for the player.
   * 
   * @param card1 First playercard in the hand.
   * @param card2 Second playercard in the hand.
   */
  public void setStartingHand(Card card1, Card card2) {
    isReady = false;
    Platform.runLater(() -> {
      clearFlopTurnRiver(); // Clears the table cards
    });

    Platform.runLater(() -> {
      for (int i = 0; i < 5; i++) { // Resets AI labels and removes all previous glow-effects.
        setUIAiStatus(i, "idle");
        setLabelUIAiBarAction(i, "");
      }
    });

    cards.clear(); // Clears previous hand
    this.card1 = card1;
    this.card2 = card2;

    highCard = card1.getCardValue();
    if (card2.getCardValue() > highCard) {
      highCard = card2.getCardValue();
    }
    cards.add(card1); // Adds two cards to hand.
    cards.add(card2);
    this.hand = new Hand(cards);

    isReady = true;
    checkHand();
    handHelp();
  }


  /**
   * Checks the player's hand and gives tips and highlights cards based on the method
   * getHighlightedCards (important during pre-flop situation).
   */
  public void checkHand() {
    Platform.runLater(() -> {

      hand.reCalc();
      playerCardsArea.requestLayout();
      playerCardsArea.getChildren().clear();
      String cardOne =
          "resources/images/" + card1.getCardValue() + card1.getCardSuit().charAt(0) + ".png";
      String cardTwo =
          "resources/images/" + card2.getCardValue() + card2.getCardSuit().charAt(0) + ".png";

      if (hand.getHighlightedCards()
          .contains(Integer.toString(card1.getCardValue()) + "," + card1.getCardSuit().charAt(0))) {
        cardOne =
            "resources/images/" + card1.getCardValue() + card1.getCardSuit().charAt(0) + "O.png";
      }

      if (hand.getHighlightedCards()
          .contains(Integer.toString(card2.getCardValue()) + "," + card2.getCardSuit().charAt(0))) {
        cardTwo =
            "resources/images/" + card2.getCardValue() + card2.getCardSuit().charAt(0) + "O.png";
      }

      Image imageTemp = null;
      ImageView imgCard1 = new ImageView(imageTemp);
      ImageView imgCard2 = new ImageView(imageTemp);

      Image image = new Image(Paths.get(cardOne).toUri().toString(), 114, 148, true, true);
      imgCard1 = new ImageView(image);
      playerCardsArea.getChildren().add(imgCard1);
      imgCard1.setX(0);
      imgCard1.setY(0);

      image = new Image(Paths.get(cardTwo).toUri().toString(), 114, 148, true, true);
      imgCard2 = new ImageView(image);
      playerCardsArea.getChildren().add(imgCard2);
      imgCard2.setX(105);
      imgCard2.setY(0);
      updatePlayerValues("");
    });
  }


  /**
   * Uses the getHighlightedCards to highlight and show cards on the table.
   * 
   * @param setOfCards Set of cards shown on the table.
   */
  public void setFlopTurnRiver(Card[] setOfCards) {

    this.cards = new ArrayList<Card>(); // Clears the cards list
    cards.add(card1); // Adds card one and card two (player's cards in the hand)
    cards.add(card2);

    for (Card c : setOfCards) {
      cards.add(c); // Adds cards from flop/turn/river
      System.out.println("creating new hand, card:" + c.getCardValue() + c.getCardSuit().charAt(0));
    }

    this.hand = new Hand(cards);
    hand.reCalc(); // Recalculates so the "new" set of cards gets highlighted

    Platform.runLater(() -> {
      tabelCardArea.getChildren().clear(); // Clears if there's cards on the table (UI)
      tabelCardArea.requestLayout();

      int xCord = 0;
      for (int i = 0; i < setOfCards.length; i++) { // Loops through all cards and highlights the
                                                    // correct ones and places them on the table
                                                    // (UI)
        String baseCard = "";
        if (hand.getHighlightedCards().contains(Integer.toString(setOfCards[i].getCardValue()) + ","
            + setOfCards[i].getCardSuit().charAt(0))) {
          baseCard = "resources/images/" + setOfCards[i].getCardValue()
              + setOfCards[i].getCardSuit().charAt(0) + "O.png";
        } else {
          baseCard = "resources/images/" + setOfCards[i].getCardValue()
              + setOfCards[i].getCardSuit().charAt(0) + ".png";
        }
        if (i == 1) {
          xCord = 105; // First card
        } else if (i > 1) {
          xCord += 105;
        }
        Image imageTemp = new Image(Paths.get(baseCard).toUri().toString(), 114, 148, true, true);

        collectionOfCardsTable[i] = new ImageView(imageTemp);
        tabelCardArea.getChildren().add(collectionOfCardsTable[i]);
        collectionOfCardsTable[i].setX(xCord);
        collectionOfCardsTable[i].setY(0);
      }
    });
    handHelp();
    checkHand();
  }


  /**
   * Clears the cards on the table.
   */
  public void clearFlopTurnRiver() {
    Platform.runLater(() -> {
      tabelCardArea.getChildren().clear();
    });
  }


  public void playerSmallBlind(int i) {
    this.alreadyPaid += i;
    System.out.println("Player paid small blind(" + i + ")");
    Platform.runLater(() -> {
      ivSmallBlind.relocate(520, 425);
    });
    // ivSmallBlind.setLayoutX(520);
    // ivSmallBlind.setLayoutY(425);

  }


  public void playerBigBlind(int i) {

    this.alreadyPaid += i;
    System.out.println("Player paid big blind(" + i + ")");
    Platform.runLater(() -> {
      ivBigBlind.relocate(520, 425);

    });

    // ivBigBlind.setLayoutX(520);
    // ivBigBlind.setLayoutY(425);
  }

  /**
   * Returns the amount of money that the player has already bet
   * 
   * @return The amount of money that the player has already bet
   */
  public int getPlayerAlreadyPaid() {
    return this.alreadyPaid;
  }


  public void playerIsDealer(int i) {
    ivDealer.setLayoutX(520);
    ivDealer.setLayoutY(425);
  }


  public void handHelp() {

    String powerBarWeakHand = "resources/images/weakHand.png";
    String powerBarMediumWeakHand = "resources/images/mediumWeakHand.png";
    String powerBarMediumStrongHand = "resources/images/mediumStrongHand.png";
    String powerBarStrongHand = "resources/images/StrongHand.png";

    Platform.runLater(() -> {

      String helpText = hand.theHelp();
      helpLabel.setText("Du har: \n" + helpText);
      String adviceText = hand.theAdvice();
      adviceLabel.setText("Råd: \n" + adviceText);

      powerBarValue = hand.toPowerBar();

      if (powerBarValue == 1) {
        powerBarArea.getChildren().remove(imgPowerBar);
        image = new Image(Paths.get(powerBarWeakHand).toUri().toString(), 120, 166, true, true);
        imgPowerBar = new ImageView(image);
        powerBarArea.getChildren().add(imgPowerBar);
        imgPowerBar.setX(15);
        imgPowerBar.setY(0);

      } else if (powerBarValue == 2) {
        powerBarArea.getChildren().remove(imgPowerBar);
        image =
            new Image(Paths.get(powerBarMediumWeakHand).toUri().toString(), 120, 166, true, true);
        imgPowerBar = new ImageView(image);
        powerBarArea.getChildren().add(imgPowerBar);
        imgPowerBar.setX(15);
        imgPowerBar.setY(0);

      } else if (powerBarValue == 3) {
        powerBarArea.getChildren().remove(imgPowerBar);
        image =
            new Image(Paths.get(powerBarMediumStrongHand).toUri().toString(), 120, 166, true, true);
        imgPowerBar = new ImageView(image);
        powerBarArea.getChildren().add(imgPowerBar);
        imgPowerBar.setX(15);
        imgPowerBar.setY(0);

      } else if (powerBarValue == 4) {
        powerBarArea.getChildren().remove(imgPowerBar);
        image = new Image(Paths.get(powerBarStrongHand).toUri().toString(), 120, 166, true, true);
        imgPowerBar = new ImageView(image);
        powerBarArea.getChildren().add(imgPowerBar);
        imgPowerBar.setX(15);
        imgPowerBar.setY(0);

      }
      this.handStrength = hand.getHandStrenght();

    });

  }


  /**
   * Returns the players decision.
   * 
   * @return The players decision.
   */
  public String getPlayerDecision() {
    return decision;
  }


  public String askForPlayerDecision() {


    // Platform.runLater(new Runnable() {
    //
    // private volatile boolean shutdown;
    //
    //
    // @Override
    // public void run() {
    //
    // while (!shutdown) {
    // try {
    // Thread.sleep(1500);
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // shutdown = true;
    //
    // }
    // }
    // });

    // TODO Fixa de buttons to be correcto
    handleButtons();
    System.out.println(spController.getCurrentMaxBet());
    System.out.println(alreadyPaid);
    playerMadeDecision = false;
    while (!playerMadeDecision) {
      try {
        SPController.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return decision;
  }


  public void playerReset(String resetDecision) {

    decision = resetDecision;
    alreadyPaid = 0;
    cards = new ArrayList<Card>();
  }


  /**
   * Sets the new player-pot.
   * 
   * @param newValue The value to add/remove from the player-pot.
   */
  public void setPlayerPot(int newValue) {
    this.playerPot += newValue;
  }


  /**
   * Shows/hides player-buttons based on allowed actions.
   */
  public void handleButtons() {

    if (alreadyPaid == spController.getCurrentMaxBet()) {
      // show check, hide all other
      System.out.println("show check, hide all other,         1");
      btCheck.setVisible(true);
      btCall.setVisible(false);
      btRaise.setVisible(true);
      btFold.setVisible(true);
    } else {
      if (alreadyPaid < spController.getCurrentMaxBet()
          && (playerPot + alreadyPaid) >= spController.getCurrentMaxBet()) {
        // 10 < max && playerPot + paid >= max
        System.out.println("hide check, show call,         2");
        // hide check, show call
        btCheck.setVisible(false);
        btCall.setVisible(true);
        btFold.setVisible(true);
      } else {
        // System.out.println("how is this a thing?");
        System.out.println("hide call, hide check,         3");
        // hide call, hide check
        btCheck.setVisible(false);
        btCall.setVisible(false);
        btFold.setVisible(true);

      }

      if ((spController.getCurrentMaxBet() - alreadyPaid) + spController.getBigBlind() <= playerPot
          && playerPot != 0) {
        // show raise
        System.out.println("show raise,         4");
        btRaise.setVisible(true);
      } else {
        // hide raise
        System.out.println("hide raise,         5");
        btRaise.setVisible(false);
      }
    }
    System.out.println(alreadyPaid + "{ " + spController.getCurrentMaxBet());
    inactivateAllAiCardGlows();
  }


  /**
   * Disables all player-buttons.
   */
  public void disableButtons() {
    btCall.setVisible(false);
    btRaise.setVisible(false);
    btCheck.setVisible(false);
    btFold.setVisible(false);
  }


  public int getHandStrength() {
    return handStrength;
  }


  public int getPlayerPot() {
    return playerPot;
  }


  public void removeAiPlayer(int AI) {
    collectionOfLabelsAi[AI][0].setVisible(true);
    collectionOfLabelsAi[AI][1].setVisible(false);
    collectionOfLabelsAi[AI][2].setText("Lost");
    collectionOfLabelsAi[AI][2].setVisible(true);
    collectionOfCardsAi[AI].setVisible(false);
  }


  /**
   * Places the AI-players in the correct position depending on chosen number of players.
   * 
   * @param aiPlayers All the AI-players that are active.
   * @param notFirstRound
   * @param deadAIIndex
   */
  public void setAiPlayers(LinkedList<Ai> aiPlayers, boolean notFirstRound, int deadAIIndex) {
    this.aiPlayers = aiPlayers;
    int totalAI = spController.getFixedNrOfAIs();
    if (!notFirstRound) {
      if (totalAI == 1) {
        setShowUIAiBar(2);
      } else if (totalAI == 3) {
        setShowUIAiBar(0);
        setShowUIAiBar(2);
        setShowUIAiBar(4);
      } else if (totalAI == 5) {
        setShowUIAiBar(0);
        setShowUIAiBar(1);
        setShowUIAiBar(2);
        setShowUIAiBar(3);
        setShowUIAiBar(4);
      }
    } else if (notFirstRound) {
      endOfRound(deadAIIndex);
    }
  }


  /**
   * Updates AI-frame based on currentAI-position and decision with the method setUIAiStatus.
   * 
   * @param currentAI Chosen AI to update AI-frame
   * @param decision Check, call, fold, raise or lost
   */
  public void aiAction(int currentAI, String decision) {
    int setAINr = spController.getFixedNrOfAIs();

    int setOfPlayers = 0; // Is used for choosing the correct set of positioning (see
                          // aiPositions[][])

    // Decides (based on chosen AI-players) which position to place the AI at
    if (setAINr == 1) {
      setOfPlayers = 0;
    } else if (setAINr == 3) {
      setOfPlayers = 1;
    } else if (setAINr == 5) {
      setOfPlayers = 2;
    }

    int currentAIPosition = aiPositions[setOfPlayers][currentAI];
    // System.out.println("Current position: " + currentAIPosition + " SET: " + setOfPlayers + "
    // currentAI: " + currentAI + " prevActivePlayer: " + prevPlayerActive + " currentAIPosition: "
    // + currentAIPosition);

    if (prevPlayerActive != -1) { // If there does exists a previous active AI-player
      setUIAiStatus(prevPlayerActive, "idle"); // Resets the previous player's image from
                                               // glowing(active) to non-glowning(idle)
    }

    Ai ai = aiPlayers.get(currentAI);

    if (decision.contains("fold") || decision.contains("lost")) {
      setUIAiStatus(currentAIPosition, "inactive");
    } else {
      setUIAiStatus(currentAIPosition, "active");
      this.prevPlayerActive = currentAIPosition;
    }

    Platform.runLater(new Runnable() {

      private volatile boolean shutdown;

      @Override
      public void run() {

        /**
         * Sets name, pot and action for the AI's (UI)
         */
        while (!shutdown) {
          System.out.println("Loop... Thread!");
          setLabelUIAiBarName(currentAIPosition, ai.getName());
          setLabelUIAiBarPot(currentAIPosition, Integer.toString(ai.aiPot()));
          setLabelUIAiBarAction(currentAIPosition, getFormattedDecision(decision));
          shutdown = true;
        }
      }
    });
  }


  /**
   * Formats action label for AI.
   * 
   * @param decision fold/lost/check/call/raise/all-in/Dealer/SmallBlind/BigBlind
   * @return Formatted decision
   */
  public String getFormattedDecision(String decision) {

    String actionText = "Error";

    if (decision.contains("fold")) {
      actionText = "Fold";
    } else if (decision.contains("lost")) {
      actionText = "Lost";
    } else if (decision.contains("check")) {
      actionText = "Check";
    } else if (decision.contains("call")) {
      actionText = "Call";
    } else if (decision.contains("raise")) {
      String[] decisionAi = decision.split(",");
      actionText = "Raise, §" + decisionAi[1];
    } else if (decision.contains("all-in")) {
      actionText = "All-In";
    } else if (decision.contains("Dealer")) {
      actionText = "Dealer";
    } else if (decision.contains("SmallBlind")) {
      actionText = "Small Blind, §" + spController.getSmallBlind();
    } else if (decision.contains("BigBlind")) {
      actionText = "Big Blind, §" + spController.getBigBlind();
    }

    return actionText;
  }

  /**
   * This metod makes sure that during the players turn, the previous AI is considered idle
   */
  public void inactivateAllAiCardGlows() {
    if (prevPlayerActive != -1) {
      setUIAiStatus(prevPlayerActive, "idle");
      this.prevPlayerActive = -1;
    }
  }


  public void closeProgram() {

    System.exit(0);
  }


  public void goToMainMenu() throws InstantiationException, IllegalAccessException {

    try {
      changeScene.switchToMainMenu();
      changeScene.prepGame();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }


  public void aboutBox() {

    confirmBox = new ConfirmBox();
    confirmBox.display("Om projektet",
        "Detta projekt är format och skapat av "
            + "Vedrana Zeba, Rikard Almgren, Amin Harirchian, Max Frennessen och Lykke Levin under "
            + "vårterminen 2017 som en del av kursen Systemutveckling och projekt 1.");

  }


  public boolean getIsReady() {

    return isReady;
  }


  public void playerLost() throws InstantiationException, IllegalAccessException {

    try {
      changeScene.switchToMainMenu();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // TODO make player lose
    // TODO return to main menu

  }


  public int getGetHighCard() {
    return highCard;
  }


  public void setTablePot() {

    // TODO Auto-generated method stub

  }


  public void setBlindsMarker(int dealer) {
    Platform.runLater(() -> {
      for (Ai ai : aiPlayers) {
        System.out.println(ai.getName() + " is small blind? " + ai.getIsSmallBlind());
        System.out.println(ai.getName() + " is big blind? " + ai.getIsBigBlind());

      }
      // TODO fortsätt

      if (aiPlayers.size() == 5) {
        if (dealer == 0) {
          ivDealer.relocate(300, 360);
          ivSmallBlind.relocate(375, 172);
          ivBigBlind.relocate(745, 172);

        } else if (dealer == 1) {
          ivDealer.relocate(375, 172);
          ivSmallBlind.relocate(745, 172);
          ivBigBlind.relocate(1010, 220);

        } else if (dealer == 2) {
          ivDealer.relocate(745, 172);
          ivSmallBlind.relocate(1010, 220);
          ivBigBlind.relocate(1010, 360);

        } else if (dealer == 3) {
          ivDealer.relocate(1010, 220);
          ivSmallBlind.relocate(1010, 360);

        } else if (dealer == 4) {
          ivDealer.relocate(1010, 360);
          ivBigBlind.relocate(300, 360);

        } else {
          ivSmallBlind.relocate(300, 360);
          ivBigBlind.relocate(375, 172);
        }
      }

      if (aiPlayers.size() == 3) {

        if (dealer == 0) {
          ivDealer.relocate(300, 360);
          ivSmallBlind.relocate(745, 172);
          ivBigBlind.relocate(1010, 360);

        } else if (dealer == 1) {
          ivDealer.relocate(745, 172);
          ivSmallBlind.relocate(1010, 360);

        } else if (dealer == 2) {
          ivDealer.relocate(1010, 360);
          ivBigBlind.relocate(300, 360);

        } else {
          // ivDealer.relocate(520, 425);
          ivSmallBlind.relocate(300, 360);
          ivBigBlind.relocate(745, 172);
        }
      }


      // if (aiPlayers.size() == 5) {
      // if (dealer == 0) {
      // ivDealer.relocate(300, 360);
      // ivSmallBlind.relocate(375, 172);
      // ivBigBlind.relocate(745, 172);
      //
      // } else if (dealer == 1) {
      // ivDealer.relocate(375, 172);
      // ivSmallBlind.relocate(745, 172);
      // ivBigBlind.relocate(1010, 220);
      //
      // } else if (dealer == 2) {
      // ivDealer.relocate(745, 172);
      // ivSmallBlind.relocate(1010, 220);
      // ivBigBlind.relocate(1010, 360);
      //
      // } else if (dealer == 3) {
      // ivDealer.relocate(1010, 220);
      // ivSmallBlind.relocate(1010, 360);
      //
      // } else if (dealer == 4) {
      // ivDealer.relocate(1010, 360);
      // ivBigBlind.relocate(300, 360);
      //
      // } else {
      // ivSmallBlind.relocate(300, 360);
      // ivBigBlind.relocate(375, 172);
      // }
      // }
      //
      // if (aiPlayers.size() == 3) {
      //
      // if (dealer == 0) {
      // ivDealer.relocate(300, 360);
      // ivSmallBlind.relocate(745, 172);
      // ivBigBlind.relocate(1010, 360);
      //
      // } else if (dealer == 1) {
      // ivDealer.relocate(745, 172);
      // ivSmallBlind.relocate(1010, 360);
      //
      // } else if (dealer == 2) {
      // ivDealer.relocate(1010, 360);
      // ivBigBlind.relocate(300, 360);
      //
      // } else {
      // // ivDealer.relocate(520, 425);
      // ivSmallBlind.relocate(300, 360);
      // ivBigBlind.relocate(745, 172);
      // }
      // }
    });
  }


  /**
   * Shows current round.
   * 
   * @param round int between 0-3 ("roundPreFlop", "roundFlop", "roundTurn", "roundRiver").
   */
  public void roundStatus(int round) {
    String[] roundStatus = new String[] {"roundPreFlop", "roundFlop", "roundTurn", "roundRiver"};

    Platform.runLater(() -> {
      paneRounds.getChildren().remove(imgRoundStatus);
      Image tempImage =
          new Image(Paths.get("resources/images/" + roundStatus[round] + ".png").toUri().toString(),
              175, 56, true, true);
      imgRoundStatus = new ImageView(tempImage);
      imgRoundStatus.setImage(tempImage);
      imgRoundStatus.setPreserveRatio(false);
      paneRounds.getChildren().add(imgRoundStatus);
    });
  }

}
