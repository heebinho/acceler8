package controllers;

import play.mvc.*;
import services.ResourceService;
import services.StravaOAuth2Api;
import views.html.*;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
    	
    	ServiceBuilder builder = new ServiceBuilder();
    	builder.apiKey(ResourceService.getKey(ResourceService.CLIENT_ID));
    	builder.apiSecret(ResourceService.getKey(ResourceService.CLIENT_SECRET));
    	builder.callback("http://localhost:9000/callback");
    	builder.responseType("code");
    	builder.scope("view_private");
    	builder.state("onLogon");
    	OAuth20Service service = builder.build(StravaOAuth2Api.instance());
    	String authorizationUrl = service.getAuthorizationUrl();
    	
    	authorizationUrl += "&approval_prompt=";
    	authorizationUrl += ResourceService.getKey(ResourceService.APPROVAL_PROMPT);
    	
    	return ok(index.render(authorizationUrl));
    }
    
    public Result callback() {
    	
    	
    	return ok("callback...");
    	
    }
    
    public Result callback(String state, String code) {
    	
    	
    	AuthorisationService service = new AuthorisationServiceImpl();
    	Integer clientId = ResourceService.getValue(ResourceService.CLIENT_ID);
    	String secret = ResourceService.getKey(ResourceService.CLIENT_SECRET);
    	
    	//Token token = service.tokenExchange(13626, 
    	//		"7cacfcb4705f11fb9473f555f81719a1361fb23e", 
    	//		code);
    	
    	
    	Token token = service.tokenExchange(clientId, secret, code);
    	
        

    	
    	return ok("callback..." + token);
    	
    }
    

}
