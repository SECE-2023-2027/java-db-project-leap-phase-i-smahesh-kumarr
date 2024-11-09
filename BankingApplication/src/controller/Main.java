package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import exception.BankingException;
import exception.InvalidAccountTypeException;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException, SQLException, BankingException, InvalidAccountTypeException, InterruptedException, ExecutionException {
		BankController bc = new BankController();
		bc.start();
	}
}
