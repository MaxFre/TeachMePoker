package Projekt;

import java.util.ArrayList;
import java.util.Random;
/**
 * 
 * @author Max Frennessen
 * Producerar korten som används för programmet
 *
 */
public class Cards {
	
	private int[] cardnbr = {2,3,4,5,6,7,8,9,10,11,12,13,14};
	private String[] suit = {"D","S","H","C"};
	private ArrayList<String> theDeck = new ArrayList<String>();		
	private String[] toPlayers = new String[10];	  // ÄNDRA TILL 5 FÖR TURN2
	private String[] deck = new String[52];
	
	
	
	public Cards(){
		
		ProduceCards();
		drawCards();
	}
	
	
	public void ProduceCards(){
		System.out.println("Produce cards");
		int nbr = 0;
		
		for(int i = 0; i<4; i++){
			for(int x = 0; x<13; x++){
				if(i==0){
					deck[nbr]=String.valueOf(cardnbr[x]) +","+ suit[i];
					nbr++;				
				}
				
				if(i==1){
					deck[nbr]=String.valueOf(cardnbr[x]) +","+  suit[i];
					nbr++;
				}
				
				if(i==2){
					deck[nbr]=String.valueOf(cardnbr[x]) +","+  suit[i];
					nbr++;
				}
				
				if(i==3){
					deck[nbr]=String.valueOf(cardnbr[x]) +","+  suit[i];
					nbr++;
				}
			
			}
		}
		
		for(int i = 0; i<deck.length; i++){
			theDeck.add(deck[i]);
		}
		
	}
	
	
	public void drawCards(){
		System.out.println("Draw cards");
		Random rand = new Random();		
		int cardcount = 52;
		
		for(int i = 0; i<10; i++){			   // ÄNDRA TILL 5 FÖR TURN2
			int card = rand.nextInt(cardcount);
			toPlayers[i]=theDeck.get(card);			
			theDeck.remove(card);
			cardcount--;
			
		}
		
		
	}
	
	public String[] getCards(){
	return toPlayers;
	}
	
	public void newRound(){
		ProduceCards();
		drawCards();
	}

}
