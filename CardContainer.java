// TODO: make printing cards with color optoinal
import java.util.Vector;
import java.util.Collections;

public class CardContainer {
	private Vector<Card> cards = new Vector<Card>();

	// prints the cardVal of all the cards
	public void printContents() {
		System.out.print("  ");
		for (int i = 0; i < cards.size(); i++) {
			// System.out.print(cards.get(i).getCardVal() + "  ");
			printCardWithColor(i);
			System.out.print("  ");
		}
		System.out.println();
	}

	// prints the cardVal of all the cards
	// index shown starts at 1
	public void printContentsWithIndex() {
		System.out.print("  ");
		for (int i = 0; i < cards.size(); i++) {
			UserInput.printTextWithColor((i + 1) + ":", UserInput.Color.WHITE);
			// System.out.print((i + 1) + ":");
			printCardWithColor(i);
			System.out.print("  ");
		}
		System.out.println();
	}

	// prints the card with colored text rather than a char at the beginning
	private void printCardWithColor(int index) {
		Card.Color cardColor = cards.get(index).getCardColor();
		byte cardNum = cards.get(index).getCardNumber();
		UserInput.Color printColor;		

		switch (cardColor) {
			case Card.Color.BLUE:
				printColor = UserInput.Color.BLUE;
				break;
			case Card.Color.YELLOW:
				printColor = UserInput.Color.YELLOW;
				break;
			case Card.Color.GREEN:
				printColor = UserInput.Color.GREEN;
				break;
			case Card.Color.RED:
				printColor = UserInput.Color.RED;
				break;
			case Card.Color.WILD:
				printColor = UserInput.Color.WHITE;
				break;
			default:
				throw new Error("Unhandled color");
		}

		if (cardNum != -1) {
			UserInput.printTextWithColor(cardNum + "", printColor);
			return;
		}

		switch (cards.get(index).getCardSpecialType()) {
			case Card.SpecialType.NONE:
				UserInput.printTextWithColor("w", printColor);
				return;
			case Card.SpecialType.DRAW2:
				UserInput.printTextWithColor("+2", printColor);
				return;
			case Card.SpecialType.DRAW4:
				UserInput.printTextWithColor("+4", printColor);
				return;
			case Card.SpecialType.SKIP:
				UserInput.printTextWithColor("S", printColor);
				return;
			case Card.SpecialType.REVERSE:
				UserInput.printTextWithColor("R", printColor);
				return;
		}
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
