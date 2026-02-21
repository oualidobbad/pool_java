import java.util.Scanner;

public class Menu {
	private TransactionsService service;
	private boolean loopCondition;

	public Menu() {
		service = new TransactionsService();
		loopCondition = true;
	}

	private void printMenu() {

		System.out.println("1. Add a user");
		System.out.println("2. View user balances");
		System.out.println("3. Perform a transfer");
		System.out.println("5. DEV - remove a transfer by ID");
		System.out.println("6. DEV - check transfer validity");
		System.out.println("7. Finish execution");
	}

	private String getLine(Scanner sc) {
		System.out.print("-> ");
		if (!sc.hasNextLine()) {
			System.out.println("by by by");
			return null;
		}
		return sc.nextLine().trim();
	}

	private void addUser(Scanner sc) {
		String line;
		User user;
		String[] lineSplited;

		System.out.println("Enter name and balance:");
		while (loopCondition) {
			if ((line = getLine(sc)) == null) {
				loopCondition = false;
				sc.close();
				return;
			}
			lineSplited = line.split(" ");
			if (line.equals("cancel")) {
				System.out.println("Operation cancelled");
				System.out.println("-------------------------------");
				return;
			}
			if (lineSplited.length != 2) {
				System.err.println("Invalid input. Enter: <name> <balance> (or 'cancel' to cancel)");
				continue;
			}
			user = new User(lineSplited[0], Integer.parseInt(lineSplited[1]));
			service.addUser(user);
			System.out.println("User with id = " + user.getIdentifier() + " is added");
			System.out.println("-------------------------------");
			return;
		}
	}

	private void viewBalance(Scanner sc) {
		String id;
		String[] idSplited;
		while (loopCondition) {
			System.out.println("Enter a user ID");
			id = getLine(sc);

			switch (id) {
				case null:
					sc.close();
					loopCondition = false;
					return;
				case "cancel":
					System.out.println("Operation cancelled");
					System.out.println("-------------------------------");
					return;
				default:
					idSplited = id.split(" ");
					if (idSplited.length != 1) {
						System.err.println("Invalid input. Enter: 'cancel' if you want to cancel");
						continue;
					}
					service.getBalanceUser(Integer.parseInt(id));
					System.out.println("-------------------------------");
					return;
			}
		}

	}

	public void lancerMenu() {
		Scanner sc;
		String choice;

		sc = new Scanner(System.in);
		while (loopCondition) {
			printMenu();
			try {
				choice = getLine(sc);
				switch (choice) {
					case null:
						sc.close();
						return;
					case "1":
						addUser(sc);
						break;
					case "2":
						viewBalance(sc);
						break;
					case "3":
						
					case "7":
						sc.close();
						return;
					default:
						System.out.println("Commande Not Found!");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
