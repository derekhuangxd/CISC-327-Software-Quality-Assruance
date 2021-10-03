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
	static ArrayList<String> accountList;
	//Stored the transaction summary file
	static ArrayList<String> transactionList;
	//Temp file to store recent actions on account list
	static ArrayList<String> tempAccountList;
	//Keep track of the mode
	private static String mode;
	//Check if the machine can be logged in
	private static boolean loginStats;
	//Store the temp summary file for later update
	private static String tempSummary;
	//Hash Map to store account info during transaction
	static Map<String, accounts> balanceStorage;
	//hash map for daily limit, acctLimit <acctNum, [depositLimit, withdrawLimit, transferLimit]>
	static Map<String, double[]> acctLimit;
	static String[] path;
	//Login
	private static void login(Scanner sc) throws IOException {
		//if not logged in
		if (loginCheck()) {
				System.out.println("Please enter the mode");
				if (sc.hasNextLine()){
					String modeInput = sc.nextLine();
					if (modeInput.equalsIgnoreCase("login")){
						System.out.println("No Subsequent login");
						tempSummary += "EOS";
						writeFile();
					}
					else if (!modeInput.equalsIgnoreCase("atm") && !modeInput.equalsIgnoreCase("agent")) {
						System.out.println("Illegal mode");
						tempSummary += "EOS";
						writeFile();
					}else {
						if (modeInput.equalsIgnoreCase("atm")){
							mode = "atm";
						}else if(modeInput.equalsIgnoreCase("agent")){
							mode = "agent";
						}
						loginStats = true;
						System.out.println("Login successful");
						}
				}else{
					tempSummary += "EOS";
					writeFile();
				}
		
		}else {
			System.out.println("Unable to login");
		}
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
	private static boolean logout() throws IOException {
		
		// if (tempSummary.contains("EOS") == false){
		// 	tempSummary += "EOS";
		// }
		if (logoutCheck()) {
			loginStats = false;
			mode = null;
			updateMaster();
			mergeFile();
			return true;
		}else {
			System.out.println("Please login correctly");
			mergeFile();
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
	private static boolean createacct(Scanner sc) throws IOException {
		accountOperation a = new accountOperation(loginStats, balanceStorage, mode,tempAccountList,sc);
		String output = a.createacct();
		tempSummary += output;
		if (output.equalsIgnoreCase("EOS")) {
			mergeFile();
			writeFile();
			return false;
		}
		balanceStorage = a.getStorage();
		tempAccountList = a.getTemp();
		return true;
	}
	
	//Delete account
	private static boolean deleteacct(Scanner sc) throws IOException {
		accountOperation a = new accountOperation(loginStats, balanceStorage, mode,tempAccountList,sc);
		String output = a.deleteacct();
		tempSummary += output;
		if (output.equalsIgnoreCase("EOS")) {
			mergeFile();
			writeFile();
			return false;
		}
		balanceStorage = a.getStorage();
		tempAccountList = a.getTemp();
		return true;
	}
	//make a deposit
	private static boolean deposit(Scanner sc) throws IOException {
		deposit a = new deposit(loginStats, balanceStorage, acctLimit,mode,sc,tempAccountList);
		String output = a.depositProcess();
		tempSummary += output;
		if (output.equalsIgnoreCase("EOS")) {
			mergeFile();
			writeFile();
			return false;
		}
		acctLimit = a.getLimit();
		return true;
	}
		

	//Withdraw 
	private static boolean withdraw(Scanner sc) throws IOException {
		withdraw a = new withdraw(loginStats, balanceStorage, acctLimit,mode,sc,tempAccountList);
		String output = a.withdrawProcess();
		tempSummary += output;
		if (output.equalsIgnoreCase("EOS")) {
			mergeFile();
			writeFile();
			return false;
		}
		acctLimit = a.getLimit();
		return true;
	}
	//make a transfer
	private static boolean transfer(Scanner sc) throws IOException {
		transfer a = new transfer(loginStats, balanceStorage, acctLimit, mode,sc,tempAccountList);
		String output = a.transferProcess();
		tempSummary += output;
		if (output.equalsIgnoreCase("EOS")) {
			mergeFile();
			writeFile();
			return false;
		}
		return true;
	}
	
	//Check transaction Summary file
	private static boolean transactionCheck(String transaction) {
		transactionCheck a = new transactionCheck(transaction);
		return a.transactionCheckCons();
	}
	
	
	//Check trans summary file with input by hand
	private static void transactionCheck(String transaction, int a) throws IOException {
		transactionCheck b = new transactionCheck(transaction);
		b.check();
		tempSummary += b.check();
		writeFile();
	}

	
	//Merge Temp Transaction File into Transaction Summary File;
	private static void mergeFile() {
		if (tempSummary.equalsIgnoreCase("EOS")){
			transactionList.add(tempSummary);
		}else{
			String[] tempFile = tempSummary.split("!");
			for (String i : tempFile) {
				//if (transactionCheck(i)) {
					transactionList.add(i);
				//}
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
		}
		if (content.equals("")) {
			sc.close();
			return arrayList;
		}
		String[] contentList = content.split("!");
		for (String i : contentList) {
			arrayList.add(i);
		}
//		for (String i : contentList) {
//			arrayList.add(i);
//		}
		sc.close();
		return arrayList;
	}
	
	
	//Update master file at the end of the day. modify storage
	private static void updateMaster() {
		double balance;
		accounts a;
		String[] updateFile = tempSummary.split("!");
		for (String i : updateFile) {
			String [] b = i.split(" ");
			if (b[0].equalsIgnoreCase("NEW") || b[0].equalsIgnoreCase("DEL")){
				continue;
			}else{
				if (b.length != 1){
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
					default:
						break;
					}
				}
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
	private static void writeFile() throws IOException {
		// FileWriter writer = new FileWriter(path[0]); 
		// ArrayList<String> result = reverseSort();
		// for(String i : result) {
		//   writer.write(i + System.lineSeparator());
		// }
		//writer.close();
		File f = new File(path[1]);
		FileWriter writer2 = new FileWriter(f,false);
		String a = tempSummary;
		if (a.equalsIgnoreCase("EOS") || a.equalsIgnoreCase("True") || a.equalsIgnoreCase("Error")){
			writer2.write(a);
		} else{
			String[] trans = tempSummary.split("!");
			for(String i : trans) {
			 	 writer2.write(i);
			}
			writer2.write("!EOS");
		}
		writer2.close();
	}
	
	//Operation
	private static void operation(ArrayList<String> accountList, ArrayList<String> transactionList, Scanner sc) throws IOException {
		String op;
		boolean flag = true;
		//Asks for input and do functionality
		while (flag){
			if (sc.hasNextLine()){
				op = sc.nextLine();
				op.toLowerCase();
				switch (op){
				case "login":
					if (loginStats == true) {
						System.out.println("No subsequent login");
						flag = false;
						break;
					}else{
						login(sc);
						break;
					}
				case "logout":
					logout();
					writeFile();
					flag=false;
					break;
				case "createacct":
					if (!createacct(sc)){
						flag = false;
					}
					break;
				case "deleteacct":
					if (!deleteacct(sc)){
						flag = false;
					}
					break;
				case "deposit":
					if (!deposit(sc)){
						flag = false;
					}
					break;
				case "withdraw":
					if (!withdraw(sc)){
						flag = false;
					}
					break;
				case "transfer":
					if (!transfer(sc)){
						flag = false;
					}
					break;
				case "checkTrans":
					String trans = sc.nextLine();
					transactionCheck(trans,1);
					flag=false;
					break;
				}
			}else{
				break;
			}
		}	
		sc.close();
	}
	
	
	public static void main(String args[]) throws IOException {
		accountList = new ArrayList<>();
		transactionList = new ArrayList<>();
		tempAccountList = new ArrayList<>();
		mode = null;
		loginStats = false;
		tempSummary = "";
		balanceStorage = new HashMap<String, accounts>();
		acctLimit = new HashMap<String, double[]>();
		double[] limitList = {0.00, 0.00, 0.00};

		Scanner sc = new Scanner(System.in);
		accountList = readFile(args[0]);
		transactionList = readFile(args[1]);
		for (String i : accountList) {
			String[] newList = i.split(" ");
			accounts a = new accounts(Integer.parseInt(newList[0]), newList[2],Double.parseDouble(newList[1]));
			tempAccountList.add(newList[1]);
			balanceStorage.put(newList[0], a);
			acctLimit.put(newList[0],limitList);
		}
		path = args;
		operation(accountList, transactionList, sc);
	}
}