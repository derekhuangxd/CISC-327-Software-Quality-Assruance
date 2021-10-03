package cisc327;

import java.io.IOException;

public class backendIntegration {
	public backendIntegration(int a) {
		runBackend(findPath(a));
	}
	
	private String findPath(int a) {
		if (a == 1) {
			return "./src/cisc327/DayOne";
		}else if(a == 2) {
			return "./src/cisc327/DayTwo";
		}else if(a == 3) {
			return "./src/cisc327/DayThree";
		}else if(a == 4) {
			return "./src/cisc327/DayFour";
		}else {
			return "./src/cisc327/DayFive";
		}
	}
	
	private void runBackend(String transactionPath) {
		String[] args = { "./src/cisc327/MasterAccount", transactionPath};
		try {
			backend.main(args);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
