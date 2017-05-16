package aiClass;

import java.util.ArrayList;
import java.util.Random;


public class AiDecide {

  private AiCalculation calculation;
  private int colorChance;
  private int straightChance;
  private int likelyhood = 0;
  private boolean highCards;
  private boolean rlyhighCards;
  private String toDo = "fold";
  private int aiPot;
  private int toBet;
  private int raiseAmount;
  private boolean sameTurn;
  private int raiseBet = 0;
  private int turn;
  private int handStrenght;
  private int howMuchToTakeAwayFromAiPot = 0;


  public AiDecide(ArrayList<String> aiCards, int aiPot, int toBet, int alreadyPaid,
      boolean sameTurn) {
    this.aiPot = aiPot;
    this.toBet = toBet;
    this.raiseBet = toBet;
    this.sameTurn = sameTurn;
    System.out.println("toBet: " + toBet);
    if (toBet != 0) {
      this.toBet = this.toBet - alreadyPaid;
    }
    System.out.println("toBet after removing what AI has paid: " + this.toBet);

    this.raiseAmount = (int) (1.25 * raiseBet);

    calculation = new AiCalculation(aiCards);
    highCards = calculation.checkHighCards();
    colorChance = calculation.checkSuit();
    straightChance = calculation.checkStraight();
    handStrenght = calculation.calcHandstrenght();
    turn = aiCards.size() - 1;


    if (turn == 1) {
      turnOne();
    } else if (turn == 4) {
      turnTwo();
    } else if (turn == 5) {
      turnThree();
    } else if (turn == 6) {
      turnFour();
    }
  }


  public void turnOne() {

    boolean check = false;
    if (toBet == 0) {
      check = true;
    }
    if (straightChance == 2) {
      likelyhood += 25;
    }

    if (highCards) {
      likelyhood += 20;
      if (rlyhighCards) {
        likelyhood += 15;
      }
    }

    if (colorChance == 2) {
      likelyhood += 20;
    }
    System.out.println("LIKELYHOOD  _: - " + likelyhood);
    if (aiPot / 10 >= toBet) {
      likelyhood += 20;
    } else if (aiPot / 5 <= toBet) {
      likelyhood -= 10;
    }

    else if (aiPot / 3 <= toBet) {
      likelyhood -= 20;
    }

    else if (aiPot / 2 <= toBet) {
      likelyhood -= 20;
    }

    System.out.println("LIKELYHOOD EFTER _: - " + likelyhood);
    Random rand = new Random();
    int roll = rand.nextInt(100);

    if (likelyhood < 35 && roll <= 15 && !(check)) { // BLUFF
      if (aiPot > toBet) {
        toDo = "call," + toBet;
        System.out.println("BLUFF!!!");
        howMuchToTakeAwayFromAiPot = toBet;
      }
    } else if (likelyhood <= 100 && check) {
      toDo = "check";
    } else if (likelyhood >= 35 && aiPot > toBet && !(check)) {
      toDo = "call," + toBet;
      howMuchToTakeAwayFromAiPot = toBet;
    } else if (handStrenght == 1) {

      if (raiseAmount < aiPot && !(sameTurn)) {
        toDo = "raise," + raiseAmount;
        howMuchToTakeAwayFromAiPot = raiseAmount;
      } else if (aiPot > toBet) {
        toDo = "call," + toBet;
      } else if (toBet >= aiPot) {
        toDo = "all-in," + aiPot;
        howMuchToTakeAwayFromAiPot = aiPot;
      }

    }
    aiPot -= howMuchToTakeAwayFromAiPot;
  }


  public void turnTwo() {

    boolean check = false;
    if (toBet == 0) {
      check = true;
    }
    if (straightChance == 3) {
      likelyhood += 25;
    }

    if (highCards) {
      likelyhood += 20;
      if (rlyhighCards) {
        likelyhood += 15;
      }
    }

    if (colorChance >= 3) {
      likelyhood += 20;
    }
    System.out.println("LIKELYHOOD  _: - " + likelyhood);
    if (aiPot / 10 >= toBet) {
      likelyhood += 20;
    } else if (aiPot / 5 <= toBet) {
      likelyhood -= 10;
    }

    else if (aiPot / 3 <= toBet) {
      likelyhood -= 20;
    }

    else if (aiPot / 2 <= toBet) {
      likelyhood -= 20;
    }

    System.out.println("LIKELYHOOD EFTER _: - " + likelyhood);
    Random rand = new Random();
    int roll = rand.nextInt(100);

    if (likelyhood < 35 && roll <= 15 && !(check)) { // BLUFF
      if (aiPot > toBet) {
        toDo = "call," + toBet;
        System.out.println("BLUFF!!!");
        howMuchToTakeAwayFromAiPot = toBet;
      }
    } else if (likelyhood <= 100 && check) {
      toDo = "check";
    } else if (likelyhood >= 35 && aiPot > toBet && !(check)) {
      toDo = "call," + toBet;
      howMuchToTakeAwayFromAiPot = toBet;
    } else if (handStrenght > 1) {

      if (raiseAmount < aiPot && !(sameTurn)) {
        toDo = "raise," + raiseAmount;
        howMuchToTakeAwayFromAiPot = raiseAmount;
      } else if (aiPot > toBet) {
        toDo = "call," + toBet;
        howMuchToTakeAwayFromAiPot = toBet;
      } else if (toBet >= aiPot) {
        toDo = "all-in," + aiPot;
        howMuchToTakeAwayFromAiPot = aiPot;
      }
      // else if(raiseAmount < aiPot && (sameTurn)){
      // if(handStrenght > 3){
      // toDo = "raise," + raiseAmount/2;
      // howMuchToTakeAwayFromAiPot = raiseAmount/2;
      // }
      // }
    }
    aiPot -= howMuchToTakeAwayFromAiPot;
  }


