import java.util.UUID;

public class TransactionsService {
    private UsersList usrList;

    public TransactionsService() {
        usrList = new UsersArrayList();
    }

    public void addUser(User usr) {
        usrList.addUser(usr);
    }

    public Integer getBalanceUser(Integer id) {
        return usrList.getUserById(id).getBalance();
    }

    public void transferTransaction(Integer senderId, Integer recipientId, Integer amount) {
        User sender = usrList.getUserById(senderId);
        User received = usrList.getUserById(recipientId);
        Transaction creditTransaction;
        Transaction debitTransaction;

        if (sender.getBalance() < amount || amount <= 0)
            throw new IllegalTransactionException("operation Failed!, check amount");
        received.setBalance(received.getBalance() + amount);
        sender.setBalance(sender.getBalance() - amount);
        debitTransaction = new Transaction(sender, received, TransferCategory.DEBIT, -amount);
        sender.getList().addTransaction(debitTransaction);
        creditTransaction = new Transaction(sender, received, TransferCategory.CREDIT, amount);
        creditTransaction.setIdentifier(debitTransaction.getIdentifier());
        received.getList().addTransaction(creditTransaction);
    }

    public Transaction[] getTransactions(Integer userId) {

        User usr = usrList.getUserById(userId);
        return usr.getList().toArray();
    }

    public void removeTransaction(Integer userId, UUID transactionId) {
        User user = usrList.getUserById(userId);
        user.getList().deleteTransaction(transactionId);

    }

    public Transaction[] checkValidityOfTransactions() {
        Transaction[] tempUnpaired = new Transaction[1000];
        int idxOfUnpaired = 0;

        for (int i = 0; i < usrList.getNumberOfUsers(); i++) {
            User user = usrList.getUserByIndex(i);
            Transaction[] arrTrans = user.getList().toArray();

            for (int j = 0; j < arrTrans.length; j++) {
                User othUser;
                if (arrTrans[j].getCategory() == TransferCategory.CREDIT)
                    othUser = arrTrans[j].getSender();
                else
                    othUser = arrTrans[j].getRecipient();
                Transaction[] arrTransOther = othUser.getList().toArray();
                boolean ispaired = false;
                for (int l = 0; l < arrTransOther.length; l++) {
                    if (arrTransOther[l].getIdentifier().equals(arrTrans[j].getIdentifier()))
                        ispaired = true;
                }
                if (ispaired == false)
                    tempUnpaired[idxOfUnpaired++] = arrTrans[j];
            }
        }
        return tempUnpaired;
    }
}
