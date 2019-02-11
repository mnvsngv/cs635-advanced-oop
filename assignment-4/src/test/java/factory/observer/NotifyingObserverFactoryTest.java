package factory.observer;

import io.reactivex.Observer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


/*
 * Verify the behaviour of the Abstract Factory implementation.
 */
class NotifyingObserverFactoryTest {

    @Test
    void createObserverTest() {
        IObserverFactory factory = new NotifyingObserverFactory();

        String[] consoleParameters = new String[] {"console"};
        Observer observer = factory.createObserver(consoleParameters);
        assertTrue(observer instanceof ConsoleNotifyingObserver);

        String[] emailParameters = new String[] {"email", "me@email.com"};
        observer = factory.createObserver(emailParameters);
        assertTrue(observer instanceof EmailNotifyingObserver);

        String[] smsParameters = new String[] {"sms", "number", "carrier"};
        observer = factory.createObserver(smsParameters);
        assertTrue(observer instanceof EmailNotifyingObserver);
    }
}