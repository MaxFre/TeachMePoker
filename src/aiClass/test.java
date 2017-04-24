package aiClass;

import java.util.ArrayList;
import java.util.Arrays;

public class test {

	private String[] fromCards = new String[10];
	private String whatToDo;
	private TurnTwo turnTwo;
	private ArrayList<String>  cards = new  ArrayList<String>();	
	private ArrayList<Integer> cardNbr = new ArrayList<Integer>();
	private ArrayList<String> cardClr = new ArrayList<String>();
	public test(){
			
		
		
		cards.add("2,h");
		cards.add("31,d");
		cards.add("41,h");
		cards.add("51,d");
		cards.add("114,h");
		
		
		getCardValues();
		int straight = turnTwo();
		System.out.println(cardNbr);
		System.out.println(straight);
		
		
		
		
		
		
	}
	
	
	public int turnTwo(){
		
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
		int check = 4;
		String currentTemp ="";
	
		
		for (int x = 0; x < tempArray.length; x++) {			
			int temp = tempArray[x] + check;
			inStraight = 1;
			
			for (int i = 0; i < tempArray.length; i++) {
				if (tempArray[i] <= temp && !(tempArray[i] < temp - 4)) {			//	 temp-4> i <temp  when i is within this range
	
					if (i > 0) {
						if (!(tempArray[i] == tempArray[i - 1])) { // kollar om 1-4 är samma som nån annan.
							inStraight++;
						}
					}

				}
			}

			if (inStraight > treshold) {
				treshold = inStraight;
				currentTemp =  String.valueOf(temp-4) + "-"+String.valueOf(temp);
			}

		}
		System.out.println("StegChans i - " + currentTemp);
		return treshold;
	}

	
	
	public void getCardValues() {
		for (int i = 0; i < cards.size(); i++) { 		// CardNumber
			String temp = cards.get(i);
			String[] splitter = temp.split(",");
			int tempInt = Integer.parseInt(splitter[0]);
			cardNbr.add(tempInt);
		}

		for (int i = 0; i < cards.size(); i++) { 		// CardColor
			String temp = cards.get(i);
			String[] splitter = temp.split(",");
			String tempString = splitter[1];
			cardClr.add(tempString);
		}
	}
	
	public static void main(String [] args){
		test run = new test();
	}
}
