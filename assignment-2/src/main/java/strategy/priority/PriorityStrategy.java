package strategy.priority;

/*
 * The base interface for creating strategy classes to calculate priorities of
 * elements that are added to the HeapPriorityQueue<E>.
 */
public interface PriorityStrategy<E> {
    double getPriority(E element);
}
