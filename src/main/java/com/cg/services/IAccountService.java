package com.cg.services;

import com.cg.dto.TransactionDto;
import com.cg.entities.WalletAccount;

public interface IAccountService {
	
	public WalletAccount addAccount(int userId);
	
	public WalletAccount addMoney(int accountId, TransactionDto transactionDto);
	
	public WalletAccount sendMoney(int senderAccountId, TransactionDto transactionDto);

	public WalletAccount getAccount(int accountId);
	
}
