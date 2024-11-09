package dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import exception.InvalidTransactionAmountException;
import exception.TransactionFailureException;
import utility.DBConnection;

public class TransactionDAOImpl implements TransactionDAO {

	@Override
	public synchronized void deposit(int accountId, double amount) throws InvalidTransactionAmountException, TransactionFailureException {
		if(amount<=0) {
			throw new InvalidTransactionAmountException("Deposit amount must be greater than 0");
		}
		try(Connection con = DBConnection.getConnection()){
			CallableStatement st = con.prepareCall("{CALL deposit_procedure(?,?)}");
			st.setInt(1,accountId);
			st.setDouble(2,amount);
			st.execute();
		}
		catch (SQLException e) {
			throw new TransactionFailureException("Deposit Failed: "+e.getMessage());
		}
	}

	@Override
	public synchronized void withdraw(int accountId, double amount) throws TransactionFailureException, InvalidTransactionAmountException {
		if(amount<=0) {
			throw new InvalidTransactionAmountException("Withdraw amount must be greater than 0");
		}
		try(Connection con = DBConnection.getConnection()){
			CallableStatement st = con.prepareCall("{CALL withdraw(?,?)}");
			st.setInt(1,accountId);
			st.setDouble(2,amount);
			st.execute();
		}
		catch (SQLException e) {
			throw new TransactionFailureException("WithDraw Failed: "+e.getMessage());
		}
}

	@Override
	public void transferFunds(int from_account, int to_account, double amount) throws TransactionFailureException, InvalidTransactionAmountException {
		if(amount<=0) {
			throw new InvalidTransactionAmountException("Not Transfer Happens");
		}
		try(Connection con = DBConnection.getConnection()){
			CallableStatement st = con.prepareCall("{CALL transfer_funds(?,?,?)}");
			st.setInt(1,from_account);
			st.setInt(2,to_account);
			st.setDouble(3, amount);
			st.execute();
		}
		catch (SQLException e) {
			throw new TransactionFailureException("WithDraw Failed: "+e.getMessage());
		}
		
	}
	
}
