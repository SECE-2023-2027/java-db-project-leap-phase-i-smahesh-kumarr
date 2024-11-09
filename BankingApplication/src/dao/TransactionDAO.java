package dao;

import exception.InvalidTransactionAmountException;
import exception.TransactionFailureException;

public interface TransactionDAO {
	void deposit (int accountId,double amount) throws InvalidTransactionAmountException, TransactionFailureException;
	void withdraw (int accountId,double amount) throws TransactionFailureException, InvalidTransactionAmountException;
	void transferFunds (int from_account, int to_account,double amount) throws TransactionFailureException, InvalidTransactionAmountException;
}
