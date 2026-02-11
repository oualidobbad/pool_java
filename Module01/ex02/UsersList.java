package Module01.ex02;

public interface UsersList {

	public void addUser(User usr);
	public User getUserById(Integer id);
	public User getUserByIndex(Integer index);
	public Integer getNumberOfUsers();
}
