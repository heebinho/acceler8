package models;

import java.util.ArrayList;
import java.util.List;

/**
 * A mail message model.
 * 
 * @author TEAM RMG
 *
 */
public class MailMessage {

    private String subject;
    private String message;
    private List<String> toEmails;

    /**
     * Constructor
     *
     * @param subject  the subject
     * @param message  a message
     * @param toEmails list of emails adress
     */
    public MailMessage(String subject, String message, List<String> toEmails) {
        this.subject = subject;
        this.message = message;
        this.toEmails = toEmails;
    }

    /**
     * Constructor
     * 
     * @param subject
     * @param message
     * @param email
     */
    public MailMessage(String subject, String message, String email) {
        this.message = message;
        this.subject = subject;
        this.toEmails = new ArrayList<String>();
        this.toEmails.add(email);
    }

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getToEmails() {
		return toEmails;
	}

	public void setToEmails(List<String> toEmails) {
		this.toEmails = toEmails;
	}
	
}
