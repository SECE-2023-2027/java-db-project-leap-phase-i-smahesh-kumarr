package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.BankingException;
import model.Bank;
import utility.DBConnection;

public class BankDAOImpl implements BankDAO {
	
	@Override
	public Bank getBankById(int bankId) throws SQLException, BankingException {
		String query = "select * from bank where bank_id=?";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(query)){
			ps.setInt(1, bankId);
			String bankName="",bankBranch="";
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				bankName = rs.getString("bank_name");
				bankBranch = rs.getString("bank_branch");
				return new Bank(bankId, bankName, bankBranch);
			}
			throw new BankingException("Bank not found for Id: "+bankId);
		}
	}
}