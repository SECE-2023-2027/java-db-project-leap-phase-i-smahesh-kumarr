package model;

public class CurrentAccount extends Account {
   private double overDraft;
   public CurrentAccount(int accountId, int customerId, Bank bank, String accountType, double balance,
			double overDraft) {
		super(accountId, customerId, bank, accountType, balance);
		this.overDraft = overDraft;
	}
 
   public double getOverDraft() {
		return overDraft;
	}
   
	public void setOverDraft(double overDraft) {
		this.overDraft = overDraft;
	}
	


   
}
