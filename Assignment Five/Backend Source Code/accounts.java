package ca.queensu.cisc327;
/**
 * Class to store account info
 * 
 *
 */

public class accounts {
	private int accountNumber;
	private double accountBalance;
	private String accountName;
	
	public accounts(int number,String name, double balance) {
		this.accountNumber = number;
		this.accountBalance = balance;
		this.accountName = name;
	}
	
	public void setBalance(double number) {
		this.accountBalance = number;
	}
	
	public int getAccountNumber() {
		return this.accountNumber;
	}
	
	public double getAccountBalance() {
		return this.accountBalance;
	}
	
	public String getAccountName() {
		return this.accountName;
	}
	
	public String toString() {
		String acctB;
		if (this.accountBalance == 0){
			acctB = "000";
		}else{
			int acctBa = (int)this.accountBalance;
			acctB = Integer.toString(acctBa);
		}
		String a = this.accountNumber + " " + acctB + " " + this.accountName + "*";
		return a;
	}
}
