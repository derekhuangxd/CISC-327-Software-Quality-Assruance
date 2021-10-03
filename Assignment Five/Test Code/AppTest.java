
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import org.junit.*;
/**
 * Helper function to run the main function and verify the output
 * 
 * @param old_master                 	A list of string as stored ion old master file
 * 
 * @param old_transaction_summaries      A list of transactions produced by the front-end
 *                                 
 * @param expected_terminal_tails        A list of string expected at the tail
 *                                       of terminal output
 * 
 * @param expected_new_master 			A list of string expected to be in the
 *                                       output of new master file
 * @param expected_valid_account_list	A list of valid accounts updated after back-end operation
 * 
 * @throws Exception
 */
public class AppTest {
	//Create Account- white box input partitioning testing
	 @Test
	    public void CR1T1() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 nameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"), // old master
	                Arrays.asList("NEW 1234568 000 0000000 name"), // old transaction
	                Arrays.asList("Exceeding length"), // expected terminal output
	                Arrays.asList("Error"),// expected new master 
	        		Arrays.asList("Error"));		// expected valid account
	    }
	 
	 @Test
	    public void CR1T2() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 kalsa"), // old master
	                Arrays.asList("NEW 1234568 000 0000000 name"), // old transaction
	                Arrays.asList("Success"), // expected terminal output
	                Arrays.asList("1234568 000 name!1234567 1000 kalsa"),// expected new master 
	        		Arrays.asList("1234568 1234567"));		// expected valid account
	    }
	 @Test
	    public void CR2T1() throws Exception {
	        runAndTest(Arrays.asList("1234567  1000  kalsa"), // old master
	                Arrays.asList("NEW 1234568 000 0000000 name"), // old transaction
	                Arrays.asList("Not separated by space"), // expected terminal output
	                Arrays.asList("Error"),// expected new master 
	        		Arrays.asList("Error"));		// expected valid account
	    }
	 @Test
	    public void CR2T2() throws Exception {
	        runAndTest(Arrays.asList("12345671000kalsa"), // old master
	                Arrays.asList("NEW 1234568 000 0000000 name"), // old transaction
	                Arrays.asList("Not separated by space"), // expected terminal output
	                Arrays.asList("Error"),// expected new master 
	        		Arrays.asList("Error"));		// expected valid account
	    }
	 @Test
	    public void CR2T3() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 kalsa"), // old master
	                Arrays.asList("NEW 1234568 000 0000000 name"), // old transaction
	                Arrays.asList("Success"), // expected terminal output
	                Arrays.asList("1234568 000 name!1234567 1000 kalsa"),// expected new master 
	        		Arrays.asList("1234568 1234567"));		// expected valid account
	    }

	 
	 @Test
	    public void CR4T1() throws Exception {
	        runAndTest(Arrays.asList("1234567 -1000 kalsa"), // old master
	                Arrays.asList("NEW 1234566 000 0000000 name"), // old transaction
	                Arrays.asList("Account balance cannot be less than 0"), // expected terminal output
	                Arrays.asList("Error"),// expected new master 
	        		Arrays.asList("Error"));		// expected valid account
	    }
	 @Test
	    public void CR4T2() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 kalsa"), // old master
	                Arrays.asList("NEW 1234566 000 0000000 name"), // old transaction
	                Arrays.asList("Success"), // expected terminal output
	                Arrays.asList("1234567 1000 kalsa!1234566 000 name"),// expected new master 
	        		Arrays.asList("1234567 1234566"));		// expected valid account
	    }
	 
	 
	 @Test
	    public void CR5T1() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 kalsa"), // old master
	                Arrays.asList("NEW 1234567 000 0000000 name"), // old transaction
	                Arrays.asList("Created account must have a new unused account number"), // expected terminal output
	                Arrays.asList("Error"),// expected new master 
	        		Arrays.asList("Error"));		// expected valid account
	    }
	 
	 @Test
	    public void CR5T2() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 kalsa"), // old master
	                Arrays.asList("NEW 1234566 000 0000000 name"), // old transaction
	                Arrays.asList("Success"), // expected terminal output
	                Arrays.asList("1234567 1000 kalsa!1234566 000 name"),// expected new master 
	        		Arrays.asList("1234567 1234566"));		// expected valid account
	    }
	 
	 @Test
	    public void CR6T1() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 kalsa"), // old master
	                Arrays.asList("NEW 12345678 000 0000000 name"), // old transaction
	                Arrays.asList("Account number begin with zero or not seven digits"), // expected terminal output
	                Arrays.asList("Error"),// expected new master 
	        		Arrays.asList("Error"));		// expected valid account
	    }
	 
	 @Test
	    public void CR6T2() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 kalsa"), // old master
	                Arrays.asList("NEW 0123456 000 0000000 name"), // old transaction
	                Arrays.asList("Account number begin with zero or not seven digits"), // expected terminal output
	                Arrays.asList("Error"),// expected new master 
	        		Arrays.asList("Error"));		// expected valid account
	    }
	 
	 @Test
	    public void CR6T3() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 kalsa"), // old master
	                Arrays.asList("NEW 1234566 000 0000000 name"), // old transaction
	                Arrays.asList("Success"), // expected terminal output
	                Arrays.asList("1234567 1000 kalsa!1234566 000 name"),// expected new master 
	        		Arrays.asList("1234567 1234566"));		// expected valid account
	    }
	 
	 
	 @Test
	    public void CR7T3() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 kalsa"), // old master
	                Arrays.asList("NEW 1234566 000 0000000 name"), // old transaction
	                Arrays.asList("Success"), // expected terminal output
	                Arrays.asList("1234567 1000 kalsa!1234566 000 name"),// expected new master 
	        		Arrays.asList("1234567 1234566"));		// expected valid account
	    }
	 @Test
	    public void CR8T1() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 kalsa"), // old master
	                Arrays.asList("NEW 1234566 000 0000000 na"), // old transaction
	                Arrays.asList("Name not between 3 and 30 chars or begin or end with space"), // expected terminal output
	                Arrays.asList("Error"),// expected new master 
	        		Arrays.asList("Error"));		// expected valid account
	    }
	 
	 @Test
	    public void CR8T2() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 kalsa"), // old master
	                Arrays.asList("NEW 1234566 000 0000000 namenamenamenamenamenamenamename"), // old transaction
	                Arrays.asList("Name not between 3 and 30 chars or begin or end with space"), // expected terminal output
	                Arrays.asList("Error"),// expected new master 
	        		Arrays.asList("Error"));		// expected valid account
	    }
	 
	 @Test
	    public void CR8T5() throws Exception {
	        runAndTest(Arrays.asList("1234567 1000 kalsa"), // old master
	                Arrays.asList("NEW 1234566 000 0000000 name"), // old transaction
	                Arrays.asList("Success"), // expected terminal output
	                Arrays.asList("1234567 1000 kalsa!1234566 000 name"),// expected new master 
	        		Arrays.asList("1234567 1234566"));		// expected valid account
	    }
	 
	//Withdraw- white box output partitioning test cases
    @Test
    public void WR1T1() throws Exception {
        runAndTest(Arrays.asList("1234567 1000000000 namaeorpigju;oaeaeroguihaerligouyhaelriguyaelirugye"), // old master
                Arrays.asList("WDR 1234567 1000 0000000 name"), // old transaction
                Arrays.asList("Exceeding length"), // expected terminal output
                Arrays.asList("Error"),// expected new master 
        		Arrays.asList("Error"));		// expected valid account
    }
    
    @Test
    public void WR1T2() throws Exception {
        runAndTest(Arrays.asList("1234567 1000 name"), // old master
                Arrays.asList("WDR 1234567 1000 0000000 name"), // old transaction
                Arrays.asList("Success"), // expected terminal output
                Arrays.asList("1234567 000 name"),// expected new master 
        		Arrays.asList("1234567"));		// expected valid account
    }
    
    @Test
    public void WR2T1() throws Exception {
        runAndTest(Arrays.asList("1234567 1000 name"), // old master
                Arrays.asList("WDR 1234567 1000 0000000 name"), // old transaction
                Arrays.asList("Success"), // expected terminal output
                Arrays.asList("1234567 000 name"),// expected new master 
        		Arrays.asList("1234567"));		// expected valid account
    }
    
    @Test
    public void WR3T1() throws Exception {
        runAndTest(Arrays.asList("1234568 1000 kalsa", "1234567 1000 name"), // old master
                Arrays.asList("WDR 1234567 1000 0000000 name"), // old transaction
                Arrays.asList("Success"), // expected terminal output
                Arrays.asList("1234568 1000 kalsa!1234567 000 name"),// expected new master 
        		Arrays.asList("1234568 1234567"));		// expected valid account
    }
    
    @Test
    public void WR4T1() throws Exception {
        runAndTest(Arrays.asList("1234567 -1000 name"), // old master
                Arrays.asList("WDR 1234567 1000 0000000 name"), // old transaction
                Arrays.asList("Account balance cannot be less than 0"), // expected terminal output
                Arrays.asList("Error"),// expected new master 
        		Arrays.asList("Error"));		// expected valid account
    }
    
    @Test
    public void WR4T2() throws Exception {
        runAndTest(Arrays.asList("1234567 1000 name"), // old master
                Arrays.asList("WDR 1234567 1000 0000000 name"), // old transaction
                Arrays.asList("Success"), // expected terminal output
                Arrays.asList("1234567 000 name"),// expected new master 
        		Arrays.asList("1234567"));		// expected valid account
    }
    
    @Test
    public void WR5T1() throws Exception {
        runAndTest(Arrays.asList("1234567 1000 na"), // old master
                Arrays.asList("WDR 1234567 1000 0000000 name"), // old transaction
                Arrays.asList("Name not between 3 and 30 chars or begin or end with space"), // expected terminal output
                Arrays.asList("Error"),// expected new master 
        		Arrays.asList("Error"));		// expected valid account
    }
    
    @Test
    public void WR5T2() throws Exception {
        runAndTest(Arrays.asList("1234567 1000 namenamenamenamenamenamenamename"), // old master
                Arrays.asList("WDR 1234567 1000 0000000 name"), // old transaction
                Arrays.asList("Name not between 3 and 30 chars or begin or end with space"), // expected terminal output
                Arrays.asList("Error"),// expected new master 
        		Arrays.asList("Error"));		// expected valid account
    }
    
    
    @Test
    public void WR5T5() throws Exception {
        runAndTest(Arrays.asList("1234567 100 name"), // old master
                Arrays.asList("WDR 1234567 1000 0000000 name"), // old transaction
                Arrays.asList("Account balance cannot be less than 0"), // expected terminal output
                Arrays.asList("Error"),// expected new master 
        		Arrays.asList("Error"));		// expected valid account
    }
    
    @Test
    public void WR6T1() throws Exception {
        runAndTest(Arrays.asList("1234567 18 name"), // old master
                Arrays.asList("WDR 1234567 10 0000000 name"), // old transaction
                Arrays.asList("Monetary not between 3 and 8 digits"), // expected terminal output
                Arrays.asList("Error"),// expected new master 
        		Arrays.asList("Error"));		// expected valid account
    }
    
    @Test
    public void WR6T3() throws Exception {
        runAndTest(Arrays.asList("1234567 1000 name"), // old master
                Arrays.asList("WDR 1234567 1000 0000000 name"), // old transaction
                Arrays.asList("Success"), // expected terminal output
                Arrays.asList("1234567 000 name"),// expected new master 
        		Arrays.asList("1234567"));		// expected valid account
    }
    
    public void runAndTest(List<String> oldMaster, //
            List<String> transactionSummary, //
            List<String> expected_terminal_tails, //
            List<String> expected_newMaster,
            List<String> expected_newValidAccount) throws Exception {

        // setup parameters for the program to run
        // create temporary files
        File master = File.createTempFile("master", ".tmp");
        Files.write(master.toPath(), String.join("\n", oldMaster).getBytes());

        File transactions = File.createTempFile("transactions", ".tmp");
        Files.write(transactions.toPath(), String.join("\n", transactionSummary).getBytes());
        
        File validaccount = File.createTempFile("validaccount", ".tmp");
        

        String[] args = { master.getAbsolutePath(), transactions.getAbsolutePath(), validaccount.getAbsolutePath() };

        // setup stdin & stdout:
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        // run the program
		ca.queensu.cisc327.backend.main(args);

        // capture terminal outputs:
        String[] printed_lines = outContent.toString().split("[\r\n]+");

        // compare the tail of the terminal outputs:
         int diff = printed_lines.length - expected_terminal_tails.size();
         for (int i = 0; i < expected_terminal_tails.size(); ++i) {
             assertEquals(expected_terminal_tails.get(i), printed_lines[i + diff]);
        }

        // compare output file content to the expected content
        String actual_output = new String(Files.readAllBytes(master.toPath()), "UTF-8");
        String[] lines = actual_output.split("[\r\n]+");
        for (int i = 0; i < lines.length; ++i) {
			assertEquals(expected_newMaster.get(i), lines[i]);
		}
       
      // compare output file content to the expected content
        String actual_outputTwo = new String(Files.readAllBytes(validaccount.toPath()), "UTF-8");
         String[] linesTwo = actual_outputTwo.split("[\r\n]+");
         for (int i = 0; i < linesTwo.length; ++i) {
             assertEquals(expected_newValidAccount.get(i), linesTwo[i]);
         }
	}


    /**
     * Retrieve the absolute path of the files in the resources folder
     * 
     * @param relativePath The file's relative path in the resources folder
     *                     (/test/resources)
     * @return the absolute path of the file in the resource folder.
     */
    String getFileFromResource(String relativePath) {
        return new File(this.getClass().getResource(relativePath).getFile()).getAbsolutePath();
    }
}