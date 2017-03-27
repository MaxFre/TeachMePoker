package aiClass;

import java.util.ArrayList;
import java.util.Random;

import deck.Card;

/**
 * 
 * @author Max Frennessen Huvudklass f�r AI-spelaren som beroende p� turn
 *         startar en AI utr�kning och skickar svar till controller p� vad AI:n
 *         kommer g�ra p� dess tur. 24-03-17
 */
public class Ai {

	private TurnOne turnOne;
	private TurnTwo turnTwo;
	private TurnThree turnThree;
	private TurnFour turnFour;
	private Cards cards;
	private String[] fromCards = new String[10];
	private String whatToDo;
	private int testAI = 0;
	private ArrayList<String> aiCards = new ArrayList<String>(); // Lista som
																	// l�gger
																	// till alla
																	// kort
																	// som
																	// kommer in
																	// och som
																	// skickas
																	// till
																	// turns.
	private int aiPot; // AIPOT - KOMMER IN VIA CONTROLLER.

	public Ai(int aiPot) { // F�R IN V�RDE H�R FR�N CONTROLLER, VET INTE HUR �N.
		this.aiPot = aiPot;

		getCards(); // S�L�NGE JAG INTE F�R IN VIA AI-CONSTRUKTOR

		testAI = 0;

		// for(int x = 0; x<2; x++){

		if (testAI == 0) {
			for (int i = 0; i < 2; i++) { // L�gger till kort till ArrayList som
											// skickas till constructor.
				aiCards.add(fromCards[i]);
			}
		}

		if (testAI == 1) {
			for (int i = 0; i < 5; i++) { // I = 2!!!
				aiCards.add(fromCards[i]);
			}
		}

		if (testAI == 2) {
			for (int i = 0; i < 6; i++) { // I = 3!!!
				aiCards.add(fromCards[i]);
			}
		}

		if (testAI == 3) {
			for (int i = 0; i < 7; i++) { // I = 4!!!
				aiCards.add(fromCards[i]);
			}
		}

		if (testAI == 0) {
			turnOne = new TurnOne(aiCards);
			whatToDo = turnOne.decision(); // TurnONe respond.
		}

		if (testAI == 1) {
			turnTwo = new TurnTwo(aiCards);
			whatToDo = turnTwo.decision(); // FlOP respond.
		}

		if (testAI == 2) {
			turnThree = new TurnThree(aiCards);
			whatToDo = turnThree.decision(); // TURN respond.
		}

		if (testAI == 3) {
			turnFour = new TurnFour(aiCards);
			whatToDo = turnFour.decision(); // RIVER respond.
		}

		// testAI++;

		System.out.println("AI - " + whatToDo);
		System.out.println("");
		System.out.println("");
	}
	// }

	public void getCards() {
		cards = new Cards();
		fromCards = cards.getCards();

	}

	public static void main(String[] args) {
		Ai run = new Ai(500);
	}

	// Rikards metoder
	public void makeDecision(int turn, int currentPot) {
		// TODO Auto-generated method stub

	}

	public void setStartingHand(Card card1, Card card2) {
		// TODO Auto-generated method stub

	}

	public void bet(int i) {
		// TODO Auto-generated method stub

	}

	public String getDecision() {
		String decision = "fold";
		return decision;
	}
}
