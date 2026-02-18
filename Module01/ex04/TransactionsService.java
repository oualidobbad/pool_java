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
        return usrList.getUserById(id).getBalance();
    }

    public void transferTransaction(Integer senderId, Integer recipientId, Integer amount){


    }
}
