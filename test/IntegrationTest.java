import org.junit.*;

import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.test.*;

import static play.test.Helpers.*;

import java.util.concurrent.CompletionStage;

import static org.junit.Assert.*;

/**
 * Integration tests.
 * 
 * 
 * @author TEAM RMG
 *
 */
public class IntegrationTest {

	

	/**
	 * Test if we can start a server and ping.
	 * 
	 */
	@Test
	public void pingServer() {
	    int testServerPort = play.api.test.Helpers.testServerPort();
	    TestServer server = testServer(testServerPort);
	    running(server, () -> {
	        try {
	            try (WSClient ws = WS.newClient(testServerPort)) {
	                CompletionStage<WSResponse> completionStage = ws.url("/ping").get();
	                WSResponse response = completionStage.toCompletableFuture().get();
	                assertEquals(OK, response.getStatus());
	            }
	        } catch (Exception e) {
	        	fail();
	        }
	    });
	}

	
	/**
	 * Test if the routing is working fine. Call an echo method.
	 * 
	 */
	@Test
	public void echoServer(){
	    int testServerPort = play.api.test.Helpers.testServerPort();
	    TestServer server = testServer(testServerPort);
	    running(server, () -> {
	        try {
	            try (WSClient ws = WS.newClient(testServerPort)) {
	            	String echo = "hello";
	                CompletionStage<WSResponse> completionStage = ws.url("/echo/" + echo).get();
	                WSResponse response = completionStage.toCompletableFuture().get();
	                assertEquals(OK, response.getStatus());
	                assertEquals(echo, response.getBody());
	            }
	        } catch (Exception e) {
	        	fail();
	        }
	    });
	}
	

}
