package checker;

import factory.observable.ObservableFactory;
import io.reactivex.Observer;
import io.reactivex.subjects.Subject;
import net.WebRequestExecutor;

import java.io.IOException;
import java.net.URL;


/*
 * Class to encapsulate a website as an Observable.
 */
public class WebsiteChecker {

    private URL siteUrl;
    private long lastModified;
    private Subject<String> subject;


    public WebsiteChecker(URL url) throws IOException {
        this.siteUrl = url;
        subject = ObservableFactory.createObservable();
        lastModified = WebRequestExecutor.getLastModified(url);
    }


    public URL getSiteUrl() {
        return siteUrl;
    }

    public void subscribe(Observer<String> observer) {
        subject.subscribe(observer);
    }

    public void checkForUpdateAndNotify() throws IOException {
        long updatedDate = WebRequestExecutor.getLastModified(siteUrl);
        if (updatedDate > lastModified) {
            lastModified = updatedDate;
            subject.onNext(siteUrl.toString());
        }
    }
}
