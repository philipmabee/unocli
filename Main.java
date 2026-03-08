import java.util.Vector;

public class Main {
	public static void main(String[] args) {
		UserInput.clearScreen();
		printStartText();

		UserInput.printTextWithColor("Welcome to unocli!\n", UserInput.Color.CYAN);
		UserInput.printTextWithColor("press h for help\n\n" , UserInput.Color.WHITE);


		Deck deck = new Deck();
		DiscardPile discardPile = new DiscardPile(deck);

		Hand player = new Hand(deck, discardPile);

		// GameLogic.printCurrentCardInfo(discardPile);

		String[] userInput;
		while (true) {
			userInput = UserInput.getUserInput("> ").split(" ");

			switch (userInput[0]) {
				case "help":
				case "h": // print help
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					printHelp();
					break;

				case "list":
				case "l": // list cards
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					player.printContents();
					break;

				case "n":
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					player.printContentsWithIndex();
					break;

				case "play":
				case "p":
					GameLogic.playCard(player, discardPile, userInput);
					break;

				case "info":
				case "i":
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					GameLogic.printGameInfo(discardPile);
					break;

				case "clear":
				case "cls":
				case "c": // clear cards
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					UserInput.clearScreen();
					break;

				case "q": // quit
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					// TODO: are you sure?
					return;

				default:
					if (!userInput[0].equals(""))
						UserInput.printTextWithColor("unknown command '" + userInput[0] + "'.\n", UserInput.Color.RED);

			}
		}
	}


	public static void printStartText() {
		UserInput.printTextWithColor("   ..   .  ..   .    ...   ", UserInput.Color.RED);
		UserInput.printTextWithColor("  ...  ..    ....  \n", UserInput.Color.BLUE);

		UserInput.printTextWithColor("   ..   .  ...  .  ..   .  ", UserInput.Color.RED);
		UserInput.printTextWithColor("..     ..     ..   \n", UserInput.Color.BLUE);

		UserInput.printTextWithColor("   ..   .  .. . .  ..   .  ", UserInput.Color.RED);
		UserInput.printTextWithColor("..     ..     ..   \n", UserInput.Color.BLUE);

		UserInput.printTextWithColor("   ..   .  ..  ..  ..   .  ", UserInput.Color.RED);
		UserInput.printTextWithColor("..     ..     ..   \n", UserInput.Color.BLUE);


		UserInput.printTextWithColor("     ...   ..   .    ...     ", UserInput.Color.RED);
		UserInput.printTextWithColor("...  ....  ....  \n", UserInput.Color.BLUE);

		System.out.println();
	}

	// COMMANDS
	public static void printHelp() {
		UserInput.printTextWithColor("Commands:\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  l", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("           list cards\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  n", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("           list cards with card pos\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  p <pos>", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("     play card\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  i", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("           show information about current game\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  h", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("           show help\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  c", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("           clear the screen\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  q", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("           quit\n\n", UserInput.Color.CYAN);
	}
}

class GameLogic {

	public static void printCurrentCardInfo(DiscardPile discardPile) {
		UserInput.printTextWithColor("the current card is " + discardPile.getCard(discardPile.getSize() - 1).getCardVal() + "\n", UserInput.Color.WHITE);
	}

	public static void printGameInfo(DiscardPile discardPile) {
		printCurrentCardInfo(discardPile);
	}

	// plays selected card(s) (will only play card if card is valid)
	public static void playCard(Hand player, DiscardPile discardPile, String[] userInput) {
			// TODO: change player variable to "hand"
			// TODO: give hands names
		if (UserInput.AssertMinArgC(userInput, 2) != 0) return;

		int[] cardIndex = new int[userInput.length - 1];

		int i = 1;
		try {
			while (i < userInput.length) {
				cardIndex[i - 1] = Integer.parseInt(userInput[i]) - 1; // because printed val starts at 1
				i++;
			}

		} catch(NumberFormatException e) {
			UserInput.printTextWithColor("'" + userInput[i] + "' is not a number\n", UserInput.Color.RED);
			return;
		}

		Vector<Integer> vec = new Vector<>();
		
		for (int j = 0; j < i - 1; j++) {
			int sub = 0;
			for (int k = 0; k < vec.size(); k++) {
				if (vec.get(k) < cardIndex[j]) sub ++;
			}

			if (j > 0) {
				Card lastCardPlayed = discardPile.getCard(discardPile.getSize() - 1);
				Card currCardPlaying = player.getCard(cardIndex[j] - sub);

				boolean numsSame = lastCardPlayed.getCardNumber() == currCardPlaying.getCardNumber();
				boolean typesSame = lastCardPlayed.getCardSpecialType() == currCardPlaying.getCardSpecialType();

				if (!(numsSame && typesSame)) {
					UserInput.printTextWithColor("cannot play " + player.getCard(cardIndex[j] - sub).getCardVal() + "\n", UserInput.Color.RED);
					break;
				}
			}


			switch (player.playCard(cardIndex[j] - sub)) {
				case 1:
					UserInput.printTextWithColor("cannot play " + player.getCard(cardIndex[j] - sub).getCardVal() + "\n", UserInput.Color.RED);
					break;
				case 2:
					UserInput.printTextWithColor("Invalid card number '" + userInput[j + 1] + "'" + "\n", UserInput.Color.RED);
					break;
				default:
					vec.add(cardIndex[j]);
					break;
			}
		}
	}

}


