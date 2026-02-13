package Module01.ex03;

// import Module01.ex01.UserIdsGenerator;
import Module01.ex01.User;

public class Program {
	public static void main (String [] args){

		TransactionsLinkedList list = new TransactionsLinkedList();

		User sender = new User("oualid", 100);
		User recipient = new User("khalid", 15);

		Transaction t1 = new Transaction(sender,recipient, TransferCategory.DEBIT, -130);

		sender = new User("obbad", 50);
		recipient = new User("hicham", 4000);

		Transaction t2 = new Transaction(sender,recipient, TransferCategory.CREDIT, 100);

		list.addTransaction(t1);
		list.addTransaction(t2);
		Transaction [] arr = list.toArray();
		// for (int i = 0; i < arr.length; i++)
		// {
		// 	arr[i].printInfo();
		// }
		list.deleteTransaction(null);
		list.deleteTransaction(t1.getIdentifier());
		arr = list.toArray();
		for (int i = 0; i < arr.length; i++)
		{
			arr[i].printInfo();
		}
	}
}
