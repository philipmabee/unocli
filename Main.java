import java.util.Scanner;

// TODO: make a card container interface or class

public class Main {
	public static void main(String[] args) {
		UserInput.clearScreen();
		printStartText();

		UserInput.changeColor(UserInput.Color.CYAN);
		System.out.println("Welcome to unocli!");
		UserInput.printTextWithColor("press h for help\n" , UserInput.Color.WHITE);


		Deck deck = new Deck();
		DiscardPile discardPile = new DiscardPile(deck);

		Hand player = new Hand(deck, discardPile);

		// GameLogic.printCurrentCardInfo(discardPile);

		String userInput;
		while (true) {
			userInput = UserInput.getUserInput("> ");

			switch (userInput) {
				case "help":
				case "h": // print help
					printHelp();
					break;

				case "list":
				case "l": // list cards
					player.printContents();
					break;

				case "info":
				case "i":
					GameLogic.printGameInfo(discardPile);
					break;


				case "clear":
				case "cls":
				case "c": // clear cards
					UserInput.clearScreen();
					break;
				case "q": // quit
					// TODO: are you sure?
					return;
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
		UserInput.printTextWithColor("  list cards\n", UserInput.Color.CYAN);


		UserInput.printTextWithColor("  i", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("  show information about current game\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  h", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("  show help\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  c", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("  clear the screen\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  q", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("  quit\n\n", UserInput.Color.CYAN);
	}
}

class GameLogic {

	public static void printCurrentCardInfo(DiscardPile discardPile) {
		UserInput.printTextWithColor("the current card is " + discardPile.getFirstCard().getCardVal() + "\n", UserInput.Color.WHITE);
	}

	public static void printGameInfo(DiscardPile discardPile) {
		printCurrentCardInfo(discardPile);
	}

}


