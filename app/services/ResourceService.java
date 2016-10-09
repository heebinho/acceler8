/**
 * 
 */
package services;

import java.util.ResourceBundle;

/**
 * @author heebinho
 *
 */
public class ResourceService {
	
	
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
	 */
	public static Integer getValue(String key) {
		return Integer.valueOf(getKey(key));
	}

	/**
	 * Get the value of a String property
	 * @param key The key name
	 * @return The value of the key from the resource bundle
	 */
	public static String getKey(String key) {
		return RESOURCE_BUNDLE.getString(key);
	}
	

}
