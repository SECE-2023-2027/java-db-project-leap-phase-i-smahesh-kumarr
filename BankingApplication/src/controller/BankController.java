package controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import exception.BankingException;
import exception.InvalidAccountTypeException;
import model.Account;
import model.Bank;
import model.CurrentAccount;
import model.SavingsAccount;
import service.AccountService;
import service.BankService;
import service.TransactionService;

public class BankController {
	private final AccountService accountService; 
	private  BufferedReader br; 
	private final BankService bankService;
	private final TransactionService transactionService;
//	private TransactionService transactionSerivce;
	
	public BankController() {
		this.accountService = new AccountService();
		this.br = new BufferedReader(new InputStreamReader(System.in));
		this.bankService = new BankService();
		this.transactionService = new TransactionService();
	}
	public void start () throws NumberFormatException, IOException, SQLException, BankingException, InvalidAccountTypeException, InterruptedException, ExecutionException {
		boolean running =true; 
		while(running) {
			displayMenu();
			int choice = Integer.parseInt(br.readLine());
			switch(choice) {
			case 1:{
				this.createAccount();
				break;	
			}
			case 2:{
				this.updateAccount();
				break;
			}
			case 3:{
				this.deleteAccount();
				break;
			}
			case 4:{
				this.viewAccount();
			}
			case 5 :{
				Deposit();
				break;
			}
			case 6 :{
				Withdraw();
				break;
			}
			case 7 :{
				Transfer();
				break;
			}
			case 0 :{
				running = false;
				transactionService.shutDownExecutorService();
				System.out.println("Exiting the app good Bye");
				break;
			}
		}
	}
}
	
	private void Transfer() throws NumberFormatException, IOException, InterruptedException, ExecutionException {
		System.out.println("Enter From_Account Id:");
		int from_id = Integer.parseInt(br.readLine());
		System.out.println("Enter To_Account Id:");
		int to_id = Integer.parseInt(br.readLine());
		System.out.println("Enter the Amount to be Transfer: ");
		double amount = Double.parseDouble(br.readLine());	
		Future<?> future = transactionService.transer_funds(from_id,to_id, amount);
		future.get();
		
	}
	private void Withdraw() throws NumberFormatException, IOException, InterruptedException, ExecutionException {
		System.out.println("Enter Account Id:");
		int id = Integer.parseInt(br.readLine());
		System.out.println("Enter the Amount to be Withdraw: ");
		double amount = Double.parseDouble(br.readLine());	
		Future<?> future = transactionService.withdraw(id, amount);
		future.get();
		
	}
	private void Deposit() throws NumberFormatException, IOException, InterruptedException, ExecutionException, SQLException {
		System.out.println("Enter Account Id:");
		int id = Integer.parseInt(br.readLine());
		System.out.println("Enter the Amount to be Deposited: ");
		double amount = Double.parseDouble(br.readLine());
		
		Future<?> future = transactionService.deposit(id, amount);
		future.get();
		
	}
	public void createAccount() throws SQLException, BankingException, IOException, InvalidAccountTypeException {
		System.out.println("Enter Customer ID: ");	
		int custId = Integer.parseInt(br.readLine());
		System.out.println("Enter bank ID: ");
		Bank bank = bankService.getBankById(Integer.parseInt(br.readLine()));
		System.out.println("Enter Account Type(Savings/Current): ");
		String accountType = br.readLine();
		System.out.println("Enter Initial Balance: ");
		double bal = Double.parseDouble(br.readLine());
		
		if("Savings".equalsIgnoreCase(accountType)) {
			System.out.println("Enter the Interest Rate: ");
			double interest =  Double.parseDouble(br.readLine());
			accountService.createAccount(new SavingsAccount(0,custId,bank,accountType,bal,interest));
		}
		else if("Current".equalsIgnoreCase(accountType)) {
			System.out.println("Enter OverdraftLimit: ");
			double overdraft = Double.parseDouble(br.readLine());
			accountService.createAccount(new CurrentAccount(0,custId,bank,accountType,bal,overdraft));
		}
		else {
			System.out.println("Invalid Account Type");
		}
		
	}
	
	
	public void updateAccount() throws NumberFormatException, IOException, InvalidAccountTypeException, SQLException {
		System.out.print("Enter Account Id: ");
		int accId = Integer.parseInt(br.readLine()); 
		System.out.println("\nEnter the new Details");
		System.out.print("Enter Customer Id: ");
		int cusId = Integer.parseInt(br.readLine());
		System.out.print("Enter Account Type: ");
		String accType = br.readLine();
		System.out.print("Enter Initial Balance: ");
		double bal = Double.parseDouble(br.readLine());
		accountService.updateAccount(accId,cusId, accType, bal);
	}
	
	public void deleteAccount() throws NumberFormatException, IOException, SQLException {
		System.out.print("Enter the customer Id: ");
		int cusId  = Integer.parseInt(br.readLine());
		accountService.deleteAccount(cusId);
	}
	
	public void viewAccount() throws NumberFormatException, IOException, SQLException {
		System.out.print("Enter the customer Id: ");
		int cusId = Integer.parseInt(br.readLine());
		accountService.viewAccount(cusId);
	}
	
	public void displayMenu(){
		System.out.println("--------------Banking Application--------------");
		System.out.println("0.Exit");
		System.out.println("1. Create Account");
		System.out.println("2. Update Account");
		System.out.println("3. Delete Account");
		System.out.println("4. View Account");
		System.out.println("5. Deposit");
		System.out.println("6. WithDraw");
		System.out.println("7. TransferFunds");
		System.out.print("Enter the choice: ");
		
	}
}
