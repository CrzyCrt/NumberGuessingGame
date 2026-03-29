import java.util.Scanner;

/**
 * The numberGuessingGame class represents a command-line game in which the computer
 * generates a random number and the user must guess the number within a limited number
 * of attempts. The game ends when the user correctly guesses the number or uses all
 * allocated attempts. When the game ends, the user is given their total number of
 * attempts used, the number and percentage of games won, the number and percentage 
 * of games lost, and an option to play again.
 */

public class NumberGuessingGame {

	static Scanner s = new Scanner(System.in);
	static int gamesWon = 0;
	static int gamesLost = 0;
	static int totalGames = 0;
	static double winRate;
	static double loseRate;
	static int userGuess;
	static final int maxAttempts = 5;
	static int attempts = 0;
	static int remainingAttempts = maxAttempts;
	static boolean playing = false;

	/**
	 * Main method: entry point of the program.
	 * It starts the game and calls all relevant methods to play.
	 * 
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {

		boolean playMore = true;

		while (playMore) {
			try {
				int computerNumber = 0;
				attempts = 0; // reset attempt counter for new game
				remainingAttempts = maxAttempts; // reset remaining attempts for new game

				System.out.println("Welcome to the Number Guessing Game\n"
						+ "-----------------------------------\n"
						+ "Rules: The computer will generate a random number,\n"
						+ "       and you will have 5 attempts to guess the\n"
						+ "       number. The computer will tell you if you are\n"
						+ "       high or low. To guess, just type your guess and\n"
						+ "       press the ENTER/RETURN key.\n");
				System.out.println("Please select your preferred difficulty.\n"
						+ "           1. Easy (1 - 25)\n"
						+ "           2. Medium (1 - 50)\n"
						+ "           3. Hard (1 - 100)");
				System.out.println("Enter your selection (number): ");
				int choice = s.nextInt();
				if (choice == 1) {
					computerNumber = (int)(Math.random() * 25) + 1; // random int 1-25
				} else if (choice == 2) {
					computerNumber = (int)(Math.random() * 50) + 1; // random int 1-50
				} else if ( choice == 3) {
					computerNumber = (int)(Math.random() * 100) + 1; // random int 1-100
				} else {
					System.out.println("Invalid selection. Please try again.");
					continue;
				}
				playGame(computerNumber);
				playMore = playAgain();
			} catch (Exception e) {
				System.out.print("Something went wrong. Please enter only numbers.");
				s.nextLine(); // clear leftover newline from previous nextInt()
			}
		}
		s.close();
		System.out.println("Thanks for playing. Goodbye!");
	}

	/**
	 * Allows player to choose whether to play another game.
	 * 
	 * @return true if player enters "Y" or "y",
	 * 			false if player enters "N" or "n"
	 */
	public static boolean playAgain() {
		s.nextLine();

		String input;
		do {
			System.out.println("Do you want to play again? Y/N");
			input = s.nextLine();
		} while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N"));

		return input.equalsIgnoreCase("Y");
	}

	/**
	 * Allows player to play one game at the selected difficulty
	 * 
	 * @param computerNumber the randomly generated number that the player
	 * 			is trying to guess
	 */
	public static void playGame(int computerNumber) {
		playing = true;
		while(playing) {

			if (remainingAttempts == 0) {
				// no attempts left -> player loses
				System.out.println("Game Over! The number was: " + computerNumber + ".");
				totalGames++;
				gamesLost++;
				break;
			}
			System.out.println("You have " + remainingAttempts + 
					" attempts remaining. Please enter your guess: ");
			userGuess = s.nextInt();
			attempts++;
			remainingAttempts--;

			if (userGuess == computerNumber) {
				System.out.println("Correct. You win!\n"
						+ "Attempts Used: " + attempts + "\n");
				totalGames++;
				gamesWon++;
				playing = false;
			} else if (userGuess < computerNumber) {
				System.out.println("Too low. Try again.");
			} else {
				System.out.println("Too high. Try again.");
			}

		}

		if (totalGames > 0) { // prevent division by zero
			winRate = ((double)gamesWon/totalGames) * 100;
			loseRate = ((double)gamesLost/totalGames) * 100;
		}

		System.out.println("You've played " + totalGames + " game(s).");
		System.out.printf("Games Won: %d (%.2f%%)%n", gamesWon, winRate);
		System.out.printf("Games Lost: %d (%.2f%%)%n", gamesLost, loseRate);
	}
}
