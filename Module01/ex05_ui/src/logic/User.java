package logic;

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
            return;
        }
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{id=" + identifier + ", name='" + name + "', balance=$" + balance + "}";
    }
}
