package hand;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * Does the actuall calculation and decides what help
 * the noob player gets.
 * @author Max Frennessen
 * @version 1.5
 * 17-04-12
 */
public class HandCalculation {
	private ArrayList<String> nbrForStraight = new ArrayList<String>();
	private ArrayList<String> nbrForStraight1 = new ArrayList<String>();
	private ArrayList<String> aiCards = new ArrayList<String>();
	private ArrayList<Integer> cardNbr = new ArrayList<Integer>();
	private ArrayList<String> cardClr = new ArrayList<String>();
	private boolean highCards = false;
	private boolean lowCards = false;
	private boolean rlyhighCards = false;
	private int colorChance;
	private int straightChance;
	private int pairsNmore;
	private String yourCard ="1,1";
	private String yourCard2 ="1,1";
	private String otherCard ="1,1";
	private String theColor;
	private ArrayList<String> toHighlight = new ArrayList<String>();
	private String advicee;
	
	/**
	 * 
	 * @param aiCards Current cards needed for evaluate.
	 */
	public HandCalculation(ArrayList<String> aiCards) {
		
		this.aiCards = aiCards;
		getCardValues(aiCards);
		toHighlight.clear();
		highCards = checkHighCards();
		colorChance = checkSuit();
		pairsNmore = checkPairAndMore();
		straightChance = checkStraight();
		
		Help();
	}

	
	/**
	 * converts the cards value into two diffirent arraylists.
	 * one for cardnumber and one for cardcolor.
	 * @param aiCards current card being used
	 */
	public void getCardValues(ArrayList<String> aiCards){
		
		for(int i = 0; i<aiCards.size(); i++){			//CardNumber
			 String temp = aiCards.get(i);
			 String[] splitter = temp.split(",");
			 int tempInt = Integer.parseInt(splitter[0]);
			 cardNbr.add(tempInt);
		}

		
		for(int i = 0; i<aiCards.size(); i++){			//CardColor		
			 String temp = aiCards.get(i);
			 String[] splitter = temp.split(",");
			 String tempString = splitter[1];
			 cardClr.add(tempString);
		}
	}
	/**
	 * 
	 * @return returns how many pair or more the player has.
	 */
	public int checkPairAndMore(){
		int same = 1;
		int nbrOftemp = 0;
		int nbrOftemp1 = 0;
		int nbrOftemp2 = 0;
		int size = aiCards.size();
		int[] cards = new int[size+1];
		
		
		for(int i = 0; i< size; i++){
			cards[i] = cardNbr.get(i);
		}
		
		if(cards[0]==cards[1]){
			int temp = cards[0];
			 nbrOftemp = 2;
			 toHighlight.clear();
			 toHighlight.add(aiCards.get(1));
			 toHighlight.add(aiCards.get(0));
			 yourCard = aiCards.get(0);
			 otherCard = aiCards.get(1);
			
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
				if(cards[i] + temp2 <=10){
					lowCards=true;
				}
				if(cards[i] + temp2 > 17){
					highCards=true;
				}

				nbrOftemp1++;
				
				yourCard = aiCards.get(0);
				otherCard = aiCards.get(i);
				yourCard2 = aiCards.get(0);
			}
			if(cards[i]==temp2){
				
				if(cards[i] + temp2 > 17){
					highCards=true;
				}
				
				if(cards[i] + temp2 <= 10){
					lowCards=true;
				}
				nbrOftemp2++;
				yourCard = aiCards.get(1);
				otherCard = aiCards.get(i);
				
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

	}
	/**
	 * 
	 * @return returns true of cards value >= 17.
	 * 'rlyHigh not yet implemented.
	 */
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
	/**
	 * 
	 * @return returns if the player has a suit or even has a chance for one.
	 */
	public int checkSuit(){
		int C = 0; int S = 0;
		int H = 0; int D = 0;
	    int color = 0;
	    
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
		
		if(S>color){
			toHighlight.clear();
			color =  S;
			theColor = "spades";
			for(int i = 0; i<cardClr.size(); i++){
				String temp = cardClr.get(i);
				if(S==5)
				if(temp.equals("S")){
					toHighlight.add(aiCards.get(i));
				}
			}
		}
		
		if(H>color){
			toHighlight.clear();
			color =  H;
			theColor = "hearts";
			for(int i = 0; i<cardClr.size(); i++){
				String temp = cardClr.get(i);
				if(H==5)
				if(temp.equals("H")){
					toHighlight.add(aiCards.get(i));
				}
			}
		}
		if(D>color){
			toHighlight.clear();
			color =  D;
			theColor = "diamond";
			for(int i = 0; i<cardClr.size(); i++){
				String temp = cardClr.get(i);
				if(D==5)
				if(temp.equals("D")){
					toHighlight.add(aiCards.get(i));
				}
			}
		}
		if(C>color){
			toHighlight.clear();
			color =  C;
			theColor = "cloves";
			for(int i = 0; i<cardClr.size(); i++){
				String temp = cardClr.get(i);
				if(C==5)
				if(temp.equals("C")){
					toHighlight.add(aiCards.get(i));
				}
			}
		}
		
		
		
	 return color;		
	}
	
	/**
	 * 
	 * @return returns if the player has a straight or even has a chance for one.
	 */
	public int checkStraight(){
		
		int[] tempArray = new int[aiCards.size()];
		int threshold = 0;
		for(int i = 0; i< aiCards.size(); i++){
			tempArray[i] = cardNbr.get(i);
		}
		
		Arrays.sort(tempArray);
		int inStraight = 0;	
		int check = 4;
		
	for(int x = 0; x<tempArray.length; x++){	
		int temp = tempArray[x]+check;
		inStraight = 0;
		check--;
		nbrForStraight.clear();
		for(int i = 0; i<tempArray.length; i++){
			
			if(tempArray[i]<=temp && !(tempArray[i]<temp-4)){
					
				if(i==0){							//kollar om 0 är samma som 1.
//					if(!(test[i]==test[i+1])){
						inStraight++;
						nbrForStraight.add(aiCards.get(i));
//					}
				}
				
				if(i>=1){							
					if(!(tempArray[i]==tempArray[i-1])){		//kollar om 1-4 är samma som nån annan.
						inStraight++;
						nbrForStraight.add(aiCards.get(i));
					}
				}	
				
			}
		}
	
		if(inStraight>threshold){
			nbrForStraight1.clear();
			nbrForStraight1 = nbrForStraight;
			
			threshold = inStraight;
	

		}
		
		
	}
	
			
		return threshold;
	}
	
	
	
	
	public int calcPwrBarLvl(){
		int pwrBar = 0;
		
		
	    if (straightChance==2) {
	    	pwrBar = 2;
	      }

	      if (highCards) {
	    	  pwrBar = 2;
	        if (rlyhighCards) {
	        	pwrBar = 3;
	        }
	      }

	      if (colorChance==2) {
	    	  pwrBar = 2;
	      }

	      if (pairsNmore > 0) {
	    	  pwrBar = 4;
	      }
		
		return pwrBar;
	}
	/**
	 * 
	 * @return returns a advice for the player that is current for his or her hand.
	 */
	public String Help(){
		
		String parisNmoreText = "";
		String advice = "";
		String helper="";
		String[] splitter = yourCard.split(",");
		String straightText = "";
		int cardNbr = Integer.parseInt(splitter[0]);
		String straighHelper = "";
		String pairsNmoreHelper = "";
		String colorChanceHelper ="";
		String yourCardInt="";
		
	
		if(cardNbr<11){
			yourCardInt = String.valueOf(cardNbr)+"s ";
			
		}
		
		if(cardNbr>10){
			if(cardNbr==11){
				yourCardInt = "Jacks";
			}
			if(cardNbr==12){
				yourCardInt = "Queens";
			}
			
			if(cardNbr==13){
				yourCardInt = "Kings";
			}
			if(cardNbr==14){
				yourCardInt = "Aces";
			}
			
		}
		
		//2 cards..
		if(aiCards.size()<3){
		
			if(straightChance==2){
			    straightText = "You have a chance for a straight, you have 2/5.\n";
				advice += straightText;
			}
			
			if(pairsNmore==2){			
				pairsNmoreHelper = "You have a pair of " + yourCardInt;
			}
			
			if(colorChance==2){
				advice += "You have a chance for a flush in " + theColor + ", you have 2/5 for it.\n";
				
			}
			
			if(highCards){
				if(advice.length()<2){
				advice = "You only have high cards. \nYou can try and see but only if its cheap\n";
			}
				else{
					advice += "You have high cards.\n";
				}
			}
		}
		
		
		
		//5+ cards.
	  if(aiCards.size()<6){
		if(straightChance==3){
			advice += "You have a chance for a straight, you have 3/5.\n";
		}		
	  }
	  
	  if(aiCards.size()<7){
		if(straightChance==4){
			advice += "You have a chance for a straight, you have 4/5.\n";
		}
	  }
	  
		if(straightChance==5){		
			straighHelper = "You have a straight!! you have 5/5.\n";
			advice += "A straight is a really good hand! Go for it!\n";
			toHighlight = nbrForStraight;
			
		}
		
		if(pairsNmore==2){			//par
			pairsNmoreHelper = "You have a pair of " + yourCardInt;
			if(lowCards){
			parisNmoreText = "A pair is a decent hand, even thou this is a low pair. If not to much, go for it.\n";
			}
			if(!(lowCards && highCards)){
				parisNmoreText = "A pair is a decent hand. If not to much, go for it.\n";
			}
			if(highCards){
				parisNmoreText = "A pair is a decent hand, and this is a high pair. If not to much, go for it.\n";
			}
			
			advice += parisNmoreText;
			toHighlight.clear();
			for(int i = 0; i<aiCards.size(); i++){			
				String[] seeIfSame = aiCards.get(i).split(",");
				int temp = Integer.parseInt(seeIfSame[0]);
				if(cardNbr==temp){
					toHighlight.add(aiCards.get(i));
				}
			}
		}
		if(pairsNmore==3){			//triss
			pairsNmoreHelper = "You have three of a kind of " + yourCardInt;			
			parisNmoreText = "Three of a kind is a solid hand. Go for it, also you should consider to raise.\n";
			advice += parisNmoreText;
			toHighlight.clear();
			for(int i = 0; i<aiCards.size(); i++){			
				String[] seeIfSame = aiCards.get(i).split(",");
				int temp = Integer.parseInt(seeIfSame[0]);
				if(cardNbr==temp){
					toHighlight.add(aiCards.get(i));
				}
			}
		}
		if(pairsNmore==4){			//fyrtal
			pairsNmoreHelper += "You have four of a kind in " + yourCardInt;
			parisNmoreText = "Four of a kind is hard to beat. Raise!\n";
			advice += parisNmoreText;
			toHighlight.clear();
			for(int i = 0; i<aiCards.size(); i++){			
				String[] seeIfSame = aiCards.get(i).split(",");
				int temp = Integer.parseInt(seeIfSame[0]);
				if(cardNbr==temp){
					toHighlight.add(aiCards.get(i));
				}
			}
		}
		
		if(pairsNmore==22){			//2-par
			String temps[] = yourCard2.split(",");
			pairsNmoreHelper = "You have two pairs of  " + yourCardInt + " and " + otherCard+"s"; //buggad!
			parisNmoreText = "Two pairs is a good hand. Go for it.\n";
			advice += parisNmoreText;
			toHighlight.clear();
			
			for(int i = 0; i<aiCards.size(); i++){			
				String[] seeIfSame = aiCards.get(i).split(",");
				int temp = Integer.parseInt(seeIfSame[0]);			
				if(cardNbr==temp || Integer.parseInt(temps[0]) == temp){
					toHighlight.add(aiCards.get(i));
				}
			}
		}
		
		if(pairsNmore == 23 || pairsNmore==32){		//kåk
			String temps[] = yourCard2.split(",");
			pairsNmoreHelper = "You have a full house with " + yourCardInt + " and " + otherCard+"s";   //Buggad!!
			parisNmoreText = "There isnt much that can beat this hand. Raise is prefered.\n";
			advice += parisNmoreText;
			
			for(int i = 0; i<aiCards.size(); i++){			
				String[] seeIfSame = aiCards.get(i).split(",");
				int temp = Integer.parseInt(seeIfSame[0]);			
				if(cardNbr==temp || Integer.parseInt(temps[0]) == temp){
					toHighlight.add(aiCards.get(i));
				}
			}
		}
		
		if(aiCards.size()<6){
			if(colorChance==3){
				advice += "You have a chance for a flush in " + theColor + ", you have 3/5 for it.\n";
				
			}
		}
		if(aiCards.size()<7){
			if(colorChance==4){
				advice += "You have a chance for a flush in " + theColor + ", you have 4/5 for it.\n";
			}
		}
			if(colorChance==5){
				colorChanceHelper = "You have a flush in " + theColor + ", you have 5/5!!\n";
				advice += "You have a flush!! Go for it, your hand is hard to beat!\n";
			}
		
		if(advice.length()==0){
			advice = "This hand might not be the best hand to continue with.\n";
		}
		

		helper += colorChanceHelper + pairsNmoreHelper + straighHelper;
		
		if(helper.length()<2){
			helper = "You have nothing, sorry.";
		}
		
		advicee = advice;
		return helper;
		
	}
	
	public String advice(){
		return advicee;
	}
	
	/**
	 * 
	 * @return what to be highlighed.
	 */
	public ArrayList<String> toHiglight(){
		return toHighlight;
		
	}
}
