public class Hand extends CardContainer {
	// private Vector<Card> cards = new Vector<Card>();
	private Deck deck;


	// adds the first card in deck to hand and removes it from deck
	public void drawFromDeck() {
		addCard(deck.getFirstCard());
		deck.removeFirstCard();
	}


	// draws 7 cards
	Hand(Deck deck) {
		this.deck = deck;
		for (int i = 0; i < 7; i++) {
			drawFromDeck();
		}
	}

	Hand(Deck deck, int numOfStartingCards) {
		this.deck = deck;
		for (int i = 0; i < numOfStartingCards; i++) {
			drawFromDeck();
		}
	}
}
