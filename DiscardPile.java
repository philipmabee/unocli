public class DiscardPile extends CardContainer {
	private Deck deck;

	DiscardPile(Deck deck) {
		this.deck = deck;

		addCard(deck.getFirstCard());
		deck.removeFirstCard();
	}
}
