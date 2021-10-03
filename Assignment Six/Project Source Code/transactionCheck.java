package cisc327;

import java.util.Arrays;

public class transactionCheck {
	private String transaction;
	
	public transactionCheck(String transaction) {
		this.transaction = transaction;
	}
	//check transaction code formuler
	public boolean transactionCheckCons() {
		String transaction = this.transaction;
		if (transaction.equalsIgnoreCase("EOS")) {
			return true;
		}

		String[] details = transaction.split(" ");
		return (transactionCons(transaction)&&transactionCons2(transaction) && transactionCons3(transaction) && transactionCons4(details)
				&& transactionCons5(details) && transactionCons6(details) && transactionCons7(details) && transactionCons8(details) && transactionCons9(details));
	}
	
	//check length
	private static boolean transactionCons(String transaction){
		if (transaction.length() > 61) {
			System.out.println("Exceeding length");
			return false;
		}
		return true;
	}
	
	//Check constrains No.2 ---- First three characters should be the transaction code
	private static boolean transactionCons2(String transaction) {
		//Check 
		String[] code = new String[] {"DEP","WDR","XFR","NEW","DEL","EOS"};
		String transCode = transaction.substring(0,3);
		for (String c : code) {
			if (c.equalsIgnoreCase(transCode)) {
				return true;
			}else if (c.equalsIgnoreCase("EOS") && !transCode.equalsIgnoreCase(c)) {
				System.out.println("First three not transcode");
				return false;
			}
		}
		return false;
	}
	
	//check segments number
	private static boolean transactionCons3(String transaction) {
		if (transaction.equalsIgnoreCase("EOS")) {
			return true;
		}
		int times = 0;
		for (char i : transaction.toCharArray()) {
			if (i == ' ') {
				times++;
			}
		}
		if (times == 4) {
			return true;
		}
		System.out.println("Not separated by space");
		return false;
	}
	
	//check both acct number 
	private static boolean transactionCons4(String[] details) {
		//deposit, create, delete, withdrow will have acctNum1 = 0000000
		if (details[0].equalsIgnoreCase("DEP") || details[0].equalsIgnoreCase("WDR") || details[0].equalsIgnoreCase("NEW") || details[0].equalsIgnoreCase("DEL")) {
			if (details[1].length() == 7) {
				if ((details[3].length() == 7 && details[3].charAt(0) != '0') ||(details[3].equalsIgnoreCase("0000000"))) {
					return true;
				}
			}
			System.out.println("Account number begin with zero or not seven digits");
			return false;
		}
		else if (details[0].equalsIgnoreCase("XFR")){
			if (details[1].length() == 7 && details[1].charAt(0) != '0') {
				if ((details[3].length() == 7 && details[3].charAt(0) != '0') ||(details[3].equalsIgnoreCase("0000000"))) {
					return true;
				}
			}
			System.out.println("Account number begin with zero or not seven digits");
			return false;
		}
		else {
			return false;
		}
	}
	
	//check monetary
	private static boolean transactionCons5(String[] details) {
		if (details[2].length() >= 3 && details[2].length() <= 8) {
			return true;
		}
		System.out.println("Monetary not between 3 and 8 digits");
		return false;
	}
	
	//check name
	private static boolean transactionCons6(String[] details) {
		String name = details[4];
		if (name.length() < 3 || name.length() > 30) {
			System.out.println("Name not between 3 and 30 chars");
			return false;
		}
		return true;
	}
	
	//check transCode
	private static boolean transactionCons7(String[] details) {
		String op = details[0];
		if (op.equalsIgnoreCase("DEP") || op.equalsIgnoreCase("WDR")) {
			if (Integer.parseInt(details[3]) == 0 && details[3].length() == 7) {
				return true;
			}
		}else if(op.equalsIgnoreCase("NEW") || op.equalsIgnoreCase("DEL") || op.equalsIgnoreCase("EOS")) {
			if (Integer.parseInt(details[3]) == 0 && details[3].length() == 7 && details[2].length() == 3) {
				return true;
			}
		}else if(op.equalsIgnoreCase("XFR")) {
			return true;
		}
		System.out.println("Unused not filled with zeros");
		return false;
	}
	
	//check name filling pattern
	private static boolean transactionCons8(String[] details) {
		String op = details[0];
		if (op.equalsIgnoreCase("DEP") || op.equalsIgnoreCase("WDR") || op.equalsIgnoreCase("XFR")) {
			if (details[4].length() == 3) {
				for (char i : details[4].toCharArray()) {
					if (i != '*') {
						System.out.println("Unused not filled with *");
						return false;
					}
				}
				return true;
			}
			System.out.println("Unused not filled with *");
			return false;
		}
		return true;
	}
	
	//check name pattern
	private static boolean transactionCons9(String[] details) {
		String name = details[4];
		if (name.charAt(0) == ' ' || name.charAt(name.length() - 1) == ' ') {
			System.out.println("Name begin or end with space");
			return false;
		}
		return true;
	}
	
	
	public String check() {
		String transaction = this.transaction;
		String[] details = transaction.split(" ");
		if (transaction.equalsIgnoreCase("EOS")){
			System.out.println("True");
			return "True";
		}
		else if (!transactionCons(transaction)) {
		System.out.println("Exceeding length");
		return "Error";
		}
		else if(!transactionCons2(transaction)) {
		System.out.println("First three not transcode");
		return "Error";
		}
		else if(!transactionCons3(transaction)) {
		System.out.println("Not separated by space");
		return "Error";
		}
		else if(!transactionCons4(details)) {
		System.out.println("Account number begin with zero or not seven digits");
		return "Error";
		}
		else if(!transactionCons5(details)) {
		System.out.println("Monetary not between 3 and 8 digits");
		return "Error";
		}
		else if(!transactionCons6(details)) {
		System.out.println("Name not between 3 and 30 chars");
		return "Error";
		}
		else if(!transactionCons7(details)) {
		System.out.println("Unused not filled with zeroes");
		return "Error";
		}
		else if(!transactionCons8(details)) {
		System.out.println("Unused not filled with *");
		return "Error";
		}
		else if(!transactionCons9(details)) {
		System.out.println("Name begin or end with space");
		return "Error";
		}
		else {
		System.out.println("True");
		return "True";
		}
	}
}
