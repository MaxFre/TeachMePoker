package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import aiClass.Ai;
import deck.Card;
import deck.Deck;
import gui.GameController;


/**
 *
 * 
 * @author Rikard Almgren
 * @version 0.9
 *
 */
public class SPController extends Thread {

  private Deck deck;
  private LinkedList<Ai> aiPlayers = new LinkedList<Ai>();
  private int noOfAi;
  private int playTurn = 0;
  private int dealer = 0;
  private int currentPlayer = 0;
  private int bigBlindPlayer;
  private int smallBlindPlayer;
  private int smallBlind;
  private int bigBlind = 10;
  private int potSize;
  private int currentPotSize;
  private int currentMaxBet;
  private int blindCounter;
  private Card card1;
  private Card card2;
  private Card turn;
  private Card river;
  private Card[] flop = new Card[3];
  private int noOfPlayers = 0;
  private boolean allCalledorFolded = false;
  private boolean winnerDeclared = false;
  private ArrayList<String> name = new ArrayList<String>();
  private GameController gController;
  private int fixedNrOfAIs;
  private int[] potSplits;
  private boolean doAllInCheck;


  /**
   * Method which receives and sets a number of starting variables and prepares for the game to be set up.
   * 
   * @param noOfAi Number of AI-players
   * @param potSize The potsize for the table(game).
   * @param playerName The players' name.
   */
  public void startGame(int noOfAi, int potSize, String playerName) {

    this.fixedNrOfAIs = noOfAi;
    gController.disableButtons();
    this.potSize = potSize;
    this.noOfAi = noOfAi;
    setNames();
    noOfPlayers = noOfAi + 1;
    bigBlind = (int) (potSize / noOfPlayers * 0.02);
    if (bigBlind < 2) {
      bigBlind = 2;
    }
    currentMaxBet = bigBlind;
    this.smallBlind = bigBlind / 2;
    gController.setPlayerPot((potSize / noOfPlayers));
    for (int i = 0; i < noOfAi; i++) {
      aiPlayers.add(new Ai(potSize / (noOfPlayers), name.remove(0)));
    }
    gController.setAiPlayers(aiPlayers, false, 69);
    potSplits = new int[noOfPlayers];

    try {
      setupPhase();
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }


  /**
   * Method which sets a GameController, the controller that controls the GUI while the game is
   * running.
   * 
   * @param gController An instance of GameController
   */
  public void setGameController(GameController gController) {

    this.gController = gController;

  }


  /**
   * Method which returns the current max bet for the table.
   * 
   * @return currentMaxbet the current max bet
   */
  public int getCurrentMaxBet() {

    return currentMaxBet;
  }


  /**
   * Method which returns the current potsize.
   * 
   * @return potSize The pot.
   */
  public int getPotSize() {

    return potSize;
  }


  /**
   * Method that creates a list of names for AI-Players to pull from
   */
  public void setNames() {

    name.add("Max");
    name.add("Vedrana");
    name.add("Lykke");
    name.add("Amin");
    name.add("Rikard");
    name.add("Kristina");
    name.add("Rolf");
    Collections.shuffle(name);
  }


  /**
   * Method which prepares a new gameround.
   * 
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private void setupPhase() throws InstantiationException, IllegalAccessException {

    if (gController.getPlayerPot() > bigBlind) {
      deck = new Deck();
      deck.shuffle();
      card1 = deck.getCard();
      card2 = deck.getCard();
      gController.setStartingHand(card1, card2);
      for (Ai ai : aiPlayers) {
        ai.setBigBlind(0, false);
        ai.setSmallBlind(0, false);
        ai.setPaidThisTurn(0);
        gController.playerReset("");
        if (!ai.getDecision().contains("lost")) {
          ai.setDecision("");
          card1 = deck.getCard();
          card2 = deck.getCard();
          ai.setStartingHand(card1, card2);
        }
      }
      setBlinds(noOfPlayers);
      for (int i = 0; i < flop.length; i++) {
        flop[i] = deck.getCard();
      }
      turn = deck.getCard();
      river = deck.getCard();
      if (!this.isAlive()) {
        start();
      } else {
        run();
      }
    } else {
      gController.playerLost();
      // TODO player loses popup?
    }

  }


  /**
   * Method that runs the gameround itself
   */
  public void run() {

    gController.hideAllIn();
    gController.activeSlider();

    Card[] turnCards = {flop[0], flop[1], flop[2], turn};
    Card[] riverCards = {flop[0], flop[1], flop[2], turn, river};
    while (playTurn < 4) {
      System.out.println("Current turn: " + playTurn);
      gController.roundStatus(playTurn);

      if (playTurn == 0) {
        int playerNr = noOfPlayers - 1;
        if (playerNr != 1) {
          try {
            if (dealer != playerNr) {
              Thread.sleep(1000);
              gController.aiAction(dealer, "Dealer");
            }
            if (smallBlindPlayer != playerNr) {
              Thread.sleep(1000);
              gController.aiAction(smallBlindPlayer, "SmallBlind");
            }
            if (bigBlindPlayer != playerNr) {
              Thread.sleep(1000);
              gController.aiAction(bigBlindPlayer, "BigBlind");
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      } else if (playTurn == 1) {
        gController.setFlopTurnRiver(flop);
      } else if (playTurn == 2) {
        gController.setFlopTurnRiver(turnCards);
      } else if (playTurn == 3) {
        gController.setFlopTurnRiver(riverCards);
      }

      while (!allCalledorFolded) {
        System.out.println("VEM FAN SPELAR? " + currentPlayer);
        System.out.println("current Pot: " + currentPotSize);
        // NOTES gController.
        if (currentPlayer == noOfPlayers - 1) {
          if (!gController.getPlayerDecision().equals("fold")) {
            if (!(checkLivePlayers() > 1)) {
              System.out.println("Player Wins " + currentPotSize);
              gController.setPlayerPot(currentPotSize);
              winnerDeclared = true;
              break;
            }

            System.out.println("player turn");

            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            askForPlayerDecision(currentMaxBet);
            System.out.println("-----------------------------------------");
          }
        } else {
          if (!aiPlayers.get(currentPlayer).getDecision().contains("lost")) {
            if (!aiPlayers.get(currentPlayer).getDecision().contains("fold")) {
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              if (!(checkLivePlayers() > 1)) {
                System.out
                    .println(aiPlayers.get(currentPlayer).getName() + " Wins " + currentPotSize);
                aiPlayers.get(currentPlayer).updateWinner(currentPotSize);
                winnerDeclared = true;
                break;
              }
              System.out.println(aiPlayers.get(currentPlayer).getName() + "'s turn");
              askForAiDecision();
              System.out.println("-----------------------------------------");
            }
          }
        }
        gController.setTablePot();
        if (currentPlayer != noOfPlayers - 1) {
          aiPlayers.get(currentPlayer).setSameTurn(true);
        }
        currentPlayer = (currentPlayer + 1) % noOfPlayers;
        System.out.println("current Pot: " + currentPotSize);
      }

      playTurn++;
      allCalledorFolded = false;
      for (Ai ai : aiPlayers) {
        if (!ai.getDecision().contains("fold") && !ai.getDecision().contains("lost")) {
          ai.setDecision("");
          ai.setSameTurn(false);
        }
      }
      if (winnerDeclared) {
        break;
      }
    }
    if (playTurn >= 4 && !winnerDeclared) {
      System.out.println("checkwin");
      checkWinner();
    }
    for (Ai ai : aiPlayers) {
      if (ai.aiPot() < bigBlind) {
        System.out
            .println(ai.getName() + "\naiPot: " + ai.aiPot() + "\n" + (ai.aiPot() < bigBlind));
        ai.setDecision("lost");
        ai.updateWinner(-ai.aiPot());
        gController.setUIAiStatus(aiPlayers.indexOf(ai), "inactive");
      }

    }


    winnerDeclared = false;
    playTurn = 0;
    blindCounter++;
    if (blindCounter >= 15) {
      bigBlind += (int) (potSize / noOfPlayers * 0.02);
      currentMaxBet = bigBlind;
      smallBlind = bigBlind / 2;
      blindCounter = 0;
    }
    dealer = (dealer + 1) % noOfPlayers;

    try {
      setupPhase();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

  }


  /**
   * Method which checks who the winner is.
   */
  private void checkWinner() {

    String winner = "";
    int bestHand = 0;
    Ai bestHandPlayer = new Ai(0, "");
    for (Ai ai : aiPlayers) {
      if (!ai.getDecision().equals("fold")) {
        if (ai.handStrength() > bestHand) {
          bestHandPlayer = ai;
          bestHand = ai.handStrength();
        } else if (ai.handStrength() == bestHand) {
          if (ai.getHighCard() > bestHandPlayer.getHighCard()) {
            bestHandPlayer = ai;
          } else if (true) {
            // TODO
          }
        }
      }
      System.out.println(bestHandPlayer.getName());
      System.out.println(bestHand);
      System.out.println(bestHandPlayer.getHighCard());
    }
    System.out.println("Player: " + gController.getHandStrength());
    System.out.println("player highcard: " + gController.getGetHighCard());
    System.out.println("supposed besthand: " + bestHand);
    System.out.println("bestAI hand: " + bestHandPlayer.handStrength());
    System.out.println("bestAI highCard: " + bestHandPlayer.getHighCard());
    System.out.println(gController.getHandStrength() > bestHand);
    if (!gController.getPlayerDecision().contains("fold")) {
      if (gController.getHandStrength() > bestHand) {
        gController.setPlayerPot(currentPotSize);
        winner = gController.getUsername();
        gController.setWinnerLabel(winner);
        System.out.println("Player Wins " + currentPotSize);
      } else if (gController.getHandStrength() == bestHand) {
        if (gController.getGetHighCard() > bestHandPlayer.getHighCard()) {
          gController.setPlayerPot(currentPotSize);
          winner = gController.getUsername();
          gController.setWinnerLabel(winner);
          System.out.println("Player Wins " + currentPotSize);
        } else if (gController.getGetHighCard() == bestHandPlayer.getHighCard()) {
          System.out.println("Draw, Pot Split between Player and " + bestHandPlayer.getName());
          bestHandPlayer.updateWinner(currentPotSize / 2);
          gController.setPlayerPot(currentPotSize / 2);
          winner = gController.getUsername() + " och " + bestHandPlayer.getName();
          gController.setWinnerLabel(winner);
        } else {
          System.out.println(bestHandPlayer.getName() + " Wins " + currentPotSize);
          bestHandPlayer.updateWinner(currentPotSize);
          winner = bestHandPlayer.getName();
          gController.setWinnerLabel(winner);
        }
      } else {
        System.out.println(bestHandPlayer.getName() + " Wins " + currentPotSize);
        bestHandPlayer.updateWinner(currentPotSize);
        winner = bestHandPlayer.getName();
        gController.setWinnerLabel(winner);
      }
    } else {
      System.out.println(bestHandPlayer.getName() + " Wins " + currentPotSize);
      bestHandPlayer.updateWinner(currentPotSize);
      winner = bestHandPlayer.getName();
      gController.setWinnerLabel(winner);
    }

  }


  /**
   * Method which checks the amount of "living" players. The amount of players whose decision is not
   * fold.
   * 
   * @return Number of "living" players
   */
  private int checkLivePlayers() {

    int livePlayers = 0;
    for (Ai ai : aiPlayers) {
      if (!ai.getDecision().equals("fold")) {
        livePlayers++;
      }
    }
    if (!gController.getPlayerDecision().equals("fold")) {
      livePlayers++;
    }
    System.out.println("live Players:" + livePlayers);
    return livePlayers;
  }


  /**
   * Method which asks the GUi to give the player a choice and calls an action when a decision has
   * been made.
   * 
   * @param currentMaxBet2 the currentmaxbet.
   */
  private void askForPlayerDecision(int currentMaxBet2) {

    System.out.println("To pay if call: " + currentMaxBet);
    gController.askForPlayerDecision();
    playerAction();
  }


  /**
   * A method which controls what to do depending on the players' action.
   */
  private void playerAction() {

    String playerDecision = gController.getPlayerDecision();
    playerDecision.toLowerCase();

    String[] split;
    if (playerDecision.contains("raise")) {
      split = playerDecision.split(",");
      currentMaxBet = Integer.parseInt(split[1]);
      currentPotSize += Integer.parseInt(split[1]);
      System.out.println("raise" + split[1]);
    } else if (playerDecision.contains("fold")) {
    } else if (playerDecision.contains("call")) {
      split = playerDecision.split(",");
      currentPotSize += currentMaxBet;
    } else if (playerDecision.contains("check")) {
    }
    // Check all call or fold
    allCallorFold();
  }


  /**
   * Method which asks the current AIplayer to make a decision based on the current max bet.
   */
  private void askForAiDecision() {

    Ai ai = aiPlayers.get(currentPlayer);
    // Starting Hand
    if (playTurn == 0) {
      ai.makeDecision(currentMaxBet);
      aiAction(currentPlayer);
      // Flop
    } else if (playTurn == 1) {
      ai.makeDecision(currentMaxBet, flop);
      aiAction(currentPlayer);
      // Turn
    } else if (playTurn == 2) {
      ai.makeDecision(currentMaxBet, turn);
      aiAction(currentPlayer);
      // River
    } else if (playTurn == 3) {
      ai.makeDecision(currentMaxBet, river);
      aiAction(currentPlayer);
    }
    // Check all call or fold
    allCallorFold();
  }


  /**
   * Method which controls what to do depending on the Ai players' action.
   * 
   * @param currentPlayer current AI player
   */
  private void aiAction(int currentPlayer) {

    Ai ai = aiPlayers.get(currentPlayer);

    String aiDecision = ai.getDecision();
    String[] split;
    if (aiDecision.contains("raise")) {
      split = aiDecision.split(",");
      currentMaxBet = Integer.parseInt(split[1]);
      currentPotSize += Integer.parseInt(split[1]);
      System.out.println("AI Raises");
      gController.aiAction(currentPlayer, aiDecision);

    } else if (aiDecision.contains("fold")) {
      System.out.println("AI folds");
      gController.aiAction(currentPlayer, aiDecision);

    } else if (aiDecision.contains("call")) {

      split = aiDecision.split(",");
      if (Integer.parseInt(split[1]) > currentMaxBet) {
        currentMaxBet = Integer.parseInt(split[1]);
        currentPotSize += Integer.parseInt(split[1]);
      } else {
        currentPotSize += Integer.parseInt(split[1]);
      }

      if (Integer.parseInt(split[1]) <= 0) {
        gController.aiAction(currentPlayer, "check");
        System.out.println("AI Checks");
      } else {
        gController.aiAction(currentPlayer, split[0]);
        System.out.println("AI Calls " + split[1]);
      }

    } else if (aiDecision.contains("check")) {
      System.out.println("AI Checks");
      gController.aiAction(currentPlayer, aiDecision);
    } else if (aiDecision.contains("all-in")) {
      // TODO hantera all-in
      split = aiDecision.split(",");
      int allin = Integer.parseInt(split[1]);
      if (currentMaxBet < allin) {
        currentPotSize += allin;
        currentMaxBet = allin;
      } else {
        doAllInCheck = true;
        // TODO potSplit
      }
      gController.aiAction(currentPlayer, aiDecision);
    } else {
      System.out.println("fan");
    }
  }


  /**
   * Method which sets who the small and big blind players are. Depending on who the dealer is.
   * 
   * @param noOfPlayers Number of players in the game
   */
  private void setBlinds(int noOfPlayers) {


    currentMaxBet = bigBlind;
    smallBlind = bigBlind / 2;
    if (noOfPlayers == 2) {
      currentPlayer = dealer;
      smallBlindPlayer = dealer;
      bigBlindPlayer = (dealer + 1) % noOfPlayers;
    } else if (noOfPlayers >= 3) {
      currentPlayer = (dealer + 3) % noOfPlayers;
      smallBlindPlayer = (dealer + 1) % noOfPlayers;
      bigBlindPlayer = (dealer + 2) % noOfPlayers;
    }
    while (dealer != noOfPlayers - 1 && aiPlayers.get(dealer).getDecision().contains("lost")) {
      dealer = (dealer + 1) % noOfPlayers;
      smallBlindPlayer = (smallBlindPlayer + 1) % noOfPlayers;
      bigBlindPlayer = (bigBlindPlayer + 1) % noOfPlayers;
    }

    while (smallBlindPlayer != (noOfPlayers - 1)
        && aiPlayers.get(smallBlindPlayer).getDecision().contains("lost")) {
      smallBlindPlayer = (smallBlindPlayer + 1) % noOfPlayers;
      bigBlindPlayer = (bigBlindPlayer + 1) % noOfPlayers;
    }
    while (bigBlindPlayer != (noOfPlayers - 1)
        && aiPlayers.get(bigBlindPlayer).getDecision().contains("lost")) {
      bigBlindPlayer = (bigBlindPlayer + 1) % noOfPlayers;
    }
    if (smallBlindPlayer == noOfPlayers - 1) {
      gController.playerSmallBlind(smallBlind);
      aiPlayers.get(bigBlindPlayer).setBigBlind(bigBlind, true);
    } else if (bigBlindPlayer == noOfPlayers - 1) {
      aiPlayers.get(smallBlindPlayer).setSmallBlind(smallBlind, true);
      gController.playerBigBlind(bigBlind);
    } else {

      aiPlayers.get(smallBlindPlayer).setSmallBlind(smallBlind, true);
      aiPlayers.get(bigBlindPlayer).setBigBlind(bigBlind, true);
      aiPlayers.get(smallBlindPlayer).setDecision("SmallBlind");
      aiPlayers.get(bigBlindPlayer).setDecision("BigBlind");
      // gController.aiAction(smallBlindPlayer, "SmallBlind");
      // gController.aiAction(bigBlindPlayer, "BigBlind");
      // sets dealer as well
    }
    if (dealer != noOfPlayers - 1) {
      System.out.println("test");
    } else {
      System.out.println("test2");
      gController.playerIsDealer(dealer);
    }
    System.out.println("DL" + dealer);
    System.out.println("SB" + smallBlindPlayer);
    System.out.println("BB" + bigBlindPlayer);
    gController.setBlindsMarker(dealer);
    this.currentPotSize = smallBlind + bigBlind;
    // TODO gui.updateTablePot
  }


  /**
   * Method which checks if everyone has folded or checked/called.
   */
  public void allCallorFold() {

    int noOfAIFoldedorCalled = 0;
    // For each AI player
    for (Ai ai : aiPlayers) {
      // Check if folded.
      if (ai.getDecision().contains("fold")) {
        noOfAIFoldedorCalled++;
        // if not folded, check if checked or called.
      } else if (ai.getDecision().contains("call") && ai.getPaidThisTurn() == currentMaxBet
          || ai.getDecision().contains("check") && ai.getPaidThisTurn() == currentMaxBet) {
        noOfAIFoldedorCalled++;
        // if neither checked, called or folded, at least one AI is live.
      } else {
        allCalledorFolded = false;
      }
    }
    // If all AI have folded or called, check if player has folded or called.
    if (noOfAIFoldedorCalled >= noOfAi) {
      String[] split = gController.getPlayerDecision().split(",");

      if (gController.getPlayerDecision().contains("fold")
          || gController.getPlayerDecision().contains("call")) {
        allCalledorFolded = true;
      } else if (gController.getPlayerDecision().contains("raise")
          && Integer.parseInt(split[1]) == currentMaxBet) {
        allCalledorFolded = true;
      } else if (gController.getPlayerDecision().contains("check")) {
        allCalledorFolded = true;
      } else {
        allCalledorFolded = false;
      }
    }
    System.out.println(allCalledorFolded);
  }


  /**
   * Method which returns the small blind value.
   * 
   * @return Current small blind
   */
  public int getSmallBlind() {

    return smallBlind;
  }


  /**
   * Method which returns the big blind value.
   * 
   * @return Current big blind
   */
  public int getBigBlind() {

    return bigBlind;
  }


  /**
   * Method which Saves chosen number of AIs
   * 
   * @return Number of chosen AIs as int
   */
  public int getFixedNrOfAIs() {

    return this.fixedNrOfAIs;
  }

}

