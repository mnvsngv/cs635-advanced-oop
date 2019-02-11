package mock;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MockObserver<T> implements Observer<T> {

    private int timesInvoked = 0;


    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(T s) {
        timesInvoked++;
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }


    public int getTimesInvoked() {
        return timesInvoked;
    }
}
