public class TransactionsService {
    private UsersList usrList;

    public TransactionsService(){
        usrList = new UsersArrayList();
    }
    public void addUser(User usr)
    {
        usrList.addUser(usr);
    }
    public Integer getBalanceUser(Integer id){
        try{
            return usrList.getUserById(id).getBalance();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void transferTransaction(Integer senderId, Integer recipientId, Integer amount){
        try{
            User sender = usrList.getUserById(senderId);
            User received = usrList.getUserById(recipientId);
            Transaction creditTransaction;
            Transaction debitTransaction;

            if (sender.getBalance() < amount || amount <= 0)
                throw new IllegalTransactionException("operation Faild!, check amount");
            received.setBalance(received.getBalance() + amount);
            sender.setBalance(sender.getBalance() - amount);
            debitTransaction = new Transaction(sender, received, TransferCategory.DEBIT, -amount);
            sender.getList().addTransaction(debitTransaction);
            creditTransaction = new Transaction(sender, received, TransferCategory.CREDIT, amount);
            creditTransaction.setIdentifier(debitTransaction.getIdentifier());
            received.getList().addTransaction(creditTransaction);
            debitTransaction.printInfo();
            creditTransaction.printInfo();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
