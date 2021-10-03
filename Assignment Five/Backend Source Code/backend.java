package ca.queensu.cisc327;
//CISC 327 DevsSkipQA
//Class for the backend prototype of the banking system
//assignment 4
import java.io.*;
import java.util.*;


public class backend{
		//Hash map that stores accounts and is modified later
		static Map<String, accounts> balanceStorage;
		//ArrayList to store new valid accounts
		static ArrayList<Integer> newValid;
		//ArrayList to store new master data
		static ArrayList<String> newMaster;
		static boolean end = false;
		static String[] path;
		static String newTrans;
		static String error;
		
		private static boolean checkContent(String[] a){
			if (a.length == 3){
				end = false;
				return true;
			}else{
				System.out.println("Not separated by space");
				end = true;
				return false;
			}
		}
		
		//Read old master file
		private static void readOldMaster(String fileName) throws FileNotFoundException{
			File file = new File(fileName);
			Scanner sc = new Scanner(file);
			String content = "";
			while (sc.hasNextLine()) {
				content += sc.nextLine();
				content += "!";
			}
			if (content.equals("")) {
				sc.close();
			}
			String[] contentList = content.split("!");
			for (String i : contentList) {
				String[] a = i.split(" ");
				if (checkContent(a)){
					if (end == true){
						break;
					}else if (Double.parseDouble(a[1]) < 0 || a[1].charAt(0) == '-') {
						System.out.println("Account balance cannot be less than 0");
						end = true;
						break;
					}else{
						accounts newA = new accounts(Integer.parseInt(a[0]), a[2],Double.parseDouble(a[1]));
						balanceStorage.put(a[0], newA);
					}
				}
			}
			sc.close();
		}
		
		//read new transaction summary file(s)
		private static String readTrans(String fileName) throws FileNotFoundException {
			File file = new File(fileName);
			Scanner sc = new Scanner(file);
			String content = "";
			while (sc.hasNextLine()) {
				content += sc.nextLine();
				content += "!";
			}
			sc.close();
			return content;
		}
	
		//update master accounts with new transaction summary files
		private static void updateMaster(String[] updateFile){
			double balance;
			accounts a;
			for (String i : updateFile) {
				String [] b = i.split(" ");
				//Will not affect balance
				if (b[0].equalsIgnoreCase("NEW") || b[0].equalsIgnoreCase("DEL")){
					continue;
				}else{
					//Update balance
					if (b.length != 1){
						a = balanceStorage.get(b[1]);
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
						if (a.getAccountBalance() < 0) {
							System.out.println("Account balance cannot be less than 0");
							end = true;
						}
					}
				}
			}
		}
			
		//update the valid account list with new transaction summary files
		private static void updateValid(String[] updateFile){
			accounts a;
			for (String i : updateFile) {
				String [] b = i.split(" ");
				//Create
				if (b[0].equalsIgnoreCase("NEW")){
					if (balanceStorage.get(b[1]) != null) {
						System.out.println("Created account must have a new unused account number");
						end = true;
						break;
					}else {
						a = new accounts(Integer.parseInt(b[1]),b[4],0);
						balanceStorage.put(b[1],a);
					}
				//delete
				}else if(b[0].equalsIgnoreCase("DEL")) {
					if(balanceStorage.get(b[1]).getAccountBalance() != 0) {
						System.out.println("Deleted account must have zero balance");
						end = true;
					}else if(!balanceStorage.get(b[1]).getAccountName().equalsIgnoreCase(b[4])) {
						System.out.println("The name must match with the name associated");
						end = true;
					}else {
						balanceStorage.remove(b[1]);
					}
				}
			}
		}
		
