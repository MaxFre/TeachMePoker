package aiClass;

import java.util.ArrayList;
import deck.Card;


/**
 * @author Max Frennessen Main class for Ai-player that depending on turn, creates a calculation and
 *         returns a respond to controller. 17-04-12
 * @version 1.5
 */

public class Ai {
  private AiDecide aiDecide;
  private boolean isSmallBlind = false;
  private boolean isBigBlind = false;
  private boolean sameTurn = false;
  private int paidThisTurn = 0;
  private String name;
  private String whatToDo = "";
  private ArrayList<String> aiCards = new ArrayList<String>(); // Lista som lägger till alla kort
                                                               // som kommer in och som skickas till
                                                               // turns.
  private int aiPot; // AIPOT - KOMMER IN VIA CONTROLLER.
  private int highCard;
  private int handStrength;


  public Ai(int aiPot, String name) {
    this.name = name;
    this.aiPot = aiPot;
  }


  public void setStartingHand(Card card1, Card card2) { // set starting hand

    aiCards.clear(); // nollställer arraylist.
    char A = card1.getCardSuit().charAt(0);
    char B = card2.getCardSuit().charAt(0);
    String firstCard = card1.getCardValue() + "," + String.valueOf(A);
    String secondCard = card2.getCardValue() + "," + String.valueOf(B);
    this.highCard = card1.getCardValue();
    if (card2.getCardValue() > highCard) {
      highCard = card2.getCardValue();
    }
    aiCards.add(firstCard);
    aiCards.add(secondCard);
    // System.out.println("starting hand set for ai player");
  }


  public void makeDecision(int currentBet) { // Make decision for the starting hand

//    turnOne = new TurnOne(aiCards, aiPot, currentBet, paidThisTurn, sameTurn);
    aiDecide = new AiDecide(aiCards, aiPot, currentBet, paidThisTurn, sameTurn);
//    whatToDo = turnOne.decision(); // TurnOne respond.
    whatToDo = aiDecide.decision();
    // System.out.println("PaidBeforeThisTurn: " + this.paidThisTurn);
    this.paidThisTurn += aiPot - aiDecide.updateAiPot();    
    aiPot = aiDecide.updateAiPot();
    // System.out.println("PaidThisTurn(including what was paid before): " + this.paidThisTurn);
    System.out.println("AiPot after round: " + aiPot);
  }


  public void makeDecision(int currentBet, Card[] flop) { // Make decision for starting hand + flop

    if (!sameTurn) {
      for (Card card : flop) {
        char A = card.getCardSuit().charAt(0);
        aiCards.add(card.getCardValue() + "," + String.valueOf(A));
      }
    }

//    turnTwo = new TurnTwo(aiCards, aiPot, currentBet, paidThisTurn, sameTurn);
    aiDecide = new AiDecide(aiCards, aiPot, currentBet, paidThisTurn, sameTurn);
//    whatToDo = turnTwo.decision(); // TurnTwo respond.
    whatToDo = aiDecide.decision();
    System.out.println("PaidBeforeThisTurn: " + this.paidThisTurn);
    this.paidThisTurn += aiPot - aiDecide.updateAiPot();

    aiPot = aiDecide.updateAiPot();
    System.out.println("PaidThisTurn(including what was paid before): " + this.paidThisTurn);
    System.out.println("AiPot after round: " + aiPot);
  }


  public void makeDecision(int currentBet, Card turn) { // Make decision for starting hand + flop +
                                                        // turn && starting hand + flop + turn +
                                                        // river

    if (!sameTurn) {
      char A = turn.getCardSuit().charAt(0);
      aiCards.add(turn.getCardValue() + "," + String.valueOf(A));
    }
    if (aiCards.size() < 7) {
      aiDecide = new AiDecide(aiCards, aiPot, currentBet, paidThisTurn, sameTurn);
//      turnThree = new TurnThree(aiCards, aiPot, currentBet, paidThisTurn, sameTurn);
//      whatToDo = turnThree.decision(); // TurnThree respond.
      whatToDo = aiDecide.decision(); // TurnThree respond.
      System.out.println("PaidBeforeThisTurn: " + this.paidThisTurn);
      this.paidThisTurn += aiPot - aiDecide.updateAiPot();
      aiPot = aiDecide.updateAiPot();
      System.out.println("PaidThisTurn(including what was paid before): " + this.paidThisTurn);
      System.out.println("AiPot after round: " + aiPot);

    } else if (aiCards.size() == 7) {
    	aiDecide = new AiDecide(aiCards, aiPot, currentBet, paidThisTurn, sameTurn);
//      turnFour = new TurnFour(aiCards, aiPot, currentBet, paidThisTurn, sameTurn);
//      whatToDo = turnFour.decision(); // TurnFour respond.
    	 whatToDo = aiDecide.decision(); // TurnFour respond.
      System.out.println("PaidBeforeThisTurn: " + this.paidThisTurn);
      this.paidThisTurn += aiPot - aiDecide.updateAiPot();
      aiPot = aiDecide.updateAiPot();
      handStrength = aiDecide.gethandStrength();
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

    this.aiPot += aiPot;
  }


  public String getName() {

    return name;
  }


  public void setBigBlind(int bigBlind, boolean b) {
    this.isBigBlind = b;
    if (bigBlind > 0) {
      System.out.println("AI " + name + " paid the big Blind (" + bigBlind + ")");
    }

    aiPot -= bigBlind;
    this.paidThisTurn += bigBlind;
  }


  public void setSmallBlind(int smallBlind, boolean b) {
    this.isSmallBlind = b;
    if (smallBlind > 0) {
      System.out.println("AI " + name + " paid the small Blind (" + smallBlind + ")");
    }

    aiPot -= smallBlind;
    this.paidThisTurn += smallBlind;
  }


  public boolean getIsSmallBlind() {
    return isSmallBlind;
  }

  public boolean getIsBigBlind() {
    return isBigBlind;
  }

  public int getPaidThisTurn() {
    return paidThisTurn;
  }

  public void setPaidThisTurn(int paidThisTurn) {
    this.paidThisTurn = paidThisTurn;
  }

  public void setSameTurn(boolean sameTurn) {
    this.sameTurn = sameTurn;
  }

  public int getHighCard() {
    return highCard;
  }

  public int handStrength() {
    return handStrength;
  }

}

