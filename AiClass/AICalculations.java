package AiClass;

import java.util.ArrayList;

/**
 * Interface som de olika AIturns använder sig av.
 * @author Max Frennessen
 * 24-03-17
 */

public interface AICalculations {

	/**
	 * 
	 * @param aiCards - ArrayList som består av korten som är
	 *  aktiva får AIn för att räkna ut ett beslut.
	 */
	void getCardValues(ArrayList<String> aiCards);
	
	
	/**
	 * 
	 * @return -returns true or false beroende om korten
	 * har ett högt värde tillsammans.
	 */
	boolean checkHighCards();
	
	
	/**
	 * 
	 * @return - returns true or false beroende om korten
	 * har samma färg.
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
	 * @return returns antal kort som håller på att bygga en stege.
	 */	
	int checkStraight();
	
	
	/**
	 * 
	 * @return returns ny int på AI pot.
	 */
	 int updateAiPot();
	
}
