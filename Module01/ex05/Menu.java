import java.lang.invoke.StringConcatException;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
	private TransactionsService service = new TransactionsService();
	private boolean loopCondition = true;
	private boolean isDev = false;
	Scanner sc = new Scanner(System.in);

	private void printMenu() {
		System.out.println("1. Add a user");
		System.out.println("2. View user balances");
		System.out.println("3. Perform a transfer");
		System.out.println("4. View all transactions for a specific user");
		if (isDev) {
			System.out.println("5. DEV - remove a transfer by ID");
			System.out.println("6. DEV - check transfer validity");
		}
		System.out.println("7. Finish execution");
	}

	public void setDev(boolean isDev) {
		this.isDev = isDev;
	}

	private String getLine() {
		System.out.print("-> ");
		if (!sc.hasNextLine())
			throw new ExitException("by by by");
		return sc.nextLine().trim();
	}

	private void addUser() {
		String line;
		User user;
		String[] lineSplited;

		System.out.println("Enter name and balance:");
		while (loopCondition) {
			try {
				line = getLine();
				if (line.equals("cancel"))
					throw new StringConcatException("Operation cancelled");
				lineSplited = line.split(" ");
				user = new User(lineSplited[0], Integer.parseInt(lineSplited[1]));
				service.addUser(user);
				throw new StringConcatException("User with id = " + user.getIdentifier() + " is added");
			} catch (StringConcatException e) {
				System.out.println(e.getMessage());
				System.out.println("-------------------------------");
				return;
			} catch (ExitException e) {
				System.out.println(e.getMessage());
				loopCondition = false;
				sc.close();
				return;
			} catch (Throwable t) {
				System.err.println("Invalid input. <name> <balance [number]>  (or 'cancel' to cancel)");
			}

		}
	}

	private void viewBalance() {
		String id;

		System.out.println("Enter a user ID");
		while (loopCondition) {
			try {
				id = getLine();
				switch (id) {
					case "cancel":
						throw new StringConcatException("Operation cancelled");
					default:
						service.getBalanceUser(Integer.parseInt(id));
						System.out.println("-------------------------------");
						return;
				}
			} catch (UserNotFoundException e) {
				System.err.println(e.getMessage());
			} catch (StringConcatException e) {
				System.out.println(e.getMessage());
				System.out.println("-------------------------------");
				return;
			} catch (ExitException e) {
				System.out.println(e.getMessage());
				loopCondition = false;
				sc.close();
				return;
			} catch (Throwable t) {
				System.err.println("Invalid input. Enter: 'cancel' if you want to cancel");
			}

		}

	}

	private void transferAmount() {
		String line;
		String lineSplited[];

		System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
		while (loopCondition) {
			try {
				line = getLine();
				lineSplited = line.split(" ");
				if (line.equals("cancel"))
					throw new StringConcatException("Operation cancelled");
				service.transferTransaction(Integer.parseInt(lineSplited[0]), Integer.parseInt(lineSplited[1]),
						Integer.parseInt(lineSplited[2]));
				throw new StringConcatException("The transfer is completed");
			} catch (ExitException e) {
				System.err.println(e.getMessage());
				sc.close();
				loopCondition = false;
				return;
			} catch (StringConcatException e) {
				System.out.println(e.getMessage());
				System.out.println("-------------------------------");
				return;
			} catch (UserNotFoundException e) {
				System.err.println(e.getMessage());
			} catch (IllegalTransactionException e) {
				System.err.println(e.getMessage());
			} catch (Throwable t) {
				System.err.println(
						"Invalid input. Enter: <sender ID [number]> <recipient ID [number]> amount [number] (or 'cancel' to cancel)");
			}
		}
	}

	private void viewAllTransaction() {
		Transaction[] transactions;
		String line;

		System.out.println("Enter a user ID");

		while (loopCondition) {
			try {
				line = getLine();
				if (line.equals("cancel"))
					throw new StringConcatException("operation cancelled");
				transactions = service.getTransactions(Integer.parseInt(line));
				if (transactions.length == 0)
					throw new StringConcatException("this user you don't have any Transaction");
				for (int i = 0; i < transactions.length; i++) {
					User usr;
					if (transactions[i].getCategory() == TransferCategory.CREDIT) {
						usr = transactions[i].getSender();
						System.out.print("From " + usr.getName() + "(id = " + usr.getIdentifier());
					} else {
						usr = transactions[i].getRecipient();
						System.out.print("To " + usr.getName() + "(id = " + usr.getIdentifier());
					}
					System.out.println(") " + transactions[i].getTransferAmount() + " with id = "
							+ transactions[i].getIdentifier());
				}
				System.out.println("-------------------------------");
				return;
			} catch (UserNotFoundException e) {
				System.err.println(e.getMessage());
			} catch (StringConcatException e) {
				System.out.println(e.getMessage());
				System.out.println("-------------------------------");
				return;
			} catch (ExitException e) {
				System.out.println(e.getMessage());
				loopCondition = false;
				sc.close();
				return;
			} catch (Throwable t) {
				System.err.println("Invalid input. Enter: 'cancel' if you want to cancel");
			}
		}

	}

	private void removeTransfer() {
		String line;
		String lineSplited[];
		System.out.println("Enter a user ID and a transfer ID");

		while (loopCondition) {
			try {
				line = getLine();
				if (line.equals("cancel"))
					throw new StringConcatException("Operation cancelled");
				lineSplited = line.split(" ");
				service.removeTransaction(Integer.parseInt(lineSplited[0]), UUID.fromString(lineSplited[1]));
				return;
			} catch (ExitException e) {
				System.err.println(e.getMessage());
				sc.close();
				loopCondition = false;
				return;
			} catch (StringConcatException e) {
				System.out.println(e.getMessage());
				System.out.println("-------------------------------");
				return;
			} catch (UserNotFoundException e) {
				System.err.println(e.getMessage());
			} catch (TransactionNotFoundException e) {
				System.err.println(e.getMessage());
			} catch (Throwable t) {
				System.err.println(
						"Invalid input. Enter: <user ID [number]> <transaction [UUID]> (or 'cancel' to cancel)");
			}
		}
	}

	private void checktransferValidity() throws StringConcatException {
		System.out.println("Check results:");

		Transaction[] unpairedTransactions = service.checkValidityOfTransactions();
		if (unpairedTransactions.length == 0) {
			throw new StringConcatException("not Find any unpairedTransaction");
		}
		for (int i = 0; i < unpairedTransactions.length; i++) {
			User resived = unpairedTransactions[i].getRecipient();
			User sender = unpairedTransactions[i].getSender();
			System.out.print(
					resived.getName() + "(id = " + resived.getIdentifier() + ") has an unacknowledged transfer id = ");
			System.out.print(unpairedTransactions[i].getIdentifier() + " from " + sender.getName() + "(id = "
					+ sender.getIdentifier());
			System.out.println(") for " + unpairedTransactions[i].getTransferAmount());
		}
		System.out.println("------------------------------------------------");
	}

	public void lancerMenu() {
		String choice = null;

		while (loopCondition) {
			printMenu();
			try {
				choice = getLine();
				switch (choice) {
					case "1":
						addUser();
						break;
					case "2":
						viewBalance();
						break;
					case "3":
						transferAmount();
						break;
					case "4":
						viewAllTransaction();
						break;
					case "5":
						if (isDev)
							removeTransfer();
						else
							throw new StringConcatException("Commande Not Found!");
						break;
					case "6":
						if (isDev)
							checktransferValidity();
						else
							throw new StringConcatException("Commande Not Found!");
						break;
					case "7":
						throw new ExitException("you are close the application");
					default:
						throw new StringConcatException("Commande Not Found!");
				}
			} catch (StringConcatException e) {
				System.out.println(e.getMessage());
			} catch (ExitException e) {
				System.out.println(e.getMessage());
				sc.close();
				return;
			}

		}
	}
}
