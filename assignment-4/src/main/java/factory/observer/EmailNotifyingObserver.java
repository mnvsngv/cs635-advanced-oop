package factory.observer;

import email.EmailSender;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/*
 * Note the package-private access so these objects are *only* creatable
 * through factory.NotifyingObserverFactory.
 */
class EmailNotifyingObserver implements Observer<String> {

    private final String RECIPIENT;
    private final EmailSender EMAIL_SENDER;


    EmailNotifyingObserver(String recipient, EmailSender emailSender) {
        this.RECIPIENT = recipient;
        EMAIL_SENDER = emailSender;
    }


    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(String s) {
        EMAIL_SENDER.sendEmail(RECIPIENT, "Update",
                "Your subscribed website has been updated!\nURL: " + s);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
