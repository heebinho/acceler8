package services;
import org.junit.*;

import services.settings.SettingsReader;

import static org.junit.Assert.*;


/**
 * Test reading application.properties
 * (Strava api settings)
 * 
 * @author TEAM RMG
 *
 */
public class SettingsReaderTest {

	
	public static Integer CLIENT_ID = 13626;
	
	
	/**
	 * Test matching client id
	 * 
	 */
    @Test
    public void checkMatchingClientId() {
        assertEquals(CLIENT_ID, SettingsReader.getValue(SettingsReader.CLIENT_ID));
    }
    
    /**
	 * Test type Missmatch.
	 * 
	 */
    @Test(expected=NumberFormatException.class)
    public void checkTypeMissmatch() {
    	SettingsReader.getValue(SettingsReader.CLIENT_SECRET);
    }


}
