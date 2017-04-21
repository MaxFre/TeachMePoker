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
			
		
		int straight = turnTwo();
		System.out.println(straight);
		
		cards.add("6,h");
		cards.add("6,d");
		cards.add("6,h");
		cards.add("6,d");
		cards.add("6,h");
		
		
		getCardValues(cards);
	}
	
	
	public int turnTwo(){
		int straight = 0;

		int[] test = new int[5];
		
		for(int i = 0; i< 5; i++){
			test[i] = cardNbr.get(i);
		}

		Arrays.sort(test);
		int inStraight = 1;
		int check = 4;

		for (int x = 0; x < test.length; x++) {
			int temp = (test[x] + check);
			inStraight = 1;
			check--;

			for (int i = 0; i < test.length; i++) {
//				if (test[i] <= temp && !(test[i] < temp - 4)) {

					
					if (i > 0) {
						if ((test[i]-1 == test[i - 1])) { 			// kollar om 1-4 är	 samma som nån annan.
							inStraight++;
						}
					}

//				}
			
			}
			if (inStraight > straight) {
				straight = inStraight;
			}
			}
		
		return straight;
	}
	
	public void getCardValues(ArrayList<String> cards){
		
		for(int i = 0; i<cards.size(); i++){			//CardNumber
			 String temp = cards.get(i);
			 String[] test = temp.split(",");
			 int tempInt = Integer.parseInt(test[0]);
			 cardNbr.add(tempInt);
		}

		
		for(int i = 0; i<cards.size(); i++){			//CardColor		
			 String temp = cards.get(i);
			 String[] test = temp.split(",");
			 String tempString = test[1];
			 cardClr.add(tempString);
		}
	}
	
//	public static void main(String [] args){
//		test run = new test();
//	}
}
