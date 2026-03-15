import java.util.Vector;

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

	// 
	// public static void handleCardSideEffect(Card card, ) {
	// 	switch (card.getCardSpecialType()) {
	// 		case Card.SpecialType.SKIP:
	// 			break;
	// 		case Card.SpecialType.REVERSE:
	// 			break;
	// 		case Card.SpecialType.DRAW2:
	// 			break;
	// 		case Card.SpecialType.DRAW4:
	// 			break;
	// 		case Card.SpecialType.NONE:
	// 			break;
	// 	}
	// }
}
