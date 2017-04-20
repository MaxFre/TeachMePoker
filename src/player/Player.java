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
  private String name;
  private int playerPot;
  private String decision = "";
  private Card card1, card2;
  private ArrayList<Card> cardsOnTable = new ArrayList<Card>();
  private int alreadyPaid = 0;

  /**
   * Constructor
   * 
   * @param initialPotsize The player's pot
   */
  public Player(int initialPotsize, String name) {
    this.playerPot = initialPotsize;
    this.name = name;
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
    } else {
      System.out.println("The current bet lies at " + currentMaxBet);
      System.out.println("You have " + playerPot + " at your disposal");
      System.out.println(
          "Your cards are: " + card1.getCardValue() + " of " + card1.getCardSuit().toString()
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
      // test
      Object[] options = {"Call", "Fold", "Raise"};
      int n = JOptionPane.showOptionDialog(null, "Hey numbnut, it's your turn!", "It is your turn!",
          JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
          options[2]);
      System.out.println(n);
      if (n == 0) {
        decision = "call";
        playerPot -= currentMaxBet - alreadyPaid;
        alreadyPaid += currentMaxBet - alreadyPaid;
      } else if (n == 1) {
        decision = "fold";
      } else if (n == 2) {
        decision = "raise," + JOptionPane.showInputDialog(
            "How much would you like to raise to? \n current bet lies at " + currentMaxBet);
        String[] split = decision.split(",");
        int oldMaxBet = currentMaxBet;
        int newMaxBet = Integer.parseInt(split[1]);
        if (newMaxBet >= playerPot) {
          newMaxBet = playerPot;
          System.out.println("Player All-ins");
        }
        int actualRaise = newMaxBet - oldMaxBet;
        playerPot -= (oldMaxBet - alreadyPaid) + actualRaise;
        alreadyPaid += actualRaise;
        System.out.println("PlayerPot = " + playerPot);
      } else if (n == -1) {
        System.exit(0);
      }
    }
    // test
    // this.decision = JOptionPane.showInputDialog(
    // "Enter your decision: \n Valid options are: \n call(this is also check), fold, raise\n Syntax
    // is: \"call\", \"fold\", \"raise,<number>\"");
    // decision.toLowerCase();
    // String[] split;
    // if (decision.contains("raise")) {
    // split = decision.split(",");
    // if (Integer.parseInt(split[1]) > playerPot) {
    // System.out.println("You don't have that much money");
    // this.decision = JOptionPane.showInputDialog(
    // "Enter your decision: \n Valid options are: \n call(this is also check), fold, raise\n Syntax
    // is: \"call\", \"fold\", \"raise,<number>\"");
    // }
    //
    //
    // }
    System.out.println("Decision was made for player (" + decision + ")");
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
    this.alreadyPaid += i;
    System.out.println("Player paid small blind(" + i + ")");

  }

  /**
   * Method which forces the player to pay the big blind when it is his turn
   * 
   * @param i The amount to pay
   */
  public void bigBlind(int i) {
    this.alreadyPaid += i;
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
    alreadyPaid = 0;
  }

  /**
   * Method which returns the players pot
   * 
   * @return playerPot
   */
  public int getPlayerPot() {
    return playerPot;
  }

  public void setPlayerPot(int newValue) {
    this.playerPot += newValue;
  }

  public String getName() {
    return name;
  }

  public int getAlreadyPaid() {
    return alreadyPaid;
  }

  public void setAlreadyPaid(int newValue) {
    this.alreadyPaid += newValue;
  }

}
