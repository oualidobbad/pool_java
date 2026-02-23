// import java.lang.invoke.StatusOperation;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
	Scanner sc = new Scanner(System.in);
	private TransactionsService service = new TransactionsService();
	private boolean tryAgain = false;
	private boolean isDev = false;

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

		if (!tryAgain)
			System.out.println("Enter name and balance:");
		line = getLine();
		if (line.equals("cancel"))
			throw new StatusOperation("Operation cancelled");
		lineSplited = line.split(" ");
		user = new User(lineSplited[0], Integer.parseInt(lineSplited[1]));
		service.addUser(user);
		throw new StatusOperation("User with id = " + user.getIdentifier() + " is added");
	}

	private void viewBalance() {
		String id;

		if (!tryAgain)
			System.out.println("Enter a user ID");
		id = getLine();
		switch (id) {
			case "cancel":
				throw new StatusOperation("Operation cancelled");
			default:
				service.getBalanceUser(Integer.parseInt(id));
				System.out.println("-------------------------------");
				return;
		}
	}

	private void transferAmount() {
		String line;
		String lineSplited[];

		if (!tryAgain)
			System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
		line = getLine();
		lineSplited = line.split(" ");
		if (line.equals("cancel"))
			throw new StatusOperation("Operation cancelled");
		service.transferTransaction(Integer.parseInt(lineSplited[0]), Integer.parseInt(lineSplited[1]),
				Integer.parseInt(lineSplited[2]));
		throw new StatusOperation("The transfer is completed");
	}

	private void viewAllTransaction() {
		Transaction[] transactions;
		String line;
		if (!tryAgain)
			System.out.println("Enter a user ID");
		line = getLine();
		if (line.equals("cancel"))
			throw new StatusOperation("operation cancelled");
		transactions = service.getTransactions(Integer.parseInt(line));
		if (transactions.length == 0)
			throw new StatusOperation("this user you don't have any Transaction");
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
	}

	private void removeTransfer() {
		String line;
		String lineSplited[];

		if (!tryAgain)
			System.out.println("Enter a user ID and a transfer ID");

		line = getLine();
		if (line.equals("cancel"))
			throw new StatusOperation("Operation cancelled");
		lineSplited = line.split(" ");
		service.removeTransaction(Integer.parseInt(lineSplited[0]), UUID.fromString(lineSplited[1]));
		return;
	}

	private void checktransferValidity(){
		System.out.println("Check results:");

		Transaction[] unpairedTransactions = service.checkValidityOfTransactions();
		if (unpairedTransactions.length == 0) {
			throw new StatusOperation("not Find any unpairedTransaction");
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
		while (true) {
			try 
			{
				if (!tryAgain){
					printMenu();
					choice = getLine();
				}
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
							throw new StatusOperation("Commande Not Found!");
						break;
					case "6":
						if (isDev)
							checktransferValidity();
						else
							throw new StatusOperation("Commande Not Found!");
						break;
					case "7":
						throw new ExitException("you are close the application");
					default:
						throw new StatusOperation("Commande Not Found!");
				}
			}
			catch (ExitException e) {
				System.err.println(e.getMessage());
				sc.close();
				return;
			} catch (StatusOperation e) {
				System.out.println(e.getMessage());
				System.out.println("-------------------------------");
			} catch (UserNotFoundException e) {
				System.err.println(e.getMessage());
				tryAgain = true;
				continue ;
			} catch (TransactionNotFoundException e) {
				System.err.println(e.getMessage());
				tryAgain = true;
				continue ;
			} 
			catch (IllegalTransactionException e) {
				System.err.println(e.getMessage());
				tryAgain = true;
				continue ;
			}
			catch (Throwable t) {
				System.err.println("Invalid input. Enter: cancel if want to cancel)");
				tryAgain = true;
				continue ;
			}
			tryAgain = false;

		}
	}
}
