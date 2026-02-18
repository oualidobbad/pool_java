public class Program {
	public static void main (String [] args){

		User usr1 = new User("admin", 1200);
		User usr2 = new User("khalid", 15);
		
		Transaction t1 = new Transaction(usr1,usr2, TransferCategory.DEBIT, -730);
		Transaction t2 = new Transaction(usr2,usr1, TransferCategory.CREDIT, 100);
		Transaction t3 = new Transaction(usr2,usr1, TransferCategory.CREDIT, 200);
		
		try {
			usr1.getList().addTransaction(t1);
			usr1.getList().addTransaction(t2);
			usr1.getList().addTransaction(t3);
			// usr1.getList().deleteTransaction(null);
			
			Transaction [] arr = usr1.getList().toArray();

			for (int i = 0; i < arr.length; i++)
			{
				arr[i].printInfo();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
