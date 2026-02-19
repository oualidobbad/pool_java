public class Program {
	public static void main(String[] args) {

		User usr1 = new User("admin", 1000);
		User usr2 = new User("khalid", 15);
		TransactionsService trs = new TransactionsService();
		trs.addUser(usr1);
		trs.addUser(usr2);

		try {
			trs.transferTransaction(usr1.getIdentifier(), usr2.getIdentifier(), 100);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
