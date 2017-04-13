package aiClass;

import java.util.ArrayList;
import deck.Card;


/**
 * @author Max Frennessen Main class for Ai-player that depending on turn, creates a calculation and
 *         returns a respond to controller. 17-04-12
 * @version 1.5
 */

public class Ai {

  private boolean isSmallBlind = false;
  private boolean isBigBlind = false;
  private int paidThisTurn = 0;
  private String name;
  private TurnOne turnOne;
  private TurnTwo turnTwo;
  private TurnThree turnThree;
  private TurnFour turnFour;
  private String whatToDo;
  private ArrayList<String> aiCards = new ArrayList<String>(); // Lista som lägger till alla kort
                                                               // som kommer in och som skickas till
                                                               // turns.
  private int aiPot; // AIPOT - KOMMER IN VIA CONTROLLER.



  public Ai(int aiPot, String name) {
    this.name = name;
    this.aiPot = aiPot;
  }


  // set starting hand
  public void setStartingHand(Card card1, Card card2) {
    aiCards.clear(); // nollställer arraylist.
    char A = card1.getCardSuit().charAt(0);
    char B = card1.getCardSuit().charAt(0);
    String firstCard = card1.getCardValue() + "," + String.valueOf(A);
    String secondCard = card2.getCardValue() + "," + String.valueOf(B);
    aiCards.add(firstCard);
    aiCards.add(secondCard);
    System.out.println("starting hand set for ai player");
  }


  // Make decision for the starting hand
  public void makeDecision(int currentBet) {
    turnOne = new TurnOne(aiCards, aiPot, currentBet);
    whatToDo = turnOne.decision(); // TurnOne respond.
    System.out.println("PaidBeforeThisTurn: " + this.paidThisTurn);
    this.paidThisTurn += aiPot - turnOne.updateAiPot();

    aiPot = turnOne.updateAiPot();
    System.out.println("PaidThisTurn(including what was paid before): " + this.paidThisTurn);
    System.out.println("AiPot after round: " + aiPot);
  }


  // Make decision for starting hand + flop
  public void makeDecision(int currentBet, Card[] flop) {
    for (Card card : flop) {
      char A = card.getCardSuit().charAt(0);
      aiCards.add(card.getCardValue() + "," + String.valueOf(A));
    }

    turnTwo = new TurnTwo(aiCards, aiPot, currentBet);
    whatToDo = turnTwo.decision(); // TurnTwo respond.
    System.out.println("PaidBeforeThisTurn: " + this.paidThisTurn);
    this.paidThisTurn += aiPot - turnTwo.updateAiPot();

    aiPot = turnTwo.updateAiPot();
    System.out.println("PaidThisTurn(including what was paid before): " + this.paidThisTurn);
    System.out.println("AiPot after round: " + aiPot);
  }


  // Make decision for starting hand + flop + turn && starting hand + flop +
  // turn + river
  public void makeDecision(int currentBet, Card turn) {
    char A = turn.getCardSuit().charAt(0);
    aiCards.add(turn.getCardValue() + "," + String.valueOf(A));

    if (aiCards.size() < 7) {
      turnThree = new TurnThree(aiCards, aiPot, currentBet);
      whatToDo = turnThree.decision(); // TurnThree respond.
      System.out.println("PaidBeforeThisTurn: " + this.paidThisTurn);
      this.paidThisTurn += aiPot - turnThree.updateAiPot();
      aiPot = turnThree.updateAiPot();
      System.out.println("PaidThisTurn(including what was paid before): " + this.paidThisTurn);
      System.out.println("AiPot after round: " + aiPot);

    } else if (aiCards.size() == 7) {
      turnFour = new TurnFour(aiCards, aiPot, currentBet);
      whatToDo = turnFour.decision(); // TurnFour respond.
      System.out.println("PaidBeforeThisTurn: " + this.paidThisTurn);
      this.paidThisTurn += aiPot - turnFour.updateAiPot();
      aiPot = turnFour.updateAiPot();
      System.out.println("PaidThisTurn(including what was paid before): " + this.paidThisTurn);
      System.out.println("AiPot after round: " + aiPot);
    }

  }


  /**
   * @param resets whatToDo.
   */
  public void setDecision(String reset) {
    whatToDo = reset;
  }


  /**
   * @return returns the Decision the ai made.
   */
  public String getDecision() {
    return whatToDo;
  }


  /**
   * @return returns the ai potSize
   */
  public int aiPot() {
    return aiPot;
  }


  /**
   * @param Updates the Ai's pot Size if it would win.
   */
  public void updateWinner(int aiPot) {
    this.aiPot = aiPot;
  }


  public String getName() {
    return name;
  }


  public void setBigBlind(int bigBlind, boolean b) {
    if (bigBlind > 0) {
      System.out.println("AI " + name + " paid the big Blind (" + bigBlind + ")");
    }
    isBigBlind = true;
    aiPot -= bigBlind;
    this.paidThisTurn += bigBlind;
  }


  public void setSmallBlind(int smallBlind, boolean b) {
    if (smallBlind > 0) {
      System.out.println("AI " + name + " paid the small Blind (" + smallBlind + ")");
    }
    isSmallBlind = true;
    aiPot -= smallBlind;
    this.paidThisTurn += smallBlind;
  }


  public boolean isSmallBlind() {
    return isSmallBlind;
  }


  public boolean isBigBlind() {
    return isBigBlind;
  }


  public int getPaidThisTurn() {
    return paidThisTurn;
  }

  public void setPaidThisTurn(int paidThisTurn) {
    this.paidThisTurn = paidThisTurn;
  }


}


