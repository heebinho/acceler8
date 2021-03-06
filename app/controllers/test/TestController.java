package controllers.test;

import java.util.Date;

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
	 * Ping action.
	 * @return html 200 ok - current date
	 */
	public Result ping() {
		return ok(new Date().toString());
	}
	
	/**
	 * Echo action.
	 * @return html 200 ok - echo response
	 */
	public Result echo(String in) {
		return ok(in);
	}
	
	
}
