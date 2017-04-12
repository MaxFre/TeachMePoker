package hand;

import java.util.ArrayList;
import java.util.Random;
import deck.Card;
import deck.Deck;

/**
 * The hand-class that will guide and help the noob player.
 * 
 * @author Max Frennessen 17-04-12
 * @version 1.5
 */
public class Hand {
  private HandCalculation calc;
  private ArrayList<Card> cards = new ArrayList<Card>();
  private ArrayList<String> aiCards = new ArrayList<String>();
  private ArrayList<String> toHighlight = new ArrayList<String>();

  /**
   * 
   * @param cards gets card that are important for this turn.
   */
  public Hand(ArrayList<Card> cards) {
    this.cards = cards;
    convertToReadable();

    calc = new HandCalculation(aiCards);
    String helper = calc.Help();
    toHighlight = calc.toHiglight();

    System.out.println(" -NEW HAND- ");
    System.out.println(aiCards);
    System.out.println("Helper - " + helper);
    System.out.println("");
    System.out.println("toHighlight - " + toHighlight);
    System.out.println("");
    System.out.println("");
  }

  /**
   * Converts the cards into readable Strings.
   */
  public void convertToReadable() {

    for (int i = 0; i < cards.size(); i++) {
      Card cardTemp = cards.get(i);
      char A = cardTemp.getCardSuit().charAt(0);
      String temp = cardTemp.getCardValue() + "," + String.valueOf(A);
      aiCards.add(temp);
    }

  }

  /**
   * @return returns what is suppost to be highlighted.
   */
  public ArrayList<String> sendToHighlight() {
    return toHighlight;
  }

}
