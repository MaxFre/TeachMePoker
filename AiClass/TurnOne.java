package AiClass;

import java.util.ArrayList;
import java.util.Random;
/**
 * 
 * @author Max Frennessen'
 * R�knar du vad AI b�r g�ra n�r den har 2 kort.
 * 24-03-17
 */
public class TurnOne implements AICalculations {
	
	private ArrayList<Integer> cardNbr = new ArrayList<Integer>();
	private ArrayList<String> cardClr = new ArrayList<String>();
	private boolean colorChance;
	private boolean straightChance;
	private int pairs = 0;
	private int likelyhood = 10;
	private boolean highCards;
	private boolean rlyhighCards;
	private String toDO = "fold";
	private int aiPot;
	private int toBet;
	
	public TurnOne(ArrayList<String> aiCards, int aiPot, int toBet) {
	this.aiPot = aiPot;
	this.toBet = toBet;
	
		getCardValues(aiCards);
		
		
		highCards = checkHighCards();
		colorChance = checkSuit();
		pairs = checkPairAndMore();
		straightChance = checkStraight();
		decide();
		
		System.out.println(aiCards);
		
		System.out.println("highCards - " + highCards);
		System.out.println("rlyhighCards - " + rlyhighCards);
		System.out.println("colorChance - " + colorChance);
		System.out.println("straightChance - " + straightChance);
		System.out.println("pairs - " + pairs);
	}

	public void decide(){
		
		if(straightChance){
			likelyhood+=20;
		}
		
		if(highCards){
			likelyhood+=20;
			if(rlyhighCards){
				likelyhood+=15;
			}
		}
		
		if(colorChance){
			likelyhood+=20;
		}
		
		if(pairs>0){
			likelyhood+=80;			
		}
		
		
		Random rand = new Random();
		
		int roll = rand.nextInt(90);
		System.out.println("likelyhood - " + likelyhood);
		System.out.println("roll - " + roll);
//		int diff = aiPot-300;
//		roll = roll - (int)(diff*0.025);
		
		
		int aipotChance = (int)(aiPot*0.025);
		int toBetChance = (int)(877*0.025*2);
		
		int diff = aipotChance-toBetChance;
		System.out.println("test ->  pot - " + aipotChance  + "    bet - "  + toBetChance  + "    dif- " + diff);
		likelyhood = likelyhood+diff;
		System.out.println("likelyhood efter toBet - " + likelyhood);
		System.out.println("toBet - " + toBet);
		System.out.println("aiPot - " + aiPot);
		
//		roll = (toBet/5) + roll;
		if(roll<=likelyhood){
			toDO = "bid";
		}
				
		
		if((likelyhood-45)>roll){			//FIXA HUR MKT RAISE

		  int raiseAmount = (int)(1.10*toBet);	
		  toDO = "Raise,"+ raiseAmount;
		  
		  if(likelyhood-55>roll){
			raiseAmount = (int)(1.17*toBet);	
			toDO = "Raise,"+ raiseAmount;
		  }
		
		  if(likelyhood-65>roll){
			  raiseAmount = (int)(1.25*toBet);	
			  toDO = "Raise,"+ raiseAmount;
		  }
		
		}
		//fixa olika pot nivåer.
	}
	
	public void getCardValues(ArrayList<String> aiCards){
		
		for(int i = 0; i<aiCards.size(); i++){			//CardNumber
			 String temp = aiCards.get(i);
			 String[] test = temp.split(",");
			 int tempInt = Integer.parseInt(test[0]);
			 cardNbr.add(tempInt);
		}

		
		for(int i = 0; i<aiCards.size(); i++){			//CardColor		
			 String temp = aiCards.get(i);
			 String[] test = temp.split(",");
			 String tempString = test[1];
			 cardClr.add(tempString);
		}
	}
	
	
	
	public boolean checkHighCards(){
		boolean high = false;
		
		int card1 = cardNbr.get(0);
		int card2 = cardNbr.get(1);
		
		int total = (card1+card2);
		
		if(total>=17){
			high=true;
		}
		
		if(card1>=11 && card2>=11){
		 rlyhighCards=true;
		}
		return high;
	}
	
	
	public int checkPairAndMore(){
		int card1 = cardNbr.get(0);
		int card2 = cardNbr.get(1);
		
		int pairs = 0;
	
		if(card1==card2){
			pairs++;
			
		}
		
		return pairs;
	}
	
	
	public boolean checkSuit(){
		int C = 0; int S = 0;
		int H = 0; int D = 0;
		boolean Color = false;
		for(String x : cardClr){
			if(x.equals("S")){
				S++;
			}
			if(x.equals("C")){
				C++;
			}
			if(x.equals("D")){
				D++;
			}
			if(x.equals("H")){
				H++;
			}
		}
		
		if(S>=2 || C>=2 || D>=2 || H>=2){
			Color=true;
		}
	 return Color;		
	}
	
	
	public String decision(){
		return toDO;
	}

	



	@Override
	public boolean checkStraight() {
		boolean stege = false;
		
		int card1 = cardNbr.get(0);
		int card2 = cardNbr.get(1);
		
		if(card1>card2){
			int temp =  card2;
			card2 = card1;
			card1 = temp;
		}
		
		if(card1+1 == card2 || card1+2==card2|| card1+3==card2|| card1+4==card2|| card1+5==card2){
			stege = true;
		}
		
		return stege;
	}
	
}
