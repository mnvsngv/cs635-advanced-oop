import checker.WebsiteChecker;
import config.ConfigParser;
import factory.observer.NotifyingObserverFactory;

import java.io.IOException;
import java.util.List;


/*
 * Loop to continually check websites for updates and notify their
 * subscribers.
 */
public class Main {
    public static void main(String[] args)
            throws IOException, InterruptedException {

        NotifyingObserverFactory observerFactory =
                new NotifyingObserverFactory();

        ConfigParser parser = new ConfigParser(observerFactory);
        List<WebsiteChecker> checkers = parser.
                parse("src/main/resources/config.txt");

        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println("Checking for updates...");
            for (WebsiteChecker checker : checkers) {
                checker.checkForUpdateAndNotify();
            }
            System.out.println("Sleeping for 5 minutes...");
            Thread.sleep(5 * 1000 * 60);  // Sleep for 5 mins...
        }
    }
}
