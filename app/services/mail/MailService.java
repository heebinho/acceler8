package services.mail;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorSystem;
import models.MailMessage;
import play.Configuration;
import play.Logger;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

/**
 * Mail service.
 * 
 * @author TEAM RMG
 *
 */
public class MailService {
    MailerClient mailerClient;

    public MailService(MailerClient mailerClient) {
        this.mailerClient = mailerClient;
    }
    
    /**
     * Send a email, using Akka to offload it to an actor.
     *
     * @param MailMessage mail to send
     */
    public void sendMail(MailMessage mailMessage) {
        MailJob mailJob = new MailJob(mailMessage, mailerClient);
        final FiniteDuration delay = Duration.create(0, TimeUnit.SECONDS);
        
        ActorSystem system = ActorSystem.apply();
        system.scheduler().scheduleOnce(
        		delay, 
        		mailJob, 
        		system.dispatcher());
    }
    
    
    /**
     * MailJob.
     * We implement Runnable, so that we execute it in an own thread.
     * 
     * @author TEAM RMG
     *
     */
    static class MailJob implements Runnable {
        MailerClient mailerClient;
        MailMessage mailMessage;


        public MailJob(MailMessage envelop, MailerClient mailerClient) {
            this.mailMessage = envelop;
            this.mailerClient = mailerClient;
        }

        public void run() {
            Email email = new Email();

            final Configuration root = Configuration.root();
            final String mailFrom = root.getString("mail.from");
            final String mailSign = root.getString("mail.sign");

            email.setFrom(mailFrom);
            email.setSubject(mailMessage.getSubject());
            email.setBodyText(mailMessage.getMessage() + "\n\n " + mailSign);
            email.setBodyHtml(mailMessage.getMessage() + "<br><br>--<br>" + mailSign);
            for (String toEmail : mailMessage.getToEmails()) {
                email.addTo(toEmail);
                Logger.debug("Mail.sendMail: Mail will be sent to " + toEmail);
            }

            mailerClient.send(email);
            Logger.debug("Mail sent - SMTP:" + root.getString("smtp.host")
                    + ":" + root.getString("smtp.port")
                    + " SSL:" + root.getString("smtp.ssl")
                    + " user:" + root.getString("smtp.user")
                    + " password:" + root.getString("smtp.password"));
        }
    }

}
