package controller;

import java.util.ArrayList;
import java.util.Collections;
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
	private int currentPlayer = 0;
	private int smallBlindPlayer, bigBlindPlayer, smallBlind = 5, bigBlind = 10;
	private int potSize, currentPotSize, currentMaxBet, blindCounter;
	private Player player;
	private Card card1, card2, turn, river;
	private Card[] flop = new Card[3];
	private int noOfPlayers = 0;
	private boolean setupPhase = true;
	private ArrayList<String> name = new ArrayList<String>();

	public SPController() {
		deck = new Deck();
		new GUI();
		startGame(3, 2000);
	}

	public void startGame(int noOfAi, int potSize) {
		this.noOfAi = noOfAi;
		setNames();
		noOfPlayers = noOfAi + 1;
		bigBlind = (int) (potSize / noOfPlayers * 0.02);
		currentMaxBet = bigBlind;
		smallBlind = bigBlind / 2;
		player = new Player(potSize / (noOfPlayers));
		for (int i = 0; i < noOfAi; i++) {
			aiPlayers.add(new Ai(potSize / (noOfPlayers), name.remove(0)));
		}

		playPoker();
	}

	public void setNames() {
		name.add("Max");
		name.add("Vedrana");
		name.add("Lykke");
		name.add("Amin");
		name.add("Rikard");
		name.add("Kristina");
		name.add("Rolf");
		name.add("Schtefan Vettu");
		Collections.shuffle(name);
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
				player.reset("", new ArrayList<Card>());
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
				System.out.println("Current turn: " + playTurn);
				while (!allCallorFold()) {
					if (currentPlayer == noOfPlayers - 1) {
						if (!player.getDecision().equals("fold")) {
							System.out.println("player turn");
							player.makeDecision(currentMaxBet);
							System.out.println("-----------------------------------------");
						}
					} else {
						
						if (!aiPlayers.get(currentPlayer).getDecision().contains("fold")) {
							System.out.println(aiPlayers.get(currentPlayer).getName() + "'s turn");
							askForAiDecision();
							System.out.println("-----------------------------------------");
						}
					}
					currentPlayer = (currentPlayer + 1) % noOfPlayers;
					allCallorFold();
				}
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

		if (smallBlindPlayer == noOfPlayers) {
			player.smallBlind(5);
			// show animation?
		} else if (bigBlindPlayer == noOfPlayers) {
			player.bigBlind(10);
			// show animation?
		} else {
			aiPlayers.get(smallBlindPlayer).setSmallBlind(5);
			aiPlayers.get(bigBlindPlayer).setBigBlind(10);
			// show animation?
		}
	}

	public boolean allCallorFold() {
		for (Ai ai : aiPlayers) {
			if (!ai.getDecision().equals("fold")) {
				return false;
			}
		}

		if (player.getDecision().equals("fold")) {
			return true;
		} else {
			return false;
		}
	}

}
