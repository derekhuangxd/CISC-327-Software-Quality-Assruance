package cisc327;

import java.io.*;
import java.util.*;

//daily script for front end
public class frontendIntegration {
	String merged;
	List<String> inputOne = new ArrayList<>(Arrays.asList("login","atm","logout"));
	List<String> inputTwo = new ArrayList<>(Arrays.asList("login","agent","createacct","1234567cisc327","logout"));
	List<String> inputThree = new ArrayList<>(Arrays.asList("login","agent","createacct","7654321cisc328", "logout"));

	List<String> inputFour = new ArrayList<>(Arrays.asList("login","agent","deposit","123456710000","logout"));
	List<String> inputFive = new ArrayList<>(Arrays.asList("login","agent","deposit","765432110000","logout"));
	List<String> inputSix = new ArrayList<>(Arrays.asList("login", "agent","createacct","9876543cisc330","logout"));

	List<String> inputSeven = new ArrayList<>(Arrays.asList("login","atm","transfer","765432112345671000","logout"));
	List<String> inputEight= new ArrayList<>(Arrays.asList("login","agent","withdraw","12345679000","logout"));
	List<String> inputNine= new ArrayList<>(Arrays.asList("login","agent","withdraw","76543211000","logout"));

	List<String> inputTen = new ArrayList<>(Arrays.asList("login", "agent","createacct","3456789cisc329","logout"));
	List<String> inputEleven = new ArrayList<>(Arrays.asList("login","agent","deposit","345678910000","logout"));
	List<String> inputTwelve = new ArrayList<>(Arrays.asList("login","atm","transfer","123456776543211000","logout"));

	List<String> inputThirteen = new ArrayList<>(Arrays.asList("login","agent","deleteacct","1234567cisc327","logout"));
	List<String> inputFourteen = new ArrayList<>(Arrays.asList("login","agent","createacct","9988776cisc422","logout"));
	List<String> inputFifteen = new ArrayList<>(Arrays.asList("login","agent", "withdraw","34567891000","logout"));
	
	
	public frontendIntegration(int date) throws IOException {
		merged = "";
		if (date == 1) {
			run(inputOne);
			run(inputTwo);
			run(inputThree);
			writeFile("./src/cisc327/DayOne");
		}else if(date == 2) {
			run(inputFour);
			run(inputFive);
			run(inputSix);
			writeFile("./src/cisc327/DayTwo");
		}else if(date == 3) {
			run(inputSeven);
			run(inputEight);
			run(inputNine);
			writeFile("./src/cisc327/DayThree");
		}else if(date == 4){
			run(inputTen);
			run(inputEleven);
			run(inputTwelve);
			writeFile("./src/cisc327/DayFour");
		}else {
			run(inputThirteen);
			run(inputFourteen);
			run(inputFifteen);
			writeFile("./src/cisc327/DayFive");
		}
	}
	
	private void readFile(String path) throws FileNotFoundException {
		File file = new File(path);
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			merged += sc.nextLine();
			merged += "!";
		}
		sc.close();
	}
	
	private void writeFile(String path) throws IOException{
		File f = new File(path);
		FileWriter writer = new FileWriter(f,false);
		String[] trans = merged.split("!");
		for(String i : trans) {
			 writer.write(i);
			 writer.write("!");
		}
		writer.close();
	}
	
	private void run(List<String> input2) throws IOException {
		// setup user input
        String userInput = String.join(System.lineSeparator(), input2);
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        File transaction_summary_file = File.createTempFile("transactions", ".tmp");
        String[] args = { "./src/cisc327/MasterAccount", transaction_summary_file.getAbsolutePath()};
		banking.main(args);
		readFile(transaction_summary_file.getAbsolutePath());
	}
}
