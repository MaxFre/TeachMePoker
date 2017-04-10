package hand;

import java.util.ArrayList;

import deck.Card;
import deck.Deck;

public class hand {
	private Calc calc;
	private Card card;
	private Deck deck;
	private ArrayList<Card> Cards = new ArrayList<Card>();
	private ArrayList<String> aiCards = new ArrayList<String>();  
	private ArrayList<String> toHighlight = new ArrayList<String>();  
	
	public hand() {
	
		
	for(int i = 0; i<15; i++){
		aiCards.clear();
		Cards.clear();
		
		deck = new Deck();
		deck.shuffle();
		getCards();
		convertToReadable();
	
		calc = new Calc(aiCards);
		String helper = calc.Help();	
		toHighlight = calc.toHiglight();
		
		
		System.out.println(aiCards);	
		System.out.println("Helper - " + helper);
		System.out.println("");
		System.out.println("toHighlight - " + toHighlight);
		System.out.println("");
		System.out.println("");
	}
	}
	
	public void convertToReadable(){
		
		for (int i = 0; i < Cards.size(); i++) {
			Card cardTemp = Cards.get(i);
			char A = cardTemp.getCardSuit().charAt(0);
			String temp = cardTemp.getCardValue()+","+String.valueOf(A);
			aiCards.add(temp);
		}
		
		
//		aiCards.add("4,C");
//		aiCards.add("8,D");
//		aiCards.add("6,H");
//		aiCards.add("5,C");
//		aiCards.add("7,C");
//		aiCards.add("13,D");
		
	}
	
	
	
	public void getCards() {

		for (int i = 0; i < 2; i++) {
			Cards.add(deck.getCard());

		}
	}
	
	public ArrayList sendToHighlight(){
		return toHighlight;
	}
	
	
	
	public static void main(String[] args) {

		hand run = new hand();
	}
}
