package com.cg.dto;

public class TransactionDto {

	int recieverAccountId;
	double amount;
	public TransactionDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TransactionDto(int recieverAccountId, double amount) {
		super();
		this.recieverAccountId = recieverAccountId;
		this.amount = amount;
	}
	public int getRecieverAccountId() {
		return recieverAccountId;
	}
	public void setRecieverAccountId(int recieverAccountId) {
		this.recieverAccountId = recieverAccountId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "TransactionDto [recieverAccountId=" + recieverAccountId + ", amount=" + amount + "]";
	}
}
