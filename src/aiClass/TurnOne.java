package aiClass;

import java.util.ArrayList;
import java.util.Random;

/**
 * @version 1.5
 * @author Max Frennessen Calculates what the Ai should do on turn one. 17-04-12
 * @version 1.5
 */
public class TurnOne {

  private AiCalculation calculation;
  private boolean colorChance;
  private int straightChance;
  private int pairs = 0;
  private int likelyhood = 0;
  private boolean highCards;
  private boolean rlyhighCards;
  private String toDO = "fold";
  private int aiPot;
  private int toBet;
  private int raiseAmount;
  private int alreadyPaid;
  private boolean sameTurn;
  private int raiseBet = 0;

  /**
   * Gets value from Ai and calls on all the methods to evaluate a respond.
   * 
   * @param aiCards - Arraylist that contains the cards that are active this turn for the ai.
   * @param aiPot - the current size of the ais pot.
   * @param toBet - how much the ai has to commit to be able to play this turn.
   */
  public TurnOne(ArrayList<String> aiCards, int aiPot, int toBet, int alreadyPaid,
      boolean sameTurn) {
    this.aiPot = aiPot;
    this.toBet = toBet;
    this.raiseBet = toBet;
    this.alreadyPaid = alreadyPaid;
    this.sameTurn = sameTurn;
    if (toBet != 0) {
      this.toBet -= alreadyPaid;
    }

    calculation = new AiCalculation(aiCards);
    highCards = calculation.checkHighCards();
    colorChance = calculation.checkSuit();
    pairs = calculation.checkPairAndMore();
    straightChance = calculation.checkStraight();
    decide();

    System.out.println(aiCards);

    System.out.println("highCards - " + highCards);
    System.out.println("rlyhighCards - " + rlyhighCards);
    System.out.println("colorChance - " + colorChance);
    System.out.println("straightChance - " + straightChance);
    System.out.println("pairs - " + pairs);

  }

  /**
   * Uses all the variables and with those it calculates a respons that the ai will do.
   */
  public void decide() {

    if (straightChance == 2) {
      likelyhood += 25;
    }

    if (highCards) {
      likelyhood += 20;
      if (rlyhighCards) {
        likelyhood += 15;
      }
    }

    if (colorChance) {
      likelyhood += 20;
    }

    if (pairs > 0) {
      likelyhood += 100;
    }

    System.out.println("likelyhood - " + likelyhood);

    // if (toBet == 0) {
    // toDO = "check";
    //
    // if (likelyhood - 35 > roll) {
    // toDO = "raise," + aiPot * 0.07;
    //
    // }
    // } else {
    // int aipotChance = (int) (aiPot * 0.025);
    // int toBetChance = (int) (toBet * 0.030 * 2);

    // int diff = aipotChance - toBetChance;



    // likelyhood = likelyhood + diff;
    System.out.println("likelyhood efter toBet - " + likelyhood);
    System.out.println("toBet - " + toBet);
    System.out.println("AlreadyPaid - " + alreadyPaid);
    System.out.println("aiPot - " + aiPot);

    if (likelyhood < 45) { // Less than 45 = fold.
      Random rand = new Random();
      int roll = rand.nextInt(100);
      System.out.println("roll - " + roll);
      if (roll <= 15) { // if roll is less than 15 (15%) Ai bluffs.
        if (toBet < aiPot) {
          toDO = "call," + toBet;
          aiPot -= (toBet);
          System.out.println("Bluff");
        } else if (toBet > aiPot) {
          toDO = "all-in," + aiPot;
          aiPot -= aiPot;
          System.out.println("Bluff");
        }
      }
    }

    if (likelyhood >= 45 && likelyhood < 115) {
      if (aiPot == toBet) {
        toDO = "all-in," + aiPot;
        aiPot -= (aiPot);
      }

      else if (aiPot > toBet) {
        System.out.println("What AI should pay = " + (toBet));
        toDO = "call," + toBet;
        System.out.println("What AI claimed to pay = " + toBet);
        aiPot -= (toBet);
      }

      else if (aiPot < toBet) {
        toDO = "all-in," + aiPot;
        aiPot -= aiPot;
      }
    }

    if (likelyhood >= 115) {
      if (sameTurn) {
        if (aiPot > toBet) {
          toDO = "call," + toBet;
          aiPot -= toBet;
        } else {
          toDO = "all-in," + aiPot;
          aiPot -= aiPot;
        }
      } else {
        raiseAmount = (int) (1.25 * raiseBet);
        if (raiseAmount < 5) {
          raiseAmount = 10;
        }

        if (aiPot <= raiseAmount) {
          toDO = "all-in," + aiPot;
          aiPot -= (aiPot);
        } else
          toDO = "raise," + raiseAmount;
        aiPot -= (raiseAmount);
      }
    }
  }


  /**
   * @return updates the AI's pot after it has commited a amount for the round.
   * 
   */
  public int updateAiPot() {
    return aiPot;
  }

  /**
   * 
   * @return returns the decision that the ai has done.
   */
  public String decision() {
    return toDO;
  }
}
