public class Program {
	public static void main(String[] args) {

		User usr1 = new User("admin", 1000);
		User usr2 = new User("oualid", 15);
		TransactionsService trs = new TransactionsService();
		trs.addUser(usr1);
		trs.addUser(usr2);

		try {
			trs.transferTransaction(usr1.getIdentifier(), usr2.getIdentifier(), 100);
			trs.transferTransaction(usr2.getIdentifier(), usr1.getIdentifier(), 100);
			Transaction arr [] = trs.getTransactions(usr1.getIdentifier());
			trs.removeTransaction(usr1.getIdentifier(),arr[0].getIdentifier());
			arr = trs.getTransactions(usr1.getIdentifier());

			for (int i = 0; i < arr.length; i++)
			{
				arr[i].printInfo();
			}


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
