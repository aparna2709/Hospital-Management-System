package hms.TestCase;

import hms.authentication.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LoginTest {
	
	@Test
	public void LoginValidation()
	{
		
		assertTrue(Login.Testlogin("user1","pwd1"));
		
	}
	
	@Test
	public void LoginValidation1()
	{
		
		assertFalse(Login.Testlogin("nita","pwd1"));
		
	}

}