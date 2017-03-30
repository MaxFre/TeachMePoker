package deck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Deck {
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	public Deck() {
		createDeck();
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

	public Card getCard() {
		return deck.remove(0);
		//return deck.get(0);
	}

	public Card removeCard() {
		return deck.remove(0);
	}

	public int getNumberOfCardsInDeck(){
		return deck.size();
	}

	public void createDeck(){
		deck = new ArrayList<Card>();

		for (Suit suit: Suit.values()) {
			try {
				for (CardValue card: CardValue.values()) {
					deck.add(new Card(suit, card,
							new ImageIcon(ImageIO.read(classLoader.getResourceAsStream("images/" + card.getCardValue() + suit.getSuitLetter() + ".png")))));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String [] args){
		Deck deck = new Deck ();
		for(int i = 0 ; i < 52; i++){
			System.out.println(deck.getCard().getCardValue() + " of " + deck.getCard().getCardSuit());
			deck.removeCard();
		}
	}
}
