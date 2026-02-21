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
		Integer choice;
		int balance;
		String name;
		User user;

		printMenu();
		sc = new Scanner(System.in);
		choice = sc.nextInt();
		switch (choice){
			case 1:
				System.out.println("Enter name and balance:");
				name = sc.next();
				balance = sc.nextInt();
				user = new User(name, balance);
				service.addUser(user);
				System.out.println("user with id "+ user.getIdentifier() + " added.");
				break;
			case 2:

		}
		if (choice.equals(1)){

		}
		sc.close();
	}
}
