package services.settings;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility class to read
 * additional Strava api settings.
 * 
 * @author TEAM RMG
 *
 */
public class SettingsReader {
	
	
	/**
	 * Cofiguration filename
	 */
	private static final String BUNDLE_NAME = "application";
	
	/**
	 * Resource bundle containing configuration properties
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * Client id is obtained from Strava during registration
	 *
	 * @see <a href="https://www.strava.com/settings/api">https://www.strava.com/settings/api</a>
	 */
	public static final String CLIENT_ID = "strava.client_id";

	/**
	 * Client secret is obtained from Strava during registration
	 *
	 * @see <a href="https://www.strava.com/settings/api">https://www.strava.com/settings/api</a>
	 */
	public static final String CLIENT_SECRET = "strava.client_secret";

	/**
	 * Force Approval
	 */
	public static final String APPROVAL_PROMPT = "strava.approval_prompt";
	
	
	
	/**
	 * Get the value of a String property
	 * @param key The key name
	 * @return Integer value of the key from the resource bundle
	 * @exception NumberFormatException if the string cannot be parsed
	 */
	public static Integer getValue(String key) throws NumberFormatException {
		return Integer.valueOf(getKey(key));
	}

	/**
	 * Get the value of a String property
	 * 
	 * @param key The key name
     * @return the string for the given key
     * @exception NullPointerException if <code>key</code> is <code>null</code>
     * @exception MissingResourceException if no object for the given key can be found
     * @exception ClassCastException if the object found for the given key is not a string
	 */
	public static String getKey(String key) {
		return RESOURCE_BUNDLE.getString(key);
	}
	

}
