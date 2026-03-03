import java.util.Scanner;

// TODO: make a card container interface or class

public class Main {
	public static void main(String[] args) {
		// String input = getUserInput("> ");
		// System.out.println("you said: " + input);

		Deck deck = new Deck();
		deck.printContents();

		System.out.println("\n");

		Hand player = new Hand(deck);
		deck.printContents();

	}

	public static String getUserInput(String prompt) {
		Scanner scanner = new Scanner(System.in);
		System.out.print(prompt);
		String input = scanner.nextLine();
		scanner.close();
		return input;
	}
}
