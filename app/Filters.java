import javax.inject.*;
import play.*;
import play.mvc.EssentialFilter;
import play.http.HttpFilters;
import filters.AuthenticationFilter;

/**
 * This class configures filters that run on every request. This
 * class is queried by Play to get a list of filters.
 *
 * Play will automatically use filters from any class called
 * <code>Filters</code> that is placed the root package. You can load filters
 * from a different class by adding a `play.http.filters` setting to
 * the <code>application.conf</code> configuration file.
 * 
 * @author TEAM RMG
 */
@Singleton
public class Filters implements HttpFilters {

    private final Environment env;
    private final EssentialFilter authenticationFilter;

    /**
     * @param env Basic environment settings for the current application.
     * @param exampleFilter A demonstration filter that adds a header to
     */
    @Inject
    public Filters(Environment env, AuthenticationFilter authenticationFilter) {
        this.env = env;
        this.authenticationFilter = authenticationFilter;
    }

    /**
     * The authentication filter is evaluated on every request
     */
    @Override
    public EssentialFilter[] filters() {	
      if (env.mode().equals(Mode.DEV)) {
          return new EssentialFilter[] { authenticationFilter };
      } else {
          return new EssentialFilter[] { authenticationFilter };
      }
    }

}
