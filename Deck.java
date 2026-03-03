// import java.util.Vector;
// import java.util.Collections;

public class Deck extends CardContainer {
	// Vector<Card> cards = new Vector<Card>();

	// public void printContents() {
	// 	for (int i = 0; i < cards.size(); i++) {
	// 		System.out.print(cards.get(i).getCardVal() + " ");
	// 	}
	// 	System.out.println();
	// }

	// public Card getFirstCard() {
	// 	return cards.get(0);
	// }

	// public void removeFirstCard() {
	// 	cards.removeFirst();
	// }


	private void addStandardCardsToDeck() {
		// Each color has one 0 card
		// Each color has 2 of the following: draw2, reverse, skip
		// there are also 4 draw4 cards and 4 wild cards
		for (int i = 0; i < 4; i++) {
			switch (i) {
				case 0:
					addCard(new Card((byte) 0, Card.Color.BLUE));

					addCard(new Card(Card.SpecialType.DRAW2, Card.Color.BLUE));
					addCard(new Card(Card.SpecialType.DRAW2, Card.Color.BLUE));

					addCard(new Card(Card.SpecialType.REVERSE, Card.Color.BLUE));
					addCard(new Card(Card.SpecialType.REVERSE, Card.Color.BLUE));

					addCard(new Card(Card.SpecialType.SKIP, Card.Color.BLUE));
					addCard(new Card(Card.SpecialType.SKIP, Card.Color.BLUE));
					break;
				case 1:
					addCard(new Card((byte) 0, Card.Color.YELLOW));

					addCard(new Card(Card.SpecialType.DRAW2, Card.Color.YELLOW));
					addCard(new Card(Card.SpecialType.DRAW2, Card.Color.YELLOW));

					addCard(new Card(Card.SpecialType.REVERSE, Card.Color.YELLOW));
					addCard(new Card(Card.SpecialType.REVERSE, Card.Color.YELLOW));

					addCard(new Card(Card.SpecialType.SKIP, Card.Color.YELLOW));
					addCard(new Card(Card.SpecialType.SKIP, Card.Color.YELLOW));
					break;
				case 2:
					addCard(new Card((byte) 0, Card.Color.GREEN));

					addCard(new Card(Card.SpecialType.DRAW2, Card.Color.GREEN));
					addCard(new Card(Card.SpecialType.DRAW2, Card.Color.GREEN));

					addCard(new Card(Card.SpecialType.REVERSE, Card.Color.GREEN));
					addCard(new Card(Card.SpecialType.REVERSE, Card.Color.GREEN));

					addCard(new Card(Card.SpecialType.SKIP, Card.Color.GREEN));
					addCard(new Card(Card.SpecialType.SKIP, Card.Color.GREEN));
					break;
				case 3:
					addCard(new Card((byte) 0, Card.Color.RED));

					addCard(new Card(Card.SpecialType.DRAW2, Card.Color.RED));
					addCard(new Card(Card.SpecialType.DRAW2, Card.Color.RED));

					addCard(new Card(Card.SpecialType.REVERSE, Card.Color.RED));
					addCard(new Card(Card.SpecialType.REVERSE, Card.Color.RED));

					addCard(new Card(Card.SpecialType.SKIP, Card.Color.RED));
					addCard(new Card(Card.SpecialType.SKIP, Card.Color.RED));
					break;
			}

			addCard(new Card()); // wild
			addCard(new Card(Card.SpecialType.DRAW4, Card.Color.WILD));
		}

		// Each color (not wild) has two of each card from 1-9
		for (byte n = 1; n < 10; n++) {
			for (byte c = 0; c < 4; c++) {
				switch (c) {
					case 0:
						addCard(new Card(n, Card.Color.BLUE));
						addCard(new Card(n, Card.Color.BLUE));
						continue;
					case 1:
						addCard(new Card(n, Card.Color.YELLOW));
						addCard(new Card(n, Card.Color.YELLOW));
						continue;
					case 2:
						addCard(new Card(n, Card.Color.GREEN));
						addCard(new Card(n, Card.Color.GREEN));
						continue;
					case 3:
						addCard(new Card(n, Card.Color.RED));
						addCard(new Card(n, Card.Color.RED));
						continue;
				}
			}
		}
	}

	Deck() {
		addStandardCardsToDeck();	
		shuffleCards();
	}

}
