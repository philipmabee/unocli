import java.util.Random;
import java.util.Vector;
// TODO: add drawing cards

public class Main {
	public static void main(String[] args) {
		UserInput.clearScreen();
		printStartText();

		UserInput.printTextWithColor("Welcome to unocli!\n", UserInput.Color.CYAN);
		// UserInput.printTextWithColor("press h for help\n\n" , UserInput.Color.MAGENTA);

		int numOfBots = getNumOfBots();

		Deck deck = new Deck();
		DiscardPile discardPile = new DiscardPile(deck);

		Hand[] hands = new Hand[numOfBots + 1];
		Hand player = new Hand("You", deck, discardPile);

		hands[0] = player;
		for (int i = 1; i <= numOfBots; i++) {
			hands[i] = new Hand("Bot" + i, deck, discardPile);
		}

		UserInput.clearScreen();
		UserInput.printTextWithColor("press h for help\n\n" , UserInput.Color.MAGENTA);
		GameLogic.printCurrentCardInfo(discardPile);

		int currHandTurn = 0;
		while (true) {
			// System.out.println(currHandTurn);
			if (hands[currHandTurn].getName().equals("You")) {
				handlePlayersTurn(player, discardPile, hands);
			} else {
				handleBotsTurn(hands[currHandTurn]);
				// TODO: handle reverse cards
			}

			if (currHandTurn == hands.length - 1) {
				currHandTurn = 0;
				continue;
			}
			currHandTurn ++;

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

	public static void printHelp() {
		UserInput.printTextWithColor("Commands (only work in game): \n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  l", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("           list cards\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  n", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("           list cards with card pos\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  p <pos>", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("     play card\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  d", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("           draw card\n", UserInput.Color.CYAN);

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

	public static void handlePlayersTurn(Hand player, DiscardPile discardPile, Hand[] hands) {
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
					if (GameLogic.playCard(player, discardPile, userInput) != 0) return;
					break;

				case "draw":
				case "d":
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					player.drawFromDeck();
					UserInput.printTextWithColor(player.getName() + " drew a card\n", UserInput.Color.WHITE);

					// if (player.getCard(player.getSize() - 1)) { // if canPlayCard then ask if want to play that card
					//
					// }
					return;

				case "info":
				case "i":
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					GameLogic.printGameInfo(discardPile, hands);
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
					System.exit(0);
					// return;

				case "debug":
					UserInput.changeColor(UserInput.Color.GREEN);
					for (int i = 0; i < hands.length; i++) {
						UserInput.printTextWithColor(hands[i].getName() + ": ", UserInput.Color.GREEN);
						hands[i].printContents();
					}
					break;


				default:
					if (!userInput[0].equals(""))
						UserInput.printTextWithColor("unknown command '" + userInput[0] + "'.\n", UserInput.Color.RED);

			}
		}
	}

	public static void handleBotsTurn(Hand bot) {
		Vector<Integer> playableCards = new Vector<>();

		// add playable card index
		for (int i = 0; i < bot.getSize(); i++) {
			if (bot.canPlayCard(i))
				playableCards.add(i);
		}

		// draw card if no playable cards
		if (playableCards.size() == 0) {
			bot.drawFromDeck();
			UserInput.printTextWithColor(bot.getName() + " drew a card\n", UserInput.Color.WHITE);
			return;
		}

		// playable card size == 1
		else if (playableCards.size() == 1) {
			bot.playCard(playableCards.get(0));
			return;
		}

		// play one of multiple cards
		Random rand = new Random();
		int randNum = rand.nextInt(playableCards.size() - 1);

		bot.playCard(playableCards.get(randNum));
	}

	public static int getNumOfBots() {
		UserInput.printTextWithColor("How many bots would you like to play against? (max of 4)\n", UserInput.Color.WHITE);
		int numOfBots;
		while (true) {
			try {
				numOfBots = Integer.parseInt(UserInput.getUserInput("> "));
			} catch (NumberFormatException e) {
				UserInput.printTextWithColor("invalid input.\n", UserInput.Color.RED);
				continue;
			}

			if (numOfBots < 1 || numOfBots > 4) {
				UserInput.printTextWithColor("invalid input.\n", UserInput.Color.RED);
				continue;
			}

			return numOfBots;
		}
	}



}

class GameLogic {

	public static void printCurrentCardInfo(DiscardPile discardPile) {
		UserInput.printTextWithColor("the current card is ", UserInput.Color.WHITE);
		discardPile.getCard(discardPile.getSize() - 1).printCardWithColor();
		System.out.println();
	}

	public static void printNumOfCardsInHands(Hand[] hands) {
		UserInput.printTextWithColor("Cards in hand:\n", UserInput.Color.WHITE);
		for(int i = 0; i < hands.length; i++) {
			UserInput.printTextWithColor("  " + String.format("%-7s",hands[i].getName() + ": ") + hands[i].getSize() +"\n", UserInput.Color.WHITE);
		}
	}

	public static void printGameInfo(DiscardPile discardPile, Hand hands[]) {
		printCurrentCardInfo(discardPile);
		printNumOfCardsInHands(hands);
	}

	// plays selected card(s) (will only play card if card is valid)
	// returns the ammount of cards successfully played
	public static int playCard(Hand player, DiscardPile discardPile, String[] userInput) {
			// TODO: change player variable to "hand"
		if (UserInput.AssertMinArgC(userInput, 2) != 0) return 0;

		int[] cardIndex = new int[userInput.length - 1];

		int i = 1;
		try {
			while (i < userInput.length) {
				cardIndex[i - 1] = Integer.parseInt(userInput[i]) - 1; // because printed val starts at 1
				i++;
			}

		} catch(NumberFormatException e) {
			UserInput.printTextWithColor("'" + userInput[i] + "' is not a number\n", UserInput.Color.RED);
			return 0;
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
		return vec.size();
	}

}


