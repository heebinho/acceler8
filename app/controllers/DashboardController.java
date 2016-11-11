/**
 * 
 */
package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.prism.ReadbackRenderTarget;

import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import models.User;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Cookie;
import services.settings.Reader;
import views.html.dashboardtemplate;


/**
 * 
 * 
 * @author TEAM RMG
 *
 */
@Security.Authenticated(Secured.class)
public class DashboardController extends Controller {
	
    /**
     * Default action dashboard.
     */
    public Result index() {
    	
    	/*
    	Token token = TokenManager.instance()
    			.retrieveTokenWithScope("renatoheeb@gmail.com", AuthorisationScope.VIEW_PRIVATE);
    	
    	
    	Strava strava = new Strava(token);
    	StravaAthlete athlete = strava.getAthlete(15283400);
    	*/
    	//athlete.getCity()
    	
    	//return ok(index.render(User.findByEmail(request().username())));
    	//return ok(dashboard.render("this is going to rock"));
    	return ok();
    }
    
    @Transactional
    public Result callback(String state, String code) {
    	
    	AuthorisationService service = new AuthorisationServiceImpl();
    	Integer clientId = Reader.getValue(Reader.CLIENT_ID);
    	String secret = Reader.getKey(Reader.CLIENT_SECRET);   	
    	
    	Token token = service.tokenExchange(clientId, secret, code, AuthorisationScope.VIEW_PRIVATE);
    	Cookie oAuth2TokenCookie = new Cookie("token", token.getToken(), 3600, null, null, false, false);
    	response().setCookie(oAuth2TokenCookie);
    	
    	String email = ctx().session().get("email");
    	
    	System.err.println(email);
//    	token.getAthlete().getEmail()
    	
    	
    	
    	return ok(dashboardtemplate.render("ff"));
    }

}
