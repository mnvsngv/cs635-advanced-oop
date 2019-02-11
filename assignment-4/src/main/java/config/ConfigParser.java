package config;

import checker.WebsiteChecker;
import factory.observer.IObserverFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * Class to create WebsiteChecker objects by reading a config file. Each
 * WebsiteChecker represents a single Observable for corresponding URL.
 */
public class ConfigParser {

    private final IObserverFactory OBSERVER_FACTORY;


    public ConfigParser(IObserverFactory observerFactory) {
        OBSERVER_FACTORY = observerFactory;
    }


    public List<WebsiteChecker> parse(String fileName)
            throws IOException {

        Path filePath = Paths.get(fileName);
        BufferedReader reader = Files.newBufferedReader(filePath);

        // We'll initially store the WebsiteChecker objects in a hash map
        // with the URL of the checker as the key. This will help us to
        // quickly find a checker to which a new observer has to be subscribed.
        Map<String, WebsiteChecker> checkerMap = new HashMap<>();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(" ");
            String url = columns[0];

            // Try to get the WebsiteChecker object and if it's absent then
            // create a new one.
            WebsiteChecker checker = checkerMap.computeIfAbsent(url,
                    k -> {
                        try {
                            return new WebsiteChecker(new URL(url));
                        } catch (IOException e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    });

            // The same observer can also subscribe to multiple different
            // observables but this has been skipped for simplicity.
            checker.subscribe(OBSERVER_FACTORY.createObserver(columns));
        }

        return new ArrayList<>(checkerMap.values());
    }
}
