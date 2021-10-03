package cisc327;

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
	
	public deposit(boolean loginStats, Map<String, accounts> balanceStorage, Map<String, double[]> acctLimit, String mode,Scanner sc) {
		this.loginStats = loginStats;
		this.balanceStorage = balanceStorage;
		this.acctLimit = acctLimit;
		this.mode = mode;
		this.sc = sc;
	}
	//deposit transaction
	public String depositProcess() {
		if (this.loginStats) {
			System.out.println("Enter account number + amount in cent, no space needed.");
			String input = this.sc.nextLine();
			if (depositCheck(input)) {
				String acctNum = input.substring(0, 7);
				double amount = Double.parseDouble(input.substring(7))/100;
				String name = this.balanceStorage.get(acctNum).getAccountName();
				//write the amount into daily limit list
				this.acctLimit.get(acctNum)[0] += amount;
				
				String newTransaction = "DEP 0000000 "+ input.substring(7) + " " + acctNum + " " + name + "*";
				return newTransaction;
			}
		}
		else {
			System.out.println("system promt a login");
		}
		return null;
	}
	//check if deposit input is valid
	private boolean depositCheck(String input) {
		
		if (input.length() < 10) {
			System.out.println("invalid input");
			return false;
		}
		
		String acctNum = input.substring(0, 7);
		double amount = Double.parseDouble(input.substring(7))/100;
		//invalid account number
		if (!this.balanceStorage.containsKey(acctNum)) {
			System.out.println("invalid account number");
			return false;
		}
		//invalid amount under atm mode
		if (this.mode.equalsIgnoreCase("atm")) {
			if (amount > 2000) {
				System.out.println("Out of limit");
				return false;
			}
			else if ((amount + this.acctLimit.get(acctNum)[0]) > 5000) {
				System.out.println("Out of limit");
				return false;
			}
		}
		//invalid amount under agent mode
		if (this.mode.equalsIgnoreCase("agent") && amount > 999999.99) {
			System.out.println("Out of limit");
			return false;
		}
		
		return true;
	}

}
