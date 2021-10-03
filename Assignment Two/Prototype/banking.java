package cisc327;
/**
 * CISC 327
 * Assignment 2 - Prototype
 * Team - DevsSkipQA
 */
import java.io.*;
import java.util.*;


public class banking {
	//Stored the valid account list
	static ArrayList<String> accountList = new ArrayList<>();
	//Stored the transaction summary file
	static ArrayList<String> transactionList = new ArrayList<>();
	//Temp file to store recent actions on account list
	static ArrayList<String> tempAccountList = new ArrayList<>();
	//Check if logouted
	private static boolean islogout = false;
	//Keep track of the mode
	private static String mode;
	//Check if the machine can be logged in
	private static boolean loginStats = false;
	//Store the temp summary file for later update
	private static String tempSummary = "";
	//Hash Map to store account info during transaction
	static Map<String, accounts> balanceStorage = new HashMap<String, accounts>();
	//hash map for daily limit, acctLimit <acctNum, [depositLimit, withdrawLimit, transferLimit]>
	static Map<String, double[]> acctLimit = new HashMap<String, double[]>();
	
	//Login
	private static void login(Scanner sc) {
		//if not logged in
		if (loginCheck()) {
			System.out.println("Please enter the mode");
			String modeInput = sc.nextLine();
			if (!modeInput.equalsIgnoreCase("atm") && !modeInput.equalsIgnoreCase("agent")) {
				System.out.println("Login Failed");
			}else {
				if (modeCheck()) {
					mode = modeInput;
					loginStats = true;
					System.out.println("Login Successful");
				}
			}
		}else {
			System.out.println("Login Failed");
		}
	}
	
	
	//Check mode
	private static boolean modeCheck() {
		if (mode == null) {
			return true;
		}
		if (mode.equalsIgnoreCase("ATM") || mode.equalsIgnoreCase("AGENT")) {
			return false;
		}
		return true;
	}
	
	//Check login constraints
	private static boolean loginCheck() {
		if (!loginStats) {
			return true;//true = not login yet
		}else {
			return false;//false = login
		}
		
	}
	
	//Logout
	private static boolean logout(Scanner sc) {
		if (logoutCheck()) {
			islogout = true;
			tempSummary += "EOS";
			loginStats = false;
			mode = null;
			updateMaster();
			mergeFile();
			return true;
		}else {
			System.out.println("Logout Failed");
		}
		return false;
	}
	
	//Check logout constraints
	private static boolean logoutCheck() {
		if (loginStats == true) {
			return true;
		}
		return false;
	}
	//Create account
	private static void createacc(Scanner sc) {
		accountOperation a = new accountOperation(loginStats, balanceStorage, acctLimit, mode,tempAccountList,sc);
		tempSummary += a.createacc();
	}
	
	//Delete account
	private static void deleteacc(Scanner sc) {
		accountOperation a = new accountOperation(loginStats, balanceStorage, acctLimit, mode,tempAccountList,sc);
		tempSummary += a.deleteacc();
	}
	//make a deposit
	private static void deposit(Scanner sc) {
		deposit a = new deposit(loginStats, balanceStorage, acctLimit,mode,sc);
		tempSummary += a.depositProcess();
	}
		

	//Withdraw 
	private static void withdraw(Scanner sc) {
		withdraw a = new withdraw(loginStats, balanceStorage, acctLimit,mode,sc);
		tempSummary += a.withdrawProcess();
	}
	//make a transfer
	private static void transfer(Scanner sc) {
		transfer a = new transfer(loginStats, balanceStorage, acctLimit, mode,sc);
		tempSummary += a.transferProcess();
	}
	
	//Check transaction Summary file
	private static boolean transactionCheck(String transaction) {
		transactionCheck a = new transactionCheck(transaction);
		return a.transactionCheckCons();
	}
	
	//Merge Temp Transaction File into Transaction Summary File;
	private static void mergeFile() {
		String[] tempFile = tempSummary.split("@");
		for (String i : tempFile) {
			if (transactionCheck(i)) {
				transactionList.add(i);
			}
		}
	}
	
