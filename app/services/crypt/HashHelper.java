package services.crypt;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Hashing with BCrypt.
 * 
 * @author TEAM RMG
 */
public class HashHelper {
	
	/**
	 * Create an encrypted password from a clear string.
	 * 
	 * @param clearString the clear password
	 * @return the encrypted password
	 * @throws IllegalArgumentException when given string is null 
	 */
	public static String createPassword(String clearString) throws IllegalArgumentException {
	    if (clearString == null) {
	        throw new IllegalArgumentException("empty.password");
	    }
	    return BCrypt.hashpw(clearString, BCrypt.gensalt());
	}
	
	/**
	 * Method to check if entered user password is the same as the one that is
	 * stored (encrypted) in the database.
	 * 
	 * @param candidate the clear candidate
	 * @param encryptedPassword the encrypted password string to check against.
	 * @return true if the candidate matches, false otherwise.
	 */
	public static boolean checkPassword(String candidate, String encryptedPassword) {
	    if (candidate == null) {
	        return false;
	    }
	    if (encryptedPassword == null) {
	        return false;
	    }
	    
	    return BCrypt.checkpw(candidate, encryptedPassword);
	}
	
}
