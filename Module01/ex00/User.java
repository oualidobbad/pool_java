package Module01.ex00;

class User {
    private final Integer identifier; 
    private final String name;
    private Integer balance;
    
    public User(Integer identifier, String name, Integer balance) {
        this.identifier = identifier;
        this.name = name;
        
        if (balance < 0) {
            System.err.println("ERROR: Initial balance cannot be negative for user '" + name + "'. Setting to 0.");
            this.balance = 0;
        } else {
            this.balance = balance;
        }
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