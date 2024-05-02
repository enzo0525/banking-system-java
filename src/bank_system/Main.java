package bank_system;

public class Main {
	private static final DatabaseHandler databaseHandler = new DatabaseHandler();
	private static final MenuHandler menuHandler = new MenuHandler(databaseHandler);
	
	public static void main(String[] args) {
		System.out.println("Welcome to Enzo's bank!\n");
		try {
			while (true) {
				int choice = menuHandler.displayMainMenuAndGetChoice();
				
				switch (choice) {
				case MenuHandler.CREATE_ACCOUNT:
					menuHandler.handleCreateAccount();
					break;
				case MenuHandler.LOG_IN:
					menuHandler.handleLogIn();
					break;
				case MenuHandler.EXIT:
					System.out.println("Thanks for using my bank.");
					return;
				default:
					System.out.println("Option not available.");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}	