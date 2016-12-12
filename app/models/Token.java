package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Date;

/**
 * Token model.
 * 
 * @author TEAM RMG
 *
 */
@Entity
public class Token {

    // Reset tokens will expire after a day.
    private static final int EXPIRATION_DAYS = 1;

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

    @Id
    private String token;

    @Constraints.Required
    @Formats.NonEmpty
    private Integer userId;

    @Constraints.Required
    private String type;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    @Constraints.Required
    @Formats.NonEmpty
    private String email;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
