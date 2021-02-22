package com.cg.services;

import java.util.List;

import com.cg.entities.WalletUser;

public interface IUserService {
	
	public WalletUser addUser(WalletUser user);
	
	public WalletUser getUser(int userId);
	
	public List<WalletUser> getAllUsers();
	
	public WalletUser changePassword(int userId, WalletUser user);

}
