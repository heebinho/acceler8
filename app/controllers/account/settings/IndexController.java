package controllers.account.settings;

import controllers.Secured;
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

	
    /**
     * Main page settings
     *
     * @return index settings
     */
    public Result index() {
        return new PasswordController().index();
    }
}
