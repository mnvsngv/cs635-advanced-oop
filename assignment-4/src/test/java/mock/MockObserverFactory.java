package mock;

import factory.observer.IObserverFactory;
import io.reactivex.Observer;

public class MockObserverFactory implements IObserverFactory {

    @Override
    public Observer<String> createObserver(String... parameters) {
        return new MockObserver<>();
    }
}
