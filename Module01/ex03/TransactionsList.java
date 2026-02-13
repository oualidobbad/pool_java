package Module01.ex03;
import java.util.UUID;


public interface TransactionsList {

	public void addTransaction(Transaction tr);
	public void deleteTransaction(UUID id);
	public Transaction[] toArray();
}