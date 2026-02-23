package com.transactions.core;

public class UserIdsGenerator {
	private Integer lastID = 0;
	private static UserIdsGenerator instance = null;

	private UserIdsGenerator()
	{
		lastID = 0;
	}

	public static UserIdsGenerator getInstance()
	{
		if (instance == null)
		{
			instance = new UserIdsGenerator();
		}
		return instance;
	}

	public Integer generateId()
	{
		return ++lastID;
	}
}
