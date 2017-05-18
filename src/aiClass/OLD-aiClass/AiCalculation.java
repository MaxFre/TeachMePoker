package aiClass;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that actually figures out what AI should do.
 * @author Max Frennessen
 * 17-04-12
 * @version 1.5
 *
 */
public class AiCalculation {

	private ArrayList<String> aiCards = new ArrayList<String>();
	private ArrayList<Integer> cardNbr = new ArrayList<Integer>();
	private ArrayList<String> cardClr = new ArrayList<String>();
	private int handStrenght = 0;
	private int same = 1;
	private boolean flush = false;
	private boolean straight = false;
	/**
	 * 
	 * @param aiCards The current cards that are important.
	 */
	public AiCalculation(ArrayList<String> aiCards) {
		this.aiCards = aiCards;
		getCardValues();
		checkHighCards();
		checkSuit();
		checkPairAndMore();
		checkStraight();
	}
	
	/**
	 * get the values of the cards seperated into to arraylists
	 * one for cardnumbers and one for cardcolor.
	 */
	public void getCardValues() {
		for (int i = 0; i < aiCards.size(); i++) { // CardNumber
			String temp = aiCards.get(i);
			String[] splitter = temp.split(",");
			int tempInt = Integer.parseInt(splitter[0]);
			cardNbr.add(tempInt);
		}

		for (int i = 0; i < aiCards.size(); i++) { // CardColor
			String temp = aiCards.get(i);
			String[] splitter = temp.split(",");
			String tempString = splitter[1];
			cardClr.add(tempString);
		}
	}
	
/**
 * 
 * @return returns if the cards have a combined value of 17 or more.
 */
	public boolean checkHighCards() {
		boolean high = false;

		int card1 = cardNbr.get(0);
		int card2 = cardNbr.get(1);

		int total = (card1 + card2);

		if (total >= 17) {
			high = true;
		}

//		if (card1 >= 10 && card2 >= 10) {		//not implemented
//			rlyhighCards = true;
//		}
		return high;
	}
	
/**
 * 
 * @return returns if the AI has a chance or has a flush
 */
	public boolean checkSuit() {
		int C = 0;
		int S = 0;
		int H = 0;
		int D = 0;
		boolean Color = false;
		for (String x : cardClr) {
			if (x.equals("S")) {
				S++;
			}
			if (x.equals("C")) {
				C++;
			}
			if (x.equals("D")) {
				D++;
			}
			if (x.equals("H")) {
				H++;
			}
		}
		if (cardClr.size() == 2) {
			if (S == 2 || C == 2 || D == 2 || H == 2) {
				Color = true;
			}
		}
		if (cardClr.size() == 5) {
			if (S >= 3 || C >= 3 || D >= 3 || H >= 3) {
				Color = true;
			}
		}
		if (cardClr.size() == 6) {
			if (S >= 4 || C >= 4 || D >= 4 || H >= 4) {
				Color = true;
			}
		}
		if (cardClr.size() == 7) {
			if (S == 5 || C == 5 || D == 5 || H == 5) {
				Color = true;
				flush = true;
			}
		}
		return Color;
	}
	
/**
 * 
 * @return returns how main pairs or more that the ai has.
 */
	public int checkPairAndMore() {
		
		int nbrOftemp = 0;
		int nbrOftemp1 = 0;
		int nbrOftemp2 = 0;
		int[] cards = new int[cardNbr.size()];

		for (int i = 0; i < cardNbr.size(); i++) {
			cards[i] = cardNbr.get(i);
		}

		if (cards[0] == cards[1]) {
			int temp = cards[0];
			nbrOftemp = 2;

			for (int i = 2; i < cards.length; i++) {
				if (cards[i] == temp) {
					nbrOftemp++;
				}
			}
		}

		else {
			int temp1 = cards[0];
			int temp2 = cards[1];

			nbrOftemp1 = 1;
			nbrOftemp2 = 1;

			for (int i = 2; i < cards.length; i++) {

				if (cards[i] == temp1) {
					nbrOftemp1++;
				}
				if (cards[i] == temp2) {
					nbrOftemp2++;
				}
			}
		}

		if (nbrOftemp > 0) {
			same = nbrOftemp;
		}

		if (nbrOftemp1 > 1) {
			same = nbrOftemp1;
		}

		if (nbrOftemp2 > 1) {
			if (nbrOftemp1 > 1) {
				same = Integer.parseInt(nbrOftemp1 + "" + nbrOftemp2);
			} else
				same = nbrOftemp2;
		}

		if (same == 1) {
			same = 0;
		}

		return same;
	}

	/**
	 * 
	 * @return returns if the Ai has a chance or has a Straight.
	 */
	public int checkStraight() {
		if(cardNbr.get(cardNbr.size()-1) == 14){
			cardNbr.add(1);
		}
		
		int[] tempArray = new int[cardNbr.size()];
		int treshold = 0;

		for (int i = 0; i < cardNbr.size(); i++) {
			tempArray[i] = cardNbr.get(i);
		}

		Arrays.sort(tempArray);
	
		int inStraight;
		
		for (int x = 0; x < tempArray.length; x++) {			
			int currentHighestInStraight = tempArray[x] + 4;
			int currentLowestInStraight = currentHighestInStraight-4;
			inStraight = 1;
			
			for (int i = 0; i < tempArray.length; i++) {
				if (tempArray[i] >= currentHighestInStraight-4 && tempArray[i] <= currentHighestInStraight) {			//	 temp-4> i <temp  when i is within this range
	
					if (i > 0) {
						if (!(tempArray[i] == tempArray[i - 1]) && (tempArray[i-1] >= currentLowestInStraight && tempArray[i-1] <= currentHighestInStraight)){ // kollar om 1-4 är samma som nån annan.
							inStraight++;
						}
					}

				}
			}

			if (inStraight > treshold) {
				treshold = inStraight;
			}
		}
		if(treshold==5){
			straight = true;
		}
		return treshold;
	}
	
	
	public int calcHandstrenght(){
		
		if(same==2){
			handStrenght=1;	
		}
		if(same==22){
			handStrenght=2;
		}
		if(same==3){
			handStrenght=3;
		}
		if(straight){
			handStrenght=4;
		}
		if(flush){
			handStrenght=5;
		}
		if(same==23 || same==32){
			handStrenght=6;
		}
		if(same == 4 || same == 42 || same ==24){
			handStrenght = 7;
		}
		if(flush && straight){
			handStrenght = 8;
		}
	
		return handStrenght;
	}
	


}