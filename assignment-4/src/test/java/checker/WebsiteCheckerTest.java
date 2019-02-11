package checker;

import io.reactivex.Observer;
import mock.MockUrlCreator;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.mockito.Mockito.*;


class WebsiteCheckerTest {

    @Test
    void subscriberNotificationTest() throws Exception {

        HttpURLConnection mockHttp = mock(HttpURLConnection.class);
        when(mockHttp.getResponseCode()).thenReturn(200);

        // The first thenReturn() is called when a WebsiteChecker is created
        // for the first time.
        when(mockHttp.getLastModified()).thenReturn(123L)
                                        .thenReturn(234L)
                                        .thenReturn(456L);

        URL mockUrl = MockUrlCreator.createMockUrl(
                new URL("http://eli.sdsu.edu/courses/fall18/cs635"),
                "index.html", mockHttp);

        WebsiteChecker checker = new WebsiteChecker(mockUrl);

        // We don't have a choice here, we can't give information about
        // generics to class literals so we have to suppress the warnings.
        @SuppressWarnings("unchecked")
        Observer<String> mockObserver = mock(Observer.class);

        checker.subscribe(mockObserver);
        checker.checkForUpdateAndNotify();
        checker.checkForUpdateAndNotify();

        // We have invoked the checker twice and we've mocked the
        // URLConnection object to return newer Last-Modified dates on each
        // call so we expect the subscribed observer(s) to be invoked twice.
        // Note that the website URL that was updated is sent to the
        // observer, that's why we check for mockUrl.toString() in onNext().
        verify(mockObserver, times(2)).onNext(mockUrl.toString());
    }

    @Test
    void nonSubscriberNotificationTest() throws Exception {
        URL mockUrl = MockUrlCreator.createDefaultMockUrl(123L);

        WebsiteChecker checker = new WebsiteChecker(mockUrl);

        // We don't have a choice here, we can't give information about
        // generics to class literals so we have to suppress the warnings.
        @SuppressWarnings("unchecked")
        Observer<String> mockObserverUnsubscribed = mock(Observer.class);
        @SuppressWarnings("unchecked")
        Observer<String> mockObserverSubscribed = mock(Observer.class);

        checker.subscribe(mockObserverSubscribed);
        checker.checkForUpdateAndNotify();

        // Now we have one observer which has subscribed and one which has
        // not so we expect only the subscribed observer to be updated.
        // Note that the website URL that was updated is sent to the
        // observer, that's why we check for mockUrl.toString() in onNext().
        verify(mockObserverSubscribed, times(1)).onNext(mockUrl.toString());
        verify(mockObserverUnsubscribed, never()).onNext(any());
    }
}