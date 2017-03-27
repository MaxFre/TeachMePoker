package deck;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
  private ArrayList<Card> cards = new ArrayList<Card>();
  private String suit;

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
      for (int value = 2; value <= 14; value++) {
        cards.add(new Card(suit, value));
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
