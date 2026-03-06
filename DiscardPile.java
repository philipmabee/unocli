public class DiscardPile extends CardContainer {
	private Deck deck;

	// puts the first normal number card in discard pile
	DiscardPile(Deck deck) {
		this.deck = deck;

		int i = 0;
		while (deck.getCard(i).getCardNumber() == -1 || deck.getCard(i).getCardColor() == Card.Color.WILD) {
			i++;
		}

		addCard(deck.getCard(i));
		deck.removeCardAtIndex(i);
	}
}
