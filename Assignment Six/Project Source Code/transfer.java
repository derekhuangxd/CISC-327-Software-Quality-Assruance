package cisc327;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class transfer {
	Scanner sc;
	private boolean loginStats;
	private String mode;
	private Map<String, accounts> balanceStorage = new HashMap<String, accounts>();
	//hash map for daily limit, acctLimit <acctNum, [depositLimit, withdrawLimit, transferLimit]>
	private Map<String, double[]> acctLimit = new HashMap<String, double[]>();
	ArrayList<String> tempAccountList = new ArrayList<>();
	
	public transfer(boolean loginStats, Map<String, accounts> balanceStorage, Map<String, double[]> acctLimit, String mode,Scanner sc, ArrayList<String> tempAccountList) {
		this.loginStats = loginStats;
		this.balanceStorage = balanceStorage;
		this.acctLimit = acctLimit;
		this.mode = mode;
		this.tempAccountList = tempAccountList;
		this.sc = sc;
	}
	//transfer transaction
	public String transferProcess() {
		if (this.loginStats) {
			System.out.println("Enter from account number + to account number + amount in cent, no space needed.");
			String input = this.sc.nextLine();
			if (transferCheck(input)) {
				String fromAcctNum = input.substring(0, 7);
				String toAcctNum = input.substring(7, 14);
				double amount = Double.parseDouble(input.substring(14))/100;
				String name = this.balanceStorage.get(fromAcctNum).getAccountName();
				//write the amount into daily limit list
				this.acctLimit.get(fromAcctNum)[2] += amount;
				System.out.println("Transferred");
				String newTransaction = "XFR " + toAcctNum + " " + input.substring(14) + " " + fromAcctNum + " " + "***" + "!";
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
	
	//check if the transfer input is valid
	private boolean transferCheck(String input) {
		if (input.length() < 17) {
			System.out.println("Invalid input");
			return false;
		}
		
		String fromAcctNum = input.substring(0, 7);
		String toAcctNum = input.substring(7, 14);
		double amount = Double.parseDouble(input.substring(14));
		String amountCheck = input.substring(14);
		int checkAmount = 0;
		for (char a : amountCheck.toCharArray()){
			if (Character.isDigit(a)){
				continue;
			}else{
				checkAmount++;
			}
		}
		if (checkAmount != 0){
			System.out.println("Invalid amount");
			return false;
		}
		if (this.tempAccountList.contains(fromAcctNum) || this.tempAccountList.contains(toAcctNum)) {
			System.out.println("Transaction not allowed");
			return false;
		}
		//invalid account number
		if (!this.balanceStorage.containsKey(fromAcctNum) || !this.balanceStorage.containsKey(toAcctNum)) {
			System.out.println("Invalid account number");
			return false;
		}
		
		if (this.mode.equalsIgnoreCase("atm")) {
			//out of single transfer limit under atm mode
			if (amount > 1000000) {
				System.out.println("Out of limit");
				return false;
			}
			//out of from account's daily limit under atm mode
			else if(amount + this.acctLimit.get(fromAcctNum)[2] > 1000000) {
				System.out.println("Out of limit");
				return false;
			}
			//insufficient balance in from account
			else if(amount > this.balanceStorage.get(fromAcctNum).getAccountBalance()) {
				System.out.println("Insufficient balance");
				return false;
			}
		}
		//invalid amount under agent mode
		if (mode.equalsIgnoreCase("agent") && amount > 99999999) {
			System.out.println("Out of limit");
			return false;
		}
		
		return true;
		
	}
}
