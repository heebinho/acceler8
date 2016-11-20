/**
 * 
 */
package services.strava;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.*;
import com.github.scribejava.core.oauth.OAuth20Service;

import services.settings.SettingsReader;


/**
 * Build the Strava access uri
 * 
 * @author TEAM RMG
 *
 */
public class StravaOAuth2Api extends DefaultApi20 {

	
	
	public static String getLink(){
		
    	ServiceBuilder builder = new ServiceBuilder();
    	builder.apiKey(SettingsReader.getKey(SettingsReader.CLIENT_ID));
    	builder.apiSecret(SettingsReader.getKey(SettingsReader.CLIENT_SECRET));
    	builder.callback("http://localhost:9000/dashboard/callback"); //TODO config
    	builder.responseType("code");
    	builder.scope("view_private");
    	builder.state("onLogon");
    	OAuth20Service service = builder.build(StravaOAuth2Api.instance());
    	String authorizationUrl = service.getAuthorizationUrl();
    	
    	authorizationUrl += "&approval_prompt=";
    	authorizationUrl += SettingsReader.getKey(SettingsReader.APPROVAL_PROMPT);
    	
    	return authorizationUrl;
	}
	
	
    private static class InstanceHolder {
        private static final StravaOAuth2Api INSTANCE = new StravaOAuth2Api();
    }

    public static StravaOAuth2Api instance() {
        return InstanceHolder.INSTANCE;
    }

    
    
    
	@Override
	public String getAccessTokenEndpoint() {
		return "https://www.strava.com/oauth/token";
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		return "https://www.strava.com/oauth/authorize";
	}


}
