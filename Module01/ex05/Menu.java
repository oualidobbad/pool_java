import java.util.Scanner;

// enum choice{
// 	ADD,
// 	VIEW_BALANCE,
// 	TRANSFER,
// 	VIEW_TRANSACTION,
// }

public class Menu {
	private TransactionsService service;

	public Menu(){
		service = new TransactionsService();
	}

	private void printMenu(){

		System.out.println("1. Add a user");
		System.out.println("2. View user balances");
		System.out.println("3. Perform a transfer");
		System.out.println("5. DEV - remove a transfer by ID");
		System.out.println("6. DEV - check transfer validity");
		System.out.println("7. Finish execution");
	}

	public void lancerMenu(){
		Scanner sc;
		String choice;
		int balance;
		String name;

		printMenu();
		sc = new Scanner(System.in);
		choice = sc.nextLine();
		if (choice.equals("1")){
			name = sc.nextLine();
			balance = sc.nextInt();
			service.addUser(new User(name, balance));
		}
		sc.close();
	}
}
