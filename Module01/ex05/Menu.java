import java.lang.invoke.StringConcatException;
import java.util.Scanner;

public class Menu {
	private TransactionsService service = new TransactionsService();
	private boolean loopCondition = true;
	private boolean isDev = false;

	private void printMenu() {
		System.out.println("1. Add a user");
		System.out.println("2. View user balances");
		System.out.println("3. Perform a transfer");
		if (isDev){
			System.out.println("5. DEV - remove a transfer by ID");
			System.out.println("6. DEV - check transfer validity");
		}
		System.out.println("7. Finish execution");
	}

	public void setDev(boolean isDev) {
		this.isDev = isDev;
	}

	private String getLine(Scanner sc){
		System.out.print("-> ");
		if (!sc.hasNextLine())
			throw new ExitException("by by by");
		return sc.nextLine().trim();
	}

	private void addUser(Scanner sc) {
		String line;
		User user;
		String[] lineSplited;

		System.out.println("Enter name and balance:");
		while (loopCondition) {
			try {
				line = getLine(sc);
				if (line.equals("cancel"))
					throw new StringConcatException("Operation cancelled");
				lineSplited = line.split(" ");
				user = new User(lineSplited[0], Integer.parseInt(lineSplited[1]));
				service.addUser(user);
				throw new StringConcatException("User with id = " + user.getIdentifier() + " is added");
			}
			catch(StringConcatException e){
				System.out.println(e.getMessage());
				System.out.println("-------------------------------");
				return ;
			}
			catch(ExitException e){
				System.out.println(e.getMessage());
				loopCondition = false;
				sc.close();
				return;
			}
			 catch (Throwable t) {
				System.err.println("Invalid input. <name> <balance [number]>  (or 'cancel' to cancel)");
			}

		}
	}

	private void viewBalance(Scanner sc) {
		String id;

		System.out.println("Enter a user ID");
		while (loopCondition) {
			try 
			{
				id = getLine(sc);
				switch (id) 
				{
					case "cancel":
						throw new StringConcatException("Operation cancelled");
					default:
						service.getBalanceUser(Integer.parseInt(id));
						System.out.println("-------------------------------");
						return ;
				}
			}
			catch(UserNotFoundException e){
				System.err.println(e.getMessage());
			}
			catch(StringConcatException e){
				System.out.println(e.getMessage());
				System.out.println("-------------------------------");
				return;
			}
			catch(ExitException e){
				System.out.println(e.getMessage());
				loopCondition = false;
				sc.close();
				return;
			}
			catch (Throwable t) {
				System.err.println("Invalid input. Enter: 'cancel' if you want to cancel");
			}

		}

	}

	private void transferAmount(Scanner sc){
		String line;
		String lineSplited[];

		System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
		while (loopCondition) 
		{
			try{
				line = getLine(sc);
				lineSplited = line.split(" ");
				if (line.equals("cancel")) 
					throw new StringConcatException("Operation cancelled");
				service.transferTransaction(Integer.parseInt(lineSplited[0]), Integer.parseInt(lineSplited[1]), Integer.parseInt(lineSplited[2]));
				throw new StringConcatException("The transfer is completed");
			}
			catch (ExitException e)
			{
				System.err.println(e.getMessage());
				sc.close();
				loopCondition = false;
				return;
			}
			catch(StringConcatException e){
				System.out.println(e.getMessage());
				System.out.println("-------------------------------");
				return;
			}
			catch(UserNotFoundException e){
				System.err.println(e.getMessage());
			}
			catch (IllegalTransactionException e)
			{
				System.err.println(e.getMessage());
			}
			catch(Throwable t){
				System.err.println("Invalid input. Enter: <sender ID [number]> <recipient ID [number]> amount [number] (or 'cancel' to cancel)");
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
					case "1":
						addUser(sc);
						break;
					case "2":
						viewBalance(sc);
						break;
					case "3":
						transferAmount(sc);
						break ;
					case "7":
						throw new ExitException("you are close the application");
					default:
						throw new StringConcatException("Commande Not Found!");
				}
			}
			catch(StringConcatException e)
			{
				System.out.println(e.getMessage());
			}
			catch(ExitException e){
				System.out.println(e.getMessage());
				sc.close();
				return;
			}
		}
	}
}
