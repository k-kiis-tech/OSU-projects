package osu.cs362.URLValidator;

import static org.junit.Assert.*;
import org.junit.Test;

public class RegexTest{
	
	//Regular expressions to test against, both a single value and array of strings
	String reg1 = "ab{1,5}a";
	String[] regArr = {"ab{1,5}a", "d", "[m-z]+", "(cat) (.+?) ", "([0-9M-V]+)"};
	
	//Test valid strings against regex and specify case insensitive, using isValid method
	@Test
	public void testRegex_ValidInsensitive_isValid(){
		
		//Test single value regex validator
		RegexValidator rv1 = new RegexValidator(reg1, false);
		boolean isValBool = rv1.isValid("Aba");
		assertTrue(isValBool);
		isValBool = rv1.isValid("ABA");
		assertTrue(isValBool);
		isValBool = rv1.isValid("aBbBbBa");
		assertTrue(isValBool);
		
		//Test multivalue regex validator
		RegexValidator rvMulti = new RegexValidator(regArr, false);
		System.out.println(rvMulti.toString());
		isValBool = rvMulti.isValid("abBa");
		assertTrue(isValBool);
		isValBool = rvMulti.isValid("D");
		assertTrue(isValBool);
		isValBool = rvMulti.isValid("TURN");
		assertTrue(isValBool);
		isValBool = rvMulti.isValid("CaT watches ");
		assertTrue(isValBool);
		isValBool = rvMulti.isValid("2Rt");
		assertTrue(isValBool);
	}
	
	//Test invalid strings against regex and specify case insensitive, using isValid method
	@Test
	public void testRegex_InvalidInsensitive_isValid(){
		
		//Test single value regex validator
		RegexValidator rv1 = new RegexValidator(reg1, false);
		boolean isValBool = rv1.isValid("aa");
		assertFalse(isValBool);
		isValBool = rv1.isValid("ba");
		assertFalse(isValBool);
		isValBool = rv1.isValid("abbbbbba");
		assertFalse(isValBool);
		
		//Test multivalue regex validator
		RegexValidator rvMulti = new RegexValidator(regArr, false);
		isValBool = rvMulti.isValid("ab");
		assertFalse(isValBool);
		isValBool = rvMulti.isValid("dd");
		assertFalse(isValBool);
		isValBool = rvMulti.isValid("cab");
		assertFalse(isValBool);
		isValBool = rvMulti.isValid("The black cat watches a bird.");
		assertFalse(isValBool);
	}
	
	//Test valid strings against regex and specify case sensitive using, isValid method
	@Test
	public void testRegex_ValidSensitive_isValid(){
		
		//Test single value regex validator
		RegexValidator rv1 = new RegexValidator(reg1);
		boolean isValBool = rv1.isValid("abbba");
		assertTrue(isValBool);
		isValBool = rv1.isValid("aba");
		assertTrue(isValBool);
		isValBool = rv1.isValid("abbbbba");
		assertTrue(isValBool);
		
		//Test multivalue regex validator
		RegexValidator rvMulti = new RegexValidator(regArr);
		isValBool = rvMulti.isValid("abba");
		assertTrue(isValBool);
		isValBool = rvMulti.isValid("d");
		assertTrue(isValBool);
		isValBool = rvMulti.isValid("turn");
		assertTrue(isValBool);
		isValBool = rvMulti.isValid("cat watches ");
		assertTrue(isValBool);
		isValBool = rvMulti.isValid("2MQ");
		assertTrue(isValBool);
	}
	
	//Test invalid strings against regex and specify case sensitive using, isValid method
	@Test
	public void testRegex_InvalidSensitive_isValid(){
		
		//Test single value regex validator
		RegexValidator rv1 = new RegexValidator(reg1);
		boolean isValBool = rv1.isValid("Aba");
		assertFalse(isValBool);
		isValBool = rv1.isValid("ABA");
		assertFalse(isValBool);
		isValBool = rv1.isValid("aBbBbBa");
		assertFalse(isValBool);
		
		//Test multivalue regex validator
		RegexValidator rvMulti = new RegexValidator(regArr);
		isValBool = rvMulti.isValid("abBa");
		assertFalse(isValBool);
		isValBool = rvMulti.isValid("D");
		assertFalse(isValBool);
		isValBool = rvMulti.isValid("XYZ");
		assertFalse(isValBool);
		isValBool = rvMulti.isValid("The black CaT watches a bird.");
		assertFalse(isValBool);
		isValBool = rvMulti.isValid("2 my 8 stumps");
		assertFalse(isValBool);
	}
	
	//Test null value using isValid method
	@Test
	public void testRegex_Null_isValid(){
		
		//Test single value regex validator
		RegexValidator rv1 = new RegexValidator(reg1, false);
		boolean isValBool = rv1.isValid(null);
		assertFalse(isValBool);
	}
	
	//Test valid string against regex and specify case insensitive, using match method
	@Test
	public void testRegex_ValidInsensitive_match(){
		
		//Test multivalue regex validator
		RegexValidator rvMulti = new RegexValidator(regArr, false);
		String value = "Cat naps ";
		boolean isValBool = rvMulti.isValid(value);
		assertTrue(isValBool);
		
		//Check groups returned
		String[] matches = rvMulti.match(value);
		String[] expected = {"Cat", "naps"};
		assertArrayEquals(expected, matches);
		
		//Second test case
		value = "Cat naps and caT plays ";
		isValBool = rvMulti.isValid(value);
		assertTrue(isValBool);
		String[] matches2 = rvMulti.match(value);
		String[] expected2 = {"Cat", "naps and caT plays"};
		assertArrayEquals(expected2, matches2);
	}
	
