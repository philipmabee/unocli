import java.util.Random;
import java.util.Vector;

class GameLogic {
	private static int currHandTrunIndex = 0;
	private static boolean handTurnDirection = true; // true incresed currHandTurnIndex. false does the opposite
	private static int cardDrawCounter = 0; // cards the hand needs to draw

	private static Deck deck;
	private static DiscardPile discardPile;
	private static Hand player;
	private static Hand[] hands;


	/**
	 * creates the game deck, game discard pile, player, and the bots.<br>
	 * This must bw called before starting the game.
	 */
	public static void setupGame(int numOfBots) {
		deck = new Deck();
		discardPile = new DiscardPile(deck);
		hands = new Hand[numOfBots + 1];

		player = new Hand("You", deck, discardPile);

		hands[0] = player;
		for (int i = 1; i <= numOfBots; i++) {
			hands[i] = new Hand("Bot" + i, deck, discardPile);
		}
	}
	
	public static int getCardDrawCounter() {
		return cardDrawCounter;
	}

	public static Deck getDeck() {
		if (deck == null) {
			throw new Error("Must call setupGame to access the gameDeck");
		}
		return deck;
	}

	public static DiscardPile getDiscardPile() {
		if (discardPile == null) {
			throw new Error("Must call setupGame to access the gameDiscardPile");
		}
		return discardPile;
	}

	public static Hand[] getHands() {
		if (hands == null) {
			throw new Error("Must call setupGame to access the hands");
		}
		return hands;
	}

	public static Hand getPlayer() {
		if (player == null) {
			throw new Error("Must call setupGame to access the player");
		}
		return player;
	}

	public static int getNumOfHands() {
		if (hands == null) {
			throw new Error("Must call setupGame to get the num of hands");
		}
		return hands.length;
	}

	public static void printCurrentCardInfo() {
		UserInput.printTextWithColor("the current card is ", UserInput.Color.WHITE);
		getDiscardPile().getLastCard().printCardWithColor();
		System.out.println();
	}

	public static void printNumOfCardsInHands() {
		UserInput.printTextWithColor("Cards in hand:\n", UserInput.Color.WHITE);
		for(int i = 0; i < getNumOfHands(); i++) {
			UserInput.printTextWithColor("  " + String.format("%-7s",getHands()[i].getName() + ": ") + getHands()[i].getSize() +"\n", UserInput.Color.WHITE);
		}
	}

	public static void printGameInfo() {
		printCurrentCardInfo();
		printNumOfCardsInHands();
	}

	// plays selected card(s) (will only play card if card is valid)
	// returns the ammount of cards successfully played
	// TODO: replace variables with getters here
	public static int playCard(String[] userInput) {
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


			switch (player.playCard(cardIndex[j] - sub, getNumOfHands())) {
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

	public static int getCurrHandTurn() {
		return currHandTrunIndex;
	}

	public static int getNextHandTurn() {
		if (handTurnDirection) {
			if (currHandTrunIndex == getNumOfHands() - 1) {
				return 0;
			}
			return currHandTrunIndex + 1;
		}
		else {
			if (currHandTrunIndex == 0) {
				return getNumOfHands() - 1;
			}
			return currHandTrunIndex - 1;
		}
	}

	public static String getCurrHandTurnName() {
		return getHands()[currHandTrunIndex].getName();
	}

	/**
	 * changes the current turn to the next one depending on direction going.
	*/
	public static void changeHandTurn() {
		if (handTurnDirection) {
			if (currHandTrunIndex == getNumOfHands() - 1) {
				currHandTrunIndex = 0;
				return;
			}
			currHandTrunIndex ++;
		}
		else {
			if (currHandTrunIndex == 0) {
				currHandTrunIndex = getNumOfHands() - 1;
				return;
			}
			currHandTrunIndex --;
		}
	}

	public static void addToCardDrawCounter(int x) {
		if (x < 1) {
			throw new Error("the value passed in 'addToCardDrawCounter' must be greater than 0");
		}
		cardDrawCounter += x;
	}

	public static void resetCardDrawCounter() {
		cardDrawCounter = 0;
	}

	// TODO: replace variables with getters here
	public static void handleCardSideEffect(Card card) {
		Hand currHand = getHands()[getCurrHandTurn()];

		switch (card.getCardSpecialType()) {
			case Card.SpecialType.SKIP:
				changeHandTurn();
				break;
			case Card.SpecialType.REVERSE:
				handTurnDirection = !handTurnDirection;
				break;
			case Card.SpecialType.DRAW2:
				cardDrawCounter += 2;
				break;
			case Card.SpecialType.DRAW4:
				cardDrawCounter += 4;
				break;
			case Card.SpecialType.NONE:
				break;
		}

		switch (card.getCardColor()) {
			case Card.Color.WILD:
				if (currHand.getName() == "You") {
					Card.Color newColor = getNewColorFromPlayer();
					discardPile.getCard(discardPile.getSize() - 1).changeCardColor(newColor, currHand);
				}
				else {
					Random rand = new Random();
					int randNum = rand.nextInt(1, 5);
					switch (randNum) { // TODO: make a 50/50 chanse of making color the most color in hand
						case 1: {
							Card.Color newColor = Card.Color.BLUE;
							discardPile.getCard(discardPile.getSize() - 1).changeCardColor(newColor, currHand);
							break;
						}
						case 2: {
							Card.Color newColor = Card.Color.YELLOW;
							discardPile.getCard(discardPile.getSize() - 1).changeCardColor(newColor, currHand);
							break;
						}
						case 3: {
							Card.Color newColor = Card.Color.GREEN;
							discardPile.getCard(discardPile.getSize() - 1).changeCardColor(newColor, currHand);
							break;
						}
						case 4: {
							Card.Color newColor = Card.Color.RED;
							discardPile.getCard(discardPile.getSize() - 1).changeCardColor(newColor, currHand);
							break;
						}

					}
				}
				break;
			default:
				break;
		}
	}

	private static Card.Color getNewColorFromPlayer() {
		UserInput.printTextWithColor("What would you like the color to be? (r, b, g, y)\n", UserInput.Color.WHITE);
		while (true) {
			String userInput = UserInput.getUserInput("> ");
			switch (userInput) {
				case "r":
					return Card.Color.RED;
				case "b":
					return Card.Color.BLUE;
				case "g":
					return Card.Color.GREEN;
				case "y":
					return Card.Color.YELLOW;
				default:
					UserInput.printTextWithColor("Invalid User Input\n", UserInput.Color.RED);
					break;
			}
		}
	}

	private GameLogic() {}
}
