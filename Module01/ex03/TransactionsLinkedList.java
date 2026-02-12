package Module01.ex03;
import Module01.ex00.Transaction;

public class TransactionsLinkedList implements	TransactionsList {
	
	private List head;
	private List last;

	private static class List {
		private Transaction transaction;
		List next;
		List(Transaction transaction){
			this.transaction = transaction;
			next = null;
		}
	}
	TransactionsLinkedList(){
		next = null;
		// head = null;
		// last = null;
	}
	@Override
	public void addTransaction(Transaction tr){
		this.transaction = tr;
		TransactionsLinkedList node = new TransactionsLinkedList();
		if (next == null){
			next = node;
		}
		else
		{
			next
		}
	}


}
