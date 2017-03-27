package projekt;

/**
 * 
 * @author Max Frennessen
 * Demo f�r koncept samt tidigt version av turn1 AI.
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
