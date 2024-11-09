package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionHistoryUtil {
	private static final String FILE_PATH = "transaction_history.txt";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
//	Method to save transaction details 
	public static void saveTransaction (String transactionType,int accountId,double amount) throws IOException{
		String timestamp = dateFormat.format(new Date()); 
		String record = String.format("%s | %s | Account Id : %d | Amount : %.2f",timestamp, transactionType,accountId, amount);
		try(BufferedWriter writer = new BufferedWriter( new FileWriter(FILE_PATH,true))){
			writer.write(record);
			writer.newLine();
		}
		catch(IOException e) {
			System.err.println("Error writing to transaction history file");
		}
	}
	public static List<String> retriveTransactionHistory(){
		List<String> history = new ArrayList<String>();
		try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
			String line;
			while((line = reader.readLine()) != null) {
				history.add(line);
			}
		}
		catch(IOException e) {
			System.err.println("Error reading transaction history file");
		}
		return history;
	}

}

