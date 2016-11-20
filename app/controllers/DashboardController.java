package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.prism.ReadbackRenderTarget;

import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import models.User;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Cookie;
import services.settings.SettingsReader;
import services.strava.StravaOAuth2Api;
import views.html.dashboard.*;
import services.account.*;


/**
 * 
 * 
 * @author TEAM RMG
 *
 */
@Security.Authenticated(Secured.class)
public class DashboardController extends BaseController {
	


	/**
     * Default action dashboard.
     */
    @Transactional
    public Result index() {
    	
    	String email = ctx().session().get("email");
    	
    	IAccountService authService = new AccountService(em());
    	User user = authService.findByEmail(email);
    	
    	if(user.getStrava_code() == null || user.getStrava_token() == null){
    		//first attempt to get strava access token...
    		return redirect(StravaOAuth2Api.getLink());
    	}
    	
    	
    	//try to get the token from the manager
    	Token token = TokenManager.instance()
    			.retrieveTokenWithScope(user.getEmail(), AuthorisationScope.VIEW_PRIVATE);
    	
    	if(token == null){
    		token = getToken(user.getStrava_code());
    	}
    		
    	
    	Strava strava = new Strava(token);
    	StravaAthlete athlete = strava.getAthlete(15283400);
    	//athlete.getCreatedAt()
    	
    	
    	List<StravaActivity> activities = strava.listAllAuthenticatedAthleteActivities();
    	
    	return ok(index.render(token.getAthlete(), activities , "aa"));
    }
    
    private Token getToken(String code){
    	
    	AuthorisationService service = new AuthorisationServiceImpl();
    	Integer clientId = SettingsReader.getValue(SettingsReader.CLIENT_ID);
    	String secret = SettingsReader.getKey(SettingsReader.CLIENT_SECRET);   	
    	
    	Token token = service.tokenExchange(clientId, secret, code, AuthorisationScope.VIEW_PRIVATE);
    	
    	//also set the token as a cookie for ajax requests
    	Cookie oAuth2TokenCookie = new Cookie("token", token.getToken(), 3600, null, null, false, false);
    	response().setCookie(oAuth2TokenCookie);
    	
    	return token;
    }
    
    /**
     * OAuth2 callback - when the user is coming back from the strava authentication/authorization dialog.
     * 
     * On success, a code will be included in the query string. 
     * If access is denied, error=access_denied will be included in the query string. 
     * In both cases, if provided, the state argument will also be included.
     * 
     * 
     * @param state 
     * @param code authorization code
     * @return
     */
    @Transactional
    public Result callback(String state, String code) {
    	
    	String email = ctx().session().get("email");
    	System.err.println(email);
    	Token token = getToken(code);
    	
    	IAccountService authService = new AccountService(em());
    	User user = authService.findByEmail(email);
    	
    	user.setStrava_code(code);
    	user.setStrava_token(token.getToken());
    	
    	authService.persistUser(user);
    	
    	return redirect(routes.DashboardController.index());
    }

}
