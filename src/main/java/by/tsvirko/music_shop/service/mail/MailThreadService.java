package by.tsvirko.music_shop.service.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Mail service
 */
public class MailThreadService extends Thread {
    private static final Logger logger = LogManager.getLogger(MailThreadService.class);

    private MimeMessage message;
    private String sendToEmail;
    private String mailSubject;
    private String mailText;
    private Properties properties;

    public MailThreadService(String sendToEmail,
                             String mailSubject, String mailText, Properties properties) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
        this.properties = properties;
    }

    private void init() {
        //mail session object
        Session mailSession = (new SessionCreator(properties)).createSession();
        //mail message object creation
        message = new MimeMessage(mailSession);
        try {
            //parameters initialization into mail message object
            message.setSubject(mailSubject, "UTF-8");
            message.setContent(mailText, "text/html; charset=UTF-8");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
        } catch (AddressException e) {
            logger.warn("Incorrect address: " + sendToEmail + " " + e);
        } catch (MessagingException e) {
            logger.warn("Error of forming message" + e);
        }
    }

    @Override
    public void run() {
        init();
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            logger.warn("Error while sending message" + e);
        }
    }
}
