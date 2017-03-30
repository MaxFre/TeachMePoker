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
	private int smallBlind, bigBlind;
	private Player player;
	private int currentPotSize;
	private Card card1, card2, turn, river;
	private Card[] flop = new Card[3];
	private int noOfPlayers = 0;
	private boolean setupPhase = true;

	public SPController() {
		deck = new Deck();
		new GUI();
		startGame(3, 500);
	}

	// testcode
	// public SPController(String trolol) {
	// deck = new Deck();
	// deck.shuffle();
	// while(deck.hasNext()) {
	// Card test = deck.getCard();
	// System.out.println(test.getCardSuit() + ", " + test.getCardValue() + ", "
	// + test.getCardIcon());
	// deck.removeCard();
	// }
	//
	// }

	public void startGame(int noOfAi, int potSize) {
		this.noOfAi = noOfAi;
		noOfPlayers = noOfAi + 1;
		player = new Player(potSize / (noOfPlayers));
		for (int i = 0; i < noOfAi; i++) {
			aiPlayers.add(new Ai(potSize / (noOfPlayers)));
		}

		playPoker();
		playPoker();
	}

	public void playPoker() {
		deck.shuffle();
		if (setupPhase) {
			setBlinds(noOfPlayers);

			card1 = deck.getCard();
			deck.removeCard();
			card2 = deck.getCard();
			deck.removeCard();
			player.setStartingHand(card1, card2);
			// show animation?
			for (Ai ai : aiPlayers) {
				card1 = deck.getCard();
				deck.removeCard();
				card2 = deck.getCard();
				deck.removeCard();
				ai.setStartingHand(card1, card2);
				// show animation?
			}
			for (int i = 0; i < flop.length; i++) {
				flop[i] = deck.getCard();
				deck.removeCard();
			}
			turn = deck.getCard();
			deck.removeCard();
			river = deck.getCard();
			deck.removeCard();
			setupPhase = false;
		} else {
			while (playTurn < 4) {
				do {
					if (currentPlayer == noOfPlayers - 1) {
						System.out.println("player turn");
						player.makeDecision();
					} else {
						System.out.println("AI turn");
						askForAiDecision();

					}
					currentPlayer = (currentPlayer + 1) % noOfPlayers;
				} while (!allCallorFold());
				playTurn++;
			}
			playTurn = 0;
			setupPhase = true;
			if (dealer < noOfPlayers)
				dealer += 1;
			else
				dealer = 0;
		}
	}

	private void askForAiDecision() {
		System.out.println("current playTurn(0 = start, 1 = flop, 2 = turn, 3 = river): " + playTurn);
		if (playTurn == 0) {
			aiPlayers.get(currentPlayer).makeDecision(playTurn, currentPotSize);
		} else if (playTurn == 1) {
			aiPlayers.get(currentPlayer).makeDecision(playTurn, currentPotSize, flop);
		} else if (playTurn == 2) {
			aiPlayers.get(currentPlayer).makeDecision(playTurn, currentPotSize, turn);
		} else if (playTurn == 3) {
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
		// for (Ai ai : aiPlayers) {
		// if (!ai.getDecision().equals("fold") ||
		// !ai.getDecision().equals("call")) {
		// return false;
		// }
		// }
		// if (player.getDecision().equals("fold") ||
		// player.getDecision().equals("call")) {
		// return true;
		// } else {
		// return false;
		// }
		Random rand = new Random();
		
		if(rand.nextInt(100) < 10) {
			System.out.println("it was randomly decided that everyone called or folded");
			return true;
		}
		return false;
	}

}
