public class Card {
	public enum Color {
		BLUE,
		YELLOW,
		GREEN,
		RED,
		WILD,
	}

	public enum SpecialType {
		REVERSE,
		SKIP,
		DRAW2,
		DRAW4,
		NONE, // when getting the card special type
	}

	private String cardVal = "";

	// returns the cards value as a string
	// ex. G1, B4, Y+2, W, W+4, RR
	public String getCardVal() {
		return cardVal;
	}

	// returns the current cards color
	public Color getCardColor() {
		switch (cardVal.charAt(0)) {
			case 'b':
				return Color.BLUE;
			case 'y':
				return Color.YELLOW;
			case 'g':
				return Color.GREEN;
			case 'r':
				return Color.RED;
			case 'w':
				return Color.WILD;
			default:
				throw new Error("Fuck You. (invalid card value)");
		}
	}

	// returns the current card number
	// if card does not have a number that it will return -1
	public byte getCardNumber() {
		if ((int) cardVal.charAt(1) < (int) '0' || (int) cardVal.charAt(1) > '9') {
			return -1;
		}

		return (byte) ((int) cardVal.charAt(1) - (int) '0');
	}

	public SpecialType getCardSpecialType() {
		switch (cardVal.charAt(1)) {
			case '+':
				if (cardVal.charAt(2) == '2') return SpecialType.DRAW2;
				return SpecialType.DRAW4;
			case 'S':
				return SpecialType.SKIP;
			case 'R':
				return SpecialType.REVERSE;
			default:
				return SpecialType.NONE;
		}
	}
		Card (byte num, Color color) {
			switch (color) {
				case BLUE:
					this.cardVal += 'b';
					break;
				case YELLOW:
					this.cardVal += 'y';
					break;
				case GREEN:
					this.cardVal += 'g';
					break;
				case RED:
					this.cardVal += 'r';
					break;
				case WILD:
					this.cardVal += 'w';
					break;
			}
			this.cardVal += num;
		}

		Card (SpecialType specialType, Color color) {
			switch (color) {
				case BLUE:
					this.cardVal += 'b';
					break;
				case YELLOW:
					this.cardVal += 'y';
					break;
				case GREEN:
					this.cardVal += 'g';
					break;
				case RED:
					this.cardVal += 'r';
					break;
				case WILD:
					this.cardVal += 'w';
					break;
			}

			switch (specialType) {
				case SpecialType.REVERSE:
					cardVal += 'R';
					break;
				case SpecialType.SKIP:
					cardVal += 'S';
					break;
				case SpecialType.DRAW2:
					cardVal += "+2";
					break;
				case SpecialType.DRAW4:
					cardVal += "+4";
					break;
			}

		}

		Card () {
			this.cardVal = "w "; // there is a space here because of reasons (DO NOT REMOVE)
		}
}
