package com.transactions.core;

public interface UsersList {

	public void addUser(User usr);
	public User getUserById(Integer id);
	public User getUserByIndex(Integer index);
	public Integer getNumberOfUsers();
}
