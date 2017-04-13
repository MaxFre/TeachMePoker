package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import aiClass.Ai;
import deck.Card;
import deck.Deck;
import gui.GUI;
import player.Player;

/**
 * 
 * @author Rikard Almgren
 * @version 0.6
 *
 */
public class SPController {

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

  /**
   * TestKonstruktor
   */
  public SPController() {
    deck = new Deck();
    new GUI();
    startGame(3, 10000);
  }

  /**
   * Method which prepares the whole Session
   * 
   * @param noOfAi Number of AI players
   * @param potSize Potsize for the whole table
   */
  public void startGame(int noOfAi, int potSize) {

    this.noOfAi = noOfAi;
    setNames();
    noOfPlayers = noOfAi + 1;
    bigBlind = (int) (potSize / noOfPlayers * 0.02);
    currentMaxBet = bigBlind;
    this.smallBlind = bigBlind / 2;
    player = new Player(potSize / (noOfPlayers));
    for (int i = 0; i < noOfAi; i++) {
      aiPlayers.add(new Ai(potSize / (noOfPlayers), name.remove(0)));
    }

    setupPhase();
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
   */
  private void setupPhase() {
    if (player.getPlayerPot() > bigBlind) {
      deck = new Deck();
      deck.shuffle();
      card1 = deck.getCard();
      card2 = deck.getCard();
      player.setStartingHand(card1, card2);
      // show animation?
      for (Ai ai : aiPlayers) {
        ai.setDecision("");
        ai.setBigBlind(0, false);
        ai.setSmallBlind(0, false);
        ai.setPaidThisTurn(0);
        player.reset("", new ArrayList<Card>());
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
      playPoker();
    }

  }

  /**
   * Method that runs the gameround itself
   */
  public void playPoker() {

    while (playTurn < 4) {
      System.out.println("Current turn: " + playTurn);
      System.out.println("<-<-<-<-<-<-<-<-<->->->->->->->->");
      while (!allCalledorFolded) {
        if (currentPlayer == noOfPlayers - 1) {
          if (!player.getDecision().equals("fold")) {
            if (!(checkLivePlayers() > 1)) {
              System.out.println("Player has won the round!");
              winnerDeclared = true;
              break;
            }
            System.out.println("player turn");
            player.makeDecision(currentMaxBet);
            System.out.println("-----------------------------------------");
          }
        } else {
          if (!aiPlayers.get(currentPlayer).getDecision().contains("fold")) {
            if (!(checkLivePlayers() > 1)) {
              System.out.println(aiPlayers.get(currentPlayer).getName() + " has won the round!");
              winnerDeclared = true;
              break;
            }
            System.out.println(aiPlayers.get(currentPlayer).getName() + "'s turn");
            askForAiDecision();
            System.out.println("-----------------------------------------");
          }
        }
        currentPlayer = (currentPlayer + 1) % noOfPlayers;
        allCallorFold();
        // testkod
        if (currentPlayer != noOfPlayers - 1) {
          System.out.println("All called or folded? " + allCalledorFolded);
        }
      }
      playTurn++;
      allCalledorFolded = false;
      if (winnerDeclared) {
        break;
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
    if (dealer < noOfPlayers) {
      dealer++;
    } else {
      dealer = 0;
    }
    setupPhase();
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
    System.out.println("Live Players: " + livePlayers);
    return livePlayers;
  }

  /**
   * Method which asks the current AIplayer to make a decision based on the current max bet.
   */
  private void askForAiDecision() {
    Ai ai = aiPlayers.get(currentPlayer);
    System.out.println("current playTurn(0 = start, 1 = flop, 2 = turn, 3 = river): " + playTurn);
    if (playTurn == 0) {
      ai.makeDecision(currentMaxBet);
      aiAction(currentPlayer);
    } else if (playTurn == 1) {
      ai.makeDecision(currentMaxBet, flop);
      aiAction(currentPlayer);
    } else if (playTurn == 2) {
      ai.makeDecision(currentMaxBet, turn);
      aiAction(currentPlayer);
    } else if (playTurn == 3) {
      ai.makeDecision(currentMaxBet, river);
      aiAction(currentPlayer);
    }
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
      System.out.println("AI " + ai.getName() + " raised by "
          + (Integer.parseInt(split[1]) - oldMaxBet) + " to " + currentMaxBet);

    } else if (aiDecision.contains("fold")) {
      System.out.println("AI " + ai.getName() + " folded");
      // TODO gui.showAIFolded?
    } else if (aiDecision.contains("call")) {
      split = aiDecision.split(",");
      currentMaxBet = Integer.parseInt(split[1]);
      currentPotSize += Integer.parseInt(split[1]);
      System.out.println("AI " + ai.getName() + " called " + Integer.parseInt(split[1]));
      // TODO gui.showAICalled?
    } else if (aiDecision.contains("check")) {
      System.out.println("AI " + ai.getName() + " checks");
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
      player.smallBlind(smallBlind);
      aiPlayers.get(bigBlindPlayer).setBigBlind(bigBlind, true);
      // show animation?
    } else if (bigBlindPlayer == noOfPlayers - 1) {
      aiPlayers.get(smallBlindPlayer).setSmallBlind(smallBlind, true);
      player.bigBlind(bigBlind);
      // show animation?
    } else {

      aiPlayers.get(smallBlindPlayer).setSmallBlind(smallBlind, true);
      aiPlayers.get(bigBlindPlayer).setBigBlind(bigBlind, true);
      // show animation?
    }
    this.currentPotSize = smallBlind + bigBlind;
  }

  /**
   * Method which checks if everyone has folded or checked/called.
   */
  public void allCallorFold() {

    int noOfAIFoldedorCalled = 0;
    for (Ai ai : aiPlayers) {
      if (ai.getDecision().equals("fold")) {
        noOfAIFoldedorCalled++;
      } else if (ai.getDecision().contains("call") || ai.getDecision().contains("check")) {
        noOfAIFoldedorCalled++;
      } else {
        allCalledorFolded = false;
      }
    }
    if (noOfAIFoldedorCalled == noOfAi) {

      if (player.getDecision().equals("fold") || player.getDecision().contains("call")) {
        allCalledorFolded = true;
      } else {
        allCalledorFolded = false;
      }
    }

  }

}
