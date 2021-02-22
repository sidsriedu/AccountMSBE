package com.cg.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dao.WalletUserRepo;
import com.cg.entities.WalletAccount;
import com.cg.entities.WalletUser;
import com.cg.exceptions.InvalidCredentialsException;
import com.cg.exceptions.UserAlreadyExistsException;
import com.cg.exceptions.UserDoesNotExistsException;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	WalletUserRepo userRepo;
	@Autowired
	IAccountService accountService;	

	@Override
	public WalletUser addUser(WalletUser user1) {
		List<WalletUser> users = getAllUsers();
		for(WalletUser user : users) {
			if(user.getPhoneNumber().equals(user1.getPhoneNumber())){
				throw new UserAlreadyExistsException("User with this phone no: already exists!!");
			}
		}
		WalletUser user2 = userRepo.save(user1);
		int userId = user2.getUserId();
		WalletAccount account = accountService.addAccount(userId);
		WalletUser user3 = userRepo.getOne(userId);
		user3.setWalletAccount(account);
		WalletUser user4 = userRepo.save(user3);
		//System.out.println(user4);
		return user4;
	}

	@Override
	public WalletUser getUser(int userId) {
		Boolean exists = userRepo.existsById(userId);;
		if(exists==false) {
			throw new UserDoesNotExistsException("User does not exists");
		}
		WalletUser user = userRepo.getOne(userId);
		return user;
	}
	
	@Override
	public List<WalletUser> getAllUsers() {
		List<WalletUser> users = userRepo.findAll();
		return users;
	}
	
	@Override
	public WalletUser changePassword(int userId, WalletUser user) {
		WalletUser walletUser = userRepo.getOne(userId);
		if(walletUser==null) {
			throw new UserDoesNotExistsException("User does not exists");
		}
		if(walletUser.getPhoneNumber().equals(user.getPhoneNumber()) && walletUser.getUserName().equalsIgnoreCase(user.getUserName())) {
			walletUser.setPassword(user.getPassword());
			WalletUser user2 = userRepo.save(walletUser);
			return user2;
		}else {
			throw new InvalidCredentialsException("Unable to verfiy");
		}
	}
	
}
