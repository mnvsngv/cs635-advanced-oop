package command;

import datastructures.priorityqueue.HeapPriorityQueue;

/**
 * Class to support removing an element from the queue and support undoing it.
 * @param <E> Type of element to be removed from the queue
 */
public class RemoveFromPriorityQueueCommand<E> implements ICommand {

    // Using HeapPriorityQueue<E> instead of AbstractQueue<E> since
    // AbstractQueue<E> does not have an interface that can support undoing
    private HeapPriorityQueue<E> priorityQueue;
    private E element;

    public RemoveFromPriorityQueueCommand(HeapPriorityQueue<E> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    @Override
    public void execute() {
        this.element = priorityQueue.poll();
    }

    @Override
    public void undo() {
        priorityQueue.add(element);
    }
}
