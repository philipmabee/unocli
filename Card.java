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

	/** 
	 * returns the cards value as a string<br>
	 * ex. G1, B4, Y+2, W, W+4, RR
	*/
	public String getCardVal() {
		return cardVal;
	}


	/** 
	* prints the card with colored text rather than a char indicating the color
	*/
	public void printCardWithColor() {
		Card.Color cardColor = getCardColor();
		byte cardNum = getCardNumber();
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

		switch (getCardSpecialType()) {
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






	/**
	 * @return the card color in the Color enum
	 * @throws Error if the cards value is invalid
	*/
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

	/** 
	 * @return the current card number.<br>
	 * if the card does not have a number than it will return -1
	*/
	public byte getCardNumber() {
		if ((int) cardVal.charAt(1) < (int) '0' || (int) cardVal.charAt(1) > '9') {
			return -1;
		}

		return (byte) ((int) cardVal.charAt(1) - (int) '0');
	}

	/** 
	 * @return the current card special type<br>
	 * if card does not have a special type than will return NONE 
	*/ 
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

	public void changeCardColor(Color color, Hand hand) {
		StringBuilder s = new StringBuilder(cardVal);
		String colorName = "";

		switch(color) {
			case Color.BLUE:
				s.setCharAt(0, 'b');
				cardVal = s.toString();
				colorName = "blue";
				break;
			case Color.YELLOW:
				s.setCharAt(0, 'y');
				cardVal = s.toString();
				colorName = "yellow";
				break;
			case Color.GREEN:
				s.setCharAt(0, 'g');
				cardVal = s.toString();
				colorName = "green";
				break;
			case Color.RED:
				s.setCharAt(0, 'r');
				cardVal = s.toString();
				colorName = "red";
				break;
			case Color.WILD:
				s.setCharAt(0, 'w');
				cardVal = s.toString();
				colorName = "wild";
				break;
		}

		UserInput.printTextWithColor(hand.getName() + " changed the color to " + colorName + "\n", UserInput.Color.WHITE);
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
