package Projekt;

/**
 * 
 * @author Max Frennessen
 * Demo för koncept samt tidigt version av turn1 AI.
 *
 */



public class StartGame {		

	
	public StartGame(){
		int nbrOfPlayers = 4;		
		new Table(nbrOfPlayers);
	}
	
	
	
	
	public static void main(String [] args){
		StartGame run = new StartGame();
	}

}
