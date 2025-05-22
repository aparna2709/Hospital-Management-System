package hms.authentication;
import java.util.*;
import java.util.regex.*;

public class Validations {

	public static final String ANSI_RED = "\u001B[31m";
//	public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLACK = "\u001B[30m";


	public static String validateGender(Scanner scanner) {
		String gender = "";
		while (true) {
			System.out.print(ANSI_BLACK);//Black color
			System.out.print("Enter Gender (M/F/O): ");
			gender = scanner.nextLine().toUpperCase();
			if (gender.equals("M") || gender.equals("F") || gender.equals("O")) {
				return gender; // Return the gender if it's valid
			} else {
				System.out.print(ANSI_RED);//Red color
				System.out.println("Invalid input. Please enter 'M' for Male, 'F' for Female, or 'O' for Other.");
			}
		}
	}

	public static String validatePhoneNumber(Scanner scanner) {
		boolean validPhoneNumber = false;
		String phoneNumber = "";

		while (!validPhoneNumber) {
			System.out.print(ANSI_BLACK);//Black color
			System.out.print("Enter Phone number : ");
			phoneNumber = scanner.next();
			String phoneNumberRegex = "^[0-9]{10}$"; // 10-digit number
			Pattern pattern = Pattern.compile(phoneNumberRegex);
			Matcher matcher = pattern.matcher(phoneNumber);

			if (!matcher.matches()) {
				System.out.print(ANSI_RED);//Red color
				System.out.println("Invalid phone number format. Please enter a 10-digit number.");
				continue; // Ask user to enter phone number again
			}

			validPhoneNumber = true; // Set to true when valid phone number is entered
		}

		return phoneNumber; // Return the validated phone number after the while loop
	}


	public static boolean isValidEmail(String email) {
		// Regular expression for email validation
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}


	public static String validateBloodGroup(Scanner scanner) {
		String bloodGroup = null; // or bloodGroup = "";

		while (true) {
			System.out.print(ANSI_BLACK);//Black color
			System.out.println("Enter Blood Group (A+,A-,B+,B-,AB+,AB-,O+,O-): ");
			bloodGroup = scanner.nextLine();

			// Define a regular expression to validate the blood group format
			String bloodGroupRegex = "^(A|B|AB|O)[+-]$"; // 2 or 3 characters	        
			// Compile the regular expression into a Pattern object
			Pattern pattern = Pattern.compile(bloodGroupRegex);

			// Create a matcher object that will match the bloodGroup string against the pattern.
			Matcher matcher = pattern.matcher(bloodGroup);

			// Check if the bloodGroup string does not match the pattern.
			if (!matcher.matches()) {
				System.out.print(ANSI_RED);//Red color
				System.out.println("Invalid blood type format.");
				continue; // Ask user to enter a valid blood type again
			}

			break; // exit the loop if the blood group is valid
		}

		return bloodGroup; // Return the validated blood group after the loop.
	}

	public static String validateUserPassword(Scanner sc) {
		boolean validUserPassword = false;
		String userPassword = "";

		while (!validUserPassword) {
			System.out.print(ANSI_BLACK);//Black color
			System.out.print("Enter New Password : ");
			userPassword = sc.nextLine();
			//			String userPasswordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
			String userPasswordRegex =  "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%!^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";;
			Pattern pattern = Pattern.compile(userPasswordRegex);
			Matcher matcher = pattern.matcher(userPassword);

			if (!matcher.matches()) {
				System.out.print(ANSI_RED);//Red color
				System.out.println("Invalid password format. Password should contain at least 8 characters, one special character, one number, and one uppercase letter.");
				continue; // Ask user to enter password again
			}

			validUserPassword = true; // Set to true when valid password is entered
		}

		return userPassword; // Return the validated password after the while loop
	}

	public static int validateIntegerInput(String input) {
		int num;
		while (true) {

			if (input.matches("^-?\\d+$")) { // Regular expression to check if it's an integer
				num = Integer.parseInt(input);
				break; // Exit the loop if a valid integer is provided
			} else {
				System.out.print(ANSI_RED);//Red color
				System.out.println("Invalid input. Please enter a valid integer .");
				System.out.print(ANSI_BLACK);//Black color
				System.out.print("Re-enter : ");
				input = new Scanner(System.in).nextLine();               
			}
		}
		return num;
	}
}
