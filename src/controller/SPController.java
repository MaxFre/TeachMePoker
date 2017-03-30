package controller;

import java.util.LinkedList;
import java.util.Random;

import aiClass.Ai;
import deck.Card;
import deck.Deck;
import gui.GUI;
import player.Player;

public class SPController {
  private int noOfAi;
  private Deck deck;
  private LinkedList<Ai> aiPlayers = new LinkedList<Ai>();
  private int playTurn = 0;
  private int dealer = 0;
  private int currentPlayer = 0;
  private int smallBlindPlayer, bigBlindPlayer, smallBlind, bigBlind;
  private int potSize, currentPotSize, currentMaxBet, blindCounter;
  private Player player;
  private Card card1, card2, turn, river;
  private Card[] flop = new Card[3];
  private int noOfPlayers = 0;
  private boolean setupPhase = true;

  public SPController() {
    deck = new Deck();
    new GUI();
    startGame(3, 1000);
  }

  public void startGame(int noOfAi, int potSize) {

    this.noOfAi = noOfAi;
    noOfPlayers = noOfAi + 1;
    bigBlind = (int) (potSize / noOfPlayers * 0.02);
    currentMaxBet = bigBlind;
    smallBlind = bigBlind / 2;
    player = new Player(potSize / (noOfPlayers));
    for (int i = 0; i < noOfAi; i++) {
      aiPlayers.add(new Ai(potSize / (noOfPlayers)));
    }

    playPoker();
  }

  public void playPoker() {
    deck.shuffle();
    if (setupPhase) {
      setBlinds(noOfPlayers);

      card1 = deck.getCard();
      card2 = deck.getCard();
      player.setStartingHand(card1, card2);
      // show animation?
      for (Ai ai : aiPlayers) {
        ai.setDecision("");
        player.setDecision("");
        card1 = deck.getCard();
        card2 = deck.getCard();
        ai.setStartingHand(card1, card2);
        // show animation?
      }
      for (int i = 0; i < flop.length; i++) {
        flop[i] = deck.getCard();
      }
      turn = deck.getCard();
      river = deck.getCard();
      setupPhase = false;
      playPoker();
    } else {
      while (playTurn < 4) {
        do {
          if (currentPlayer == noOfPlayers - 1) {
            System.out.println("player turn");
            if (!player.getDecision().equals("fold")) {
              player.makeDecision(currentMaxBet);
            }
          } else {
            System.out.println("AI turn");
            if (!aiPlayers.get(currentPlayer).getDecision().equals("fold")) {
              askForAiDecision();
            }
          }
          currentPlayer = (currentPlayer + 1) % noOfPlayers;
        } while (!allCallorFold());
        playTurn++;
      }
      playTurn = 0;
      setupPhase = true;
      blindCounter++;
      if (blindCounter >= 15) {
        bigBlind += (int) (potSize / noOfPlayers * 0.02);
        currentMaxBet = bigBlind;
        smallBlind = bigBlind / 2;
        blindCounter = 0;
      }
      if (dealer < noOfPlayers)
        dealer += 1;
      else
        dealer = 0;
    }
  }

  private void askForAiDecision() {
    System.out.println("current playTurn(0 = start, 1 = flop, 2 = turn, 3 = river): " + playTurn);
    if (playTurn == 0) {
      aiPlayers.get(currentPlayer).makeDecision(currentMaxBet);
    } else if (playTurn == 1) {
      aiPlayers.get(currentPlayer).makeDecision(currentMaxBet, flop);
    } else if (playTurn == 2) {
      aiPlayers.get(currentPlayer).makeDecision(currentMaxBet, turn);
    } else if (playTurn == 3) {
      aiPlayers.get(currentPlayer).makeDecision(currentMaxBet, river);
    }
  }

  private void setBlinds(int noOfPlayers) {
    if (noOfPlayers == 2) {
      currentPlayer = dealer;
      smallBlindPlayer = dealer;
      bigBlindPlayer = (dealer + 1) % noOfPlayers;
    } else if (noOfPlayers >= 3) {
      currentPlayer = (dealer + 3) % noOfPlayers;
      smallBlindPlayer = (dealer + 1) % noOfPlayers;
      bigBlindPlayer = (dealer + 2) % noOfPlayers;
    }

    if (smallBlindPlayer == noOfPlayers) {
      player.bet(5);
      // show animation?
    } else if (bigBlindPlayer == noOfPlayers) {
      player.bet(10);
      // show animation?
    } else {
      aiPlayers.get(smallBlindPlayer).setBlind(5);
      aiPlayers.get(bigBlindPlayer).setBlind(10);
      // show animation?
    }
  }

  public boolean allCallorFold() {
    for (Ai ai : aiPlayers) {
      if (!ai.getDecision().equals("fold") || !ai.getDecision().equals("call")) {
        return false;
      }
    }
    if (player.getDecision().equals("fold") || player.getDecision().equals("call")) {
      return true;
    } else {
      return false;
    }
  }

}
