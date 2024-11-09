package dao;

import java.sql.SQLException;

import exception.BankingException;
import model.Bank;

public interface BankDAO {
	Bank getBankById(int bankId) throws SQLException, BankingException;
}
