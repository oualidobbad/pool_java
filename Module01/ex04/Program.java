import java.util.UUID;

public class Program {
	public static void main(String[] args) {

		User usr1 = new User("Alice", 1000);
		User usr2 = new User("Bob", 500);
		User usr3 = new User("Charlie", 200);

		TransactionsService trs = new TransactionsService();
		trs.addUser(usr1);
		trs.addUser(usr2);
		trs.addUser(usr3);

		// Transfer Alice -> Bob $200
		trs.transferTransaction(usr1.getIdentifier(), usr2.getIdentifier(), 200);
		// Transfer Bob -> Charlie $100
		trs.transferTransaction(usr2.getIdentifier(), usr3.getIdentifier(), 100);

		System.out.println("Balances after transfers:");
		System.out.println("Alice: $" + trs.getBalanceUser(usr1.getIdentifier()));
		System.out.println("Bob: $" + trs.getBalanceUser(usr2.getIdentifier()));
		System.out.println("Charlie: $" + trs.getBalanceUser(usr3.getIdentifier()));

		System.out.println("\nAlice's transactions:");
		Transaction[] arr = trs.getTransactions(usr1.getIdentifier());
		for (int i = 0; i < arr.length; i++)
			arr[i].printInfo();

		trs.removeTransaction(usr1.getIdentifier(), arr[0].getIdentifier());
		System.out.println("\nAlice after removing 1 transaction:");
		arr = trs.getTransactions(usr1.getIdentifier());
		for (int i = 0; i < arr.length; i++)
			arr[i].printInfo();

		System.out.println("\nUnpaired transactions:");
		Transaction[] unpaired = trs.checkValidityOfTransactions();
		for (int i = 0; i < unpaired.length; i++) {
			if (unpaired[i] != null)
				unpaired[i].printInfo();
		}

		try {
			trs.transferTransaction(usr1.getIdentifier(), usr2.getIdentifier(), 999999);
		} catch (Exception e) {
			System.out.println("\nCaught: " + e.getMessage());
		}

		try {
			trs.removeTransaction(usr1.getIdentifier(), UUID.randomUUID());
		} catch (Exception e) {
			System.out.println("Caught: " + e.getMessage());
		}
	}
}
