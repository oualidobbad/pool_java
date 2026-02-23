package logic;

import java.util.UUID;

public class TransactionsService {
    private UsersList usrList;

    public TransactionsService() {
        usrList = new UsersArrayList();
    }

    public void addUser(User usr) {
        usrList.addUser(usr);
    }

    public User getUserById(Integer id) {
        return usrList.getUserById(id);
    }

    public Integer getBalanceUser(Integer id) {
        User user = usrList.getUserById(id);
        return user.getBalance();
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

    public String removeTransaction(Integer userId, UUID transactionId) {
        User user = usrList.getUserById(userId);
        Transaction tr = user.getList().geTransaction(transactionId);
        user.getList().deleteTransaction(transactionId);

        if (tr.getSender().getIdentifier().equals(user.getIdentifier()))
            return "Transfer To " + tr.getRecipient().getName() + "(id = " + tr.getRecipient().getIdentifier() + ") " + tr.getTransferAmount() + " removed";
        else
            return "Transfer To " + tr.getSender().getName() + "(id = " + tr.getSender().getIdentifier() + ") " + tr.getTransferAmount() + " removed";
    }

    public Transaction[] checkValidityOfTransactions() {
        int count = 0;

        for (int i = 0; i < usrList.getNumberOfUsers(); i++) {
            User user = usrList.getUserByIndex(i);
            Transaction[] arrTrans = user.getList().toArray();
            for (int j = 0; j < arrTrans.length; j++) {
                if (!isPaired(arrTrans[j]))
                    count++;
            }
        }

        Transaction[] unpaired = new Transaction[count];
        int idx = 0;

        for (int i = 0; i < usrList.getNumberOfUsers(); i++) {
            User user = usrList.getUserByIndex(i);
            Transaction[] arrTrans = user.getList().toArray();
            for (int j = 0; j < arrTrans.length; j++) {
                if (!isPaired(arrTrans[j]))
                    unpaired[idx++] = arrTrans[j];
            }
        }
        return unpaired;
    }

    public int getNumberOfUsers() {
        return usrList.getNumberOfUsers();
    }

    public User getUserByIndex(int index) {
        return usrList.getUserByIndex(index);
    }

    private boolean isPaired(Transaction tr) {
        User othUser;
        if (tr.getCategory() == TransferCategory.CREDIT)
            othUser = tr.getSender();
        else
            othUser = tr.getRecipient();
        Transaction[] arrTransOther = othUser.getList().toArray();
        for (int l = 0; l < arrTransOther.length; l++) {
            if (arrTransOther[l].getIdentifier().equals(tr.getIdentifier()))
                return true;
        }
        return false;
    }
}
