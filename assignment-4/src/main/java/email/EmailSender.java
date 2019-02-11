package email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


/*
 * Class to facilitate sending emails using SMTP protocol using Gmail.
 */
public class EmailSender {

    private final Session SESSION;
    private final String USER = "sdsudummy654567@gmail.com";
    private final String PASS = "Password098";
    private final EmailTransport TRANSPORT;


    public EmailSender() {
        this(new EmailTransport());
    }

    @SuppressWarnings("WeakerAccess")
    public EmailSender(EmailTransport transport) {
        TRANSPORT = transport;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", true);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER, PASS);
                    }
                });
        session.setDebug(true);
        this.SESSION = session;
    }


    public void sendEmail(String recipient, String subject, String body) {
        String from = "me@email.com";

        try {
            MimeMessage msg = new MimeMessage(SESSION);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(recipient)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(body);

            TRANSPORT.send(msg);
        } catch (MessagingException mex) {
            mex.printStackTrace();
            System.out.println();
            Exception ex = mex;
            do {
                if (ex instanceof SendFailedException) {
                    SendFailedException sfex = (SendFailedException) ex;
                    Address[] invalid = sfex.getInvalidAddresses();
                    if (invalid != null) {
                        System.out.println("    ** Invalid Addresses");
                        for (Address anInvalid : invalid)
                            System.out.println("         " + anInvalid);
                    }
                    Address[] validUnsent = sfex.getValidUnsentAddresses();
                    if (validUnsent != null) {
                        System.out.println("    ** ValidUnsent Addresses");
                        for (Address aValidUnsent : validUnsent)
                            System.out.println("         " + aValidUnsent);
                    }
                    Address[] validSent = sfex.getValidSentAddresses();
                    if (validSent != null) {
                        System.out.println("    ** ValidSent Addresses");
                        for (Address aValidSent : validSent)
                            System.out.println("         " + aValidSent);
                    }
                }
                System.out.println();
                if (ex instanceof MessagingException)
                    ex = ((MessagingException) ex).getNextException();
                else
                    ex = null;
            } while (ex != null);
        }
    }
}
