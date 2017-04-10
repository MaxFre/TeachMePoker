package player;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import deck.Card;

public class Player {
	private int playerPot;
	private String decision = "";
	private Card card1, card2;
	private ArrayList<Card> cardsOnTable = new ArrayList<Card>();

	public Player(int initialPotsize) {
		playerPot = initialPotsize;
	}

	public void makeDecision(int currentMaxBet) {
		int loopcounter = 0;
		if (currentMaxBet > playerPot) {
			System.out.println("YOU LOSE");
		}
		System.out.println("The current bet lies at " + currentMaxBet);
		System.out.println("Your cards are: " + card1.getCardValue() + " of " + card1.getCardSuit().toString() + " and "
				+ card2.getCardValue() + " of " + card2.getCardSuit().toString());
		if (!cardsOnTable.isEmpty()) {
			System.out.println("and the cards on the table so far are: \n");
			if (cardsOnTable.size() > 3) {
				for (Card c : cardsOnTable) {
					System.out.print(c.getCardValue() + " of " + c.getCardSuit().toString());
					if (loopcounter < cardsOnTable.size())
						System.out.println(", ");
				}
				loopcounter++;
			}
		}
		loopcounter = 0;
		this.decision = JOptionPane.showInputDialog("Enter your decision: \n Valid options are: \n call, fold, raise");
		System.out.println("decision was made for player");
		// TODO Auto-generated method stub

	}

	public void setStartingHand(Card card1, Card card2) {
		this.card1 = card1;
		this.card2 = card2;

	}

	public void smallBlind(int i) {
		System.out.println("Player paid small blind(" + i + ")");

	}

	public void bigBlind(int i) {
		System.out.println("Player paid big blind(" + i + ")");
	}

	public String getDecision() {

		return decision;
	}

	public void reset(String resetDecision, ArrayList resetCardsOnTable) {
		decision = resetDecision;
		cardsOnTable = resetCardsOnTable;
	}

}
