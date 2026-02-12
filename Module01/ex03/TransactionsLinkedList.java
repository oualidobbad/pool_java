package Module01.ex03;
import java.util.UUID;

import Module01.ex00.Transaction;

public class TransactionsLinkedList implements	TransactionsList {
	
	private List head;
	private List lastNode;
	private int size;

	private static class List {
		private Transaction transaction;
		List next;
		List prev;

		List(Transaction transaction){
			this.transaction = transaction;
			next = null;
			prev = null;
		}
	}
	public TransactionsLinkedList()
	{
		head = null;
		lastNode = null;
		size = 0;
	}
	@Override
	public void addTransaction(Transaction tr){
		if (tr == null)
			return ;
		List node = new List(tr);
		if (head == null){
			head = node;
			lastNode = node;
		}
		else
		{
			lastNode.next = node;
			node.prev = lastNode;
			lastNode = node;
		}
		size++;
	}

	@Override
	public void deleteTransaction(UUID id){
		List curreNode;

		if (head == null)
			return ;
		size--;
		if (head == lastNode && id.equals(head.transaction.getIdentifier()))
		{
			head = null;
			lastNode = null;
			return ;
		}
		else if (id.equals(head.transaction.getIdentifier()))
		{
			head = head.next;
			head.prev = null;
			return ;
		}
		else if (id.equals(lastNode.transaction.getIdentifier()))
		{
			lastNode = lastNode.prev;
			lastNode.next = null;
			return ;
		}
		curreNode = head;
		while (curreNode != null) {
			if (id.equals(curreNode.transaction.getIdentifier()))
			{
				curreNode.prev.next = curreNode.next;
				curreNode.next.prev = curreNode.prev;
				return ;
			}
			curreNode = curreNode.next;
		}
		size++;
	}

	@Override
	public Transaction[] toArray(){
		Transaction [] arr = new Transaction[size];
		List currList = head;
		int i = 0;
		while (currList != null) {
			arr[i++] = currList.transaction;
			currList = currList.next;
		}
		return arr;
	}
}
