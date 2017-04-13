package ProjektTEST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 
 * @author Max Frennessen
 * Turn2 för AI med förbättrad kod
 * Som ska implementeras för turn1 också.
 * FÖr att denna ska funka, ändra markerade saker i cards.
 *
 */


public class turn2 {
	
	private Cards cards;
	private String[] secondTurn = new String[5];
	private ArrayList<Integer> cardNbr = new ArrayList<Integer>();
	private ArrayList<String> cardClr = new ArrayList<String>();
	private boolean colorChance;
	private boolean straightChance;
	private int pairs = 0;
	private int likelyhood = 10;
	private boolean highCards;
	private boolean bid = false;
	
	public turn2(String card1 , String card2, String card3, String card4, String card5){   // har strings för senare versioner. INget som används nu

		cards = new Cards();
		secondTurn = cards.getCards();	
		getCardValues();
		highCards = checkHighCards();
		colorChance = checkSuit();
		pairs = checkPairAndMore();
		straightChance = checkStraight();
	
		System.out.println("cards");
		for(String x : secondTurn){
			System.out.println(x);
		}
		
		System.out.println("high cards - " + highCards);
		System.out.println("colorChance - " + colorChance);
		System.out.println("pairs - " + pairs);
		System.out.println("straightChance - " + straightChance);
		
		
		
		decide();
		
//		for(String x : cardClr){
//			System.out.println(x);
//		}
//		
//		System.out.println("colorChance - " + colorChance);
		
		
		
//		System.out.println(straightChance);
//		System.out.println("pairs - " + pairs);
		
	
	}
	
	public void decide(){
		
		if(highCards){
			likelyhood+=20;
		}
		
		if(colorChance){
			likelyhood+=20;
		}
		
		if(pairs==1){
			likelyhood+=40;
			if(pairs>=2){
				likelyhood+=100;
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
			bid=true;
		}
		
		System.out.println("bid - " + bid);
		
		if((likelyhood-50)>roll){
		System.out.println("Raise!" );
		}
	}
	

	
	public void getCardValues(){
		
		for(int i = 0; i<secondTurn.length; i++){		//CardNumber
			String[] temp;
			 temp = secondTurn[i].split(",");
			 int tempInt = Integer.parseInt(temp[0]);
			 cardNbr.add(tempInt);
		}

		
		for(int i = 0; i<secondTurn.length; i++){			//CardColor		
			String[] temp = secondTurn[i].split(",");
			 cardClr.add(temp[1]);
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
		
		return high;
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
		
		
		
//		for(int i = 0; i<test.length; i++){
//			for(int x = 0; x< test.length; x++){
//				if(test[i]==test[x]){
//					pairs++;
//				}
//			}
//		}
		
//		return (pairs-5)/2;
		
		return pairs;
	}
	
	
	public Boolean checkSuit(){
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
	
	
	public static void main(String [] args){
		turn2 run = new turn2(null, null, null, null, null);    //har flera nulls för framtida versioner där jag får in värden via dem.
	}

}
