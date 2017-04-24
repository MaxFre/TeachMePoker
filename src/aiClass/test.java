package aiClass;

import java.util.ArrayList;
import java.util.Arrays;

public class test {

	private String[] fromCards = new String[10];
	private String whatToDo;
	private TurnTwo turnTwo;
	private ArrayList<String> temphihglightForStraight = new ArrayList<String>();
	private ArrayList<String> currentHighlightForStraight = new ArrayList<String>();
	private ArrayList<String>  cards = new  ArrayList<String>();	
	private ArrayList<Integer> cardNbr = new ArrayList<Integer>();
	private ArrayList<String> cardClr = new ArrayList<String>();
	public test(){
			
		
		
		cards.add("140,h");
		cards.add("2,d");
		cards.add("14,h");
		cards.add("4,d");
		cards.add("2,h");
		cards.add("5,d");
		cards.add("3,h");
		
		
		getCardValues();
		int straight = turnTwo();
		System.out.println(cardNbr);
		System.out.println(straight);
				
	}
	
	
	public int turnTwo(){
		int treshold = 0;
		String[] correctOrder = new String[cards.size()];
		
		int a =  0;
		for(String x: cards){
			correctOrder[a] = x;
			a++;
		}
		
		for(int i = 0; i<cardNbr.size(); i++){
		 if(cardNbr.get(i) == 14){
			cardNbr.add(1);
		 }
		}
		
		int[] tempArray = new int[cardNbr.size()];
		

		for (int i = 0; i < cardNbr.size(); i++) {
			tempArray[i] = cardNbr.get(i);
		}
		
		Arrays.sort(tempArray);

		
		int inStraight;
		
		for (int x = 0; x < tempArray.length; x++) {	
			
			int currentHighestInStraight = tempArray[x] + 4;
			int currentLowestInStraight = currentHighestInStraight-4;
			inStraight = 1;

			for (int i = 0; i < cards.size(); i++) {
			
				if (tempArray[i] >= currentLowestInStraight && tempArray[i] <= currentHighestInStraight) {			//	 temp-4> i <temp  when i is within this range
	
//				if(i == 0){
//					if(!(tempArray[0]==tempArray[1]) && tempArray[0]>=currentLowestInStraight && tempArray[0]<=currentHighestInStraight){
//							String[] split = correctOrder[i].split(",");
//							int split2 = Integer.parseInt(split[0]);
//							if(tempArray[i]==split2){
//								
//						}
//						
//					}
//				}
				
					if (i >= 0) {
						
						if(i == 0){
							for(int find = 0; find<cards.size(); find++){
								String[] split = correctOrder[i].split(",");
								if(split[0].equals(String.valueOf(tempArray[find]))){
									temphihglightForStraight.add(correctOrder[find]);
								}
							}			
						}
						
					else if (!(tempArray[i] == tempArray[i - 1])){ // kollar om 1-4 är samma som nån annan.
							inStraight++;	
							for(int find = 0; find<cards.size(); find++){
								String[] split = correctOrder[i].split(",");
								if(split[0].equals(String.valueOf(tempArray[find]))){
									temphihglightForStraight.add(correctOrder[find]);
								}
							}
//							temphihglightForStraight.add(String.valueOf(tempArray[i]));
						}
					}

				}
			}

			if (inStraight > treshold) {
				treshold = inStraight;
				currentHighlightForStraight = temphihglightForStraight;
				System.out.println("sista currentHighlightForStraight - " + currentHighlightForStraight);
			}

		}
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
