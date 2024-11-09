package service;
import java.sql.SQLException;
import dao.AccountDAO;
import dao.AccountDAOImpl;
import exception.InvalidAccountTypeException;
import model.Account;

public class AccountService {
	private final AccountDAO accountDAO;
	
	public AccountService() {
		this.accountDAO= new AccountDAOImpl();
	}
	public void createAccount (Account account) throws SQLException , InvalidAccountTypeException {
		accountDAO.createAccount(account);
	}
	public void updateAccount(int accId,int cusId,String accType,double bal) throws InvalidAccountTypeException, SQLException {
		accountDAO.updateAccount(accId,cusId, accType, bal);
	}
	public void deleteAccount(int cusId) throws SQLException {
		accountDAO.deleteAccount(cusId);
	}
	public void viewAccount(int cusId) throws SQLException {
		accountDAO.viewAccount(cusId);
	}
}
