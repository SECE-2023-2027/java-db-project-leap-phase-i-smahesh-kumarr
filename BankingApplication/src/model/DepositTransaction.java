package model;

import java.sql.Date;

public class DepositTransaction extends Transaction {
public String getDepositMethod() {
		return depositMethod;
	}
	public void setDepositMethod(String depositMethod) {
		this.depositMethod = depositMethod;
	}
private String depositMethod;
 public DepositTransaction(int transactionId, int accountId, String transactionType, double amount,
			Date transactionDate, String depositMethod) {
		super(transactionId, accountId, transactionType, amount, transactionDate);
		this.depositMethod = depositMethod;
	}
 
 @Override 
 public String getTransactionDetails(){
	 return "Transaction type is + " +this.depositMethod;
 }
 
}
