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
	Boolean delete = false;
	
	public accountOperation(boolean loginStats, Map<String, accounts> balanceStorage, String mode,ArrayList<String> tempAccountList, Scanner sc) {
		this.loginStats = loginStats;
		this.balanceStorage = balanceStorage;
		this.mode = mode;
		this.tempAccountList = tempAccountList;
		this.sc = sc;
	}
	//create account operation
	public String createacct() {
		if (this.loginStats && this.mode.equalsIgnoreCase("agent")) {
				System.out.println("Enter 7 digits accounts and name, no space needed.");
			    String newAcct = this.sc.nextLine();
			if(checkAcct(newAcct)) {
				String acctNum = newAcct.substring(0,7);
				String name = newAcct.substring(7);
				accounts a = new accounts(Integer.parseInt(acctNum), name,0);
				this.balanceStorage.put(acctNum, a);
				System.out.println("Account created");
				//mark new account and no other transaction can be done in the same day
				this.tempAccountList.add(acctNum);
				
				String newTransaction = "NEW " + acctNum +" 000 " + "0000000 " +name + "!";
				return newTransaction;
			}
			else {
				return "EOS";
			}
		}
		else{
			System.out.println("Please login correctly");
			return "EOS";
		}
	}
	
	public ArrayList<String> getTemp(){
		return tempAccountList;
	}
	
	public Map<String,accounts> getStorage(){
		return this.balanceStorage;
	}
	
	//check if a number+name input is valid
	private boolean checkAcct(String newAcct) {
		String acctNum = newAcct.substring(0,7);
		String name = newAcct.substring(7);
		if (delete == false) {
			if (this.balanceStorage.containsKey(acctNum)) {
				System.out.println("Account number used");
				return false;
			}
			//check if the account number is valid
			if (!isInteger(acctNum) || acctNum.charAt(0) == '0' || (balanceStorage.containsKey(acctNum) == true)) {
				System.out.println("Invalid account number");
				return false;
			}
			//check if the account name is valid
			if (!isAlphanumeric(name) || name.length() < 3 || name.length() > 30 || name.charAt(0) == ' ' || name.charAt(name.length() - 1) == ' ') {
				System.out.println("Invalid account name");
				return false;
			}
			
			return true;
		}else {
			if (this.balanceStorage.containsKey(acctNum)) {
				if (this.balanceStorage.get(acctNum).getAccountName().equalsIgnoreCase(name)) {
					return true;
				}
				System.out.println("Invalid account name");
				return false;
			}
			System.out.println("Invalid account");
			return false;
		}
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
	public String deleteacct() {
		if (this.loginStats && this.mode.equalsIgnoreCase("agent")) { 
			System.out.println("Enter 7 digits accounts and name, no space needed.");
			String acct = this.sc.nextLine();
			delete = true;
			if(checkAcct(acct)) {
				String acctNum = acct.substring(0,7);
				String name = acct.substring(7);
				
				this.balanceStorage.remove(acctNum);
				System.out.println("Account deleted");
				//mark deleted account and no other transaction can be done in the same day
				this.tempAccountList.add(acctNum);
				
				String newTransaction = "DEL " + acctNum +" 000 " + "0000000 " + name + "!";
				return newTransaction;
			}else{
				return "EOS";
			}
		}
		else {
			System.out.println("Please login correctly");
			return "EOS";
		}
	}
}