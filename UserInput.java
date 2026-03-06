import java.util.Scanner;

public class UserInput {

	static private Scanner s = new Scanner(System.in);

	public enum Color {
		RED,
		BLUE,
		CYAN,
		GREEN,
		BLACK,
		MAGENTA,
		WHITE,
		YELLOW,
	}

	public static void changeColor(Color color) {
		switch (color) {
			case BLACK:
				System.out.print((char) 27 + "[30m");
				break;
			case RED:
				System.out.print((char) 27 + "[31m");
				break;
			case GREEN:
				System.out.print((char) 27 + "[32m");
				break;
			case YELLOW:
				System.out.print((char) 27 + "[33m");
				break;
			case BLUE:
				System.out.print((char) 27 + "[34m");
				break;
			case MAGENTA:
				System.out.print((char) 27 + "[35m");
				break;
			case CYAN:
				System.out.print((char) 27 + "[36m");
				break;
			case WHITE:
				System.out.print((char) 27 + "[37m");
				break;
		}
	}


	public static void printTextWithColor(String text, Color color) {
		changeColor(color);
		System.out.print(text);
	}

	public static String getUserInput(String prompt) {
		changeColor(Color.GREEN);
		System.out.print(prompt);
		String input = s.nextLine();
		return input.strip().toLowerCase();
	}

	public static void clearScreen() {
		System.out.print("\033[H\033[2J"); // TODO: find a better way to clear the screen
		System.out.flush();
	}

	// will print error if the arc count given is not n
	// returns the difference in the ammount of arguments needed or not needed
	public static int AssertArgC(String[] userInput, int n) {
		// returns diff between args given and n
		int diff = userInput.length - n;

		if (diff > 0) {
			printTextWithColor("Too many args given for " + userInput[0] + "\n", Color.RED);
		} else if (diff < 0) {
			printTextWithColor("not enough args for " + userInput[0] + "\n", Color.RED);
		}

		return diff;
	}
	
	// will print error if the arc count given is less than n
	// if argc is less than n than will return args needed
	public static int AssertMinArgC(String[] userInput, int n) {
		int diff = userInput.length - n;
		if (diff < 0) {
			printTextWithColor("not enough args for " + userInput[0] + "\n", Color.RED);
			return diff * -1;
		}

		return 0;
	}


}
