package factory.observable;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/*
 * Within the scope of this assignment, the ObservableFactory is unnecessary.
 * But in a bigger application, it would make sense to have this class so that
 * we can easily switch out the created Observable object at one place instead
 * of having to update its creation all over the program.
 */
public class ObservableFactory {
    public static Subject<String> createObservable() {
        return PublishSubject.create();
    }
}
