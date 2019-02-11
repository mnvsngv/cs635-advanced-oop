package mock;

import java.io.IOException;
import java.net.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/*
 * Class to facilitate creating mock URL objects. java.net.URL is a final
 * class and so it cannot be mocked directly. However, the underlying
 * HttpURLConnection object can be mocked by creating a URLStreamHandler that
 * returns a mocked HttpURLConnection.
 * This class contains methods that do this.
 */
public class MockUrlCreator {

    private static URL DEFAULT_URL;
    private static final String DEFAULT_SPEC = "index.html";


    static {
        try {
            DEFAULT_URL = new URL("http://eli.sdsu.edu/courses/fall18/cs635");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a mocked URL that will always returned the specified date value
     * when HttpURLConnection's lastModified() is called. This method uses a
     * default website URL.
     *
     * @param dateValue The date value that should be returned when
     *                  lastModified() is called
     * @return The mocked URL object
     * @throws IOException
     */
    @SuppressWarnings("JavaDoc")
    public static URL createDefaultMockUrl(long dateValue) throws IOException {
        HttpURLConnection mockHttp = mock(HttpURLConnection.class);
        when(mockHttp.getResponseCode()).thenReturn(200);
        when(mockHttp.getLastModified()).thenReturn(0L)
                                        .thenReturn(dateValue);

        return createMockUrl(DEFAULT_URL, DEFAULT_SPEC, mockHttp);
    }

    /**
     * Create a mocked URL that mocks connecting to the specified website URL.
     * A mocked HttpURLConnection is also provided here, so customized
     * mocking with the HTTP connection is possible.
     *
     * @param context        The base URL
     * @param spec           The page to be accessed
     * @param mockConnection The mocked URLConnection object
     * @return The mocked URL object
     * @throws MalformedURLException
     */
    @SuppressWarnings("JavaDoc")
    public static URL createMockUrl(URL context, String spec,
                                    URLConnection mockConnection)
            throws MalformedURLException {
        URLStreamHandler stubUrlHandler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) {
                return mockConnection;
            }
        };

        return new URL(context, spec, stubUrlHandler);
    }
}
