package controller;

import java.util.LinkedList;

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
  private int currentPlayer = 0;
  private Player player;
  private int currentPotSize;
  private Card card1, card2, turn, river;
  private Card[] flop;

  public SPController() {
    new GUI();
    deck = new Deck();
  }

  public void startGame(int noOfAi, int potSize) {
    this.noOfAi = noOfAi;
    player = new Player(potSize / (noOfAi + 1));
    for (int i = 0; i < noOfAi; i++) {
      aiPlayers.add(new Ai(potSize / (noOfAi + 1)));
    }
  }

  public void playPoker() {
    deck.shuffle();
    if (playTurn == 0) {
      card1 = deck.getCard();
      card2 = deck.getCard();
      player.setStartingHand(card1, card2);
      for (Ai ai : aiPlayers) {
        card1 = deck.getCard();
        card2 = deck.getCard();
        ai.setStartingHand(card1, card2);
      }
    } else {
      do {
        if (currentPlayer == noOfAi + 1) {
          player.makeDecision();
        } else {
          aiPlayers.get(currentPlayer).makeDecision(playTurn, currentPotSize);
        }
      } while (!allCallorFold());
    }
  }

  public boolean allCallorFold() {
    return false;
  }


}
