package hand;

import java.util.ArrayList;
import java.util.Random;

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
		int[] test = new int[4];
		ArrayList<Integer> testar = new ArrayList<Integer>();
		
		testar.add(2);
		testar.add(5);
		testar.add(6);
		testar.add(7);
		
	
		
		Random rand = new Random();
		int kolla = rand.nextInt(3);
		int checking = testar.get(kolla);
		
		for (int i = 0; i < checking; i++) {
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
