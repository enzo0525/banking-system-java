package bank_system;

import java.util.Scanner;

public class MenuHandler {
	public static final int CREATE_ACCOUNT = 1;
	public static final int LOG_IN = 2;
	public static final int EXIT = 3;
	
	private static final int CHECK_BALANCE = 1;
	private static final int DEPOSIT = 2;
	private static final int WITHDRAW = 3;
	private static final int DELETE_ACCOUNT = 4;
	private static final int BACK_MENU = 5;
	
	private final Scanner input = new Scanner(System.in);
	private final DatabaseHandler databaseHandler;
	
	public MenuHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	public int displayMainMenuAndGetChoice() {
		System.out.println("1. Create an account.\n"
				 		 + "2. Log in.\n"
				 		 + "3. Exit.");
		return input.nextInt();
	}
	
	public int displayLoggedInMenu() {
		System.out.println("1. Check balance.\n"
						 + "2. Deposit.\n"
						 + "3. Withdraw.\n"
						 + "4. Delete account.\n"
						 + "5. Back to Menu.");
		
		return input.nextInt();
	}
	
	public void handleCreateAccount() {
		try {
			System.out.print("First name: ");
			String fname = input.next();
			
			System.out.print("Last name: ");
			String lname = input.next();
		
			String gender = genderInput("Gender (M/F): ");
			
			System.out.print("How much you would like to add to your new account? (Type 0 if you would not want to deposit.): ");
			double balance = input.nextDouble();
			
			if (balance >= 0) {
				User user = new User(fname, lname, gender, balance);
				databaseHandler.createUserInDatabase(user);
				System.out.println("\nThanks for creating an account with us " + fname.substring(0,1).toUpperCase() + fname.substring(1) + ".\n"
						 + "Make sure to remember your account number: " + user.getAccountNumber() + ".\n"
						 + "Your deposit of $" + balance + " will be in your account shortly.\n");
			}
			else {
				throw new CustomExceptionMessage("Cannot create account with negative balance.");
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void handleLogIn() {
		try {
			System.out.print("\nWhat's your account number? ");
			String accNumInput = input.next();
			User user = databaseHandler.findUserByAccNum(accNumInput);
			
			if (user != null) {
				logIn(user);
			}
			else {
				System.out.println("Account not found.\n");
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void logIn(User user) {
		System.out.println("Hello " + user.fname + " choose an option below.");
		while (true) {
			int choice = displayLoggedInMenu();
			
			switch (choice) {
			case CHECK_BALANCE:
				System.out.println("Your total balance is $" + user.getBalance());
				break;
			case DEPOSIT:
				System.out.print("How much would you like to deposit? $");
				double depositAmount = input.nextDouble();
				user.deposit(depositAmount);
				break;
			case WITHDRAW:
				System.out.print("How much would you like to withdraw? $");
				double withdrawAmount = input.nextDouble();
				user.withdraw(withdrawAmount);
				break;
			case DELETE_ACCOUNT:
				deleteUserPrompt(user);
				return;
			case BACK_MENU:
				System.out.println("Returning to main menu");
				return;
			default:
				System.out.println("Not an option.");
				break;
			}
		}
	}
	
	private void deleteUserPrompt(User user) {
		System.out.print("Are you sure you want to delete your account? (Y/N): ");
		String choice = input.next().toUpperCase();
		switch (choice) {
		case "Y":
			databaseHandler.deleteUserFromDatabase(user);
			break;
		case "N":
			System.out.println("We're glad you decided to stay.");
			break;
		default:
			System.out.println("Invalid option.");
			deleteUserPrompt(user);
			break;
		}
	}

	private String genderInput(String prompt) {
	    String gender;
	    do {
	        System.out.print(prompt);
	        gender = input.next().toUpperCase();
	    } while (!gender.equals("M") && !gender.equals("F"));
	    return gender;
	}
	

	public void closeInputScanner() {
		input.close();
	}
	
}
