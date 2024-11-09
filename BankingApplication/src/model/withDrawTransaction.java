package model;

import java.sql.Date;

public class withDrawTransaction extends Transaction {
	private String withdrawMethod;
	public String getWithdrawMethod() {
		return withdrawMethod;
	}

	public void setWithdrawMethod(String withdrawMethod) {
		this.withdrawMethod = withdrawMethod;
	}

	public withDrawTransaction(int transactionId, int accountId, String transactionType, double amount,
			Date transactionDate, String withdrawMethod) {
		super(transactionId, accountId, transactionType, amount, transactionDate);
		this.withdrawMethod = withdrawMethod;
	}

	 @Override 
	 public String getTransactionDetails() {
		 return "Transaction type is + " +this.withdrawMethod;
	 }
	
}
