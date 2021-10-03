package cisc327;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class deposit {
	Scanner sc;
	private boolean loginStats;
	private String mode;
	private Map<String, accounts> balanceStorage = new HashMap<String, accounts>();
	//hash map for daily limit, acctLimit <acctNum, [depositLimit, withdrawLimit, transferLimit]>
	private Map<String, double[]> acctLimit = new HashMap<String, double[]>();
	ArrayList<String> tempAccountList = new ArrayList<>();
	
	public deposit(boolean loginStats, Map<String, accounts> balanceStorage, Map<String, double[]> acctLimit, String mode,Scanner sc, ArrayList<String>tempAccountList) {
		this.loginStats = loginStats;
		this.balanceStorage = balanceStorage;
		this.acctLimit = acctLimit;
		this.mode = mode;
		this.sc = sc;
		this.tempAccountList = tempAccountList;
	}
	//deposit transaction
	public String depositProcess() {
		if (this.loginStats) {
			System.out.println("Enter account number + amount in cent, no space needed.");
			String input = this.sc.nextLine();
			if (depositCheck(input)) {
				String acctNum = input.substring(0, 7);
				double amount = Double.parseDouble(input.substring(7));
				String name = this.balanceStorage.get(acctNum).getAccountName();
				//write the amount into daily limit list
				this.acctLimit.get(acctNum)[0] += amount;
				System.out.println("Deposited");
				String newTransaction = "DEP " + acctNum + " "+ input.substring(7) + " " + "0000000 " + "***" + "!";
				return newTransaction;
			}
		}
		else {
			System.out.println("Please login correctly");
			return "EOS";
		}
		return "EOS";
	}
	
	public Map<String,double[]> getLimit() {
		return this.acctLimit;
	}
	//check if deposit input is valid
	private boolean depositCheck(String input) {
		
		if (input.length() < 10) {
			System.out.println("Invalid input");
			return false;
		}
		
		String acctNum = input.substring(0, 7);
		double amount = Double.parseDouble(input.substring(7));
		String amountCheck = input.substring(7);
		for (char i : amountCheck.toCharArray()){
			if (!Character.isDigit(i)){
				System.out.println("Invalid input");
				return false;
			}
		}
		//invalid account number
		if (this.tempAccountList.contains(acctNum)) {
			System.out.println("Transaction not allowed");
			return false;
		}
		if (!this.balanceStorage.containsKey(acctNum)) {
			System.out.println("Invalid account number");
			return false;
		}
		//invalid amount under atm mode
		if (this.mode.equalsIgnoreCase("atm")) {
			if (amount > 200000) {
				System.out.println("Out of limit");
				return false;
			}
			else if ((amount + this.acctLimit.get(acctNum)[0]) > 500000) {
				System.out.println("Out of limit");
				return false;
			}
		}
		//invalid amount under agent mode
		if (this.mode.equalsIgnoreCase("agent") && amount > 99999999) {
			System.out.println("Out of limit");
			return false;
		}
		
		return true;
	}

}
