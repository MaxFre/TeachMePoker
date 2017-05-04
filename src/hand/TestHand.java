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
  Card card1;
  Card card2;
  Card card3;
  Card card4;
  Card card5;
  
  private ArrayList<Card> cards = new ArrayList<Card>();
  private int[] turn = {2, 5, 6, 7};

  public TestHand() {

//		 1=2h   14 = 2s   27 = 2c   40=2d 
//	  2H, 2S, 2C,4H,4S
	  deck = new Deck();
//    deck.shuffle();

		for (int i = 0; i < 1; i++) {
			card1 = deck.getCard();
		}
		deck = new Deck();
		for (int i = 0; i < 14; i++) {
			card2 = deck.getCard();
		}
    //FLOPP
		deck = new Deck();
		for (int i = 0; i < 1; i++) {
			card3 = deck.getCard();
		}
		
		deck = new Deck();
		for (int i = 0; i < 1; i++) {
			card4 = deck.getCard();
		}
		deck = new Deck();
		for (int i = 0; i < 15; i++) {
			card5 = deck.getCard();
		}
	  
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		cards.add(card4);
		cards.add(card5);

		
//    for (int testAlot = 0; testAlot < 15; testAlot++) {
//      cards.clear();
//      deck = new Deck();
//      deck.shuffle();
//
//      Random rand = new Random();
//      int RandomSize = rand.nextInt(4);
//
//      int thisTurn = turn[3];
//
//      for (int i = 0; i < thisTurn; i++) {
//        cards.add(deck.getCard());
//      }

      hand = new Hand(cards);
      
    
    }
		

//  }

  public static void main(String[] args) {
    TestHand run = new TestHand();
  }
}
