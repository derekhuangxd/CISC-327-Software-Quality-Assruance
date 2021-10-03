package cisc327;
//CISC 327 DevsSkipQA
//Class for the backend prototype of the banking system
//assignment 4
import java.io.*;
import java.util.*;


public class backend{
		//Hash map that stores accounts and is modified later
		static Map<String, accounts> balanceStorage = new HashMap<String, accounts>();
		//ArrayList to store new valid accounts
		static ArrayList<String> newValid = new ArrayList<>();
		//ArrayList to store new master data
		static ArrayList<String> newMaster = new ArrayList<>();
		static ArrayList<String> newAccount = new ArrayList<>();
		static ArrayList<String> delAccount = new ArrayList<>();
		static boolean end = false;
		static boolean check;
		
		
		//Read old master file
		private static void readOldMaster(String fileName) throws FileNotFoundException {
			File file = new File(fileName);
			Scanner sc = new Scanner(file);
			String content = "";
			while (sc.hasNextLine()) {
				content += sc.nextLine();
				content += "!";
			}
			if (content.equals("")) {
				sc.close();
			}else {
				String[] contentList = content.split("!");
				for (String i : contentList) {
					String[] a = i.split(" ");
					if (Double.parseDouble(a[1]) < 0) {
						System.out.println("Account balance cannot be less than 0");
						end = true;
					}
					accounts newA = new accounts(Integer.parseInt(a[0]), a[2],Double.parseDouble(a[1]));
					balanceStorage.put(a[0], newA);
				}
				sc.close();
			}
		}
		
		//read new transaction summary file(s)
		private static String readTrans(String fileName) throws FileNotFoundException {
			File file = new File(fileName);
			Scanner sc = new Scanner(file);
			String content = "";
			while (sc.hasNextLine()) {
				content += sc.nextLine();
			}
			sc.close();
			return content;
		}
	
		//update master accounts with new transaction summary files
		private static void updateMaster(String[] updateFile) {
			double balance;
			accounts a;
			for (String i : updateFile) {
				String [] b = i.split(" ");
				//Will not affect balance
				if (b[0].equalsIgnoreCase("NEW")){
					accounts newAccounts = new accounts(Integer.parseInt(b[1]),b[4],000);
					balanceStorage.put(b[1],newAccounts);
					newAccount.add(b[1]);
				}else if(b[0].equalsIgnoreCase("DEL")) {
					if(balanceStorage.get(b[1]).getAccountBalance() != 0) {
						System.out.println("Deleted account must have zero balance");
						end = true;
					}else if(!balanceStorage.get(b[1]).getAccountName().equalsIgnoreCase(b[4])) {
						System.out.println("The name must match with the name associated");
						end = true;
					}else {
						balanceStorage.remove(b[1]);
						delAccount.add(b[1]);
					}
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
		private static void updateValid(String[] updateFile) {
			accounts a;
			for (String i : updateFile) {
				String [] b = i.split(" ");
				//Create
				if (b[0].equalsIgnoreCase("NEW")){
					if (balanceStorage.get(b[1]) != null && !(newAccount.contains(b[1]))) {
						System.out.println("Created account must have a new unused account number");
						end = true;
					}else {
						a = new accounts(Integer.parseInt(b[1]),b[4],0);
						balanceStorage.put(b[1],a);
					}
				//delete
				}else if(b[0].equalsIgnoreCase("DEL")) {
					if(!delAccount.contains(b[1])) {
						System.out.println("Unable to delete");
						end = true;
					}
				}
			}
		}
		
		//Sort the master account in descending order
		private static ArrayList<String> reverseSort() {
			Object[] accounts = balanceStorage.values().toArray();
			ArrayList<accounts> account = new ArrayList<>();
			for (Object i : accounts) {
				account.add((accounts) i);
			}
			int i, j;
//			accounts temp; 
	        boolean swapped; 
	        for (i = 0; i < account.size() - 1; i++){ 
	            swapped = false; 
	            for (j = 0; j < account.size() - i - 1; j++)  
	            { 
	                if (account.get(j).getAccountNumber() < account.get(j+1).getAccountNumber()){ 
	                	Collections.swap(account, j, j+1);
//	                    temp = account.get(j); 
//	                    account.get(j) = account.get(j + 1); 
//	                    account.get(j + 1) = temp; 
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
		private static ArrayList<String> newValidList(){
			Object[] accounts = balanceStorage.keySet().toArray();
			ArrayList <String> result = new ArrayList<String>();
			for (Object i : accounts) {
				result.add((String) i);
			}
	        return result;
		}
		
		//Check constraints of the new master account file
		private static boolean checkMaster(ArrayList<String> result) {
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
			int c;
			for (String str: result) {
				c = 0;
				for (char i: str.toCharArray()) {
					 if (i == ' ') {
						 c++;
					 }
				 }
				 if (c == 2) {
					 continue;
				 }
				 else {
					 System.out.println(c);
					 System.out.println("Not seprated by space");
					 return false;
			  }
			}
			//Other constraints
			for (String detail : result) {
				String[]details = detail.split(" ");
				if (details[0].length() == 7) {
					continue;
				}else {
					System.out.println("Account number begin with zero or not seven digits");
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
			File f = new File(path);
			FileWriter writer = new FileWriter(f,false);
			if (check == false || end == true) {
				writer.write("Error");
			}else {
				for(String i : newMaster) {
					 writer.write(i);
					 writer.write("!");
				}
			}
			writer.close();
		}
		
		//write valid account list
		private static void writeValid(String path) throws IOException {
			File f = new File(path);
			FileWriter writer = new FileWriter(f,false);
			if (check == false || end == true) {
				writer.write("Error");
			}else {
				for(String i : newValid) {
					 writer.write(i);
					 writer.write("!");
				}
			}
			writer.close();
		}
		

		public static void main(String args[]) throws IOException {
			int i = 0;
			String newTrans = "";
			//args[0] is the old master account file
			readOldMaster(args[0]);
			//read transaction summary file(s)
			newTrans += readTrans(args[1]);
			String[] transaction = newTrans.split("!");
			updateMaster(transaction);
			updateValid(transaction);
			newMaster = reverseSort();
			newValid = newValidList();
			check = checkMaster(newMaster);
			//Write new master file
			writeMaster(args[0]);
			writeValid("./src/cisc327/Validaccount");
			System.out.println("Backend Success");
		}
}