  public void turnThree() {

    boolean check = false;
    if (toBet == 0) {
      check = true;
    }
    if (straightChance == 2) {
      likelyhood += 25;
    }

    if (highCards) {
      likelyhood += 20;
      if (rlyhighCards) {
        likelyhood += 15;
      }
    }

    if (colorChance >= 4) {
      likelyhood += 20;
    }
    System.out.println("LIKELYHOOD  _: - " + likelyhood);
    if (aiPot / 10 >= toBet) {
      likelyhood += 20;
    } else if (aiPot / 5 <= toBet) {
      likelyhood -= 10;
    }

    else if (aiPot / 3 <= toBet) {
      likelyhood -= 20;
    }

    else if (aiPot / 2 <= toBet) {
      likelyhood -= 20;
    }

    System.out.println("LIKELYHOOD EFTER _: - " + likelyhood);
    Random rand = new Random();
    int roll = rand.nextInt(100);

    if (likelyhood < 35 && roll <= 15 && !(check)) { // BLUFF
      if (aiPot > toBet) {
        toDo = "call," + toBet;
        System.out.println("BLUFF!!!");
        howMuchToTakeAwayFromAiPot = toBet;
      }
    } else if (likelyhood <= 100 && check) {
      toDo = "check";
    } else if (likelyhood >= 35 && aiPot > toBet && !(check)) {
      toDo = "call," + toBet;
      howMuchToTakeAwayFromAiPot = toBet;
    } else if (handStrenght > 1) {

      if (raiseAmount < aiPot && !(sameTurn)) {
        toDo = "raise," + raiseAmount;
        howMuchToTakeAwayFromAiPot = raiseAmount;
      } else if (aiPot > toBet) {
        toDo = "call," + toBet;
        howMuchToTakeAwayFromAiPot = toBet;
      } else if (toBet >= aiPot) {
        toDo = "all-in," + aiPot;
        howMuchToTakeAwayFromAiPot = aiPot;
      }
      // else if(raiseAmount < aiPot && (sameTurn)){
      // if(handStrenght > 3){
      // toDo = "raise," + raiseAmount/2;
      // howMuchToTakeAwayFromAiPot = raiseAmount/2;
      // }
      // }
    }
    aiPot -= howMuchToTakeAwayFromAiPot;

  }


  public void turnFour() {

    boolean check = false;
    if (toBet == 0) {
      check = true;
    }


    if (highCards) {
      likelyhood += 20;
      if (rlyhighCards) {
        likelyhood += 15;
      }
    }


    System.out.println("LIKELYHOOD  _: - " + likelyhood);
    if (aiPot / 10 >= toBet) {
      likelyhood += 20;
    } else if (aiPot / 5 <= toBet) {
      likelyhood -= 10;
    }

    else if (aiPot / 3 <= toBet) {
      likelyhood -= 20;
    }

    else if (aiPot / 2 <= toBet) {
      likelyhood -= 20;
    }

    System.out.println("LIKELYHOOD EFTER _: - " + likelyhood);
    Random rand = new Random();
    int roll = rand.nextInt(100);

    if (likelyhood < 35 && roll <= 15 && !(check)) { // BLUFF
      if (aiPot > toBet) {
        toDo = "call," + toBet;
        System.out.println("BLUFF!!!");
        howMuchToTakeAwayFromAiPot = toBet;
      }
    } else if (likelyhood <= 100 && check) {
      toDo = "check";
    } else if (likelyhood >= 35 && aiPot > toBet && !(check)) {
      toDo = "call," + toBet;
      howMuchToTakeAwayFromAiPot = toBet;
    } else if (handStrenght > 1) {

      if (raiseAmount < aiPot && !(sameTurn)) {
        toDo = "raise," + raiseAmount;
        howMuchToTakeAwayFromAiPot = raiseAmount;
      } else if (aiPot > toBet) {
        toDo = "call," + toBet;
        howMuchToTakeAwayFromAiPot = toBet;
      } else if (toBet >= aiPot) {
        toDo = "all-in," + aiPot;
        howMuchToTakeAwayFromAiPot = aiPot;
      }
      // else if(raiseAmount < aiPot && (sameTurn)){
      // if(handStrenght > 3){
      // toDo = "raise," + raiseAmount/2;
      // howMuchToTakeAwayFromAiPot = raiseAmount/2;
      // }
      // }
    }
    aiPot -= howMuchToTakeAwayFromAiPot;

  }


  // TO GAME

  public int gethandStrength() {

    return handStrenght;
  }


  public int updateAiPot() {

    return aiPot;
  }


  public String decision() {

    return toDo;
  }
}
