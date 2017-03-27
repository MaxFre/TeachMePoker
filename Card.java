package teachMePoker;

import javax.swing.Icon;

public class Card {
	
	private String cardSuit;
	private int cardValue;
	private Icon cardIcon;

	public Card(String suit, int value, Icon cardIcon){
		this.cardSuit = suit;
		this.cardValue = value;
		this.cardIcon = cardIcon;
	}
	
	public int getCardValue(){
		return cardValue;
	}
	
	public String getCardSuit(){
		return cardSuit;
	}
	
	public Icon getCardIcon(){
		return cardIcon;
	}
	
	public static void main(String [] args){
		
	}
}
