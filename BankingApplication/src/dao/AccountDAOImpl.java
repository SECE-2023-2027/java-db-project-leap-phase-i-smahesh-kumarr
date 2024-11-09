package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import exception.InvalidAccountTypeException;
import model.Account;
import utility.DBConnection;

public class AccountDAOImpl implements AccountDAO{
	@Override
	public void createAccount(Account account) throws SQLException, InvalidAccountTypeException{
	String sql = "insert into Account(customer_id,bank_id,account_type,balance) values(?,?,?,?)";
	try(Connection con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sql))
	{
			ps.setInt(1,account.getCustomerId());
			ps.setInt(2,account.getBank().getBankId());
			ps.setString(3,account.getAccountType());
			ps.setDouble(4,account.getBalance());
			int result = ps.executeUpdate();
			if(result == 0) {
				throw new InvalidAccountTypeException (""
						+ "Account type not recognized");
			}
		}
	}
	
	@Override
	public void updateAccount(int accId,int cusId,String accType,double bal) throws InvalidAccountTypeException, SQLException {
		String sql = "update account set customer_id = ?,account_type = ? ,balance=? where account_id = ?";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql))
		{
			if(!("Savings".equalsIgnoreCase(accType) || "Current".equalsIgnoreCase(accType))){
			    throw new InvalidAccountTypeException("Account type not recognized");
			}
			ps.setInt(1,cusId);
			ps.setString(2, accType);
			ps.setDouble(3, bal);
			ps.setInt(4,accId);
			int result = ps.executeUpdate();
			if(result > 0) {
				System.out.println("Updated successfully");
			}
		}
	}
	
	@Override 
	public void deleteAccount(int cusId) throws SQLException {
		String sql = "delete from account where customer_id = ?";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1,cusId);
			int result =ps.executeUpdate();
			if(result > 0) {
				System.out.println("Deleted successfully");
			}
		}
	}
	
	@Override 
	public void viewAccount(int cusId) throws SQLException {
		String sql = "select * from account where customer_id = ?";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1,cusId);
			ResultSet rs =ps.executeQuery();
			System.out.println("\nDetails");
			
			if(rs.next()){
				System.out.println("Account Id:"+rs.getInt("account_id"));
				System.out.println("Customer Id:"+rs.getInt("customer_id"));
				System.out.println("Bank Id:"+rs.getInt("bank_id"));
				System.out.println("Account Type:"+rs.getString("account_type"));
				System.out.println("Balance:"+rs.getDouble("balance"));


			}
		}
	}
}

