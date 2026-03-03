import java.util.Vector;
import java.util.Collections;

public class CardContainer {
	private Vector<Card> cards = new Vector<Card>();

	// NOTE: debug
	public void printContents() {
		for (int i = 0; i < cards.size(); i++) {
			System.out.print(cards.get(i).getCardVal() + " ");
		}
		System.out.println();
	}

	public Card getCard(int index) {
		return cards.get(index);
	}

	public void addCard(Card card) {
		cards.add(card);
	}

	public Card getFirstCard() {
		return cards.get(0);
	}
	public void removeFirstCard() {
		cards.removeFirst();
	}

	public void removeCardAtIndex(int index) {
		cards.removeElementAt(index);
	}

	public void shuffleCards() {
		Collections.shuffle(cards);
	}

}
