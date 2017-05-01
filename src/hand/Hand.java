package hand;

import java.util.ArrayList;

import deck.Card;

/**
 * The hand-class that will guide and help the noob player.
 * 
 * @author Max Frennessen 17-04-12
 * @version 1.5
 * @
 */
public class Hand {
	private HandCalculation calc;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<String> aiCards = new ArrayList<String>();
	private ArrayList<String> toHighlight = new ArrayList<String>();
	private String helper;
	private String advice;
	private int pwrBar;
	/**
	 * Constructor
	 * @param cards gets card that are important for this turn.
	 */
	public Hand(ArrayList<Card> cards) {
		this.cards = cards;
		convertToReadable();

		calc = new HandCalculation(aiCards);
		helper = calc.Help();
		advice = calc.advice();
		pwrBar = calc.calcPwrBarLvl();
		toHighlight = calc.toHiglight();

		System.out.println(" -NEW HAND- ");
		System.out.println(aiCards);
		System.out.println("Helper - " + helper);
		System.out.println("");
		System.out.println("Advice - " + advice);
		System.out.println("");
		System.out.println("pwrBar - " + pwrBar);
		System.out.println("toHighlight - " + toHighlight);
		System.out.println("");

	}

	/**
	 * Converts the cards into readable Strings.
	 */
	public void convertToReadable() {

		for (int i = 0; i < cards.size(); i++) {
			Card cardTemp = cards.get(i);
			char A = cardTemp.getCardSuit().charAt(0);
			String temp = cardTemp.getCardValue() + "," + String.valueOf(A);
			aiCards.add(temp);
		}
	}

	public int toPowerBar(){  // 1-4 ska det va.
		return pwrBar;
	}

	public String theHelp(){
		return helper;
	}

	public String theAdvice(){
		return advice;
	}
	/**
	 * @return returns what is suppost to be highlighted.
	 */
	public ArrayList<String> sendToHighlight() {
		return toHighlight;
	}

	/**
	 * @return highlighted without ",".
	 */
	public ArrayList<String> sendToHighlightChecker() {
		ArrayList<String> converterHighlight = new ArrayList<String>();
		for(int i = 0; i< toHighlight.size() ; i++){
			String[] splitter = toHighlight.get(i).split(",");
			converterHighlight.add(splitter[0]);
		}  
		return converterHighlight;
	}



	public void reCalc(){
		this.calc = new HandCalculation(aiCards);

		this.toHighlight = calc.toHiglight();
	}
	
	
}

