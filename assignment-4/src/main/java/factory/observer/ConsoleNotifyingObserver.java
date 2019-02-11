package factory.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/*
 * Note the package-private access so these objects are *only* creatable
 * through factory.NotifyingObserverFactory.
 */
class ConsoleNotifyingObserver implements Observer<String> {

    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(String s) {
        System.out.println("Updated: " + s);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
