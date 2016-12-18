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

    public String subject;
    public String message;
    public List<String> toEmails;

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
	
}
