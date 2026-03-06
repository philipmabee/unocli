import java.util.Vector;
import java.util.Collections;

public class CardContainer {
	private Vector<Card> cards = new Vector<Card>();

	// prints the cardVal of all the cards
	public void printContents() {
		System.out.print("  ");
		for (int i = 0; i < cards.size(); i++) {
			System.out.print(cards.get(i).getCardVal() + "  ");
		}
		System.out.println();
	}

	// prints the cardVal of all the cards
	// index shown starts at 1
	public void printContentsWithIndex() {
		System.out.print("  ");
		for (int i = 0; i < cards.size(); i++) {
			System.out.print((i + 1) + ":" + cards.get(i).getCardVal() + "  ");
		}
		System.out.println();
	}

	public int getSize() {
		return cards.size();	
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
