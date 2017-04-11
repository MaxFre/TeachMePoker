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
	private boolean setupPhase = true;
	private boolean allCalledorFolded = false;
	private boolean winnerDeclared = true;
	private ArrayList<String> name = new ArrayList<String>();

	public SPController() {
		deck = new Deck();
		new GUI();
		startGame(3, 5000);
	}

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
		Collections.shuffle(name);
	}

	public void playPoker() {
		deck = new Deck();
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
				System.out.println("<-<-<-<-<-<-<-<-<->->->->->->->->");
				while (!allCalledorFolded) {
					if (currentPlayer == noOfPlayers - 1) {
						if (!player.getDecision().equals("fold")) {
							if (!(checkLivePlayers() > 1)) {
								System.out.println();
								System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||");
								System.out.println("much wow, amazeballs");
								System.out.println("Player has won the round!");
								System.out.println("much wow, amazeballs");
								System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||");
								System.out.println("\n\n");
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
								System.out.println();
								System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||");
								System.out.println("much wow, amazeballs");
								System.out.println(aiPlayers.get(currentPlayer).getName() + " has won the round!");
								System.out.println("much wow, amazeballs");
								System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||");
								System.out.println("\n\n");
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
				}
				playTurn++;
				allCalledorFolded = false;
				if (winnerDeclared) {
					break;
				}
			}
			winnerDeclared = false;
			playTurn = 0;
			setupPhase = true;
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
			playPoker();
		}
	}

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
		return livePlayers;
	}

	private void askForAiDecision() {

		System.out.println("current playTurn(0 = start, 1 = flop, 2 = turn, 3 = river): " + playTurn);
		if (playTurn == 0) {
			aiPlayers.get(currentPlayer).makeDecision(currentMaxBet);
			aiAction(currentPlayer);
		} else if (playTurn == 1) {
			aiPlayers.get(currentPlayer).makeDecision(currentMaxBet, flop);
			aiAction(currentPlayer);
		} else if (playTurn == 2) {
			aiPlayers.get(currentPlayer).makeDecision(currentMaxBet, turn);
			aiAction(currentPlayer);
		} else if (playTurn == 3) {
			aiPlayers.get(currentPlayer).makeDecision(currentMaxBet, river);
			aiAction(currentPlayer);
		}
	}

	private void aiAction(int currentPlayer) {
		String aiDecision = aiPlayers.get(currentPlayer).getDecision();
		String[] split;
		if (aiDecision.contains("raise")) {
			split = aiDecision.split(",");
			currentMaxBet = Integer.parseInt(split[1]);
		} else if (aiDecision.contains("fold")) {
			// TODO gui.showAIFolded?
		} else if (aiDecision.contains("call")) {
			split = aiDecision.split(",");
			currentMaxBet = Integer.parseInt(split[1]);
			// TODO gui.showAICalled?
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

		if (smallBlindPlayer == noOfPlayers - 1) {
			player.smallBlind(smallBlind);
			aiPlayers.get(bigBlindPlayer).setBigBlind(bigBlind);
			// show animation?
		} else if (bigBlindPlayer == noOfPlayers - 1) {
			aiPlayers.get(smallBlindPlayer).setSmallBlind(smallBlind);
			player.bigBlind(bigBlind);
			// show animation?
		} else {

			aiPlayers.get(smallBlindPlayer).setSmallBlind(smallBlind);
			aiPlayers.get(bigBlindPlayer).setBigBlind(bigBlind);
			// show animation?
		}
		this.currentPotSize = smallBlind + bigBlind;
	}

	public void allCallorFold() {
		int noOfAIFoldedorCalled = 0;
		for (Ai ai : aiPlayers) {
			if (ai.getDecision().equals("fold")) {
				noOfAIFoldedorCalled++;
			} else if (ai.getDecision().contains("call")) {
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
