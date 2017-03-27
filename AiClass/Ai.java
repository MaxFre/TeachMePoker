package AiClass;

import java.util.ArrayList;
import java.util.Random;


/**
 * 
 * @author Max Frennessen
 * Huvudklass för AI-spelaren som beroende på turn 
 * startar en AI uträkning och skickar svar till controller på
 * vad AI:n kommer göra på dess tur.
 *	24-03-17
 */
public class Ai {

	private TurnOne turnOne;
	private TurnTwo turnTwo;
	private TurnThree turnThree;
	private TurnFour turnFour;
	private Cards cards;
	private String[] fromCards = new String[10];
	private String whatToDo;
	private int testAI = 0;
	private ArrayList<String> aiCards = new ArrayList<String>();    //Lista som lägger till alla kort som kommer in och som skickas till turns.
	private int aiPot = 1000;   //AIPOT - KOMMER IN VIA CONTROLLER.
	private int toBet = 32;  //HUR MKT SOM AI MÅSTE BETA FÖR ATT VA MED.
	
	public Ai(){  //FÅR IN VÄRDE HÄR FRÅN CONTROLLER, VET INTE HUR ÄN.
			
		
		getCards();   //SÅLÄNGE JAG INTE FÅR IN VIA AI-CONSTRUKTOR
		
		testAI = 0;
		
//	  for(int x = 0; x<5; x++){
	   
	   if(testAI==0){
		  for(int i = 0; i<2; i++){			//Lägger till kort till ArrayList som skickas till constructor.
			aiCards.add(fromCards[i]);
			}
		}
		
		if(testAI==1){
			for(int i = 0; i<5; i++){			// I = 2!!!
				aiCards.add(fromCards[i]);
				}
		}
		
		if(testAI==2){
			for(int i = 0; i<6; i++){			// I = 3!!!
				aiCards.add(fromCards[i]);
				}
		}
		
		if(testAI==3){
			for(int i = 0; i<7; i++){			// I = 4!!!
				aiCards.add(fromCards[i]);
				}
		}
		
		if(testAI==0){
			turnOne =  new TurnOne(aiCards,aiPot, toBet);
			whatToDo = turnOne.decision();   //TurnONe respond.
		}
		
		if(testAI==1){
			turnTwo =  new TurnTwo(aiCards);
			whatToDo = turnTwo.decision();   //FlOP respond.
		}
		
		if(testAI==2){
			turnThree =  new TurnThree(aiCards);
			whatToDo = turnThree.decision();   //TURN respond.
		}
		
		if(testAI==3){
			turnFour =  new TurnFour(aiCards);
			whatToDo = turnFour.decision();   //RIVER respond.
		}
		
//		testAI++;
	
		System.out.println("AI - " + whatToDo);
		System.out.println("");
		System.out.println("");
		}
//	}
	
	
	public void getCards(){
	cards = new Cards();
	fromCards = cards.getCards();
	
	}
	
	public static void main(String[] args){
		Ai run = new Ai();
	}
}
