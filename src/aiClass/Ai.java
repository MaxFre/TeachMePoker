package aiClass;

import java.util.ArrayList;
import java.util.Random;

import deck.Card;
import deck.Deck;

/**
 * 
 * @author Max Frennessen Huvudklass för AI-spelaren som beroende på turn startar en AI uträkning
 *         och skickar svar till controller på vad AI:n kommer göra på dess tur. 24-03-17
 */
public class Ai {
  private String name;
  private Card card;
  private TurnOne turnOne;
  private TurnTwo turnTwo;
  private TurnThree turnThree;
  private TurnFour turnFour;
  private Cards cards;
  private String[] fromCards = new String[10];
  private String whatToDo;
  private int testAI = 0;
  private ArrayList<String> aiCards = new ArrayList<String>(); // Lista som lägger till alla kort
                                                               // som kommer in och som skickas till
                                                               // turns.
  private int aiPot; // AIPOT - KOMMER IN VIA CONTROLLER.
  private int toBet; // HUR MKT SOM AI MÅSTE BETA FÖR ATT VA MED.

  public Ai(int aiPot, String name) {
    this.aiPot = aiPot;
    this.name = name;
    // }
    // Random rand = new Random();
    // aiPot = rand.nextInt(1000)+400;
    // toBet = rand.nextInt(600);
    //
    //
    // fromCards[0] = "2,H";
    // fromCards[1] = "2,D";
    // fromCards[2] = "4,D";
    // fromCards[3] = "5,D";
    // fromCards[4] = "6,D";
    //// fromCards[5] = "3,S";
    //
    //
    // testAI = 1;
    //
    //
    // aiPot = 160;
    // toBet = 10;
    //
    //
    // aiCards.clear();
    //
    //
    //
    //// for(int x = 0; x<5; x++){
    //
    // if(testAI==0){
    // for(int i = 0; i<2; i++){ //Lägger till kort till ArrayList som skickas till constructor.
    // aiCards.add(fromCards[i]);
    // }
    // }
    //
    // if(testAI==1){
    // for(int i = 0; i<5; i++){ // I = 2!!!
    // aiCards.add(fromCards[i]);
    // }
    // }
    //
    // if(testAI==2){
    // for(int i = 0; i<6; i++){ // I = 2!!!
    // aiCards.add(fromCards[i]);
    // }
    //// aiCards.add(fromCards[6]);
    // }
    //
    //
    // if(testAI==3){
    // for(int i = 0; i<7; i++){ // I = 2!!!
    // aiCards.add(fromCards[i]);
    // }
    //// aiCards.add(fromCards[7]);
    // }
    //
    //
    // if(testAI==0){
    // turnOne = new TurnOne(aiCards,aiPot, toBet);
    // whatToDo = turnOne.decision(); //TurnONe respond.
    // aiPot = turnOne.updateAiPot();
    //
    // }
    //
    // if(testAI==1){
    // turnTwo = new TurnTwo(aiCards,aiPot, toBet);
    // whatToDo = turnTwo.decision(); //FlOP respond.
    // aiPot = turnTwo.updateAiPot();
    //
    // }
    //
    // if(testAI==2){
    // turnThree = new TurnThree(aiCards,aiPot, toBet);
    // whatToDo = turnThree.decision(); //TURN respond.
    // aiPot = turnThree.updateAiPot();
    //
    // }
    //
    // if(testAI==3){
    // turnFour = new TurnFour(aiCards,aiPot, toBet);
    // whatToDo = turnFour.decision(); //RIVER respond.
    // aiPot = turnFour.updateAiPot();
    //
    // }
    //
    //
    // System.out.println("AI - " + whatToDo);
    // System.out.println("AI pot - " + aiPot);
    // if(!(whatToDo.equals("fold"))){
    // testAI++;
    // }
    //// else x = 50;
    // System.out.println("");
    // System.out.println("");
    //// }
    // }
    //
    //
    // public void getCards(){
    // cards = new Cards();
    // fromCards = cards.getCards();
    //
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
    // TODO Auto-generated method stub

  }

  // Make decision for the starting hand
  public void makeDecision(int currentBet) {
    turnOne = new TurnOne(aiCards, aiPot, currentBet);
    whatToDo = turnOne.decision(); // TurnONe respond.
    aiPot = turnOne.updateAiPot();
    System.out.println("AIplayer made a decision(" + whatToDo + ") using the starting hand");
  }

  // Make decision for starting hand + flop
  public void makeDecision(int currentBet, Card[] flop) {

    for (Card card : flop) {
      char A = card.getCardSuit().charAt(0);
      aiCards.add(card.getCardValue() + "," + String.valueOf(A));
    }

    turnTwo = new TurnTwo(aiCards, aiPot, currentBet);
    whatToDo = turnTwo.decision(); // TurnONe respond.
    aiPot = turnTwo.updateAiPot();
    System.out.println(
        "AIplayer made a decision(" + whatToDo + ") using the starting hand and flop cards");

  }

  // Make decision for starting hand + flop + turn && starting hand + flop +
  // turn + river
  public void makeDecision(int currentBet, Card turn) {
    aiCards.add(turn.getCardValue() + "," + turn.getCardSuit());

    if (aiCards.size() < 7) {
      turnThree = new TurnThree(aiCards, aiPot, currentBet);
      whatToDo = turnThree.decision();
      aiPot = turnThree.updateAiPot();
      System.out.println(
          "AIplayer made a decision(" + whatToDo + ") based on starting hand, flop and turn");

    } else if (aiCards.size() == 7) {
      turnFour = new TurnFour(aiCards, aiPot, currentBet);
      whatToDo = turnFour.decision();
      aiPot = turnFour.updateAiPot();
      System.out.println("AIplayer made a decision(" + whatToDo
          + ") based on starting hand, flop, turn and river");
    }

  }

  public void setDecision(String reset) {
    whatToDo = reset;
  }

  public String getDecision() {
    return whatToDo;
  }

  public int aiPot() {
    return aiPot;
  }

  public void updateWinner(int aiPot) {
    this.aiPot = aiPot;
  }

  public String getName() {
    return name;
  }



  public void setBigBlind(int bigBlind) {
    System.out.println("Ai named " + name + " paid the big blind(" + bigBlind + ")");

  }



  public void setSmallBlind(int smallBlind) {
    System.out.println("Ai named " + name + " paid the small blind(" + smallBlind + ")");

  }

  // public static void main(String[] args){
  // Ai run = new Ai(1000);
  // }
}


