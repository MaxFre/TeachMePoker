package aiClass;

import java.util.ArrayList;
import deck.Card;


/**
 * @author Max Frennessen
 * Main class for Ai-player that depending on turn, creates a calculation and
 * returns a respond to controller.
 *	17-04-12
 * @version 1.5
 */
public class Ai {

	private TurnOne turnOne;
	private TurnTwo turnTwo;
	private TurnThree turnThree;
	private TurnFour turnFour;
	private String whatToDo;
	private ArrayList<String> aiCards = new ArrayList<String>();    //Lista som lägger till alla kort som kommer in och som skickas till turns.
	private int aiPot;   //AIPOT - KOMMER IN VIA CONTROLLER.
	
	
	
	public Ai(int aiPot) {
		this.aiPot = aiPot;
	}

	// set starting hand
	public void setStartingHand(Card card1, Card card2) {
		aiCards.clear(); // nollställer arraylist.
		char A = card1.getCardSuit().charAt(0);
		char B = card1.getCardSuit().charAt(0);
		String firstCard = card1.getCardValue() + "," + String.valueOf(A);
		String secondCard = card2.getCardValue() + "," + String.valueOf(B);
		aiCards.add(firstCard);
		aiCards.add(secondCard);
		System.out.println("starting hand set for ai player");
	}

	// Make decision for the starting hand
	public void makeDecision(int currentBet) {
		turnOne = new TurnOne(aiCards, aiPot, currentBet);
		whatToDo = turnOne.decision(); // TurnOne respond.
		aiPot = turnOne.updateAiPot();
	}

	// Make decision for starting hand + flop
	public void makeDecision(int currentBet, Card[] flop) {
		for (Card card : flop) {
			char A = card.getCardSuit().charAt(0);
			aiCards.add(card.getCardValue() + "," + String.valueOf(A));
		}

		turnTwo = new TurnTwo(aiCards, aiPot, currentBet);
		whatToDo = turnTwo.decision(); // TurnTwo respond.
		aiPot = turnTwo.updateAiPot();

	}

	// Make decision for starting hand + flop + turn && starting hand + flop +
	// turn + river
	public void makeDecision(int currentBet, Card turn) {
		char A = turn.getCardSuit().charAt(0);
		aiCards.add(turn.getCardValue() + "," + String.valueOf(A));

		if (aiCards.size() < 7) {
			turnThree = new TurnThree(aiCards, aiPot, currentBet);
			whatToDo = turnThree.decision(); // TurnThree respond.
			aiPot = turnThree.updateAiPot();

		} else if (aiCards.size() == 7) {
			turnFour = new TurnFour(aiCards, aiPot, currentBet);
			whatToDo = turnFour.decision(); // TurnFour respond.
			aiPot = turnFour.updateAiPot();
		}

	}

	/**
	 * @param resets whatToDo.         
	 */
	public void setDecision(String reset) {
		whatToDo = reset;

	}

	/**
	 * @return returns the Decision the ai made.
	 */
	public String getDecision() {
		return whatToDo;
	}

	/**
	 * @return returns the ai potSize
	 */
	public int aiPot() {
		return aiPot;
	}
	/** 
	 * @param Updates the Ai's pot Size if it would win.
	 */
	public void updateWinner(int aiPot) {
		this.aiPot = aiPot;
	}

}