		//Sort the master account in descending order
		private static ArrayList<String> reverseSort() {
			accounts[] account = balanceStorage.values().toArray(new accounts[0]);
			int i, j;
			accounts temp; 
	        boolean swapped; 
	        for (i = 0; i < account.length - 1; i++){ 
	            swapped = false; 
	            for (j = 0; j < account.length - i - 1; j++)  
	            { 
	                if (account[j].getAccountNumber() < account[j + 1].getAccountNumber()){ 
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
	        	String b = a.toString();
	        	b = b.substring(0,b.length()-1);
	        	result.add(b);
	        }
	        return result;    
		}
		
		//Create the new valid account list based on old master account + new transaction summary files
		private static void newValidList(ArrayList<String> sortedResult) throws IOException{
	        for (String a : sortedResult) {
				String[] master = a.split(" ");
	        	newValid.add(Integer.parseInt(master[0]));
	        }
		}
		
		//Check constraints of the new master account file
		private static boolean checkMaster(ArrayList<String> result){
			String[] a;
			String[] b;
			//Exceeding length
			for (String info : result) {
				if (info.length()>47) {
					System.out.println("Exceeding length");
					return false;
				}
			}
			//Not separated by space
			int c = 0;
			for (String str: result) {
				for (char i: str.toCharArray()) {
					 if (i == ' ') {
						 c++;
					 }
				 }
				 if (c == 2) {
					 break;
				 }
				 else {
					 System.out.println("Not seprated by space");
					 return false;
			  }
			}
			//Other constraints
			for (String detail : result) {
				boolean split = false;
				if (detail.charAt(detail.length()-1) == ' '){
					split = true;
				}
				String[]details = detail.split(" ");
				//System.out.println(java.util.Arrays.toString(details));
				if (details[0].length() != 7 || details[0].charAt(0) == '0') {
					System.out.println("Account number begin with zero or not seven digits");
					return false;
				}
				if (details[1].length() < 3 || details[1].length() > 8) {
					System.out.println("Monetary not between 3 and 8 digits");
					return false;
				}
				String name = details[2];
				if (name.length() < 3 || name.length() > 30 || details.length > 3 || split == true) {
					System.out.println("Name not between 3 and 30 chars or begin or end with space");
					return false;
				}
			}
			for (int i = 0; i < result.size(); i++) {
				if (i + 1 != result.size()) {
					a = result.get(i).split(" ");
					b = result.get(i+1).split(" ");
					if (Integer.parseInt(a[0]) < Integer.parseInt(b[0])) {
						System.out.println("Master account file is not kept in descending order");
						return false;
					}
				}
			}
			return true;
		}

		//write master account file
		private static void writeMaster(String path) throws IOException {
			int a = 0;
			File f = new File(path);
			FileWriter writer = new FileWriter(f,false);
			if (error.equals(" ")) {
				String written = "";
				for(int i = 0; i < newMaster.size(); i++) {
					 if (a == 0){
						 written += newMaster.get(i);
						 a++;
					 }else{
						 written += "!" + newMaster.get(i);
					 }
				}
				writer.write(written);
			}else{
				writer.write(error);
			}
			writer.close();
		}
		
		//write valid account list
		private static void writeValid(String path) throws IOException {
			int a = 0;
			File f = new File(path);
			FileWriter writer = new FileWriter(f,false);
			if (error.equals(" ")) {
				String written = "";
				for(int i = 0; i < newValid.size(); i++) {
					 if (a == 0){
						 written += Integer.toString(newValid.get(i));
						 a++;
					 }else{
						 written += " " + Integer.toString(newValid.get(i));
					 }
				}
				writer.write(written);
			}else{
				writer.write(error);
			}
			writer.close();
		}
		
		public static void main(String args[]) throws IOException {
			balanceStorage = new HashMap<String, accounts>();
			newValid = new ArrayList<>();
			newMaster = new ArrayList<>();
			newTrans = "";
			end = false;
			error = " ";
			
			path = args;
			int i = 0;
			newTrans = readTrans(args[1]);;
			//args[0] is the old master account file
			readOldMaster(args[0]);
			//read transaction summary file(s)
			//for (String a : args) {
			//	if (i == 0) {
			//		continue;
			//	}else if(i == args.length - 1){
			//		break;
			//	}else {
			//		newTrans += readTrans(a);
			//	}
			//	i++;
			//}
			//System.out.println(newTrans);
			String[] transaction = newTrans.split("!");
			//System.out.println(java.util.Arrays.toString(transaction));
			if (end == true){
				error = "Error";
				writeMaster(args[0]);
				writeValid(args[args.length - 1]);
			}else{
				updateMaster(transaction);
				updateValid(transaction);
				if (end == true){
					error = "Error";
					writeMaster(args[0]);
					writeValid(args[args.length - 1]);
				}else{
					newMaster = reverseSort();
					newValidList(newMaster);
					if (end == true){
						error = "Error";
						writeMaster(args[0]);
						writeValid(args[args.length - 1]);
					}else{
						if (checkMaster(newMaster)){
							error = " ";
							writeMaster(args[0]);
							writeValid(args[args.length - 1]);
							System.out.println("Success");
						}else{
							end = true;
							error = "Error";
							writeMaster(args[0]);
							writeValid(args[args.length - 1]);
						}
					}
				}
			}
		}
}
