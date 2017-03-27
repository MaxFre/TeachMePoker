package deck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Deck {
	private ArrayList<Card> cards = new ArrayList<Card>();
	private String suit;
	private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	public Deck() {
		for (int suitNo = 0; suitNo < 4; suitNo++) {
			if (suitNo == 0) {
				suit = "Hearts";
			} else if (suitNo == 1) {
				suit = "Diamonds";
			} else if (suitNo == 2) {
				suit = "Clubs";
			} else if (suitNo == 3) {
				suit = "Spades";
			}
			try {
				for (int value = 2; value <= 14; value++) {
					cards.add(new Card(suit, value, new ImageIcon(ImageIO
							.read(classLoader.getResourceAsStream("/resources/images/" + value + suit + ".png")))));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void shuffle() {
		Collections.shuffle(cards);
	}

	public Card getCard() {
		return cards.remove(0);
	}

}
