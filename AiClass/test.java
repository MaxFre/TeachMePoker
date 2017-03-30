package AiClass;

import java.util.Arrays;

public class test {

	private int[] fromCards = new int[10];
	private String whatToDo;
	private TurnTwo turnTwo;
	int[] cards = new int[7];	
	
	public test(){
			
		cards[0]=2;
		cards[1]=2;
		cards[2]=3;
		cards[3]=4;
		cards[4]=5;
		cards[5]=6;
		cards[6]=17;
		
		int straight = turnTwo();
		System.out.println(straight);
		


	}
	
	
	public int turnTwo(){
		int straight = 0;
		int[] test = cards;

		Arrays.sort(test);
		int inStraight = 1;
		int check = 4;

		for (int x = 0; x < test.length; x++) {
			int temp = test[x] + check;
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
	
	
	
	public static void main(String [] args){
		test run = new test();
	}
}
