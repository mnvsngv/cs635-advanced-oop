package net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/*
 * Class that returns the Last-Modified header value from an HTTP GET request
 * to a website.
 */
public class WebRequestExecutor {

    public static long getLastModified(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        int responseCode = con.getResponseCode();

        if (responseCode == 200) {
            return con.getLastModified();
        }

        // Should log & handle the non 200 response in an actual application
        return -1;
    }
}
