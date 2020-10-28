import java.util.Scanner;

/**
 * Driver class runs the program. Used for testing.
 * 
 * @author Jacob Strokus
 *
 */
public class Driver {

	/**
	 * Controls the flow of the program.
	 * 
	 * @param args Command-line arguments supplied as an Array of String Objects.
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Please provide the file name");
			System.exit(0);
		}
		CovidHealthBuilder build = new CovidHealthBuilder(args[0]);
		String response;
		do {
			build.decide();
			System.out.print("Try again? ");
			response = getUserResponse();
		} while (response.toLowerCase().equals("yes"));
		System.out.println("Have a nice day!");
	} // end main

	/**
	 * Method to get the user response.
	 * 
	 * @return String literal for user input.
	 */
	public static String getUserResponse() {
		Scanner keyboard = new Scanner(System.in);
		String response = keyboard.nextLine();

		return response;
	} // endgetUserResponse

	/**
	 * Method to check if the user input yes.
	 * 
	 * @return boolean whether user entered yes.
	 */
	public static boolean isUserResponseYes() {
		String answer = getUserResponse();
		if (answer.toLowerCase().equals("yes"))
			return true;
		else
			return false;
	} // end isUserResponseYes
}
