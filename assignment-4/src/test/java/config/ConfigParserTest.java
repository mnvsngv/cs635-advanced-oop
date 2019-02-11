package config;

import checker.WebsiteChecker;
import factory.observer.IObserverFactory;
import mock.MockObserverFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


class ConfigParserTest {

    @Test
    void parseConfigFileTest() throws IOException {
        // MockObserverFactory will be returning MockObservers.
        IObserverFactory observerFactory = new MockObserverFactory();

        String configFileName = "src/main/resources/config.txt";
        List<WebsiteChecker> checkers =
                new ConfigParser(observerFactory).parse(configFileName);

        // Ensure the expected number of checkers have been created. There
        // should be only one checker per unique URL in the config file.
        assertEquals(4, checkers.size());

        // 1. Put default false values for the keys we expect
        Map<String, Boolean> urlMap = new HashMap<>();
        urlMap.put("http://www.eli.sdsu.edu/index.html", Boolean.FALSE);
        urlMap.put("http://bismarck.sdsu.edu/CS635", Boolean.FALSE);
        urlMap.put("http://www.eli.sdsu.edu/courses/fall18/cs635/notes/" +
                "index.html", Boolean.FALSE);

        // 2. Then update them to true
        for (WebsiteChecker checker : checkers) {
            urlMap.put(checker.getSiteUrl().toString(), Boolean.TRUE);
        }

        // 3. Lastly, verify there are no false values remaining
        assertFalse(urlMap.containsValue(Boolean.FALSE));
    }
}