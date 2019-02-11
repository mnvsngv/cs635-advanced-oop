package factory.observer;

import email.EmailSender;
import email.OperatorEmailProvider;
import io.reactivex.Observer;


/*
 * Factory for creating our custom Observer objects. Note how this abstracts
 * away the details on how an observer object is created, and also how
 * clients are unaware of the concrete, underlying observer object's class.
 */
public class NotifyingObserverFactory implements IObserverFactory {

    private final EmailSender EMAIL_SENDER = new EmailSender();


    private Observer<String> createConsoleObserver() {
        return new ConsoleNotifyingObserver();
    }

    private Observer<String> createSmsObserver(String phoneNumber,
                                              String operator) {
        String email = OperatorEmailProvider.getEmailFor(operator);
        String recipient = phoneNumber + "@" + email;
        return new EmailNotifyingObserver(recipient, EMAIL_SENDER);
    }

    private Observer<String> createEmailObserver(String recipient) {
        return new EmailNotifyingObserver(recipient, EMAIL_SENDER);
    }


    /**
     * Factory method to create an Observer that will send a notification to
     * one of the notification channels. The type of Observer and its
     * corresponding notification channel will depend on the parameters that
     * are passed to the method.
     *
     * Valid parameters for each index:
     * 0: Type of the observer. Can be "console", "email", or "sms".
     * 1: The email ID for the recipient in case of email observer, or phone
     *    number of the recipient in case of sms observer.
     * 2: The name of the operator in case of sms observer. Refer to
     *    OperatorEmailProvider for a list of valid operator names.
     *
     * @param parameters A varargs of String parameters to initialize observers
     * @return The Observer object
     */
    @Override
    public Observer<String> createObserver(String... parameters) {
        switch (parameters[0]) {
            case "console":
                return createConsoleObserver();

            case "email":
                return createEmailObserver(parameters[1]);

            case "sms":
                return createSmsObserver(parameters[1], parameters[2]);

        }
        return null;
    }
}
