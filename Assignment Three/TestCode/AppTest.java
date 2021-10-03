package ca.queensu.cisc327;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import org.junit.*;

public class AppTest {

    //Login
    @Test
    public void R1T1() throws Exception {
    	System.out.println("\n\nLogin");
    	System.out.println("R1T1");
        runAndTest(Arrays.asList("logout"), //
                Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Please login correctly"), //
                Arrays.asList(""));
    }
    @Test
    public void R1T2() throws Exception {
    	System.out.println("\nR1T2");
        runAndTest(Arrays.asList("createacct"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Please login correctly"), //
                Arrays.asList("EOS"));
    }
    @Test
    public void R1T3() throws Exception {
    	System.out.println("\nR1T3");
        runAndTest(Arrays.asList("deleteacct"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Please login correctly"), //
                Arrays.asList("EOS"));
    }
    @Test
    public void R1T4() throws Exception {
    	System.out.println("\nR1T4");
        runAndTest(Arrays.asList("deposit"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Please login correctly"), //
                Arrays.asList("EOS"));
    }
    @Test
    public void R1T5() throws Exception {
    	System.out.println("\nR1T5");
        runAndTest(Arrays.asList("withdraw"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Please login correctly"), //
                Arrays.asList("EOS"));
    }
    @Test
    public void R1T6() throws Exception {
    	System.out.println("\nR1T6");
        runAndTest(Arrays.asList("transfer"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Please login correctly"), //
                Arrays.asList("EOS"));
    }
    @Test
    public void R2T1() throws Exception {
    	System.out.println("\nR2T1");
        runAndTest(Arrays.asList("login","login"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("No Subsequent login"), //
                Arrays.asList("EOS"));
    }
    @Test
    public void R3T1() throws Exception {
    	System.out.println("\nR3T1");
        runAndTest(Arrays.asList("login","abc"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Illegal mode"), //
                Arrays.asList("EOS"));
    }
    @Test
    public void R3T2() throws Exception {
    	System.out.println("\nR3T2");
        runAndTest(Arrays.asList("login","atm","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Login successful"),
                Arrays.asList(""));
    }
    @Test
    public void R3T3() throws Exception {
    	System.out.println("\nR3T3");
    	runAndTest(Arrays.asList("login","agent","logout"), //
    			Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Login successful"),
                Arrays.asList(""));
    }
    @Test
    public void R4T1() throws Exception {
    	System.out.println("\nR4T1");
        runAndTest(Arrays.asList("login","atm","createacct","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Please login correctly"), //
                Arrays.asList("EOS"));
    }
    

    //Logout
    @Test
    public void loR1T1() throws Exception {
    	System.out.println("\n\nLogout");
    	System.out.println("\nR1T1");
        runAndTest(Arrays.asList("login","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Illegal mode"),
                Arrays.asList("EOS"));
    }
    @Test
    public void loR2T1() throws Exception {
    	System.out.println("\nR2T1");
        runAndTest(Arrays.asList("logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Please login correctly"),
                Arrays.asList(""));
    }
    @Test
    public void loR3T1() throws Exception {
    	System.out.println("\nR3T1");
        runAndTest(Arrays.asList("login","logout","transfer"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
                Arrays.asList("Please login correctly"),
                Arrays.asList("EOSEOS"));
    }
    
  //Create Account 
    // @Test
    // public void crR1T1() throws Exception {
    //     System.out.println("\n\nCreate Account");
    //     System.out.println("caR1T1");
    //     runAndTest(Arrays.asList("login","atm","createacct","logout"), //
    //     		Arrays.asList("1010201","adjakjsd","1000"), //
    //     Arrays.asList("Please login correctly"),
    //     Arrays.asList("EOS"));
    // }
    
    @Test
    public void crR2T1() throws Exception {
        System.out.println("\ncaR2T1");
        runAndTest(Arrays.asList("login","agent","createacct","0123456789","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account number"),
        Arrays.asList("EOS"));
    }
    
    @Test
    public void crR2T2() throws Exception {
        System.out.println("\ncaR2T2");
        runAndTest(Arrays.asList("login","agent","createacct","abc@123","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account number"),
        Arrays.asList("EOS"));
    }
    
    @Test
    public void crR2T3() throws Exception {
        System.out.println("\ncaR2T3");
        runAndTest(Arrays.asList("login","agent","createacct","abc@123","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account number"),
        Arrays.asList("EOS"));
    }
    
    @Test
    public void crR2T4() throws Exception {
        System.out.println("\ncaR2T4");
        runAndTest(Arrays.asList("login","agent","createacct","0123456789","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account number"),
        Arrays.asList("EOS"));
    }
    @Test
    public void crR2T5() throws Exception {
        System.out.println("\ncaR2T5");
        runAndTest(Arrays.asList("login","agent","createacct","0000000","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account number"),
        Arrays.asList("EOS"));
    }
    @Test
    public void crR2T6() throws Exception {
        System.out.println("\ncaR2T6");
        runAndTest(Arrays.asList("login","agent","createacct","0000000","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account number"),
        Arrays.asList("EOS"));
    }
    @Test
    public void crR2T7() throws Exception {
        System.out.println("\ncaR2T7");
        runAndTest(Arrays.asList("login","agent","createacct","1234567","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account name"),
        Arrays.asList("EOS"));
    }
    @Test
    public void crR3T1() throws Exception {
        System.out.println("\ncaR3T1");
        runAndTest(Arrays.asList("login","agent","createacct","1234567","logout"), //
        Arrays.asList("1234567","Canada11","000"), //
        Arrays.asList("Account number used"),
        Arrays.asList("EOS"));
    }
    @Test
    public void crR3T2() throws Exception {
        System.out.println("\ncaR3T2");
        runAndTest(Arrays.asList("login","agent","createacct","1234568","logout"), //
        Arrays.asList("1234567","Canada11","000"), //
        Arrays.asList("Invalid account name"),
        Arrays.asList("EOS"));
    }
    @Test
    public void crR4T1() throws Exception {
        System.out.println("\ncaR4T1");
        runAndTest(Arrays.asList("login","agent","createacct","1234568 @ aB1","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account name"),
        Arrays.asList("EOS"));
    }
    @Test
    public void crR4T2() throws Exception {
        System.out.println("\ncaR4T2");
        runAndTest(Arrays.asList("login","agent","createacct","123456812","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account name"),
        Arrays.asList("EOS"));
    }
    @Test
    public void crR4T3() throws Exception {
        System.out.println("\ncaR4T3");
        runAndTest(Arrays.asList("login","agent","createacct","1234568 CangLing97","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account name"),
        Arrays.asList("EOS"));
    }
    @Test
    public void crR4T4() throws Exception {
        System.out.println("\ncaR4T4");
        runAndTest(Arrays.asList("login","agent","createacct","1234568@ aB1","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account name"),
        Arrays.asList("EOS"));
    }
    @Test
    public void crR4T5() throws Exception {
        System.out.println("\ncaR4T5");
        runAndTest(Arrays.asList("login","agent","createacct","1234568CangLing97 ","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account name"),
        Arrays.asList("EOS"));
    }
    @Test
    public void crR4T6() throws Exception {
        System.out.println("\ncaR4T6");
        runAndTest(Arrays.asList("login","agent","createacct","1234568CangLing97","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Account created"),
        Arrays.asList("NEW 1234568 000 0000000 CangLing97","EOS"));
    }

    //Delete Account

    @Test
    public void delR2T1() throws Exception {
        System.out.println("\ndelR2T1");
        runAndTest(Arrays.asList("login","agent","deleteacct","7654321","logout"), //
        Arrays.asList("1234567","Canada11","000"), //
        Arrays.asList("Invalid account"),
        Arrays.asList("EOS"));
    }
    @Test
    public void delR2T2() throws Exception {
        System.out.println("\ndelR2T2");
        runAndTest(Arrays.asList("login","agent","deleteacct","1234567","logout"), //
        Arrays.asList("1234567","Canada11","000"), //
        Arrays.asList("Invalid account name"),
        Arrays.asList("EOS"));
    }
    @Test
    public void delR3T1() throws Exception {
        System.out.println("\ndelR3T1");
        runAndTest(Arrays.asList("login","agent","deleteacct","1234567notmatchname","logout"), //
        Arrays.asList("1234567","Canada11","000"), //
        Arrays.asList("Invalid account name"),
        Arrays.asList("EOS"));
    }

    @Test
    public void delR4T1() throws Exception {
        System.out.println("\ndelR4T1");
        runAndTest(Arrays.asList("login","agent","transfer","12345672234567000","logout"), //
        		Arrays.asList("1010201","adjakjsd","1000"), //
        Arrays.asList("Invalid account number"),
        Arrays.asList("EOS"));
    }

    //deposit
    @Test
    public void deR1T1() throws Exception {
        System.out.println("\n\ndeposit");
        System.out.println("\nR1T1");
        runAndTest(Arrays.asList("login","atm","deposit","12345671000","logout"), //
                Arrays.asList("1234567","aaaaaaaa","10000"), //
                Arrays.asList("Deposited"), //
                Arrays.asList("DEP 1234567 1000 0000000 ***", "EOS"));
    }

    @Test
    public void deR1T2() throws Exception {
        System.out.println("\nR1T2");
        runAndTest(Arrays.asList("login","atm","deposit","33335671000","logout"), //
                Arrays.asList("1234567","aaaaaaaa","10000"), //
                Arrays.asList("Invalid account number"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void deR1T3() throws Exception {
        System.out.println("\nR1T3");
        runAndTest(Arrays.asList("login","atm","deposit","12345671234","logout"), //
                Arrays.asList("1234567","aaaaaaaa","10000"), //
                Arrays.asList("Deposited"), //
                Arrays.asList("DEP 1234567 1234 0000000 ***", "EOS"));
    }

    @Test
    public void deR1T4() throws Exception {
        System.out.println("\nR1T4");
        runAndTest(Arrays.asList("login", "atm", "deposit", "1234567-20", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000"), //
                Arrays.asList("Invalid input"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void deR1T5() throws Exception {
        System.out.println("\nR1T5");
        runAndTest(Arrays.asList("login", "atm", "deposit", "123456720000000000", "logout"), //
                Arrays.asList("123456", "aaaaaaaa", "10000"), //
                Arrays.asList("Out of limit"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void deR1T6() throws Exception {
        System.out.println("\nR1T6");
        runAndTest(Arrays.asList("login", "atm", "deposit", "12345671000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000"), //
                Arrays.asList("Deposited"), //
                Arrays.asList("DEP 1234567 1000 0000000 ***", "EOS"));
    }

    @Test
    public void deR1T7() throws Exception {
        System.out.println("\nR1T7");
        runAndTest(Arrays.asList("login", "atm", "deposit", "1234567150000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000"), //
                Arrays.asList("Deposited"), //
                Arrays.asList("DEP 1234567 150000 0000000 ***", "EOS"));
    }

    @Test
    public void deR1T9() throws Exception {
        System.out.println("\nR1T9");
        runAndTest(Arrays.asList("login", "agent", "deposit", "12345671000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000"), //
                Arrays.asList("Deposited"), //
                Arrays.asList("DEP 1234567 1000 0000000 ***", "EOS"));
    }

    @Test
    public void deR1T10() throws Exception {
        System.out.println("\nR1T10");
        runAndTest(Arrays.asList("login", "agent", "deposit", "1234567150000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000"), //
                Arrays.asList("Deposited"), //
                Arrays.asList("DEP 1234567 150000 0000000 ***", "EOS"));
    }

    @Test
    public void deR1T11() throws Exception {
        System.out.println("\nR1T11");
        runAndTest(Arrays.asList("login", "agent", "deposit", "1234567200000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000"), //
                Arrays.asList("Deposited"), //
                Arrays.asList("DEP 1234567 200000 0000000 ***", "EOS"));
    }

    @Test
    public void deR1T12() throws Exception {
        System.out.println("\nR1T12");
        runAndTest(Arrays.asList("login", "agent", "deposit", "12345671500000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000"), //
                Arrays.asList("Deposited"), //
                Arrays.asList("DEP 1234567 1500000 0000000 ***", "EOS"));
    }

    @Test
    public void deR1T13() throws Exception {
        System.out.println("\nR1T13");
        runAndTest(Arrays.asList("login", "agent", "deposit", "123456799999999", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000"), //
                Arrays.asList("Deposited"), //
                Arrays.asList("DEP 1234567 99999999 0000000 ***", "EOS"));
    }

    @Test
    public void deR1T14() throws Exception {
        System.out.println("\nR1T14");
        runAndTest(Arrays.asList("login", "agent", "deposit", "123456710000000000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000"), //
                Arrays.asList("Out of limit"), //
                Arrays.asList("EOS"));
    }

    
    //withdraw
    @Test
    public void wiR1T1() throws Exception {
        System.out.println("\n\nwithdraw");
        System.out.println("\nR1T1");
        runAndTest(Arrays.asList("login", "atm", "withdraw", "1234567100000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "1000000"), //
                Arrays.asList("Withdrawed"), //
                Arrays.asList("WDR 1234567 100000 0000000 ***", "EOS"));
    }

    @Test
    public void wiR1T2() throws Exception {
        System.out.println("\nR1T2");
        runAndTest(Arrays.asList("login", "atm", "withdraw", "3234567100000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "1000000"), //
                Arrays.asList("Invalid account number"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void wiR1T3() throws Exception {
        System.out.println("\nR1T3");
        runAndTest(Arrays.asList("login", "atm", "withdraw", "1234567100000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "1000000"), //
                Arrays.asList("Withdrawed"), //
                Arrays.asList("WDR 1234567 100000 0000000 ***", "EOS"));
    }

    @Test
    public void wiR1T4() throws Exception {
        System.out.println("\nR1T4");
        runAndTest(Arrays.asList("login", "atm", "withdraw", "1234567X", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000"), //
                Arrays.asList("Invalid input"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void wiR1T6() throws Exception {
        System.out.println("\nR1T5b");
        runAndTest(Arrays.asList("login", "atm", "withdraw", "123456770000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "100"), //
                Arrays.asList("Insufficient balance"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void wiR1T7() throws Exception {
        System.out.println("\nR1T7");
        runAndTest(Arrays.asList("login", "atm", "withdraw", "123456720000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000000"), //
                Arrays.asList("Withdrawed"), //
                Arrays.asList("WDR 1234567 20000 0000000 ***", "EOS"));
    }

    @Test
    public void wiR1T8() throws Exception {
        System.out.println("\nR1T8");
        runAndTest(Arrays.asList("login", "atm", "withdraw", "123456770000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000000"), //
                Arrays.asList("Withdrawed"), //
                Arrays.asList("WDR 1234567 70000 0000000 ***", "EOS"));
    }

    @Test
    public void wiR1T9() throws Exception {
        System.out.println("\nR1T9");
        runAndTest(Arrays.asList("login", "atm", "withdraw", "1234567100000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "1000000"), //
                Arrays.asList("Withdrawed"), //
                Arrays.asList("WDR 1234567 100000 0000000 ***", "EOS"));
    }

    @Test
    public void wiR1T10() throws Exception {
        System.out.println("\nR1T10");
        runAndTest(Arrays.asList("login", "atm", "withdraw", "1234567110000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "1000000"), //
                Arrays.asList("Withdrawed"), //
                Arrays.asList("WDR 1234567 110000 0000000 ***"));
    }

	
	@Test
    public void wiR1T13() throws Exception {
        System.out.println("\nR1T13");
        runAndTest(Arrays.asList("login", "agent", "withdraw", "12345671000000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "1000000000"), //
                Arrays.asList("Withdrawed"), //
                Arrays.asList("WDR 1234567 1000000 0000000 ***", "EOS"));
    }
	
	@Test
    public void wiR1T14() throws Exception {
        System.out.println("\nR1T14");
        runAndTest(Arrays.asList("login", "atm", "withdraw", "1234567100000000", "logout"), //
                Arrays.asList("1234567", "aaaaaaaa", "10000000000"), //
                Arrays.asList("Out of limit"), //
                Arrays.asList("EOS"));
    }
	
	//Transfer
	@Test
    public void trR1T1() throws Exception {
        System.out.println("\n\ntransfer");
        System.out.println("\nR1T1");
        runAndTest(Arrays.asList("login","agent","transfer","1000001100000299999999","logout"), //
                Arrays.asList("1000001","aaaaa","1000000",
                        "1000002","bbbbb","1000000"), //
                Arrays.asList("Transferred"), //
                Arrays.asList("XFR 1000002 99999999 1000001 ***", "EOS"));
    }

    @Test
    public void trR1T2() throws Exception {
        System.out.println("\nR1T2");
        runAndTest(Arrays.asList("login","agent","transfer","1000001100000210000000000","logout"), //
                Arrays.asList("1000001","aaaaa","1000000",
                        "1000002","bbbbb","1000000"), //
                Arrays.asList("Out of limit"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void trR1T3() throws Exception {
        System.out.println("\nR1T3");
        runAndTest(Arrays.asList("login","agent","transfer","10000011000002100000.000","logout"), //
                Arrays.asList("1000001","aaaaa","1000000",
                        "1000002","bbbbb","1000000"), //
                Arrays.asList("Invalid amount"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void trR2T1() throws Exception {
        System.out.println("\nR2T1");
        runAndTest(Arrays.asList("login","atm","transfer","100000110000021000000","logout"), //
                Arrays.asList("1000001","aaaaa","1000000",
                        "1000002","bbbbb","1000000"), //
                Arrays.asList("Transferred"), //
                Arrays.asList("XFR 1000002 1000000 1000001 ***"));
    }

    @Test
    public void trR2T2() throws Exception {
        System.out.println("\nR2T2");
        runAndTest(Arrays.asList("login","atm","transfer","100000110000021000001","logout"), //
                Arrays.asList("1000001","aaaaa","1000000",
                        "1000002","bbbbb","1000000"), //
                Arrays.asList("Out of limit"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void trR2T3() throws Exception {
        System.out.println("\nR2T3");
        runAndTest(Arrays.asList("login","atm","transfer","abcdef"), //
                Arrays.asList("1000001","aaaaa","1000000",
                        "1000002","bbbbb","1000000"), //
                Arrays.asList("Invalid input"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void trR2T4() throws Exception {
        System.out.println("\nR2T4");
        runAndTest(Arrays.asList("login","atm","transfer","1000001abcdef"), //
                Arrays.asList("1000001","aaaaa","1000000",
                        "1000002","bbbbb","1000000"), //
                Arrays.asList("Invalid input"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void trR2T5() throws Exception {
        System.out.println("\nR2T5");
        runAndTest(Arrays.asList("login","atm","transfer","8888888"), //
                Arrays.asList("1000001","aaaaa","1000000",
                        "1000002","bbbbb","1000000"), //
                Arrays.asList("Invalid input"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void trR2T6() throws Exception {
        System.out.println("\nR2T6");
        runAndTest(Arrays.asList("login","atm","transfer","100000110000020"), //
                Arrays.asList("1000001","aaaaa","1000000",
                        "1000002","bbbbb","1000000"), //
                Arrays.asList("Invalid input"), //
                Arrays.asList("EOS"));
    }

    @Test
    public void trR2T7() throws Exception {
        System.out.println("\nR2T7");
        runAndTest(Arrays.asList("login","atm","transfer","10000011000002-1"), //
                Arrays.asList("1000001","aaaaa","1000000",
                        "1000002","bbbbb","1000000"), //
                Arrays.asList("Invalid input"), //
                Arrays.asList("EOS"));
    }
	
  //TransactionSummary
    @Test
        public void tsR1T1() throws Exception {
         System.out.println("\n\nTransactionSummary");
         System.out.println("\nR1T1");
            runAndTest(Arrays.asList("checkTrans","eraiuaskjdadbvfkafewihulageihuo;ewfahu;aguhi;ebgsdfiuwaefyguawfegilaegiuawfegyfweagyuahuo;ehuwefyagfigeua;wayerivbaweyi;rvbawrituyvabweituetry;iueyfbvelriuyahvwlebt"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Exceeding length"),
                    Arrays.asList("Error"));
        }
    @Test
        public void tsR1T2() throws Exception {
         System.out.println("\nR1T2");
            runAndTest(Arrays.asList("checkTrans","EOS"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("True"),
                    Arrays.asList("True"));
        }
    @Test  
        public void tsR2T1() throws Exception {
         System.out.println("\nR2T1");
            runAndTest(Arrays.asList("checkTrans","DEP 1234567 100000 0000000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("True"),
                    Arrays.asList("True"));
        }
    @Test
        public void tsR2T2() throws Exception {
         System.out.println("\nR2T2");
            runAndTest(Arrays.asList("checkTrans","132 DEP456 100000000 0000000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("First three not transcode"),
                    Arrays.asList("Error"));
        }
    @Test 
        public void tsR2T3() throws Exception {
         System.out.println("\nR2T3");
            runAndTest(Arrays.asList("checkTrans","996 123456 100000000 DEP0 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("First three not transcode"),
                    Arrays.asList("Error"));
        }
    @Test   
        public void tsR3T1() throws Exception {
         System.out.println("\nR3T1");
            runAndTest(Arrays.asList("checkTrans","DEP 1234567 1000000 0000000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("True"),
                    Arrays.asList("True"));
        }
    @Test
        public void tsR3T2() throws Exception {
         System.out.println("\nR3T2");
            runAndTest(Arrays.asList("checkTrans","DEP123456 100000000 000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Not separated by space"),
                    Arrays.asList("Error"));
        }
    @Test
        public void tsR3T3() throws Exception {
         System.out.println("\nR3T3");
            runAndTest(Arrays.asList("checkTrans","DEP123456 100000000 000***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Not separated by space"),
                    Arrays.asList("Error"));
        }
    @Test
        public void tsR3T4() throws Exception {
         System.out.println("\nR3T4");
            runAndTest(Arrays.asList("checkTrans","DEP123456100000000000***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Not separated by space"),
                    Arrays.asList("Error"));
        }
    @Test
        public void tsR4T1() throws Exception {
         System.out.println("\nR4T1");
            runAndTest(Arrays.asList("checkTrans","DEP 123456 100000000 000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Account number begin with zero or not seven digits"),
                    Arrays.asList("Error"));
        }
    @Test
        public void tsR4T2() throws Exception {
         System.out.println("\nR4T2");
            runAndTest(Arrays.asList("checkTrans","DEP 1000000000 100000000 000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Account number begin with zero or not seven digits"),
                    Arrays.asList("Error"));
        }
    @Test
        public void tsR4T3() throws Exception {
         System.out.println("\nR4T3");
            runAndTest(Arrays.asList("checkTrans","DEP 1234567 100000 0000000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("True"),
                    Arrays.asList("True"));
        }
    @Test
        public void tsR4T4() throws Exception {
         System.out.println("\nR4T4");
            runAndTest(Arrays.asList("checkTrans","DEP ar12345 100000000 000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Account number begin with zero or not seven digits"),
                    Arrays.asList("Error"));
        }
    @Test
        public void tsR4T5() throws Exception {
         System.out.println("\nR4T5");
            runAndTest(Arrays.asList("checkTrans","DEP aasdliajsoi 100000000 000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Account number begin with zero or not seven digits"),
                    Arrays.asList("Error"));
        }

    @Test
        public void tsR5T2() throws Exception {
         System.out.println("\nR5T2");
            runAndTest(Arrays.asList("checkTrans","DEP 1234567 12 0000000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Monetary not between 3 and 8 digits"),
                    Arrays.asList("Error"));
        }
    @Test
        public void tsR5T3() throws Exception {
         System.out.println("\nR5T3");
            runAndTest(Arrays.asList("checkTrans","DEP 1234567 1000000000 0000000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Monetary not between 3 and 8 digits"),
                    Arrays.asList("Error"));
        }
    @Test
        public void tsR5T4() throws Exception {
         System.out.println("\nR5T4");
            runAndTest(Arrays.asList("checkTrans","DEP 1234567 1234567 0000000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("True"),
                    Arrays.asList("True"));
        }
    @Test
        public void tsR5T5() throws Exception {
         System.out.println("\nR5T5");
            runAndTest(Arrays.asList("checkTrans","DEP 1234567 1234 0000000 ***"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("True"),
                    Arrays.asList("True"));
        }
    @Test
        public void tsR6T1() throws Exception {
         System.out.println("\nR6T1");
            runAndTest(Arrays.asList("checkTrans","DEP 1234567 1234 0000000 zzl"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Unused not filled with *"),
                    Arrays.asList("Error"));
        }
    @Test
        public void tsR6T2() throws Exception {
         System.out.println("\nR6T2");
            runAndTest(Arrays.asList("checkTrans","DEP 1234567 1234 000 a o q ei as"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Not separated by space"),
                    Arrays.asList("Error"));
        }
    @Test
        public void tsR6T3() throws Exception {
         System.out.println("\nR6T3");
            runAndTest(Arrays.asList("checkTrans","NEW 1234567 000 0000000 alfihaslrguohasrokg"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("True"),
                    Arrays.asList("True"));
        }
    @Test
        public void tsR6T4() throws Exception {
         System.out.println("\nR6T4");
            runAndTest(Arrays.asList("checkTrans","DEP 1234567 1234 0000000  alfihaslrguohasrokg"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Not separated by space"),
                    Arrays.asList("Error"));
        }
    @Test
        public void tsR6T5() throws Exception {
         System.out.println("\nR6T5");
            runAndTest(Arrays.asList("checkTrans","NEW 1234567 000 0000000 al"), //
            		Arrays.asList("1010201","adjakjsd","1000"), //
                    Arrays.asList("Name not between 3 and 30 chars"),
                    Arrays.asList("Error"));
        }


    

    /**
     * Helper function to run the main function and verify the output
     * 
     * @param terminal_input                 A list of string as the terminal input
     *                                       to run the program
     * 
     * @param valid_accounts                 A list of valid accounts to be used for
     *                                       the test case
     * 
     * @param expected_terminal_tails        A list of string expected at the tail
     *                                       of terminal output
     * 
     * @param expected_transaction_summaries A list of string expected to be in the
     *                                       output transaction summary file
     * 
     * @throws Exception
     */
    public void runAndTest(List<String> terminal_input, //
            List<String> valid_accounts, //
            List<String> expected_terminal_tails, //
            List<String> expected_transaction_summaries) throws Exception {

        // setup parameters for the program to run
        // create temporary files
        File valid_account_list_file = File.createTempFile("valid-accounts", ".tmp");
        Files.write(valid_account_list_file.toPath(), String.join("\n", valid_accounts).getBytes());

        File transaction_summary_file = File.createTempFile("transactions", ".tmp");

        String[] args = { valid_account_list_file.getAbsolutePath(), transaction_summary_file.getAbsolutePath() };

        // setup user input
        String userInput = String.join(System.lineSeparator(), terminal_input);
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        // setup stdin & stdout:
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        // run the program
        banking a = new banking();
        a.main(args);

        // capture terminal outputs:
        String[] printed_lines = outContent.toString().split("[\r\n]+");

        // compare the tail of the terminal outputs:
         int diff = printed_lines.length - expected_terminal_tails.size();
         for (int i = 0; i < expected_terminal_tails.size(); ++i) {
             assertEquals(expected_terminal_tails.get(i), printed_lines[i + diff]);
         }

        // compare output file content to the expected content
         String actual_output = new String(Files.readAllBytes(transaction_summary_file.toPath()), "UTF-8");
         String[] lines = actual_output.split("[\r\n]+");
         for (int i = 0; i < lines.length; ++i)
             assertEquals(expected_transaction_summaries.get(i), lines[i]);


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
