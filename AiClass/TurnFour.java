package AiClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
/**
 * 
 * @author Max Frennessen'
 * R�knar du vad AI b�r g�ra n�r den har 7 kort.
 * 24-03-17
 */
public class TurnFour implements AICalculations{

	private ArrayList<Integer> cardNbr = new ArrayList<Integer>();
	private ArrayList<String> cardClr = new ArrayList<String>();

	private boolean colorChance;
	private boolean straightChance;
	private int pairs = 0;
	private int likelyhood = 10;
	private boolean highCards;
	private String toDO = "fold";
	
	public TurnFour(ArrayList<String> aiCards){
		
		getCardValues(aiCards);
		highCards = checkHighCards();
		colorChance = checkSuit();
		pairs = checkPairAndMore();
		straightChance = checkStraight();
		
		decide();
		
		System.out.println(aiCards);
		
		System.out.println("highCards - " + highCards);
		System.out.println("colorChance - " + colorChance);
		System.out.println("straightChance - " + straightChance);
		System.out.println("pairs - " + pairs);
	}
	
	
	public void decide(){
		
		if(highCards){
			likelyhood+=20;
		}
		
		if(colorChance){
			likelyhood+=20;
		}
		
		if(pairs>0){
			likelyhood+=40;
			if(pairs>1){
				likelyhood+=50;
			}
		}
		
		if(straightChance){
			likelyhood+=20;
		}
		
		Random rand = new Random();
		
		int roll = rand.nextInt(100);
		System.out.println("likelyhood - " + likelyhood);
		System.out.println("roll - " + roll);
		
		if(roll<=likelyhood){
			toDO = "bid";
		}
				
		
		if((likelyhood-35)>roll){			//FIXA HUR MKT RAISE

			  int raiseAmount = (int)(1.10*toBet);	
			  toDO = "Raise,"+ raiseAmount;
			  
			  if(likelyhood-45>roll){
				raiseAmount = (int)(1.17*toBet);	
				toDO = "Raise,"+ raiseAmount;
			  }
			
			  if(likelyhood-55>roll){
				  raiseAmount = (int)(1.25*toBet);	
				  toDO = "Raise,"+ raiseAmount;
			  }
			
			}
		
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
	
	
	public int checkPairAndMore(){
		int[] test = new int[5];
		int pairs = 0;
			
		for(int i = 0; i< 5; i++){
			test[i] = cardNbr.get(i);
		}
				
		
		if(test[0]==test[1]){
			pairs++;
			
		}
		else
			
		for(int i = 1; i<test.length; i++){
			if(test[0]==test[i]){
				pairs++; ;
			}
		}
		
		for(int i = 2; i<test.length; i++){
			if(test[1]==test[i]){
				pairs++;
			}
		}
		
		return pairs;
	}
	
	public boolean checkHighCards(){
		boolean high = false;
		
		int card1 = cardNbr.get(0);
		int card2 = cardNbr.get(1);
		
		int total = (card1+card2);
		
		if(total>=17){
			high=true;
		}
		
		return high;
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
		
		if(S>=3 || C>=3 || D>=3 || H>=3){
			Color=true;
		}
	 return Color;		
	}
	
	
	public boolean checkStraight(){
		boolean straight = false;		
		int[] test = new int[5];
			
		for(int i = 0; i< 5; i++){
			test[i] = cardNbr.get(i);
		}
		
		Arrays.sort(test);
				
		for(int i = 0; i<test.length; i++){
		 
			if(i<test.length-1){	
			  
			    if(test[i]+2 == test[i+1]){
			    	 if(i<test.length-2){
			    		 if(test[i+1]+1==test[i+2]){
			    			 straight=true;
			    		 }
			    	 }	
			    }
								
				if(test[i]+1 == test[i+1]){
				  if(i<test.length-2){	
					if(test[i+1]+1==test[i+2]){
						straight=true;
					}
				   }
			     }
			}	
		}
			
		return straight;
	}
	
	
	public String decision(){
		return toDO;
	}
}


