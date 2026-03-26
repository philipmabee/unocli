import java.util.Vector;

class GameLogic {

	private static int currHandTrunIndex = 0;
	private static boolean handTurnDirection = true; // true incresed currHandTurnIndex. false does the opposite
	private static int cardDrawCounter = 0; // cards the hand needs to draw

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
	public static int playCard(Hand player, DiscardPile discardPile, String[] userInput, int numOfHands) {
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


			switch (player.playCard(cardIndex[j] - sub, numOfHands)) {
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

	public static int getNextHandTurn(int numOfHands) {
		if (handTurnDirection) {
			if (currHandTrunIndex == numOfHands - 1) {
				return 0;
			}
			return currHandTrunIndex + 1;
		}
		else {
			if (currHandTrunIndex == 0) {
				return numOfHands - 1;
			}
			return currHandTrunIndex - 1;
		}
	}

	public static String getCurrHandTurnName(Hand[] hands) {
		return hands[currHandTrunIndex].getName();
	}

	/**
	 * changes the current turn to the next one depending on direction going.
	*/
	public static void changeHandTurn(int numOfHands) {
		if (handTurnDirection) {
			if (currHandTrunIndex == numOfHands - 1) {
				currHandTrunIndex = 0;
				return;
			}
			currHandTrunIndex ++;
		}
		else {
			if (currHandTrunIndex == 0) {
				currHandTrunIndex = numOfHands - 1;
				return;
			}
			currHandTrunIndex --;
		}
	}
	// TODO: move hands to GameLogic

	public static int getCardDrawCounter() {
		return cardDrawCounter;
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

	public static void handleCardSideEffect(Card card, int numOfHands, Hand hand, DiscardPile discardPile) {
		switch (card.getCardSpecialType()) {
			case Card.SpecialType.SKIP:
				changeHandTurn(numOfHands);
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
				if (hand.getName() == "You") {
					Card.Color newColor = getNewColorFromPlayer();
					discardPile.getCard(discardPile.getSize() - 1).changeCardColor(newColor);
				}
				else {
					// TODO: handle bot wild card
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

}
