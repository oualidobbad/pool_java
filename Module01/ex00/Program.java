package Module01.ex00;

public class Program {
	public static void main(String [] args)
	{
		User user1 = new User(99990,"oualid", 50);
		System.out.println("========== user1 ==========");
		user1.printInfo();
		User user2 = new User(91111,"obbad", 100);
		System.out.println("========== user2 ==========");
		user2.printInfo();
		Transaction transaction = new Transaction(user1, user2, TransferCategory.DEBIT, -100);
		transaction.printInfo();
		transaction.setTransferAmount(-1200);
		transaction.printInfo();
	}
}
