package ProjektTEST;



 // CONTROLLER-klass


public class Dealer {
	Cards cards;
	Table table;
	Ai ai;
//	String[] fromDeck;
	
	
	private String[] firstTurn = new String[10];
	
	
	
	public Dealer() {	
		
		new Table();
		cards = new Cards();
		firstTurn = cards.getCards();
		table.updateBoard(firstTurn);
		
		
		
		
		
//		new Ai();
		
	}

	public void newRound(){
		cards.newRound();
		table.newRound();
	}

	public boolean getAiAnswer() {
		boolean aibet;
		boolean res;
		
		ai = new Ai(firstTurn[2],firstTurn[3], 500);
		aibet = ai.decision();
		if(aibet){
			res=true;
		}
		else
			res =false;
			
		return res;
	}

}