	//Test invalid string against regex and specify case insensitive, using match method
	@Test
	public void testRegex_InvalidInsensitive_match(){
		
		//Test multivalue regex validator
		RegexValidator rvMulti = new RegexValidator(regArr, false);
		String value = "The brown Dog naps while the gray doG plays and the black DOG watches a bird";
		boolean isValBool = rvMulti.isValid(value);
		assertFalse(isValBool);
		
		//Check groups returned
		String[] matches = rvMulti.match(value);
		String[] expected = null;
		assertArrayEquals(expected, matches);
	}
	
	//Test valid string against regex and specify case sensitive, using match method
	@Test
	public void testRegex_ValidSensitive_match(){
		
		//Test multivalue regex validator
		RegexValidator rvMulti = new RegexValidator(regArr);
		String value = "cat naps and cat plays ";
		boolean isValBool = rvMulti.isValid(value);
		assertTrue(isValBool);
		
		//Check groups returned
		String[] matches = rvMulti.match(value);
		String[] expected = {"cat", "naps and cat plays"};
		assertArrayEquals(expected, matches);
		
		//Null test case
		String[] matches2 = rvMulti.match(null);
		String expected2 = null;
		assertEquals(expected2, matches2);
	}
	
	//Test invalid string against regex and specify case sensitive, using match method
	@Test
	public void testRegex_InvalidSensitive_match(){
		
		//Test multivalue regex validator
		RegexValidator rvMulti = new RegexValidator(regArr);
		String value = "Cat naps and caT plays ";
		boolean isValBool = rvMulti.isValid(value);
		assertFalse(isValBool);
		
		//Check groups returned
		String[] matches = rvMulti.match(value);
		String[] expected = null;
		assertArrayEquals(expected, matches);
	}
	
	//Test valid string against regex and specify case insensitive, using validate method
	@Test
	public void testRegex_ValidInsensitive_validate(){
		
		//Test multivalue regex validator
		RegexValidator rvMulti = new RegexValidator(regArr, false);
		String value = "Cat naps ";
		boolean isValBool = rvMulti.isValid(value);
		assertTrue(isValBool);
		
		//Check groups returned
		String matches = rvMulti.validate(value);
		System.out.println("String: " + matches);
		String expected = "Catnaps";
		assertEquals(expected, matches);
		
		//Second test case
		value = "Cat naps and caT plays ";
		isValBool = rvMulti.isValid(value);
		assertTrue(isValBool);
		String matches2 = rvMulti.validate(value);
		String expected2 = "Catnaps and caT plays";
		assertEquals(expected2, matches2);
		
		//Third test case - 1 group
		value = "2op";
		isValBool = rvMulti.isValid(value);
		assertTrue(isValBool);
		String matches3 = rvMulti.validate(value);
		String expected3 = "2op";
		assertEquals(expected3, matches3);
	}
	
	//Test invalid string against regex and specify case insensitive, using validate method
	@Test
	public void testRegex_InvalidInsensitive_validate(){
		
		//Test multivalue regex validator
		RegexValidator rvMulti = new RegexValidator(regArr, false);
		String value = "The brown Dog naps while the gray doG plays and the black DOG watches a bird";
		boolean isValBool = rvMulti.isValid(value);
		assertFalse(isValBool);
		
		//Check groups returned
		String matches = rvMulti.validate(value);
		String expected = null;
		assertEquals(expected, matches);
	}
	
	//Test valid string against regex and specify case sensitive, using validate method
	@Test
	public void testRegex_ValidSensitive_validate(){
		
		//Test multivalue regex validator
		RegexValidator rvMulti = new RegexValidator(regArr);
		String value = "cat naps and cat plays ";
		boolean isValBool = rvMulti.isValid(value);
		assertTrue(isValBool);
		
		//Check groups returned
		String matches = rvMulti.validate(value);
		String expected = "catnaps and cat plays";
		assertEquals(expected, matches);
		
		//Null test case
		String matches2 = rvMulti.validate(null);
		String expected2 = null;
		assertEquals(expected2, matches2);
	}
	
	//Test invalid string against regex and specify case sensitive, using validate method
	@Test
	public void testRegex_InvalidSensitive_validate(){
		
		//Test multivalue regex validator
		RegexValidator rvMulti = new RegexValidator(regArr);
		String value = "Cat naps and caT plays ";
		boolean isValBool = rvMulti.isValid(value);
		assertFalse(isValBool);
		
		//Check groups returned
		String matches = rvMulti.validate(value);
		String expected = null;
		assertEquals(expected, matches);
	}

	//Test constructors to ensure exception thrown for null regex
	@Test(expected=IllegalArgumentException.class)
	public void testRegex_ConstructorNull(){
		String regex = null;
		RegexValidator rv1 = new RegexValidator(regex);
	}
	
	//Test exception thrown for null regex in string array
	@Test(expected=IllegalArgumentException.class)
	public void testRegex_ConstructorArrayNull(){
		String[] regArr = {"cat", "d", null, ".*b.*"};
		RegexValidator rvMulti = new RegexValidator(regArr);
	}
	
}