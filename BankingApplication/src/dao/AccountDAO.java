package dao;

import java.sql.SQLException;

import exception.InvalidAccountTypeException;
import model.Account;

public interface AccountDAO {
	public void createAccount(Account account) throws SQLException, InvalidAccountTypeException;
	public void updateAccount(int accId,int cusId,String accType,double bal) throws InvalidAccountTypeException, SQLException;
	public void deleteAccount(int cusId) throws SQLException;
	public void viewAccount (int cusId) throws SQLException;
}
