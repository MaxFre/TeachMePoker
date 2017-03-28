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
	private int dealer = 0;
	private int currentPlayer;
	private int smallBlind, bigBlind;
	private Player player;
	private int currentPotSize;
	private Card card1, card2, turn, river;
	private Card[] flop;
	private int noOfPlayers = noOfAi + 1;

	public SPController() {
		deck = new Deck();
		new GUI();
	}
	
	//testcode
//	public SPController(String trolol) {
//		deck = new Deck();
//		deck.shuffle();
//		while(deck.hasNext()) {
//			Card test = deck.getCard();
//			System.out.println(test.getCardSuit() + ", " + test.getCardValue() + ", " + test.getCardIcon());
//		}
//	}

	public void startGame(int noOfAi, int potSize) {
		this.noOfAi = noOfAi;
		player = new Player(potSize / (noOfPlayers));
		for (int i = 0; i < noOfAi; i++) {
			aiPlayers.add(new Ai(potSize / (noOfPlayers)));
		}
	}

	public void playPoker() {
		deck.shuffle();
		if (playTurn == 0) {
			setBlinds(noOfPlayers);

			card1 = deck.getCard();
			card2 = deck.getCard();
			player.setStartingHand(card1, card2);
			// show animation?
			for (Ai ai : aiPlayers) {
				card1 = deck.getCard();
				card2 = deck.getCard();
				ai.setStartingHand(card1, card2);
				// show animation?
			}
		} else {
			while (playTurn <= 4) {
				do {
					if (currentPlayer == noOfPlayers - 1) {
						player.makeDecision();
					} else {
						askForAiDecision();
						
					}
					currentPlayer = (currentPlayer + 1) % noOfPlayers;
				} while (!allCallorFold());
				playTurn++;
			}
			playTurn = 0;
			if (dealer < noOfPlayers)
				dealer += 1;
			else
				dealer = 0;
		}
	}

	private void askForAiDecision() {
		if(playTurn == 0) {
		aiPlayers.get(currentPlayer).makeDecision(playTurn, currentPotSize);
		} else if(playTurn == 1) {
			aiPlayers.get(currentPlayer).makeDecision(playTurn, currentPotSize, flop);
		} else if(playTurn == 2) {
			aiPlayers.get(currentPlayer).makeDecision(playTurn, currentPotSize, turn);
		} else if(playTurn == 3) {
			aiPlayers.get(currentPlayer).makeDecision(playTurn, currentPotSize, river);
		}
	}

	private void setBlinds(int noOfPlayers) {
		if (noOfPlayers == 2) {
			currentPlayer = dealer;
			smallBlind = dealer;
			bigBlind = (dealer + 1) % noOfPlayers;
		} else if (noOfPlayers >= 3) {
			currentPlayer = (dealer + 3) % noOfPlayers;
			smallBlind = (dealer + 1) % noOfPlayers;
			bigBlind = (dealer + 2) % noOfPlayers;
		}

		if (smallBlind == noOfPlayers) {
			player.bet(5);
			// show animation?
		} else if (bigBlind == noOfPlayers) {
			player.bet(10);
			// show animation?
		} else {
			aiPlayers.get(smallBlind).bet(5);
			aiPlayers.get(bigBlind).bet(10);
			// show animation?
		}
	}

	public boolean allCallorFold() {
		// This method is not done.
		// this will not work
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
