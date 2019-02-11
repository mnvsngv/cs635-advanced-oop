package factory.observer;

import io.reactivex.Observer;


/*
 * Abstract Factory class. Can be concretely instantiated as a factory that
 * creates instances of objects that actually send notifications or as a
 * factory that creates mock objects.
 *
 * Using Mockito is a better option in general as it's more flexible & enables
 * more robust testing but in case it is not possible then it's better to use
 * an Abstract Factory pattern to streamline creating mock objects for testing.
 */
public interface IObserverFactory {

    Observer<String> createObserver(String... parameters);

}
