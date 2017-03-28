package deck;

public enum Suit {
	HEARTS('h'),
	SPADES('s'),
	CLUBS('c'),
	DIAMONDS('d');

	private char suit;
	private Suit (char firstLetter){
		this.suit = firstLetter;
	}

	public char getSuitLetter(){
		return suit;
	}
}

