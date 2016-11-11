package controllers.account.settings;

import javax.inject.Inject;

import controllers.Secured;
import play.db.jpa.JPAApi;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


/**
 * 
 * 
 * @author TEAM RMG
 *
 */
@Security.Authenticated(Secured.class)
public class IndexController extends Controller {

	@Inject
	JPAApi jpa;
	
    /**
     * Main page settings
     *
     * @return index settings
     */
    public Result index() {
        return new PasswordController(jpa).index();
    }
}
