package cisc327;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class accountOperation {
	Scanner sc;
	ArrayList<String> accountList = new ArrayList<>();
	//the accounts that cannot do other transactions 
	ArrayList<String> tempAccountList = new ArrayList<>();
	private String mode;
	private boolean loginStats;
	//Hash Map to store account info during transaction
	Map<String, accounts> balanceStorage;
	//hash map for daily limit, acctLimit <acctNum, [depositLimit, withdrawLimit, transferLimit]>
	Map<String, double[]> acctLimit;
	
	public accountOperation(boolean loginStats, Map<String, accounts> balanceStorage, Map<String, double[]> acctLimit, String mode,ArrayList<String> tempAccountList, Scanner sc) {
		this.loginStats = loginStats;
		this.balanceStorage = balanceStorage;
		this.acctLimit = acctLimit;
		this.mode = mode;
		this.tempAccountList = tempAccountList;
		this.sc = sc;
	}
	//create account operation
	public String createacc() {
		if (this.loginStats && this.mode.equalsIgnoreCase("agent")) {
				System.out.println("Enter 7 digits accounts and name, no space needed.");
			    String newAcct = this.sc.nextLine();
			if(checkAcct(newAcct)) {
				String acctNum = newAcct.substring(0,7);
				String name = newAcct.substring(7);
				accounts a = new accounts(Integer.parseInt(acctNum), 0, name);
				this.balanceStorage.put(acctNum, a);
				System.out.println("account created");
				//mark new account and no other transaction can be done in the same day
				this.tempAccountList.add(acctNum);
				
				String newTransaction = "NEW 0000000 000 " + acctNum + " " + name + "*";
				return newTransaction;
			}
			else {
				System.out.println("creat account failed");
			}
		}
		else {
			System.out.println("system promt a login");
		}
		return null;
	}
	
	//check if a number+name input is valid
	private boolean checkAcct(String newAcct) {
		
		if (newAcct.length() < 10) {
			System.out.println("invalid account");
			return false;
		}
		for (String i : this.tempAccountList) {
			if (i.equalsIgnoreCase(newAcct)) {
				return false;
			}
		}
		String acctNum = newAcct.substring(0,7);
		String name = newAcct.substring(7);
		//check if the account number is valid
		if (!isInteger(acctNum) || acctNum.charAt(0) == '0' || (balanceStorage.containsKey(acctNum) == true)) {
			System.out.println("invalid account number");
			return false;
		}
		//check if the account name is valid
		if (!isAlphanumeric(name) || name.length() < 3 || name.length() > 30 || name.charAt(0) == ' ' || name.charAt(name.length() - 1) == ' ') {
			System.out.println("invalid name");
			return false;
		}
		
		return true;
	}
	//check if a string only contains integer
	private static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	//check if a string only contains letters, number and spaces
	private static boolean isAlphanumeric(String input) {
	    for(int i=0; i < input.length(); i++) {
	    	char ch = input.charAt(i);
	    	if(Character.isLetter(ch) || ch == ' ' || Character.isDigit(ch)) {
	    		continue;
	    	}
	    	return false;
	    }
	    return true;
	}
	
	//delete account operation
	public String deleteacc() {
		if (this.loginStats && this.mode.equalsIgnoreCase("agent")) { 
			System.out.println("Enter 7 digits accounts and name, no space needed.");
			String acct = this.sc.nextLine();
			if(checkAcct(acct)) {
				String acctNum = acct.substring(0,7);
				String name = acct.substring(7);
				
				this.balanceStorage.remove(acctNum);
				System.out.println("account deleted");
				//mark deleted account and no other transaction can be done in the same day
				this.tempAccountList.add(acctNum);
				
				String newTransaction = "DEL 0000000 000 " + acctNum + " " + name + "*";
				return newTransaction;
			}
		}
		else {
			System.out.println("system promt a login");
		}
		return null;
	}

}