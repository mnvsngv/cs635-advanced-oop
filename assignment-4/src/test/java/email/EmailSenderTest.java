package email;

import org.junit.jupiter.api.Test;

class EmailSenderTest {

    @Test
    void sendEmail() {
        EmailSender emailSender = new EmailSender(new EmailTransport(true));
        emailSender.sendEmail("msanghavi7071@sdsu.edu", "subject", "body");
    }
}