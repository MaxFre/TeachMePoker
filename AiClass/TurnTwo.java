package AiClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
/**
 * 
 * @author Max Frennessen
 * Räknar du vad AI bör göra när den har 5 kort.
 * 24-03-17
 */
public class TurnTwo implements AICalculations{
	
	private ArrayList<Integer> cardNbr = new ArrayList<Integer>();
	private ArrayList<String> cardClr = new ArrayList<String>();

	private boolean colorChance;
	private int straightChance;
	private int pairsNmore = 0;
	private int likelyhood = 10;
	private boolean highCards,rlyhighCards;
	private String toDO = "fold";
	private int aiPot;
	private int toBet;
	private int raiseAmount;
	
	public TurnTwo(ArrayList<String> aiCards, int aiPot, int toBet){
		this.aiPot = aiPot;
		this.toBet = toBet;
		
		getCardValues(aiCards);
		highCards = checkHighCards();
		colorChance = checkSuit();
		pairsNmore = checkPairAndMore();
		straightChance = checkStraight();
		
		decide();
		System.out.println("aiPot - " +aiPot+"  toBet - " +toBet );
		System.out.println(aiCards);
		
		System.out.println("highCards - " + highCards);
		System.out.println("colorChance - " + colorChance);
		System.out.println("straightChance - " + straightChance);
		System.out.println("same - " + pairsNmore);
	}
	
	
	public void decide(){
		
		if(highCards){
			likelyhood+=20;
			if(rlyhighCards){
				likelyhood+=15;
			}
		}
		
		if(colorChance){
			likelyhood+=20;
		}
		
		if(pairsNmore==2){			//par
			likelyhood+=40;
		}
		if(pairsNmore==3){			//triss
			likelyhood+=100;
		}
		if(pairsNmore==4){			//fyrtal
			likelyhood+=120;
		}
		
		if(pairsNmore==22){			//2-par
			likelyhood+=80;	
		}
		
		if(pairsNmore == 23 || pairsNmore==32){		//kåk
			likelyhood+=120;
		}
		
		if(pairsNmore>32|| pairsNmore==24){					// fyrtal + par?
			likelyhood+=120;
		}

		
		if(straightChance>=3){
			likelyhood+=25;
			if(straightChance>=4){
				likelyhood+=15;
			 }
				if(straightChance==5){
				likelyhood+=30;
				}
		}
		
		if(toBet==0){
			toDO="Check";
		}
		
		else{
		Random rand = new Random();
		
		int roll = rand.nextInt(100);
		System.out.println("likelyhood - " + likelyhood);
		System.out.println("roll - " + roll);

		int aipotChance = (int)(aiPot*0.025);
		int toBetChance = (int)(toBet*0.030*2);
		
		int diff = aipotChance-toBetChance;
		likelyhood = likelyhood+diff;
		
		System.out.println("likelyhood efter diff - " + likelyhood);
		
		
		if(likelyhood>roll && likelyhood-35<roll && !(toBet==0)){
			toDO = "bid,"+toBet;
			aiPot-=toBet;
		}
		
		
		if(likelyhood-45>roll){
			  raiseAmount = (int)(1.10*toBet);	
			  if(raiseAmount<(toBet+5)){  //så man inte höjer med bara 1..
				  raiseAmount=(toBet+10);			  
			  }			
			 toDO = "Raise,"+ raiseAmount;
			 
			  if((likelyhood-55)>roll){
				raiseAmount = (int)(1.17*toBet);	
				toDO = "Raise,";
			  }
			
			  if((likelyhood)-65>roll){
				  raiseAmount = (int)(1.25*toBet);	
				  toDO = "Raise,";
			  }
			
			if (raiseAmount > aiPot){
				toDO = "All-in," + String.valueOf(aiPot);
				aiPot -= aiPot;
			}
			
			if (raiseAmount < aiPot){
				toDO = "Raise," + String.valueOf(raiseAmount);
				aiPot -= raiseAmount;
			}
		 }
		}
		
	 
			
//		if(likelyhood>100 && aiPot>=toBet){ //trots mkt på spel å man har bra hand så kör.
//			toDO = "bid," + aiPot;
//			aiPot-=toBet;
//		}
//		
//		if(roll<=likelyhood && aiPot>toBet){						//vanligt bet.
//			
//			toDO = "bid," + toBet;
//			aiPot-=toBet;
//			
//		}
//			
//		if(aiPot<=60){								//om bara 60 kvar, gå all in.
//			toDO = "All-in,"+aiPot;
//			aiPot-=aiPot;
//		}
//		
//		if(likelyhood>=roll && aiPot<toBet){							//om toBet större än pot och och likelyhood större än roll, all in.
//			toDO = "All-in,"+aiPot;
//			aiPot-=aiPot;
//		}
//		
//		if((likelyhood-35)>roll){			
//			
//			   raiseAmount = (int)(1.10*toBet);	
//			  if(raiseAmount<(toBet+5)){  //så man inte höjer med bara 1..
//				  raiseAmount=(toBet+10);			  
//			  }
//			
//			 toDO = "Raise,"+ raiseAmount;
////			 aiPot-=raiseAmount;
//			 
//			  if((likelyhood-45)>roll){
//				raiseAmount = (int)(1.17*toBet);	
//				toDO = "Raise,"+ raiseAmount;
////				 aiPot-=raiseAmount;
//			  }
//			
//			  if((likelyhood)-55>roll){
//				  raiseAmount = (int)(1.25*toBet);	
//				  toDO = "Raise,"+ raiseAmount;
////				  aiPot-=raiseAmount;
//			  }
//			  if(raiseAmount<aiPot)
//			  aiPot-=raiseAmount;
//			}
//		
//		if(raiseAmount>=aiPot){				//så AI nt bettar mer än vad han har
//			toDO = "All-in,"+aiPot;
//			 aiPot-=aiPot;
//		}
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
		int same = 1;
		int nbrOftemp = 0;
		int nbrOftemp1 = 0;
		int nbrOftemp2 = 0;
		int[] cards = new int[5];
		
		
		for(int i = 0; i< 5; i++){
			cards[i] = cardNbr.get(i);
		}
		
		if(cards[0]==cards[1]){
			int temp = cards[0];
			 nbrOftemp = 2;
			
			for(int i = 2; i<cards.length; i++){
				if(cards[i]==temp){
					nbrOftemp++;
				}
			}
		}
		
		else{
		int temp1 = cards[0];
		int temp2 = cards[1];	
		
		
		 nbrOftemp1 = 1;
		 nbrOftemp2 = 1;
		
		
		for(int i = 2; i<cards.length; i++){
				
			if(cards[i]==temp1){
				nbrOftemp1++;
			}
			if(cards[i]==temp2){
				nbrOftemp2++;
			}
		  }
		}	
			
		if(nbrOftemp>0){
			same = nbrOftemp;
		}
		
		if(nbrOftemp1>1){
			same=nbrOftemp1;
		}
		
		if(nbrOftemp2>1){
			if(nbrOftemp1>1){
				same =Integer.parseInt(nbrOftemp1 +""+ nbrOftemp2);
			}
			else
			same = nbrOftemp2;
		}	
		
		if(same==1)
			same=0;
		return same;
//		int[] test = new int[5];
//		int same = 0;
//			
//		for(int i = 0; i< 5; i++){
//			test[i] = cardNbr.get(i);
//		}
//			
//		
//		for(int i = 1; i<test.length; i++){
//			if(test[0]==test[i]){
//				same++;
//			}
//			
//			if(i>1){
//				if(test[1]==test[i]){
//					same++;
//				}
//			}
//		}
//		
////		for(int i = 2; i<test.length; i++){
////			if(test[1]==test[i]){
////				same++;
////			}
////		}
//		
////		if(test[0]==test[1]){
////			same++;		
////		}
////		else
////			
////		for(int i = 1; i<test.length; i++){
////			if(test[0]==test[i]){
////				same++; ;
////			}
////		}
////		
////		for(int i = 2; i<test.length; i++){
////			if(test[1]==test[i]){
////				same++;
////			}
////		}
////		
//		return same;
	}
	
