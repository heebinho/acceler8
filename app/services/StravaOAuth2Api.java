/**
 * 
 */
package services;

import com.github.scribejava.core.builder.api.*;


/**
 * @author heebinho
 *
 */
public class StravaOAuth2Api extends DefaultApi20 {

	
	private static String getRequestAccessURI(){
		
		String uri = "https://www.strava.com/oauth/authorize?";
		
		uri += "client_id=13626";
		uri+= "&response_type=code";
		uri+= "&redirect_uri=http://testapp.com/token_exchange";
		uri+= "&scope=view_private";
		uri+= "&state=mystate";
		uri+= "&approval_prompt=auto";
		
		return uri;
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
