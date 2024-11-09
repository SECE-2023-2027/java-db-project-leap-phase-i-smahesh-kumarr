package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dao.TransactionDAO;
import dao.TransactionDAOImpl;
import exception.InvalidTransactionAmountException;
import exception.TransactionFailureException;
import utility.TransactionHistoryUtil;

public class TransactionService {
	private final TransactionDAO transactionDAO;
	private final ExecutorService executeService;
	public TransactionService() {
		super();
		this.transactionDAO = new TransactionDAOImpl();
		this.executeService = Executors.newFixedThreadPool(5);
	}
	
	
	
	public Future<?> deposit(int accountId,double amount) throws SQLException
	{
		
		return executeService.submit(()->{
			try 
			{
			transactionDAO.deposit(accountId, amount);
			TransactionHistoryUtil.saveTransaction("Deposit",accountId,amount);
			}
			catch(InvalidTransactionAmountException | TransactionFailureException | IOException e)
			{
				System.err.println("Errorduring deposit :"+ e.getMessage());
			}
		});
	}
	
	public Future<?> withdraw (int accountId,double amount){
		return executeService.submit(()->{
		try {
			transactionDAO.withdraw(accountId, amount);
			TransactionHistoryUtil.saveTransaction("Withdraw",accountId,amount);
		}
		catch(InvalidTransactionAmountException | TransactionFailureException | IOException e ) {
			System.err.println("Error During Withdraw :"+e.getMessage());
		}
	});
	}
	
	public Future<?> transer_funds (int from_account,int to_account,double amount){
		return executeService.submit(()->{
		try {
			transactionDAO.transferFunds(from_account,to_account,amount);
			TransactionHistoryUtil.saveTransaction("TransferOUT",from_account,amount);
			TransactionHistoryUtil.saveTransaction("TransferIN",to_account,amount);
		}
		catch(InvalidTransactionAmountException | TransactionFailureException | IOException e ) {
			System.err.println("Error During Withdraw :"+e.getMessage());
		}
	});
	}
	
	public void shutDownExecutorService() {
		executeService.shutdown();
		
	}
}
