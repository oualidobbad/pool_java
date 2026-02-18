
public class User {
    private final Integer identifier; 
    private final String name;
    private Integer balance;
	private TransactionsList listTransactions;
    
    public User(String name, Integer balance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
		this.listTransactions = new TransactionsLinkedList();
        
        if (balance < 0) {
            System.err.println("ERROR: Initial balance cannot be negative for user '" + name + "'. Setting to 0.");
            this.balance = 0;
        } else {
            this.balance = balance;
        }
    }

	public TransactionsList getList() {
		return listTransactions;
	}
    public Integer getIdentifier() {
        return identifier;
    }
    public String getName() {
        return name;
    }
    public Integer getBalance() {
        return balance;
    }
    
    public void setBalance(Integer balance) {
        if (balance < 0) {
            System.err.println("ERROR: Balance cannot be negative for user '" + name + "'. Value not changed.");
            return;
        }
        this.balance = balance;
    }
    
    public void printInfo() {
        System.out.println("User ID: " + identifier);
        System.out.println("Name: " + name);
        System.out.println("Balance: $" + balance);
    }
}
