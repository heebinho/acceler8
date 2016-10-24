package services;
import org.junit.*;

import services.crypt.HashHelper;

import static org.junit.Assert.*;


/**
 * Test hashing services
 * 
 * 
 * @author TEAM RMG
 *
 */
public class CryptographyTest {

	
	/**
	 * Test our password hashing.
	 * Matching password.
	 */
    @Test
    public void checkMatchingPassword() {
    	String clearPass = "secretKey";
    	String encryptedPass = HashHelper.createPassword(clearPass);
        assertTrue(HashHelper.checkPassword(clearPass, encryptedPass));
    }
    
    /**
	 * Test our password hashing.
	 * Not matching password.
	 */
    @Test
    public void checkNonMatchingPassword() {
    	String clearPass = "secretKey";
    	String encryptedPass = HashHelper.createPassword(clearPass);
        assertFalse(HashHelper.checkPassword("notMatchingPwd", encryptedPass));
    }
    
    /**
	 * Test our password hashing.
	 * Try to create password with null value.
	 */
    @Test(expected=IllegalArgumentException.class)
    public void checkIllegalArgumentException() {
    	HashHelper.createPassword(null);
    }

    /**
	 * Test our password hashing.
	 * Encrypted password is a null value
	 */
    @Test
    public void checkEncryptedPassIsNull() {
        assertFalse(HashHelper.checkPassword("anyPassword", null));
    }
    
    /**
	 * Test our password hashing.
	 * Encrypted password is a null value
	 */
    @Test
    public void checkCandidateIsNull() {
        assertFalse(HashHelper.checkPassword(null, "c"));
    }
    


}
