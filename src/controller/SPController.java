package controller;

import java.util.ArrayList;
import java.util.LinkedList;
import aiClass.Ai;
import deck.Card;
import deck.Deck;
import gui.GameController;


/**
 * 
 * @author Rikard Almgren
 * @version 0.6
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


  /**
   * Method which prepares the whole Session
   * 
   * @param noOfAi Number of AI players
   * @param potSize Potsize for the whole table
   */
  public void startGame(int noOfAi, int potSize, String playerName) {

    gController.disableButtons();
    this.potSize = potSize;
    this.noOfAi = noOfAi;
    setNames();
    noOfPlayers = noOfAi + 1;
    bigBlind = (int) (potSize / noOfPlayers * 0.02);
    currentMaxBet = bigBlind;
    this.smallBlind = bigBlind / 2;
    gController.setPlayerPot((potSize / noOfPlayers));
    for (int i = 0; i < noOfAi; i++) {
      aiPlayers.add(new Ai(potSize / (noOfPlayers), name.remove(0)));
    }
    gController.setAiPlayers(aiPlayers);
    try {
      SPController.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    setupPhase();
  }


  public void setGameController(GameController gController) {

    this.gController = gController;
    System.out.println("This happens");

  }


  public int getCurrentMaxBet() {

    return currentMaxBet;
  }


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
    // Collections.shuffle(name);
  }


  /**
   * Method which prepares a new gameround.
   */
  private void setupPhase() {

    if (gController.getPlayerPot() > bigBlind) {
      deck = new Deck();
      deck.shuffle();
      card1 = deck.getCard();
      card2 = deck.getCard();
      gController.setStartingHand(card1, card2);
      for (Ai ai : aiPlayers) {
        ai.setDecision("");
        ai.setBigBlind(0, false);
        ai.setSmallBlind(0, false);
        ai.setPaidThisTurn(0);
        gController.playerReset("");
        card1 = deck.getCard();
        card2 = deck.getCard();
        ai.setStartingHand(card1, card2);
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
      System.exit(0);
      // TODO player loses
    }

  }


  /**
   * Method that runs the gameround itself public void playPoker() {
   */
  public void run() {

    Card[] turnCards = {flop[0], flop[1], flop[2], turn};
    Card[] riverCards = {flop[0], flop[1], flop[2], turn, river};
    while (playTurn < 4) {
      System.out.println("Current turn: " + playTurn);
      if (playTurn == 1) {

        gController.setFlopTurnRiver(flop);
      } else if (playTurn == 2) {
        gController.setFlopTurnRiver(turnCards);

      } else if (playTurn == 3) {
        gController.setFlopTurnRiver(riverCards);

      }

      while (!allCalledorFolded) {
        System.out.println("VEM FAN SPELAR? " + currentPlayer);
        System.out.println("current Pot: " + currentPotSize);
        if (currentPlayer == noOfPlayers - 1) {
          if (!gController.getPlayerDecision().equals("fold")) {
            if (!(checkLivePlayers() > 1)) {
              System.out.println("Player Wins " + currentPotSize);
              gController.setPlayerPot(currentPotSize);
              winnerDeclared = true;
              break;
            }

            System.out.println("player turn");
            askForPlayerDecision(currentMaxBet);
            System.out.println("-----------------------------------------");
          }
        } else {
          if (!aiPlayers.get(currentPlayer).getDecision().contains("fold")) {
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
        if (currentPlayer != noOfPlayers - 1) {
          aiPlayers.get(currentPlayer).setSameTurn(true);
        }
        currentPlayer = (currentPlayer + 1) % noOfPlayers;

        try {
          Thread.sleep(1000);
          // TODO Make it obvious which player is playing?
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      playTurn++;
      allCalledorFolded = false;
      for (Ai ai : aiPlayers) {
        if (!ai.getDecision().equals("fold")) {
          ai.setDecision("");
          ai.setSameTurn(false);
        }
      }
      if (winnerDeclared) {
        break;
      }
    }
    if (playTurn >= 4 && !winnerDeclared) {
      checkWinner();
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

    setupPhase();
  }


  private void checkWinner() {

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
          }
        }
      }
    }
    if (!gController.getPlayerDecision().equals("fold")) {
      if (gController.getHandStrength() > bestHand) {
        gController.setPlayerPot(currentPotSize);
        System.out.println("Player Wins " + currentPotSize);
      } else if (gController.getHandStrength() == bestHand) {
        if (gController.getGetHighCard() > bestHandPlayer.getHighCard()) {
          gController.setPlayerPot(currentPotSize);
          System.out.println("Player Wins " + currentPotSize);
        }
      } else {
        System.out.println(bestHandPlayer.getName() + " Wins " + currentPotSize);
        bestHandPlayer.updateWinner(currentPotSize);
      }
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


  private void askForPlayerDecision(int currentMaxBet2) {

    gController.askForPlayerDecision();
    playerAction();
  }


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
      currentPotSize += currentMaxBet;
    } else if (playerDecision.contains("check")) {
    }
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
   * TestMethod, Maybe used to update GUI in the future? Prints what the AI player decided to do in
   * the console
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
        currentPotSize += currentMaxBet;
      }

      if (Integer.parseInt(split[1]) <= 0) {
        gController.aiAction(currentPlayer, "Check");
        System.out.println("AI Checks");
      } else {
        gController.aiAction(currentPlayer, split[0]);
        System.out.println("AI Calls");
      }

    } else if (aiDecision.contains("check")) {
      System.out.println("AI Checks");
      gController.aiAction(currentPlayer, aiDecision);
    }
  }


  /**
   * Method which sets who the small and big blind players are dependant on who the dealer is.
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
      gController.aiAction(smallBlindPlayer, "SmallBlind");
      gController.aiAction(bigBlindPlayer, "BigBlind");
      // sets dealer as well
      if (dealer != noOfPlayers - 1) {
        gController.aiAction(dealer, "Dealer");
      } else {
        // TODO set player as Dealer
      }
    }
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
      } else if (ai.getDecision().contains("call") || ai.getDecision().contains("check")) {
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


  public int getBigBlind() {
    return bigBlind;
  }

}