	public boolean checkHighCards(){
		boolean high = false;
		
		int card1 = cardNbr.get(0);
		int card2 = cardNbr.get(1);
		
		int total = (card1+card2);
		
		if(total>=17){
			high=true;
		}
		if(card1>=10 && card2>=10){
			 rlyhighCards=true;
			}
		
		return high;
	}
	
	public boolean checkSuit(){
		int C = 0; int S = 0;
		int H = 0; int D = 0;
		boolean Color = false;
		for(String x : cardClr){
			if(x.equals("s")){
				S++;
			}
			if(x.equals("c")){
				C++;
			}
			if(x.equals("d")){
				D++;
			}
			if(x.equals("h")){
				H++;
			}
		}
		
		if(S>=3 || C>=3 || D>=3 || H>=3){
			Color=true;
		}
	 return Color;		
	}
	
	
	public int checkStraight(){
		
		int[] test = new int[5];
		int testar = 0;
		for(int i = 0; i< 5; i++){
			test[i] = cardNbr.get(i);
		}
		
		Arrays.sort(test);
		int inStraight = 0;	
		int check = 4;
		
	for(int x = 0; x<test.length; x++){	
		int temp = test[x]+check;
		inStraight = 0;
		check--;
		for(int i = 0; i<test.length; i++){
			if(test[i]<=temp && !(test[i]<temp-4)){
				
				//problem med dubbletter i början, ex 3-3.
				
				if(i==0){							//kollar om 0 är samma som 1.
					if(!(test[i]==test[i+1])){
						inStraight++;
					}
				}
				if(i>=1){							
					if(!(test[i]==test[i-1])){		//kollar om 1-4 är samma som nån annan.
						inStraight++;
					}
				}	
				
			}
		}
	
		if(inStraight>testar){
			testar = inStraight;
		}
		

	}
//	System.out.println("testar -" + testar);
//		for(int i = 0; i<test.length; i++){
//		 
//			if(i<test.length-1){	
//			  
//			    if(test[i]+2 == test[i+1]){
//			    	inStraigh++;
//			    	 if(i<test.length-2){
//			    		 if(test[i+1]+1==test[i+2]){
//			    			 inStraigh++;
//			    			 straight=true;
//			    		 }
//			    	 }	
//			    }
//								
//				if(test[i]+1 == test[i+1]){
//					inStraigh++;
//				  if(i<test.length-2){	
//					if(test[i+1]+1==test[i+2]){
//						inStraigh++;
//						straight=true;
//					}
//				   }
//			     }
//			}	
//		}
			
		return testar;
	}
	
	
	public String decision(){
		return toDO;
	}


	@Override
	public int updateAiPot() {
		return aiPot;
	}
}