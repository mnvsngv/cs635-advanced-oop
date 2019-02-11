package command;

import datastructures.priorityqueue.HeapPriorityQueue;

/**
 * Class to support adding an element from the queue and support undoing it.
 * @param <E> Type of element to be added to the queue
 */
public class AddToPriorityQueueCommand<E> implements ICommand {

    // Using HeapPriorityQueue<E> instead of AbstractQueue<E> since
    // AbstractQueue<E> does not have an interface that can support undoing
    private HeapPriorityQueue<E> priorityQueue;
    private E elementToBeAdded;
    private int elementIndex;

    public AddToPriorityQueueCommand(HeapPriorityQueue<E> priorityQueue,
                                     E elementToBeAdded) {
        this.priorityQueue = priorityQueue;
        this.elementToBeAdded = elementToBeAdded;
    }

    @Override
    public void execute() {
        priorityQueue.add(elementToBeAdded);
        elementIndex = priorityQueue.indexOf(elementToBeAdded);
    }

    @Override
    public void undo() {
        priorityQueue.removeAtIndex(elementIndex);
    }
}
