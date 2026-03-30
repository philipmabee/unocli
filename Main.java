import java.util.Random;
import java.util.Vector;

public class Main {
	public static void main(String[] args) {
		UserInput.clearScreen();
		printStartText();

		UserInput.printTextWithColor("Welcome to unocli!\n", UserInput.Color.CYAN);

		int numOfBots = getNumOfBots();
		GameLogic.setupGame(numOfBots);

		UserInput.clearScreen();
		UserInput.printTextWithColor("press h for help\n\n" , UserInput.Color.MAGENTA);
		GameLogic.printCurrentCardInfo();

		while (true) {
			if (GameLogic.getCurrHandTurnName().equals("You")) {
				handlePlayersTurn();
			} else {
				handleBotsTurn(GameLogic.getHands()[GameLogic.getCurrHandTurn()], GameLogic.getHands().length);
			}

			GameLogic.changeHandTurn();
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
		UserInput.printTextWithColor("How To Play:\n", UserInput.Color.CYAN);
		UserInput.printTextWithColor("  The goal of this game is to remove all the cards in your hand.\n", UserInput.Color.WHITE);
		UserInput.printTextWithColor("  You can play a card if the card number or color matches the previously played card.\n\n", UserInput.Color.WHITE);
		UserInput.printTextWithColor("Cards:\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  '", UserInput.Color.WHITE);
		UserInput.printTextWithColor("w", UserInput.Color.GREEN);
		UserInput.printTextWithColor("'  can be played at any time. When played you can change what the color is\n", UserInput.Color.WHITE);

		UserInput.printTextWithColor("  '", UserInput.Color.WHITE);
		UserInput.printTextWithColor("S", UserInput.Color.GREEN);
		UserInput.printTextWithColor("'  will skip the next players turn\n", UserInput.Color.WHITE);

		UserInput.printTextWithColor("  '", UserInput.Color.WHITE);
		UserInput.printTextWithColor("R", UserInput.Color.GREEN);
		UserInput.printTextWithColor("'  will reverse the order of who gets to play next\n", UserInput.Color.WHITE);

		UserInput.printTextWithColor("  '", UserInput.Color.WHITE);
		UserInput.printTextWithColor("+2", UserInput.Color.GREEN);
		UserInput.printTextWithColor("' will make the next player draw 2 cards\n", UserInput.Color.WHITE);

		UserInput.printTextWithColor("  '", UserInput.Color.WHITE);
		UserInput.printTextWithColor("+4", UserInput.Color.GREEN);
		UserInput.printTextWithColor("' can be played at any time. Will make the next player draw 4 cards. You also change what the color is\n\n", UserInput.Color.WHITE);

		UserInput.printTextWithColor("Commands: \n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  l", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("           list cards\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  n", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("           list cards with card pos\n", UserInput.Color.CYAN);

		UserInput.printTextWithColor("  p <pos>", UserInput.Color.GREEN);
		UserInput.printTextWithColor(":", UserInput.Color.WHITE);
		UserInput.printTextWithColor("     play card (you can play multiple cards)\n", UserInput.Color.CYAN);

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

	public static void handlePlayersTurn() {
		String[] userInput;
		while (true) {

			if (GameLogic.getCardDrawCounter() > 0) {
				System.out.println("yoooooo");
				handlePlayerDrawCounterTurn();
				return;
			}



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
					GameLogic.getPlayer().printContents();
					break;

				case "n":
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					GameLogic.getPlayer().printContentsWithIndex();
					break;

				case "play":
				case "p":
					if (GameLogic.playCard(userInput) != 0) return;
					break;

				case "draw":
				case "d":
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					GameLogic.getPlayer().drawFromDeck();
					UserInput.printTextWithColor(GameLogic.getPlayer().getName() + " drew a card\n", UserInput.Color.WHITE);

					// if (player.getCard(player.getSize() - 1)) { // if canPlayCard then ask if want to play that card
					//
					// }
					return;

				case "info":
				case "i":
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					GameLogic.printGameInfo();
					break;

				case "clear":
				case "cls":
				case "c": // clear cards
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					UserInput.clearScreen();
					break;

				case "quit":
				case "exit":
				case "q": // quit
					if (UserInput.AssertArgC(userInput, 1) != 0) break;
					System.exit(0);
					// return;

				case "debug":
					UserInput.changeColor(UserInput.Color.GREEN);
					for (int i = 0; i < GameLogic.getHands().length; i++) {
						UserInput.printTextWithColor(GameLogic.getHands()[i].getName() + ": ", UserInput.Color.GREEN);
						GameLogic.getHands()[i].printContents();
					}
					break;


				default:
					if (!userInput[0].equals(""))
						UserInput.printTextWithColor("unknown command '" + userInput[0] + "'.\n", UserInput.Color.RED);

			}
		}
	}

	public static void handleBotsTurn(Hand bot, int numOfHands) {
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
			bot.playCard(playableCards.get(0), numOfHands);
			return;
		}

		// play one of multiple cards
		Random rand = new Random();
		int randNum = rand.nextInt(playableCards.size() - 1);

		bot.playCard(playableCards.get(randNum), numOfHands);
	}

	public static void handlePlayerDrawCounterTurn() {
		Card.SpecialType drawCardType;
		if (GameLogic.getDiscardPile().getCard(GameLogic.getDiscardPile().getSize() - 1).getCardSpecialType() == Card.SpecialType.DRAW2)
			drawCardType = Card.SpecialType.DRAW2;
		else
			drawCardType = Card.SpecialType.DRAW4;

		UserInput.printTextWithColor("press d to draw " + GameLogic.getCardDrawCounter() + " cards or play another +" + 
				((drawCardType == Card.SpecialType.DRAW2) ? "2" : "4") + " card with p\n",
				UserInput.Color.WHITE);


		while (true) {

			String[] userInput = UserInput.getUserInput("> ").split(" ");

			switch (userInput[0]) {
				case "d":
					if (UserInput.AssertArgC(userInput, 1) != 0) continue;
					for (int i = 0; i < GameLogic.getCardDrawCounter(); i++) {
						if (UserInput.AssertArgC(userInput, 1) != 0) break;
						GameLogic.getPlayer().drawFromDeck();
						UserInput.printTextWithColor(GameLogic.getPlayer().getName() + " drew a card\n", UserInput.Color.WHITE);
					}

					GameLogic.resetCardDrawCounter();
					return;

				case "p": {
					if (UserInput.AssertMinArgC(userInput, 2) != 0) continue;
					int[] cardIndex = new int[userInput.length - 1];

					int i = 1;
					try {
						while (i < userInput.length) {
							cardIndex[i - 1] = Integer.parseInt(userInput[i]) - 1; // because printed val starts at 1
							i++;
						}

					} catch(NumberFormatException e) {
						UserInput.printTextWithColor("'" + userInput[i] + "' is not a number\n", UserInput.Color.RED);
						continue;
					}

					Vector<Integer> vec = new Vector<>();

					for (int j = 0; j < i - 1; j++) {
						int sub = 0;
						for (int k = 0; k < vec.size(); k++) {
							if (vec.get(k) < cardIndex[j]) sub ++;
						}

						if (j > 0) {
							Card lastCardPlayed = GameLogic.getDiscardPile().getCard(GameLogic.getDiscardPile().getSize() - 1);
							Card currCardPlaying = GameLogic.getPlayer().getCard(cardIndex[j] - sub);

							boolean typesSame = lastCardPlayed.getCardSpecialType() == currCardPlaying.getCardSpecialType();

							if (!typesSame) {
								UserInput.printTextWithColor("cannot play " + GameLogic.getPlayer().getCard(cardIndex[j] - sub).getCardVal() + "\n", UserInput.Color.RED);
								break;
							}
						}

						switch (GameLogic.getPlayer().playCard(cardIndex[j] - sub, GameLogic.getNumOfHands())) {
							case 1:
								UserInput.printTextWithColor("cannot play " + GameLogic.getPlayer().getCard(cardIndex[j] - sub).getCardVal() + "\n", UserInput.Color.RED);
								break;
							case 2:
								UserInput.printTextWithColor("Invalid card number '" + userInput[j + 1] + "'" + "\n", UserInput.Color.RED);
								break;
							default:
								GameLogic.addToCardDrawCounter((GameLogic.getDiscardPile().getCard(GameLogic.getDiscardPile().getSize() - 1).getCardSpecialType() == Card.SpecialType.DRAW2) ? 2 : 4);
								vec.add(cardIndex[j]);
								break;
						}
					}
					break;
				}
				// }

				case "q":
					 System.exit(0);

				default:
					continue;
			}

		}

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

