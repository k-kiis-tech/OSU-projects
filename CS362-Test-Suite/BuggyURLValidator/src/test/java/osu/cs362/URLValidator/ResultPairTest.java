package osu.cs362.URLValidator;


import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;

public class ResultPairTest {


	@Test //KT 
    public void assert_UpdatesTest(){
    	String testStr = "Test"; //Declare test values
    	Boolean testBool = true; 

    	ResultPair resultPair = new ResultPair(testStr, testBool); 

    	//Test: "Valid" value updated to true from test data 
    	assertEquals(true, resultPair.valid); 

    	//Test: "Item" value updated to test string 
    	assertEquals(testStr, resultPair.item); 
    	
    }

    @Test //KT 
    public void assert_UpdatesTest2(){
        String newStr = "New"; //Declare test values
        Boolean newBool = false;

        ResultPair resultPair = new ResultPair(newStr, newBool); 

        //Test: "Valid" value updated to true from test data 
        assertEquals(false, resultPair.valid); 

        //Test: "Item" value updated to test string 
        assertEquals(newStr, resultPair.item); 
    }
}
