package osu.cs362.URLValidator;


import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;

public class InetTest {

	@Test //KT
    public void IDP_validInputTest(){
    	Boolean testResult; 
    	String testAddr = "153.90.5.67"; //Valid IP address 
    	String testAddr1 = "15.90.55.67"; //Valid IP address 
    	String testAddr2 = "2.3.5.9"; //Valid IP address 
    	String testMax = "255.255.255.255"; //Max range IP address 
    	String testMin = "0.0.0.0"; //Min range of IP address 
    	String badChar = "ab.cd.ef.gh"; 
    	String badRange2 = "999.888.777.1111";
    	String badRange4 = "3000.3001.3002.3003";
    	String badNeg = "-10.-10.-10.-10"; 
    	String badLong = "100.100.100.100.100";
    	String badShort = "200";
    	String badShort1 = "200.20";
    	String badShort2 = "200.20.56";
    	String badDot = "100.100.100/100";
    	String badDot1 = "100a100.100.100";
    	String badDot2 = "100.100;100.100";
    	String badDot3 = "100 100.100.100";
    	String badStr = "Software engineering";
    	String badStr1 = "OSU 153.90.5.67";
    	String badStr2 = "153.90.5.67 OSU";

    	InetAddressValidator inetAddrVal = new InetAddressValidator(); 

    	//Test: Good value for IP address 
    	testResult = inetAddrVal.isValid(testAddr); 
    	assertEquals(true, testResult); 

    	testResult = inetAddrVal.isValid(testAddr1); 
    	assertEquals(true, testResult);

    	testResult = inetAddrVal.isValid(testAddr2); 
    	assertEquals(true, testResult);

    	//Test: Max range
    	testResult = inetAddrVal.isValid(testMax); 
    	assertEquals(true, testResult); 

    	//Test: Min range
    	testResult = inetAddrVal.isValid(testMin); 
    	assertEquals(true, testResult); 

    	//Test: Bad input - characters
    	testResult = inetAddrVal.isValid(badChar); 
    	assertEquals(false, testResult); 

    	//Test: Bad input - out of range
    	testResult = inetAddrVal.isValid(badRange2); 
    	assertEquals(false, testResult); 

    	testResult = inetAddrVal.isValid(badRange4); 
    	assertEquals(false, testResult); 

    	//Test: Bad input - negative numbers 
    	testResult = inetAddrVal.isValid(badNeg); 
    	assertEquals(false, testResult); 

    	//Test: Bad input - string too long 
    	testResult = inetAddrVal.isValid(badLong); 
    	assertEquals(false, testResult); 

    	//Test: Bad input - string too short 
    	testResult = inetAddrVal.isValid(badShort); 
    	assertEquals(false, testResult); 

    	testResult = inetAddrVal.isValid(badShort1); 
    	assertEquals(false, testResult);

    	testResult = inetAddrVal.isValid(badShort2); 
    	assertEquals(false, testResult);

    	//Test: Bad input - not dotted decimal 
    	testResult = inetAddrVal.isValid(badDot); 
    	assertEquals(false, testResult);

    	testResult = inetAddrVal.isValid(badDot1); 
    	assertEquals(false, testResult);

    	testResult = inetAddrVal.isValid(badDot2); 
    	assertEquals(false, testResult);

    	testResult = inetAddrVal.isValid(badDot3); 
    	assertEquals(false, testResult);

    	//Test: Bad input - string 
    	testResult = inetAddrVal.isValid(badStr); 
    	assertEquals(false, testResult);

    	//Test: Bad input - string with more characters before
    	testResult = inetAddrVal.isValid(badStr1); 
    	assertEquals(false, testResult);

    	//Test: Bad input - string with more characters after 
    	testResult = inetAddrVal.isValid(badStr2); 
    	assertEquals(false, testResult);

    }

	@Test //KT
    public void failRangeTest(){
        Boolean testResult;
        String badRange = "256.256.256.256";

        InetAddressValidator inetAddrVal = new InetAddressValidator(); 

        //Test: Bad input - out of range
    //FAILURE - RETURNS TRUE
        testResult = inetAddrVal.isValid(badRange); //256.256.256.256
        assertEquals(false, testResult); 

    }

    @Test //KT
    public void failRange3Test(){
        Boolean testResult;
        String badRange3 = "300.300.300.300"; 

        InetAddressValidator inetAddrVal = new InetAddressValidator(); 

        //Test: Bad input - out of range
    //FAILURE - RETURNS TRUE
        testResult = inetAddrVal.isValid(badRange3); //300.300.300.300
        assertEquals(false, testResult); 
        
    }

}