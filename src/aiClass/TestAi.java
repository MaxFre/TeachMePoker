package aiClass;


import deck.Card;
import deck.Deck;

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

  public TestAi() {

    deck = new Deck();
    deck.shuffle();

    card1 = deck.getCard();
    deck.shuffle();
    card2 = deck.getCard();
    cardTurn = deck.getCard();
    cardRiver = deck.getCard();

    for (int i = 0; i < 3; i++) { // 3 kort för flopp.
      deck.shuffle();
      flop[i] = deck.getCard();
    }

    ai = new Ai(808, "TestAiName");

    ai.setStartingHand(card1, card2);
    ai.makeDecision(150);
    System.out.println(ai.getDecision());

//    System.out.println("\n\n-Test FLOP-");
//    ai.makeDecision(32, flop);
//    System.out.println(ai.getDecision());
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
