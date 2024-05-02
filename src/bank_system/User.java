package bank_system;

import java.util.Random;

public class User {
	
	public String fname, lname, gender;
	private String accountNumber;
	private double balance;
	private final DatabaseHandler databaseHandler = new DatabaseHandler();
	
	public User(String fname, String lname, String gender, double balance) {
		this.fname = fname.substring(0,1).toUpperCase() + fname.substring(1);
		this.lname = lname.substring(0,1).toUpperCase() + lname.substring(1);;
		this.gender = gender;
		this.balance = balance;
		this.accountNumber = accountNumberGenerator(); //generate a random number
	}
	
	public User(String accountNumber, String fname, String lname, String gender, double balance) {
		this.accountNumber = accountNumber;
		this.fname = fname.substring(0,1).toUpperCase() + fname.substring(1);
		this.lname = lname.substring(0,1).toUpperCase() + lname.substring(1);;
		this.gender = gender;
		this.balance = balance;
	}
	
	public void addToDatabase() {
		databaseHandler.createUserInDatabase(this);
	}
	
	public String getAccountNumber() {
		return this.accountNumber;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public void deposit(double amount) {
		this.balance += amount;
		databaseHandler.updateBalanceInDatabase(this);
		System.out.println("The amount of $" + amount + " was added to the account.");
	}
	
	public void withdraw(double amount) {
		if (amount < 0) {
			System.out.println("Cannot withdraw negative integers.");
		}
		else if (amount <= this.balance) {
			balance -= amount;
			databaseHandler.updateBalanceInDatabase(this);
			System.out.println("The amount of $" + amount + " got withdrew from the account.\n"
							 + "Your remaining balance is: $" + this.balance);
			return;
		}
		else {
			System.out.println("Insufficient funds.");	
		}
	}
	
	public void deleteFromDatabase() {
		databaseHandler.deleteUserFromDatabase(this);
    }
	
	public String accountNumberGenerator() {
		Random random = new Random();
		int newAccountNumber = random.nextInt(900000) + 100000;
		return String.valueOf(newAccountNumber);
	}
}