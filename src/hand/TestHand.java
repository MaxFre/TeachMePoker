package hand;

import java.util.ArrayList;
import java.util.Random;
import deck.Card;
import deck.Deck;

/**
 * Class for testing the hand-class.
 * 
 * @author Max Frennessen 17-04-12
 * @version 1.5
 */
public class TestHand {
  Deck deck;
  Hand hand;
  private ArrayList<Card> cards = new ArrayList<Card>();
  private int[] turn = {2, 5, 6, 7};

  public TestHand() {

    for (int testAlot = 0; testAlot < 15; testAlot++) { // runs 15 random hands that are being tested.
      cards.clear();
      deck = new Deck();
      deck.shuffle();

      Random rand = new Random();
      int RandomSize = rand.nextInt(4);

      int thisTurn = turn[RandomSize];

      for (int i = 0; i < thisTurn; i++) {
        cards.add(deck.getCard());
      }

      hand = new Hand(cards);
    }
  }

  public static void main(String[] args) {
    TestHand run = new TestHand();
  }
}
