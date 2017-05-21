package aiClass;

import java.util.ArrayList;
import java.util.Random;

/**
 * @version 1.5
 * @author Max Frennessen Calculates what the Ai should do on turn four. 17-04-12
 * @version 1.5
 */
public class TurnFour {


  private AiCalculation calculation;
  private boolean colorChance;
  private int straightChance;
  private int pairsNmore = 0;
  private int likelyhood = 10;
  private boolean highCards;
  private String toDO = "fold";
  private int aiPot;
  private int toBet;
  private int raiseAmount;
  private int alreadyPaid;
  private boolean sameTurn;
  private int handStrength;
  private int raiseBet = 0;


  /**
   * Gets value from Ai and calls on all the methods to evaluate a respond.
   * 
   * @param aiCards - Arraylist that contains the cards that are active this turn for the ai.
   * @param aiPot - the current size of the ais pot.
   * @param toBet - how much the ai has to commit to be able to play this turn.
   */
  public TurnFour(ArrayList<String> aiCards, int aiPot, int toBet, int alreadyPaid,
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
    pairsNmore = calculation.checkPairAndMore();
    straightChance = calculation.checkStraight();
    handStrength = calculation.calcHandstrenght();
    decide();

    System.out.println(aiCards);

    System.out.println("highCards - " + highCards);
    System.out.println("colorChance - " + colorChance);
    System.out.println("straightChance - " + straightChance);
    System.out.println("same - " + pairsNmore);
  }

  /**
   * Uses all the variables and with those it calculates a respons that the ai will do.
   */
  public void decide() {

    if (highCards) {
      likelyhood += 20;
    }

    if (pairsNmore == 2) { // par
      likelyhood += 40;
    }
    if (pairsNmore == 3) { // triss
      likelyhood += 100;
    }
    if (pairsNmore == 4) { // fyrtal
      likelyhood += 120;
    }

    if (pairsNmore == 22) { // 2-par
      likelyhood += 80;
    }

    if (pairsNmore == 23 || pairsNmore == 32) { // kåk
      likelyhood += 120;
    }

    if (pairsNmore > 42 || pairsNmore == 24) { // fyrtal + par?
      likelyhood += 120;
    }

    if (straightChance == 5) {
      likelyhood += 60;
    }

    if (toBet == 0) {
      toDO = "check";
    }

    else {
      Random rand = new Random();

      int roll = rand.nextInt(100);
      System.out.println("likelyhood - " + likelyhood);
      System.out.println("roll - " + roll);

      int aipotChance = (int) (aiPot * 0.025);
      int toBetChance = (int) (toBet * 0.030 * 2);

      int diff = aipotChance - toBetChance;
      likelyhood = likelyhood + diff;

      System.out.println("likelyhood efter toBet - " + likelyhood);
      System.out.println("toBet - " + toBet);
      System.out.println("aiPot - " + aiPot);

      if (likelyhood < 45) {
        if (roll <= 15 && aiPot > toBet) {
          toDO = "call," + toBet;
          aiPot -= (toBet);
          System.out.println("Bluff");
        } else {
          toDO = "fold";
        }
      }

      if (likelyhood >= 45 && likelyhood < 115) {
        if (aiPot == toBet) {
          toDO = "all-in," + aiPot;
          aiPot -= (aiPot);
        } else if (aiPot > toBet) {
          toDO = "call," + toBet;
          aiPot -= (toBet);
        } else {
          toDO = "fold";
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
  }

  public int gethandStrength() {
    return handStrength;
  }

  /**
   * @return updates the AI's pot after it has commited a amount for the round.
   */
  public String decision() {
    return toDO;
  }

  public int updateAiPot() {
    return aiPot;
  }
}


