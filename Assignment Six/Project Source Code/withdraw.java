package cisc327;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class withdraw {
	Scanner sc;
	private boolean loginStats;
	private String mode;
	private Map<String, accounts> balanceStorage = new HashMap<String, accounts>();
	//hash map for daily limit, acctLimit <acctNum, [depositLimit, withdrawLimit, transferLimit]>
	private Map<String, double[]> acctLimit = new HashMap<String, double[]>();
	ArrayList<String> tempAccountList = new ArrayList<>();
	
	public withdraw(boolean loginStats, Map<String, accounts> balanceStorage, Map<String, double[]> acctLimit, String mode,Scanner sc, ArrayList<String> tempAccountList) {
		this.loginStats = loginStats;
		this.balanceStorage = balanceStorage;
		this.acctLimit = acctLimit;
		this.mode = mode;
		this.sc = sc;
		this.tempAccountList = tempAccountList;
	}
	//withdraw transaction
	public String withdrawProcess() {
		if (this.loginStats) {
			System.out.println("Enter account number + amount in cent, no space needed.");
			String input = this.sc.nextLine();
			if (withdrawCheck(input)) {
				String acctNum = input.substring(0, 7);
				double amount = Double.parseDouble(input.substring(7))/100;
				String name = this.balanceStorage.get(acctNum).getAccountName();	
				////write the amount into daily limit list
				this.acctLimit.get(acctNum)[1] += amount;	
				System.out.println("Withdrawed");
				String newTransaction = "WDR " + acctNum + " "+ input.substring(7) + " " + "0000000 " + "***" + "!";
				return newTransaction;
			}
		}
		else {
			System.out.println("Please login correctly");
			return "EOS";
		}
		return "EOS";
	}
	
	public Map<String,double[]> getLimit(){
		return this.acctLimit;
	}
	
	//check if the withdraw input is valid
	private boolean withdrawCheck(String input) {
		if (input.length() < 10) {
			System.out.println("Invalid input");
			return false;
		}
		
		String acctNum = input.substring(0, 7);
		double amount = Double.parseDouble(input.substring(7));
		accounts a = balanceStorage.get(acctNum);
		//invalid account number
		if (this.tempAccountList.contains(acctNum)) {
			System.out.println("Transaction not allowed");
			return false;
		}
		if (!this.balanceStorage.containsKey(acctNum)) {
			System.out.println("Invalid account number");
			return false;
		}
		if (a.getAccountBalance() < amount){
			System.out.println("Insufficient balance");
			return false;
		}
		
		if (this.mode.equalsIgnoreCase("atm")) {
			//out of single withdraw limit under atm mode
			if (amount > 1000000) {
				System.out.println("Out of limit");
				return false;
			}
			//out of account daily limit under atm mode
			else if ((amount + this.acctLimit.get(acctNum)[1]) > 500000) {
				System.out.println("Out of limit");
				return false;
			}
		}
		//out of limit under agent mode 
		if (this.mode.equalsIgnoreCase("agent") && amount > 99999999) {
			System.out.println("Out of limit");
			return false;
		}
		
		return true;
	}
}
