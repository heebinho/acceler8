package controllers.test;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import play.db.jpa.JPAApi;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.mvc.*;

/**
 * Testing. Ping. Echo.
 * 
 * 
 * @author TEAM RMG
 *
 */
public class TestController extends Controller {


	/**
	 * 
	 * @return html 200 ok - current date
	 */
	public Result ping() {
		return ok(new Date().toString());
	}
	
	/**
	 * 
	 * @return html 200 ok - echo response
	 */
	public Result echo(String in) {
		return ok(in);
	}
	
	
}
