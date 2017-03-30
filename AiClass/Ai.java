package AiClass;

import java.util.ArrayList;
import java.util.Random;

import deck.Card;
import deck.Deck;

/**
 * 
 * @author Max Frennessen
 * Huvudklass för AI-spelaren som beroende på turn 
 * startar en AI uträkning och skickar svar till controller på
 * vad AI:n kommer göra på dess tur.
 *	24-03-17
 */
public class Ai {
	private Card card;
	private TurnOne turnOne;
	private TurnTwo turnTwo;
	private TurnThree turnThree;
	private TurnFour turnFour;
	private Cards cards;
	private String[] fromCards = new String[10];
	private String whatToDo;
	private int testAI = 0;
	private ArrayList<String> aiCards = new ArrayList<String>();    //Lista som lägger till alla kort som kommer in och som skickas till turns.
	private int aiPot;   //AIPOT - KOMMER IN VIA CONTROLLER.
	private int toBet;  //HUR MKT SOM AI MÅSTE BETA FÖR ATT VA MED.
	
	public Ai(int aiPot){  
		this.aiPot = aiPot;
		
//		Random rand =  new Random();
//		aiPot = rand.nextInt(1000)+400;
//		toBet = rand.nextInt(600);
//		
//		aiPot = 700;
//		toBet = 20;
		
		getCards();   //SÅLÄNGE JAG INTE FÅR IN VIA AI-CONSTRUKTOR
		aiCards.clear();
		
		testAI = 0;
		
	  for(int x = 0; x<5; x++){
	   
	   if(testAI==0){
		  for(int i = 0; i<2; i++){			//Lägger till kort till ArrayList som skickas till constructor.
			aiCards.add(fromCards[i]);
			}
		}
		
		if(testAI==1){
			for(int i = 2; i<5; i++){			// I = 2!!!
				aiCards.add(fromCards[i]);
				}
		}
		
		if(testAI==2){
												
				aiCards.add(fromCards[6]);
				}
		
		
		if(testAI==3){

				aiCards.add(fromCards[7]);
				}
		
		
		if(testAI==0){
			turnOne =  new TurnOne(aiCards,aiPot, toBet);
			whatToDo = turnOne.decision();   //TurnONe respond.
			aiPot = turnOne.updateAiPot();
		
		}
		
		if(testAI==1){
			turnTwo =  new TurnTwo(aiCards,aiPot, toBet);
			whatToDo = turnTwo.decision();   //FlOP respond.
			aiPot = turnTwo.updateAiPot();
			
		}
		
		if(testAI==2){
			turnThree =  new TurnThree(aiCards,aiPot, toBet);
			whatToDo = turnThree.decision();   //TURN respond.
			aiPot = turnThree.updateAiPot();
			
		}
		
		if(testAI==3){
			turnFour =  new TurnFour(aiCards,aiPot, toBet);
			whatToDo = turnFour.decision();   //RIVER respond.
			aiPot = turnFour.updateAiPot();
			
		}
		
	
		System.out.println("AI - " + whatToDo);
		System.out.println("AI pot - " + aiPot);
		if(!(whatToDo.equals("fold"))){
			testAI++;
		}
		else x = 50;
		System.out.println("");
		System.out.println("");
		}
	}
	
	
	public void getCards(){
	cards = new Cards();
	fromCards = cards.getCards();
	
	}
	
	// Rikards metoder
	
		//set starting hand
		public void setStartingHand(Card card1, Card card2) {
			aiCards.clear(); //nollställer arraylist.
			
			String firstCard = card1.getCardValue() +card1.getCardSuit();
			String secondCard = card2.getCardValue() +card2.getCardSuit();
			aiCards.add(firstCard);
			aiCards.add(secondCard);
			System.out.println("starting hand set for ai player");
			// TODO Auto-generated method stub

		}

		// Make decision for the starting hand
		public void makeDecision(int currentBet) {
			turnOne =  new TurnOne(aiCards,aiPot, currentBet);
			whatToDo = turnOne.decision();   //TurnONe respond.
			aiPot = turnOne.updateAiPot();
		}

		// Make decision for starting hand + flop
		public void makeDecision(int playTurn, int currentBet, Card[] flop) {
		
			for (Card card : flop) {				
				aiCards.add(card.getCardValue()+card.getCardSuit());
			}
			
			turnTwo =  new TurnTwo(aiCards,aiPot, currentBet);
			whatToDo = turnTwo.decision();   //TurnONe respond.
			aiPot = turnTwo.updateAiPot();

		}

		// Make decision for starting hand + flop + turn && starting hand + flop +
		// turn + river
		public void makeDecision(int playTurn, int currentBet, Card turn) {					
			aiCards.add(turn.getCardValue()+turn.getCardSuit());
			
			if (aiCards.size() < 7) {
				turnThree =  new TurnThree(aiCards,aiPot, currentBet);
				whatToDo = turnThree.decision();  
				aiPot = turnThree.updateAiPot();
				
			} else if (aiCards.size() == 7) {
				turnFour =  new TurnFour(aiCards,aiPot, currentBet);
				whatToDo = turnFour.decision();  
				aiPot = turnFour.updateAiPot();
			}
			
		}

		private void setDecision(String reset) {
			whatToDo = reset;

		}

		public String getDecision() {
			return whatToDo;
		}

	
	
	
	
	
	
	
	
	public void updateWinner(int aiPot){
		this.aiPot = aiPot;
	}
	
	public static void main(String[] args){
		Ai run = new Ai(1000);
	}
}
