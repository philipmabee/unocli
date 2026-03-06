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

	// gets the card at index and puts it into discard pile
	// returns 0 if successfull
	// returns 1 if cannot play that card
	// returns 2 if invalid index
	// TODO: make this return an enum
	public int playCard(int index) {
		if (index > getSize() - 1 || index < 0) {
			return 2;
		}

		Card.Color cardColor = getCard(index).getCardColor();
		int cardNum = getCard(index).getCardNumber();
		
		Card currGameCard = discardPile.getCard(discardPile.getSize() - 1);
		
		if (cardColor == currGameCard.getCardColor() || cardNum == currGameCard.getCardNumber() || cardColor == Card.Color.WILD) {
			discardPile.addCard(getCard(index));
			removeCardAtIndex(index);

			return 0;
		}

		return 1;
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