	//Read transaction Summary File and master account file
	private static ArrayList<String> readFile(String fileName) throws FileNotFoundException {
		ArrayList<String> arrayList = new ArrayList<>();
		File file = new File(fileName);
		Scanner sc = new Scanner(file);
		String content = "";
		while (sc.hasNextLine()) {
			content += sc.nextLine();
			content += "*";
		}
		if (content.equals("")) {
			sc.close();
			return arrayList;
		}
		String[] contentList = content.split("*");
		for (String i : contentList) {
			arrayList.add(i);
		}
		sc.close();
		return arrayList;
	}
	
	//Update master file at the end of the day. modify storage
	private static void updateMaster() {
		double balance;
		accounts a;
		String[] updateFile = tempSummary.split("*");
		for (String i : updateFile) {
			String [] b = i.split(" ");
			a = balanceStorage.get(b[1]);
			//Already stored NEW and DEL so dont need to check
			switch (b[0]) {
			case "DEP":
				balance = a.getAccountBalance();
				a.setBalance(balance + Double.parseDouble(b[2]));
				break;
			case "WDR":
				balance = a.getAccountBalance();
				a.setBalance(balance - Double.parseDouble(b[2]));
				break;
			case "XFR":
				accounts to = balanceStorage.get(b[3]);
				balance = a.getAccountBalance();
				a.setBalance(balance - Double.parseDouble(b[2]));
				to.setBalance(balance + Double.parseDouble(b[2]));
				break;
			}
		}
	}
		
	//Reverse sort the storage file so it is in decending order
	private static ArrayList<String> reverseSort() {
		accounts[] account = (accounts[]) balanceStorage.values().toArray();
		int i, j;
		accounts temp; 
        boolean swapped; 
        for (i = 0; i < account.length - 1; i++){ 
            swapped = false; 
            for (j = 0; j < account.length - i - 1; j++)  
            { 
                if (Integer.parseInt(account[j].getAccountName()) > Integer.parseInt(account[j + 1].getAccountName())){ 
                    temp = account[j]; 
                    account[j] = account[j + 1]; 
                    account[j + 1] = temp; 
                    swapped = true; 
                } 
            } 
            if (swapped == false) {
                break; 
            }
        } 
        ArrayList <String> result = new ArrayList<String>();
        for (accounts a : account) {
        	result.add(a.toString());
        }
        return result;
        
        
	}
	//Write transaction Summary File and Master Account File
	private static void writeFile(String[] path) throws IOException {
		FileWriter writer = new FileWriter(path[0]); 
		ArrayList<String> result = reverseSort();
		for(String i : result) {
		  writer.write(i + System.lineSeparator());
		}
		writer.close();
		FileWriter writer2 = new FileWriter(path[1]); 
		for(String i : transactionList) {
		  writer2.write(i + System.lineSeparator());
		}
		writer2.close();
		
	}
	
	//Operation
	private static void operation(ArrayList<String> accountList, ArrayList<String> transactionList, String[] path,Scanner sc) throws IOException {
		String op;
		boolean flag = true;
		//Asks for input and do functionality
		while (flag){
			op = sc.nextLine();
			op.toLowerCase();
			switch (op){
			case "login":
				login(sc);
				break;
			case "logout":
				logout(sc);
				writeFile(path);
				break;
			case "createacc":
				createacc(sc);
				break;
			case "deleteacc":
				deleteacc(sc);
				break;
			case "deposit":
				deposit(sc);
				break;
			case "withdraw":
				withdraw(sc);
				break;
			case "transfer":
				transfer(sc);
				break;
			}
			if (islogout) {
				writeFile(path);
				flag = false;
			}
		}	
	}
	
	
	public static void main(String args[]) throws IOException {
		//Read both input files into list
		Scanner sc = new Scanner(System.in);
		accountList = readFile(args[0]);
		transactionList = readFile(args[1]);
		for (String i : accountList) {
			String[] newList = i.split(" ");
			accounts a = new accounts(Integer.parseInt(newList[0]), Integer.parseInt(newList[1]), newList[2]);
			tempAccountList.add(newList[1]);
			balanceStorage.put(newList[0], a);
		}
		operation(accountList, transactionList, args,sc);
	}
}