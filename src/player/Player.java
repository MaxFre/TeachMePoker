package player;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import deck.Card;

/**
 * The Player class holds values and a few methods for the playable Player
 * 
 * @author Tzeentch
 * @version 0.2
 */
public class Player {
  private int playerPot;
  private String decision = "";
  private Card card1, card2;
  private ArrayList<Card> cardsOnTable = new ArrayList<Card>();

  /**
   * Constructor
   * 
   * @param initialPotsize The player's pot
   */
  public Player(int initialPotsize) {
    playerPot = initialPotsize;
  }

  /**
   * Placeholder method that prompts for a decision. No errorhandling.
   * 
   * @param currentMaxBet Highest bet on the table.
   */
  public void makeDecision(int currentMaxBet) {
    int loopcounter = 0;
    if (currentMaxBet > playerPot) {
      System.out.println("YOU LOSE");
    }
    System.out.println("The current bet lies at " + currentMaxBet);
    System.out
        .println("Your cards are: " + card1.getCardValue() + " of " + card1.getCardSuit().toString()
            + " and " + card2.getCardValue() + " of " + card2.getCardSuit().toString());
    if (!cardsOnTable.isEmpty()) {
      System.out.println("and the cards on the table so far are: \n");
      if (cardsOnTable.size() > 3) {
        for (Card c : cardsOnTable) {
          System.out.print(c.getCardValue() + " of " + c.getCardSuit().toString());
          if (loopcounter < cardsOnTable.size())
            System.out.println(", ");
        }
        loopcounter++;
      }
    }
    loopcounter = 0;
    this.decision = JOptionPane.showInputDialog("Enter your decision: \n Valid options are: \n call, fold, raise");
       
    System.out.println("decision was made for player (" + decision + ")");
    // TODO Auto-generated method stub

  }

  /**
   * Method which sets the starting hand(hole cards) for the player
   * 
   * @param card1 His first card
   * @param card2 His second card
   */
  public void setStartingHand(Card card1, Card card2) {
    this.card1 = card1;
    this.card2 = card2;

  }

  /**
   * Method which forces the player to pay the small blind when it is his turn
   * 
   * @param i The amount to pay
   */
  public void smallBlind(int i) {
    System.out.println("Player paid small blind(" + i + ")");

  }

  /**
   * Method which forces the player to pay the big blind when it is his turn
   * 
   * @param i The amount to pay
   */
  public void bigBlind(int i) {
    System.out.println("Player paid big blind(" + i + ")");
  }

  /*
   * Method which returns the players decision.
   */
  public String getDecision() {

    return decision;
  }

  /**
   * Method which resets the players decision and his "stored cards on the table"
   * 
   * @param resetDecision Resets his decision
   * @param resetCardsOnTable Resets his "stored table cards"
   */
  public void reset(String resetDecision, ArrayList<Card> resetCardsOnTable) {
    decision = resetDecision;
    cardsOnTable = resetCardsOnTable;
  }

  /**
   * Method which returns the players pot
   * 
   * @return playerPot
   */
  public int getPlayerPot() {
    return playerPot;
  }

}
