package player;

import java.util.Random;

import deck.Card;

public class Player {
    private int playerPot;
	private String decision = "";

	public Player(int initialPotsize) {
	  playerPot = initialPotsize;
	}

	public void makeDecision(int currentMaxBet) {
	    if(currentMaxBet > playerPot) {
	      System.out.println("YOU LOSE");
	    }
		System.out.println("decision was made for player");
		// TODO Auto-generated method stub

	}

	public void setStartingHand(Card card1, Card card2) {
		// TODO Auto-generated method stub

	}

	public void bet(int i) {
		// TODO Auto-generated method stub

	}

	public String getDecision() {
		Random rand = new Random();
		if (rand.nextInt(100) < 10) {
			decision = "fold";
		} else if (rand.nextInt(100) > 95) {
			decision = "all-in";
		}
		return decision;
	}

	public void setDecision(String string) {
		decision = string;
	}

}
