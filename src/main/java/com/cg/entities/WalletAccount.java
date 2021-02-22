package com.cg.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.cg.util.Status;

@Entity
public class WalletAccount {
	@Id
	private int accountId;
	private double accountBalance;
	private Status status;
	public WalletAccount() {
		super();
	}
	public WalletAccount(int accountId, double accountBalance, Status status) {
		super();
		this.accountId = accountId;
		this.accountBalance = accountBalance;
		this.status = status;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(accountBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + accountId;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WalletAccount other = (WalletAccount) obj;
		if (Double.doubleToLongBits(accountBalance) != Double.doubleToLongBits(other.accountBalance))
			return false;
		if (accountId != other.accountId)
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WalletAccount [accountId=" + accountId + ", accountBalance=" + accountBalance + ", status=" + status
				+ "]";
	}
}
