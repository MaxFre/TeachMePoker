package aiClass;

import java.util.ArrayList;

/**
 * Interface som de olika AIturns anv�nder sig av.
 * @author Max Frennessen
 * 24-03-17
 */

public interface AICalculations {

	/**
	 * 
	 * @param aiCards - ArrayList som best�r av korten som �r
	 *  aktiva f�r AIn f�r att r�kna ut ett beslut.
	 */
	void getCardValues(ArrayList<String> aiCards);
	
	
	/**
	 * 
	 * @return -returns true or false beroende om korten
	 * har ett h�gt v�rde tillsammans.
	 */
	boolean checkHighCards();
	
	
	/**
	 * 
	 * @return - returns true or false beroende om korten
	 * har samma f�rg.
	 */
	boolean checkSuit();
	
	
	/**
	 * 
	 * @return returns antal par/triss/fyrtal  
	 * 
	 */
	int checkPairAndMore();
	
	/**
	 * 
	 * @return returns true or false beroende om korten 
	 * har en chans att bli en stege.
	 */	
	boolean checkStraight();
	
}
