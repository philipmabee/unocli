public class Hand extends CardContainer {
	private Deck deck;
	private DiscardPile discardPile;

	private String name;

	/** 
	* copies the first card from the deck to hand and removes that card from the deck.<br>
	* Will move all the cards form discard pile to deck and shuffle cards if deck is empty.
	*/
	public void drawFromDeck() {
		if (deck.getSize() == 0) {

			if (discardPile.getSize() == 0) {
				throw new Error("Unable to draw card from discard pile");
			}

			while (discardPile.getSize() > 1) {
				Card currCard = discardPile.getCard(1);
				if (currCard.getCardSpecialType() == Card.SpecialType.DRAW4 || (currCard.getCardSpecialType() == Card.SpecialType.NONE && currCard.getCardNumber() == -1)) {
					System.out.println("THE FUNCTION IS RUNNING"); // TODO: test this
					System.out.println("card was: " + currCard.getCardVal());
					System.out.println("card is now: " + new Card(currCard.getCardSpecialType(), Card.Color.WILD).getCardVal() + "\n\n");
					deck.addCard(new Card(currCard.getCardSpecialType(), Card.Color.WILD));
					discardPile.removeCardAtIndex(1);
					continue;
				}


				deck.addCard(discardPile.getCard(1));
				discardPile.removeCardAtIndex(1);
			}
			deck.shuffleCards();
			return;
		}

		addCard(deck.getFirstCard());
		deck.removeFirstCard();
	}

	// TODO: make this return an enum
	/**
	 * gets the card at index and puts it into discard pile
	 * @return
	 * 0 if successfull<br>
	 * 1 if cannot play that card<br>
	 * 2 if invalid index
	*/
	public int playCard(int index, int numOfHands) {
		if (index > getSize() - 1 || index < 0) {
			return 2;
		}

		if (canPlayCard(index)) {
			UserInput.printTextWithColor(name + " played ", UserInput.Color.WHITE);
			getCard(index).printCardWithColor();
			System.out.println();

			discardPile.addCard(getCard(index));
			removeCardAtIndex(index);

			GameLogic.handleCardSideEffect(discardPile.getCard(discardPile.getSize() -1), numOfHands, this, discardPile);

			return 0;
		}

		return 1;
	}

	/** 
	 * @return true if card card color matches, the card num matches and special type matches, or the card color is WILD<br>
	*/
	public boolean canPlayCard(int index) {
		Card.Color cardColor = getCard(index).getCardColor();
		Card.SpecialType cardType = getCard(index).getCardSpecialType();
		int cardNum = getCard(index).getCardNumber();
		Card currGameCard = discardPile.getCard(discardPile.getSize() - 1);

		if (cardColor == currGameCard.getCardColor() || (cardNum == currGameCard.getCardNumber() && cardType == currGameCard.getCardSpecialType()) || cardColor == Card.Color.WILD)
			return true;
		return false;
	}

	/** 
	* @return the hand name
	*/
	public String getName() {
		return name;
	}

	// draws 7 cards
	Hand(String name, Deck deck, DiscardPile discardPile) {
		this.deck = deck;
		this.discardPile = discardPile;
		this.name = name;

		for (int i = 0; i < 7; i++) {
			drawFromDeck();
		}
	}

	// draw specified number of cards
	Hand(String name, Deck deck, DiscardPile discardPile, int numOfStartingCards) {
		this.deck = deck;
		this.discardPile = discardPile;
		this.name = name;

		for (int i = 0; i < numOfStartingCards; i++) {
			drawFromDeck();
		}
	}
}
