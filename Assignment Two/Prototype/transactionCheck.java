package cisc327;

public class transactionCheck {
	private String transaction;
	
	public transactionCheck(String transaction) {
		this.transaction = transaction;
	}
	

	public boolean transactionCheckCons() {
		String transaction = this.transaction;
		if (transaction.length() > 61) {
			return false;
		}
		String[] details = transaction.split(" ");
		return (transactionCons2(transaction) && transactionCons3(transaction) && transactionCons4(details)
				&& transactionCons5(details) && transactionCons6(details) && transactionCons7(details) && transactionCons8(details));
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
				return false;
			}
		}
		return false;
	}
	
	private static boolean transactionCons3(String transaction) {
		int times = 0;
		for (char i : transaction.toCharArray()) {
			if (i == ' ') {
				times++;
			}
		}
		if (times == 4) {
			return true;
		}
		return false;
	}
	
	private static boolean transactionCons4(String[] details) {
		if (details[1].length() == 7 || details[1].charAt(0) != '0') {
			if (details[3].length() == 7 || details[3].charAt(0) != '0') {
				return true;
			}
		}
		return false;
	}
	
	private static boolean transactionCons5(String[] details) {
		if (details[2].length() >= 3 && details[2].length() <= 8) {
			return true;
		}
		return false;
	}

	private static boolean transactionCons6(String[] details) {
		String name = details[4];
		if (name.length() < 3 && name.length() > 30) {
			return false;
		}
		for (char i : name.toCharArray()) {
			if (!(Character.isDigit(i) || Character.isLetter(i))){
				return false;
			}
		}
		return true;
	}
	
	private static boolean transactionCons7(String[] details) {
		String op = details[0];
		if (op.equalsIgnoreCase("DEP") || op.equalsIgnoreCase("WDR")) {
			if (Integer.parseInt(details[3]) == 0 && details[3].length() == 7) {
				return true;
			}
		}else if(op.equalsIgnoreCase("NEW") || op.equalsIgnoreCase("DEL") || op.equalsIgnoreCase("EOS")) {
			if (Integer.parseInt(details[2]) == 0 && Integer.parseInt(details[3]) == 0 && details[3].length() == 7 && details[2].length() == 3) {
				return true;
			}
		}else if(op.equalsIgnoreCase("XFR")) {
			return true;
		}
		return false;
	}

	private static boolean transactionCons8(String[] details) {
		String op = details[0];
		if (op.equalsIgnoreCase("DEP") || op.equalsIgnoreCase("WDR") || op.equalsIgnoreCase("XFR")) {
			if (details[4].length() == 3) {
				for (char i : details[4].toCharArray()) {
					if (i != '*') {
						return false;
					}
				}
				return true;
			}
			return false;
		}
		return true;
	}
}
