package models;

import models.Mail;
import models.dao.ITokenDao;
import models.dao.TokenDao;
import play.Configuration;
import play.Logger;
import play.data.format.Formats;
import play.data.validation.Constraints;
//import com.avaje.ebean.Model;
import play.i18n.Messages;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import services.account.IAccountService;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

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
    public Date dateCreation;

    @Constraints.Required
    @Formats.NonEmpty
    public String email;


    /**
     * @return true if the reset token is too old to use, false otherwise.
     */
    public boolean isExpired() {
        return dateCreation != null && dateCreation.before(expirationTime());
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
