package email;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;


/*
 * Class to help mock sending an email. Package-private so it's not possible
 * to create a mock except in this package & in test cases.
 */
class EmailTransport {

    private boolean isMocked;


    @SuppressWarnings("WeakerAccess")
    public EmailTransport() {
        this(false);
    }


    @SuppressWarnings("WeakerAccess")
    public EmailTransport(boolean isMocked) {
        this.isMocked = isMocked;
    }

    @SuppressWarnings("WeakerAccess")
    public void send(MimeMessage message) throws MessagingException {
        if (!isMocked) Transport.send(message);
    }
}
