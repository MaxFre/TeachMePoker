package aiClass;


import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import deck.Card;
import deck.CardValue;
import deck.Deck;
import deck.Suit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
 * Class that is for testing of the AI.
 * 
 * @author Max Frennessen 17-04-12
 * @version 1.5
 */
public class TestAi {
  Deck deck;
  Ai ai;
  private Card card1;
  private Card card2;
  private Card[] flop = new Card[3];
  private Card cardTurn;
  private Card cardRiver;

  private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
  private ArrayList<Card> decken = new ArrayList<Card>();
  
  public TestAi() {
	  decken = new ArrayList<Card>();

//	 1=2h   14 = 2s   27 = 2c   40=2d 
//	  2H, 2S , 2D,4C, 3D
	  deck = new Deck();
//    deck.shuffle();

		for (int i = 0; i < 1; i++) {
			card1 = deck.getCard();
		}
		deck = new Deck();
		for (int i = 0; i < 2; i++) {
			card2 = deck.getCard();
		}
    //FLOPP
		deck = new Deck();
		for (int i = 0; i < 3; i++) {
			flop[0] = deck.getCard();
		}
		
		deck = new Deck();
		for (int i = 0; i < 4; i++) {
			flop[1] = deck.getCard();
		}
		deck = new Deck();
		for (int i = 0; i < 5; i++) {
			flop[2] = deck.getCard();
		}
//   //TURN
//		deck = new Deck();
//		for (int i = 0; i < 16; i++) {
//			cardTurn = deck.getCard();
//		}
//	//RIVER
//		deck = new Deck();
//		for (int i = 0; i < 16; i++) {
//			cardRiver = deck.getCard();
//		}
			
	
//    card1 = deck.getCard();
//    card2 = deck.getCard();
//    cardTurn = deck.getCard();
//    cardRiver = deck.getCard();

//    for (int i = 0; i < 3; i++) { // 3 kort för flopp.
////      deck.shuffle();
//      flop[i] = deck.getCard();
//    }

    ai = new Ai(808, "TestAiName");

    ai.setStartingHand(card1, card2);
//    ai.makeDecision(150);
//    System.out.println(ai.getDecision());

    System.out.println("\n\n-Test FLOP-");
    ai.makeDecision(32, flop);
    System.out.println(ai.getDecision());
//
//    System.out.println("\n\n-Test TURN-");
//    ai.makeDecision(32, cardTurn);
//    System.out.println(ai.getDecision());
//
//    System.out.println("\n\n-Test RIVER-");
//    ai.makeDecision(32, cardRiver);
//    System.out.println(ai.getDecision());

//    ai.updateWinner(1032);
//    System.out.println("\n\nAI-pot - " + ai.aiPot());
  }


  
  public static void main(String[] args) {
    TestAi run = new TestAi();
  }
}
