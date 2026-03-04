public class Hand extends CardContainer {
	private Deck deck;
	private DiscardPile discardPile;

	public void drawFromDeck() {
		if (deck.getSize() == 0) {

			if (discardPile.getSize() == 0) {
				throw new Error("Unable to draw card from discard pile");
			}

			while (discardPile.getSize() > 1) {
				deck.addCard(discardPile.getCard(1));
				removeCardAtIndex(1);
			}
			deck.shuffleCards();
			return;
		}

		addCard(deck.getFirstCard());
		deck.removeFirstCard();
	}

	// draws 7 cards
	Hand(Deck deck, DiscardPile discardPile) {
		this.deck = deck;
		this.discardPile = discardPile;

		for (int i = 0; i < 7; i++) {
			drawFromDeck();
		}
	}

	// draw specified number of cards
	Hand(Deck deck, DiscardPile discardPile, int numOfStartingCards) {
		this.deck = deck;
		this.discardPile = discardPile;

		for (int i = 0; i < numOfStartingCards; i++) {
			drawFromDeck();
		}
	}
}
