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
  private Hand tableCards;
  private int bet;
  private int tablePotValue = 2000; // just for testing, its coming from
  // controller
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
  private int sliderValue;
  private LinkedList<Ai> aiPlayers;
  private Label[][] collectionOfLabelsAi;
  private ImageView[] collectionOfCardsAi;
  private ImageView[] collectionOfCardsTable;
  private int[][] aiPositions;
  private int highCard;


  public void initialize() throws Exception {

    this.collectionOfLabelsAi =
        new Label[][] {{labelPlayerOneName, labelPlayerOnePot, labelPlayerOneAction},
            {labelPlayerTwoName, labelPlayerTwoPot, labelPlayerTwoAction},
            {labelPlayerThreeName, labelPlayerThreePot, labelPlayerThreeAction},
            {labelPlayerFourName, labelPlayerFourPot, labelPlayerFourAction},
            {labelPlayerFiveName, labelPlayerFivePot, labelPlayerFiveAction}};

    this.collectionOfCardsAi = new ImageView[] {imgPlayerOneCards, imgPlayerTwoCards,
        imgPlayerThreeCards, imgPlayerFourCards, imgPlayerFiveCards};

    this.aiPositions = new int[][] {{2}, {0, 2, 4}, {0, 1, 2, 3, 4, 5}};


    this.collectionOfCardsTable =
        new ImageView[] {imgCard3, imgCard4, imgCard5, imgCard6, imgCard7};
    this.tableCards = null;

  }


  public void setShowUIAiBar(int position) {

    collectionOfLabelsAi[position][0].setVisible(true);
    collectionOfLabelsAi[position][1].setVisible(true);
    collectionOfLabelsAi[position][2].setVisible(true);

    collectionOfCardsAi[position].setVisible(true);
  }


  public void setLabelUIAiBarName(int position, String name) {

    collectionOfLabelsAi[position][0].setText(name);
  }


  public void setLabelUIAiBarPot(int position, String pot) {

    collectionOfLabelsAi[position][1].setText(pot);
  }


  public void setLabelUIAiBarAction(int position, String action) {

    collectionOfLabelsAi[position][2].setText(action);
  }


  public void setUIAiFolded(int position, boolean folded) {

    String resource = "resources/images/";

    Image showCards = new Image(Paths.get(resource + "aiBarWithCards.png").toUri().toString(), 122,
        158, true, true);
    Image foldCards = new Image(Paths.get(resource + "aiBarWithoutCards.png").toUri().toString(),
        122, 158, true, true);

    if (folded == true) {
      collectionOfCardsAi[position].setImage(showCards);

    } else {
      collectionOfCardsAi[position].setImage(foldCards);
    }
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
    this.decision = "check";
    lbPlayerAction.setText("Check");
    playerMadeDecision = true;
    updatePlayerValues(decision);
  }


  public void playerFold() {

    disableButtons();
    System.out.println("Player folded");
    this.decision = "fold";
    lbPlayerAction.setText("Fold");
    playerMadeDecision = true;
    updatePlayerValues(decision);
  }


  @FXML
  public void playerCall() {

    disableButtons();

    System.out.println("Player call");
    this.playerPot -= (spController.getCurrentMaxBet() - alreadyPaid);
    this.alreadyPaid = alreadyPaid + (spController.getCurrentMaxBet() - alreadyPaid);
    this.decision = "call," + Integer.toString(alreadyPaid);
    playerMadeDecision = true;
    updatePlayerValues(decision);
  }


  public void updatePlayerValues(String action) {

    lbPotValue.setText(" §" + Integer.toString(playerPot));
    lbPlayerAction.setText(action);
    setSliderValues();
  }


  public void playerRaise() {

    disableButtons();

    this.playerPot -= (int) slider.getValue();

    this.decision = "raise," + ((int) slider.getValue() + alreadyPaid);
    this.alreadyPaid = ((int) slider.getValue() + alreadyPaid);
    playerMadeDecision = true;
    bet = (int) (slider.getValue());
    updatePlayerValues(decision);
    try {
      tablePotValue = spController.getPotSize();
      
      if (tablePotValue >= bet) {
        lbPlayerAction.setText("Raise" + bet + " §");
        lbPotValue.setText("" + playerPot + " §");
      } else if (bet == playerPot) {
        lbPlayerAction.setText("ALL IN");
        lbPotValue.setText("" + 0 + " §");
        playerPot = 0;
        // slider.setMax(tablePotValue + bet);
        slider.setDisable(true);
        showAllIn();
        disableButtons();
      } else if (bet > playerPot) {
        playerPot = 0;
        lbPlayerAction.setText("ALL IN");
        lbPotValue.setText("" + 0 + " §");
        checkHand();
      } else {
        lbPotValue.setText("0.0");
        showAllIn();
        slider.setDisable(true);
      }
    } catch (Exception e) {
    }
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


  public void setSliderValues() {

    slider.setMax(playerPot);
    if ((spController.getCurrentMaxBet() - alreadyPaid)
        + (spController.getCurrentMaxBet() + spController.getBigBlind()) <= playerPot) {
      slider.setMin(spController.getCurrentMaxBet() + spController.getBigBlind());
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


  public void sliderChange() {

    int val = 0;
    val = (int) slider.getValue();
    this.sliderValue = val;
    slider.valueProperty().addListener(e -> {
      raiseLabel.setText(String.valueOf((int) slider.getValue()));

    });

    // ****************** if we like to show the bet value in the text field
    // ******************
    // field.setText(""+val);
    // slider.setValue((int)(INIT_VALUE));
    // field.setText(new Integer((int)(INIT_VALUE)).toString());
    // field.textProperty().bindBidirectional(slider.valueProperty(),
    // NumberFormat.getNumberInstance());
    // ****************************************************************************************
  }


  public void soundSetting() {

    // TODO Av-mutea, lägg till effektljud. Ny ruta med settings?
    Sound.mp.setMute(true);

    if (Sound.mp.isMute() == true) {
      Sound.mp.play();
    }
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

    // TODO Find out what this is for
    System.out.println(tablePotValue);
    return tablePotValue;
  }


  // restart all the images
  // TODO behövs?
  // public void nextRound() {
  //
  // image = null;
  // imgCard1.setImage(null);
  // imgCard2.setImage(null);
  // imgCard3.setImage(null);
  // imgCard4.setImage(null);
  // imgCard5.setImage(null);
  // imgCard6.setImage(null);
  // imgCard7.setImage(null);
  // imgPowerBar.setImage(null);
  // tabelCardArea.requestLayout();
  //
  // }

  public void setUsername(String name) {

    userName.setText(name);

  }


  // TODO Vet inte?
  public void showAllIn() {

    lbAllIn.setVisible(true);
  }


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


  public void setStartingHand(Card card1, Card card2) {

    isReady = false;


    Platform.runLater(() -> {
      clearFlopTurnRiver();


    });


    Platform.runLater(() -> {


      for (int i = 0; i < 5; i++) {
        setUIAiFolded(i, true);
        setLabelUIAiBarAction(i, "");
      }
    });

    cards.clear();
    this.card1 = card1;
    System.out.println("----------------" + card1.getCardValue());
    this.card2 = card2;
    System.out.println("----------------" + card2.getCardValue());

    highCard = card1.getCardValue();
    if (card2.getCardValue() > highCard) {
      highCard = card2.getCardValue();
    }
    cards.add(card1);
    cards.add(card2);
    this.hand = new Hand(cards);

    handHelp();
    isReady = true;
    checkHand();

  }


  public void checkHand() {

    Platform.runLater(() -> {

      Hand tempHand = hand;

      tempHand.reCalc();
      // System.out.println("X-------------------------");
      // System.out.println(tempHand.sendToHighlightChecker());
      // System.out.println(tempHand.sendToHighlight());
      // System.out.println("X-------------------------");
      playerCardsArea.requestLayout();
      playerCardsArea.getChildren().clear();
      String cardOne =
          "resources/images/" + card1.getCardValue() + card1.getCardSuit().charAt(0) + ".png";
      String cardTwo =
          "resources/images/" + card2.getCardValue() + card2.getCardSuit().charAt(0) + ".png";

      if (tempHand.sendToHighlightChecker().contains(Integer.toString(card1.getCardValue()))) {
        // System.out.println("-------------------------");
        // System.out.println("cardONE TRUE!!! " +
        // Integer.toString(card1.getCardValue()) + " || " +
        // tempHand.sendToHighlightChecker());
        // System.out.println("-------------------------");
        cardOne =
            "resources/images/" + card1.getCardValue() + card1.getCardSuit().charAt(0) + "O.png";
      } else {
        // System.out.println("-------------------------");
        // System.out.println("cardONE False!!! " +
        // Integer.toString(card1.getCardValue()) + " || " +
        // tempHand.sendToHighlightChecker());
        // System.out.println("-------------------------");
        // cardTwo = "resources/images/" + card2.getCardValue() +
        // card2.getCardSuit().charAt(0) + ".png";
      }

      if (tempHand.sendToHighlightChecker().contains(Integer.toString(card2.getCardValue()))) {
        cardTwo =
            "resources/images/" + card2.getCardValue() + card2.getCardSuit().charAt(0) + "O.png";
      } else {
        // System.out.println("-------------------------");
        // System.out.println("cardTwocardTwo False!!! " +
        // Integer.toString(card2.getCardValue()) + " || " +
        // tempHand.sendToHighlightChecker());
        // System.out.println("-------------------------");
        // cardTwo = "resources/images/" + card2.getCardValue() +
        // card2.getCardSuit().charAt(0) + ".png";
      }

      // IF PAIR IN HAND
      if (card1.getCardValue() == card2.getCardValue()) {
        cardOne =
            "resources/images/" + card1.getCardValue() + card1.getCardSuit().charAt(0) + "O.png";
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


  public void setFlopTurnRiver(Card[] setOfCards) {

    this.cards.clear();
    ArrayList<Card> tempCard = new ArrayList<Card>();
    this.cards = new ArrayList<Card>();

    tempCard.add(card1);
    tempCard.add(card2);
    for (Card c : setOfCards) {
      cards.add(c);
      tempCard.add(c);
      System.out.println(cards.size());
      System.out.println("creating new hand, card:" + c.getCardValue() + c.getCardSuit().charAt(0));
    }
    this.tableCards = new Hand(tempCard);
    this.hand = new Hand(tempCard);
    tableCards.reCalc();
    hand.reCalc();
    Platform.runLater(() -> {
      tabelCardArea.getChildren().clear();
      tabelCardArea.requestLayout();
      int xCord = 0;
      for (int i = 0; i < setOfCards.length; i++) {
        String baseCard = "";
        if (tableCards.sendToHighlightChecker()
            .contains(Integer.toString(setOfCards[i].getCardValue()))) {
          baseCard = "resources/images/" + setOfCards[i].getCardValue()
              + setOfCards[i].getCardSuit().charAt(0) + "O.png";
        } else {
          baseCard = "resources/images/" + setOfCards[i].getCardValue()
              + setOfCards[i].getCardSuit().charAt(0) + ".png";
        }
        if (i == 1) {
          xCord = 105;
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


  public ImageView getNow(int i) {

    return this.collectionOfCardsTable[i];
  }


  public void clearFlopTurnRiver() {

    Platform.runLater(() -> {
      tabelCardArea.getChildren().clear();
    });

  }


  public void playerSmallBlind(int i) {

    this.alreadyPaid += i;
    System.out.println("Player paid small blind(" + i + ")");
    Platform.runLater(() -> {
    ivSmallBlind.setLayoutX(520);
    ivSmallBlind.setLayoutY(425);
    });

  }


  public void playerBigBlind(int i) {

    this.alreadyPaid += i;
    System.out.println("Player paid big blind(" + i + ")");
    Platform.runLater(() -> {
      ivSmallBlind.setLayoutX(520);
      ivSmallBlind.setLayoutY(425);
      });
  }


  public int getPlayerAlreadyPaid() {

    return this.alreadyPaid;
  }


  public void playerIsDealer() {

    ivDealer.setLayoutX(520);
    ivDealer.setLayoutY(425);
  }


  public void handHelp() {

    String powerBarWeakHand = "resources/images/weakHand.png";
    String powerBarMediumWeakHand = "resources/images/mediumWeakHand.png";
    String powerBarMediumStrongHand = "resources/images/mediumStrongHand.png";
    String powerBarStrongHand = "resources/images/StrongHand.png";

    Platform.runLater(() -> {
      if (tableCards != null) {
        String helpText = tableCards.theHelp();
        helpLabel.setText("Du har: \n" + helpText);
        String adviceText = tableCards.theAdvice();
        adviceLabel.setText("Råd: \n" + adviceText);

        powerBarValue = hand.toPowerBar();
      } else {
        String helpText = hand.theHelp();
        helpLabel.setText("Du har: \n" + helpText);
        String adviceText = hand.theAdvice();
        adviceLabel.setText("Råd: \n" + adviceText);

        powerBarValue = hand.toPowerBar();
      }

      powerBarValue = hand.toPowerBar();
      if (powerBarValue == 1) {
        powerBarArea.getChildren().remove(imgPowerBar);
        image = new Image(Paths.get(powerBarWeakHand).toUri().toString(), 120, 166, true, true);
        imgPowerBar = new ImageView(image);
        powerBarArea.getChildren().add(imgPowerBar);
        imgPowerBar.setX(30);
        imgPowerBar.setY(15);

      } else if (powerBarValue == 2) {
        powerBarArea.getChildren().remove(imgPowerBar);
        image =
            new Image(Paths.get(powerBarMediumWeakHand).toUri().toString(), 120, 166, true, true);
        imgPowerBar = new ImageView(image);
        powerBarArea.getChildren().add(imgPowerBar);
        imgPowerBar.setX(30);
        imgPowerBar.setY(15);

      } else if (powerBarValue == 3) {
        powerBarArea.getChildren().remove(imgPowerBar);
        image =
            new Image(Paths.get(powerBarMediumStrongHand).toUri().toString(), 120, 166, true, true);
        imgPowerBar = new ImageView(image);
        powerBarArea.getChildren().add(imgPowerBar);
        imgPowerBar.setX(30);
        imgPowerBar.setY(15);

      } else if (powerBarValue == 4) {
        powerBarArea.getChildren().remove(imgPowerBar);
        image = new Image(Paths.get(powerBarStrongHand).toUri().toString(), 120, 166, true, true);
        imgPowerBar = new ImageView(image);
        powerBarArea.getChildren().add(imgPowerBar);
        imgPowerBar.setX(30);
        imgPowerBar.setY(15);

      }
      this.handStrength = hand.getHandStrenght();

    });

  }

  public String getPlayerDecision() {

    return decision;
  }


  public String askForPlayerDecision() {

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


  public void setPlayerPot(int newValue) {

    this.playerPot += newValue;
  }


  public void handleButtons() {

    if (alreadyPaid == spController.getCurrentMaxBet()) {
      // show check, hide all other
      btCheck.setVisible(true);
      btCall.setVisible(false);
      btRaise.setVisible(true);
      btFold.setVisible(true);
    } else {
      if (alreadyPaid < spController.getCurrentMaxBet()
          && (playerPot+alreadyPaid) >= spController.getCurrentMaxBet()) {
        // hide check, show call
        btCheck.setVisible(false);
        btCall.setVisible(true);
        btFold.setVisible(true);
      } else {
        System.out.println("how is this a thing?");
        // hide call, hide check
        btCheck.setVisible(false);
        btCall.setVisible(false);
        btFold.setVisible(true);

      }
      if ((spController.getCurrentMaxBet() - alreadyPaid)
          + (int) (spController.getCurrentMaxBet() + spController.getBigBlind()) <= playerPot) {
        // show raise
        btRaise.setVisible(true);
      } else {
        // hide raise
        btRaise.setVisible(false);
      }
    }
  }


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


  public void setAiPlayers(LinkedList<Ai> aiPlayers, boolean notFirstRound, int deadAIIndex) {

    this.aiPlayers = aiPlayers;

    int totalAI = aiPlayers.size();
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


  public void setPlayerAtTable() {

  }


  public void aiAction(int currentAI, String decision) {

    Ai ai = aiPlayers.get(currentAI);
    if (decision.contains("fold")) {
      setUIAiFolded(currentAI, false);
    } else {
      setUIAiFolded(currentAI, true);
    }

    int setOfPlayers = 0;
    if (aiPlayers.size() == 1) {
      setOfPlayers = 0;
    } else if (aiPlayers.size() == 3) {

      setOfPlayers = 1;
    } else if (aiPlayers.size() == 5) {
      setOfPlayers = 2;
    }

    int currentAIPosition = aiPositions[setOfPlayers][currentAI];

    Platform.runLater(new Runnable() {

      private volatile boolean shutdown;


      @Override
      public void run() {

        while (!shutdown) {
          System.out.println("Loop... Thread!");
          setLabelUIAiBarName(currentAIPosition, ai.getName());
          setLabelUIAiBarPot(currentAIPosition, Integer.toString(ai.aiPot()));
          setLabelUIAiBarAction(currentAIPosition, decision);
          shutdown = true;

        }
      }
    });
  }

  public void closeProgram() {

    System.exit(0);
  }


  public void goToMainMenu() {

    try {
      changeScene.switchToMainMenu();
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


  public void playerLost() {
    
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


  public void setBlindsMarker(int smallBlindPlayer, int bigBlindPlayer) {

    if (smallBlindPlayer == 0) {

      System.out.println("OKEEEEEJJJJJ 0");
      ivSmallBlind.setLayoutX(100);
      ivSmallBlind.setLayoutY(100);
    } else if (smallBlindPlayer == 1) {
      System.out.println("OKEEEEEJJJJJ 1");
      ivSmallBlind.setLayoutX(600);
      ivSmallBlind.setLayoutY(600);

    } else if (smallBlindPlayer == 2) {
      System.out.println("OKEEEEEJJJJJ 2");
      ivSmallBlind.setLayoutX(800);
      ivSmallBlind.setLayoutY(700);

    } else if (smallBlindPlayer == 3) {
      System.out.println("OKEEEEEJJJJJ 3");
      ivSmallBlind.setLayoutX(670);
      ivSmallBlind.setLayoutY(542);

    } else if (smallBlindPlayer == 4) {
      System.out.println("OKEEEEEJJJJJ 4");
      ivSmallBlind.setLayoutX(392);
      ivSmallBlind.setLayoutY(570);

    }
  }
}
