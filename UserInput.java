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
}
