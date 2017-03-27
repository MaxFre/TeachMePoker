package teachMePoker;

import javax.swing.Icon;
/**
 * Representation of a playing card
 * @author Lykke Levin
 *
 */

public class Card {
	
	private String cardSuit;
	private int cardValue;
	private Icon cardIcon;

	/**
	 * Creates a Card with a suit, value and picture 
	 * @param suit "Hearts", "Diamonds", "Clubs", "Spades"
	 * @param value 2,3,4,5,6,7,8,9,10,11,12,13,14
	 * @param cardIcon 
	 */
	public Card(String suit, int value, Icon cardIcon){
		this.cardSuit = suit;
		this.cardValue = value;
		this.cardIcon = cardIcon;
	}
	/**
	 * Returns the value of the card. 
	 * @return cardValue
	 */
	public int getCardValue(){
		return cardValue;
	}
	/**
	 * Returns the suit of the card.
	 * @return cardSuit
	 */
	public String getCardSuit(){
		return cardSuit;
	}
	/**
	 * Returns the icon of the card.
	 * @return cardIcon
	 */
	public Icon getCardIcon(){
		return cardIcon;
	}
	
}
