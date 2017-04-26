package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import aiClass.Ai;
import deck.Card;
import deck.Deck;
import gui.GameController;
import player.Player;


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
  private Player player;
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

    this.potSize = potSize;
    this.noOfAi = noOfAi;
    setNames();
    noOfPlayers = noOfAi + 1;
    bigBlind = (int) (potSize / noOfPlayers * 0.02);
    currentMaxBet = bigBlind;
    this.smallBlind = bigBlind / 2;
    player = new Player(potSize / (noOfPlayers), playerName);
    for (int i = 0; i < noOfAi; i++) {
      aiPlayers.add(new Ai(potSize / (noOfPlayers), name.remove(0)));
    }
    try {
      this.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    setupPhase();
  }

  public void setGameController(GameController gController) {

    this.gController = gController;
    System.out.println("This happens");

  }


  /**
   * Method that creates a list of names for AI-Players to pull from
   */
  public void setNames() {

    name.add("RngJesus-Max");
    name.add("MLG-Vedrana");
    name.add("Dabmaster-Lykke");
    name.add("NoScope-Amin");
    name.add("Swag-Rikard");
    name.add("Yolo-Kristina");
    name.add("420-Rolf");
    Collections.shuffle(name);
  }


  /**
   * Method which prepares a new gameround.
   */
  private void setupPhase() {

    if (player.getPlayerPot() > bigBlind) {
      deck = new Deck();
      deck.shuffle();
      card1 = deck.getCard();
      card2 = deck.getCard();
      player.setStartingHand(card1, card2);
      gController.setStartingHand(card1, card2);
      // show animation?
      for (Ai ai : aiPlayers) {
        ai.setDecision("");
        ai.setBigBlind(0, false);
        ai.setSmallBlind(0, false);
        ai.setPaidThisTurn(0);
        player.reset("");
        card1 = deck.getCard();
        card2 = deck.getCard();
        ai.setStartingHand(card1, card2);
        // show animation?
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
    }

  }


  /**
   * Method that runs the gameround itself public void playPoker() {
   */
  public void run() {



    while (playTurn < 4) {
      System.out.println("Current turn: " + playTurn);
      while (!allCalledorFolded) {
        if (currentPlayer == noOfPlayers - 1) {
          if (!player.getDecision().equals("fold")) {
            if (!(checkLivePlayers() > 1)) {
              player.setPlayerPot(currentPotSize);
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
              System.out.println(aiPlayers.get(currentPlayer).getName() + " Wins");
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
      // TODO Calculate an actual winner
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
    if (dealer < noOfPlayers) {
      dealer++;
    } else {
      dealer = 0;
    }
    setupPhase();
  }


  private void checkWinner() {

    int bestHand = 0;
    Ai bestHandPlayer = new Ai(0,"");
    for (Ai ai : aiPlayers) {
      if (!ai.getDecision().equals("fold")) {
        if (ai.handStrength() > bestHand) {
          bestHandPlayer = ai;
          bestHand = ai.handStrength();
        } else if (ai.handStrength() == bestHand) {
          if(ai.getHighCard() > bestHandPlayer.getHighCard()) {
            bestHandPlayer = ai;
          }
        }
      }
    }
    if (!player.getDecision().equals("fold")) {
      player.getHandStrength();
    }
    // TODO Check Winner
    System.out.println("Winner");
    System.out.println(bestHandPlayer.getName());
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
    if (!player.getDecision().equals("fold")) {
      livePlayers++;
    }
    System.out.println("live Players:" + livePlayers);
    return livePlayers;
  }


  private void askForPlayerDecision(int currentMaxBet2) {

    // TODO gui.playerDecision?
    System.out.println(gController);

    player.setDecision(gController.getPlayerDecision());
    playerAction();
  }


  private void playerAction() {

    // TODO get player Decision
    String playerDecision = player.getDecision();
    playerDecision.toLowerCase();

    String[] split;
    if (playerDecision.contains("raise")) {
      split = playerDecision.split(",");
      int oldMaxBet = currentMaxBet;
      currentMaxBet = Integer.parseInt(split[1]);
      currentPotSize += Integer.parseInt(split[1]);
      // TODO gui.showPlayerRaised?
    } else if (playerDecision.contains("fold")) {
      // TODO gui.showPlayerFolded?
    } else if (playerDecision.contains("call")) {
      currentPotSize += currentMaxBet;
      // TODO gui.showPlayerCalled?
    } else if (playerDecision.contains("check")) {
      // TODO gui.showPlayerChecked?
    }
    // TODO gui.updateTablePot
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
      int oldMaxBet = currentMaxBet;
      currentMaxBet = Integer.parseInt(split[1]);
      currentPotSize += Integer.parseInt(split[1]);
      System.out.println("AI Raises");
      // TODO gui.showAIRaised

    } else if (aiDecision.contains("fold")) {
      System.out.println("AI folds");
      // TODO gui.showAIFolded?
    } else if (aiDecision.contains("call")) {
      
      split = aiDecision.split(",");
      currentMaxBet = Integer.parseInt(split[1]);
      currentPotSize += Integer.parseInt(split[1]);
      System.out.println("AI Calls");
      // TODO gui.showAICalled?
    } else if (aiDecision.contains("check")) {
      System.out.println("AI Checks");
      // TODO gui.showAIChecked
    }
    // TODO gui.updateTablePot
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
      player.smallBlind(smallBlind);
      aiPlayers.get(bigBlindPlayer).setBigBlind(bigBlind, true);
      // TODO show animation?
    } else if (bigBlindPlayer == noOfPlayers - 1) {
      aiPlayers.get(smallBlindPlayer).setSmallBlind(smallBlind, true);
      player.bigBlind(bigBlind);
      // TODO show animation?
    } else {

      aiPlayers.get(smallBlindPlayer).setSmallBlind(smallBlind, true);
      aiPlayers.get(bigBlindPlayer).setBigBlind(bigBlind, true);
      // TODO show animation?
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
      if (ai.getDecision().equals("fold")) {
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
    if (noOfAIFoldedorCalled == noOfAi) {

      if (player.getDecision().equals("fold") || player.getDecision().contains("call")) {
        allCalledorFolded = true;
      } else {
        allCalledorFolded = false;
      }
    }

  }

}
