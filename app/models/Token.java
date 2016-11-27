package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wsargent
 * @since 5/15/12
 */
@Entity
public class Token {

    // Reset tokens will expire after a day.
    private static final int EXPIRATION_DAYS = 1;


    public enum TypeToken {
        password("reset"), email("email");
        private String urlPath;

        TypeToken(String urlPath) {
            this.urlPath = urlPath;
        }
        
        public String getUrlPath(){ return urlPath;}

    }


    @Id
    public String token;

    @Constraints.Required
    @Formats.NonEmpty
    public Integer userId;

    @Constraints.Required
    @Enumerated(EnumType.STRING)
    public TypeToken type;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date ts;

    @Constraints.Required
    @Formats.NonEmpty
    public String email;


    /**
     * @return true if the reset token is too old to use, false otherwise.
     */
    public boolean isExpired() {
        return ts != null && ts.before(expirationTime());
    }

    /**
     * @return a date before which the password link has expired.
     */
    private Date expirationTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, -EXPIRATION_DAYS);
        return cal.getTime();
    }


}
