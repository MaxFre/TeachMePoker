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

//		if (card1 >= 10 && card2 >= 10) {
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
			}
		}
		return Color;
	}
	
/**
 * 
 * @return returns how main pairs or more that the ai has.
 */
	public int checkPairAndMore() {
		int same = 1;
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

		int[] tempArray = new int[aiCards.size()];
		int treshold = 0;
		for (int i = 0; i < aiCards.size(); i++) {
			tempArray[i] = cardNbr.get(i);
		}

		Arrays.sort(tempArray);
		int inStraight = 0;
		int check = 4;

		for (int x = 0; x < tempArray.length; x++) {
			int temp = tempArray[x] + check;
			inStraight = 0;
			check--;
			for (int i = 0; i < tempArray.length; i++) {
				if (tempArray[i] <= temp && !(tempArray[i] < temp - 4)) {

					// problem med dubbletter i början, ex 3-3.

					if (i == 0) { 			// kollar om 0 är samma som 1.
						if (!(tempArray[i] == tempArray[i + 1])) {
							inStraight++;
						}
					}
					if (i >= 1) {
						if (!(tempArray[i] == tempArray[i - 1])) { // kollar om 1-4 är samma som nån annan.
							inStraight++;
						}
					}

				}
			}

			if (inStraight > treshold) {
				treshold = inStraight;
			}

		}

		return treshold;
	}

}
