package com.cg.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dao.WalletAccountRepo;
import com.cg.dto.TransactionDto;
import com.cg.entities.WalletAccount;
import com.cg.exceptions.AccountDoesNotExistsException;
import com.cg.exceptions.InsufficientFundsException;
import com.cg.exceptions.InvalidCredentialsException;
import com.cg.util.Status;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService {

	@Autowired
	WalletAccountRepo accountRepo;
	
	@Override
	public WalletAccount addAccount(int userId) {
		WalletAccount account = new WalletAccount();
		account.setAccountId(userId);
		account.setAccountBalance(0);
		account.setStatus(Status.TRUE);
		WalletAccount account2 = accountRepo.save(account);
		return account2;
	}

	@Override
	public WalletAccount addMoney(int accountId, TransactionDto transactionDto) {
		Boolean exists = accountRepo.existsById(accountId);
		if(exists == false) {
			throw new AccountDoesNotExistsException("User with accountId "+accountId+" does not exists");
		}
		WalletAccount account = accountRepo.getOne(accountId);
		account.setAccountBalance(account.getAccountBalance()+transactionDto.getAmount());
		account = accountRepo.save(account);
		return account;
	}

	@Override
	public WalletAccount sendMoney(int senderAccountId, TransactionDto transactionDto) {
		Boolean senderExists = accountRepo.existsById(senderAccountId);
		if(senderExists==false) {
			throw new AccountDoesNotExistsException("User  does not exists");
		}
		if(senderAccountId==transactionDto.getRecieverAccountId()) {
			throw new InvalidCredentialsException("Invalid Credentials !");
		}
		WalletAccount senderAccount = accountRepo.getOne(senderAccountId);
		Boolean recieverExists = accountRepo.existsById(transactionDto.getRecieverAccountId());
		if(recieverExists==false) {
			throw new AccountDoesNotExistsException("User with accountId "+transactionDto.getRecieverAccountId()+" does not exists");
		}
		WalletAccount recieverAccount = accountRepo.getOne(transactionDto.getRecieverAccountId());
		if(senderAccount.getAccountBalance()-transactionDto.getAmount()>=0) {
			senderAccount.setAccountBalance(senderAccount.getAccountBalance()-transactionDto.getAmount());
		}else {
			throw new InsufficientFundsException("Insufficient funds to send money");
		}
		WalletAccount account =accountRepo.save(senderAccount);
		recieverAccount.setAccountBalance(recieverAccount.getAccountBalance()+transactionDto.getAmount());
		accountRepo.save(recieverAccount);
		return account;
	}

	@Override
	public WalletAccount getAccount(int accountId) {
		boolean exists = accountRepo.existsById(accountId);
		if(exists == false) {
			throw new AccountDoesNotExistsException("User with accountId "+accountId+" does not exists");
		}
		WalletAccount account = accountRepo.getOne(accountId);
		return account;
		
	}
	
}
