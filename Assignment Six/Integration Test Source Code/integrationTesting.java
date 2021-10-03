package cisc327;

import java.io.*;
import java.util.*;

public class integrationTesting {
	public static void main(String args[]) throws NumberFormatException, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("1 for daily, 2 for weekly");
		String choice = sc.nextLine();
		if (choice.equals("1")) {
			frontendIntegration a = new frontendIntegration(1);
			backendIntegration ab = new backendIntegration(1);
		}else {
			frontendIntegration a = new frontendIntegration(1);
			backendIntegration ab = new backendIntegration(1);
			frontendIntegration b = new frontendIntegration(2);
			backendIntegration bb = new backendIntegration(2);
			frontendIntegration c = new frontendIntegration(3);
			backendIntegration cb = new backendIntegration(3);
			frontendIntegration d = new frontendIntegration(4);
			backendIntegration db = new backendIntegration(4);
			frontendIntegration e = new frontendIntegration(5);
			backendIntegration eb = new backendIntegration(5);
		}
		System.out.println("Test Complete");
	}

}
