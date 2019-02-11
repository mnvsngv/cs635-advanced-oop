package datastructures.priorityqueue;

import strategy.priority.PriorityStrategy;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*
 * HeapPriorityQueue is a heap based implementation of the priority queue.
 * In order to allow the user to choose the ordering of the priorities of the
 * elements in the queue, this class was made abstract and a BiFunction object
 * is taken as input, to determine if this will be a min heap or a max heap.
 *
 * NOTE 1: This data structure does not prevent adding the same
 * object twice.
 * NOTE 2: This implementation makes NO GUARANTEE on the ordering
 * of objects with the same priority value.
 */
public class HeapPriorityQueue<E> extends AbstractQueue<E> {

    // We use an ArrayList to make the array implementation of a heap.
    // An ArrayList is a List backed by an array. Add operations take constant
    // amortized time for a single element. Get operations are always constant
    // time since the list is backed by an array.
    // Since this is an array implementation of the heap, we'll concretely
    // set the data type of heapArray to ArrayList<E>, instead of keeping it
    // as a general List<E>.
    private ArrayList<E> heapArray = new ArrayList<>();

    // We'll store the length of the heap to quickly find the last element in
    // the heap as well as keep track of the size of the heap.
    private int heapLength = 0;

    // This BiFunction takes a lambda which will be used to determine
    // if this heap is a min heap or max heap.
    // NOTE: We always compare by passing the upper/parent element first,
    // followed by the lower/child element. When elements are passed in this
    // order, the function will evaluate to TRUE if it satisfies the heap
    // priorities, otherwise it will evaluate to FALSE.
    private BiFunction<Double, Double, Boolean> comparisonStrategy;

    // The PriorityStrategy object allows us to pass an object from the queue
    // and calculate its priority. This is strategy object is accepted as an
    // argument during the instantiation of the queue at runtime, hence we can
    // easily create different queue objects which take different formulae to
    // calculate the priority of its elements.
    private PriorityStrategy<E> priorityStrategy;


    HeapPriorityQueue(BiFunction<Double, Double, Boolean> comparisonStrategy,
                      PriorityStrategy<E> priorityStrategy) {
        this.comparisonStrategy = comparisonStrategy;
        this.priorityStrategy = priorityStrategy;
    }

    // This constructor is used to make a copy for iterating over its elements
    // and is unused outside the class (for now), hence the private access.
    private HeapPriorityQueue(HeapPriorityQueue<E> initializingQueue) {
        this.comparisonStrategy = initializingQueue.comparisonStrategy;
        this.priorityStrategy = initializingQueue.priorityStrategy;
        this.heapArray = new ArrayList<>(initializingQueue.heapArray);
        this.heapLength = initializingQueue.heapLength;
    }


    /**
     * The add(E) method adds an element to the heap queue
     * based on the element's priority.
     * This is done by adding the element to the end of the array
     * and then percolating it upward recursively.
     * We then increment heapLength so as to keep the count of elements
     * accurate.
     */
    @Override
    public boolean add(E element) {
        heapArray.add(element);
        heapLength++;
        // Since our array is zero-indexed, the last element will be at index
        // length - 1:
        percolateUp(heapLength - 1);
        return true;
    }

    /**
     * Simply returns the root element of the heap, which in the case of this
     * array implementation is the first element of the array.
     * Returns null if the heap is empty.
     */
    @Override
    public E peek() {
        if(heapLength <= 0) {
            // There are no elements to remove!
            return null;
        }

        return heapArray.get(0);
    }

    /**
     * poll() will take the max element (which is the first
     * element of the array) out of the heap, then it will take the last
     * element in the array and put it at the top of the heap. This top element
     * will then be percolated downwards.
     *
     * Returns null if the heap is empty.
     */
    @Override
    public E poll() {
        if(heapLength <= 0) {
            // There are no elements to remove!
            return null;
        }

        // Grab the element with maximum priority, which we have to return:
        E maxElement = peek();

        // Replace the root element with the last element (indices are reduced
        // by 1 since our array index is zero-based):
        heapArray.set(0, heapArray.get(heapLength - 1));

        // We do not actually need to remove the element from the array, we
        // can simply reduce the value of our heapLength to indicate that there
        // are no more elements at the end.
        heapLength--;
        heapArray.remove(heapLength);

        // Now we percolate the new root element downwards into the tree, as
        // per the priority of the element:
        percolateDown(0);

        // Finally return the element that we had saved earlier:
        return maxElement;
    }

    @Override
    public Iterator<E> iterator() {
        return new HeapPriorityQueueIterator(this);
    }

    @Override
    public int size() {
        return heapLength;
    }

    @Override
    public Object[] toArray() {
        Object[] resultArray = new Object[heapArray.size()];
        Iterable<E> iterable = () -> new HeapPriorityQueueIterator(this);
        int index = 0;
        for(E element : iterable) {
            resultArray[index++] = element;
        }
        return resultArray;
    }

    /**
     * Implement toArray(T[]) like the toArray(T[]) of Java's ArrayList
     */
    @Override
    public <T> T[] toArray(T[] elementArray) {
        if (elementArray.length < size()) {
            return (T[]) Arrays.copyOf(toArray(), size(),
                    elementArray.getClass());
        } else {
            System.arraycopy(toArray(), 0, elementArray, 0, size());
        }

        return elementArray;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(E element : this) {
            stringBuilder.append(element).append(", ");
        }
        return stringBuilder.toString();
    }

    // STUBBED METHODS
    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return false;
    }

    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    @Override
    public Stream<E> stream() {
        return null;
    }

    @Override
    public Stream<E> parallelStream() {
        return null;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public void forEach(Consumer<? super E> action) {}


    /**
     * Get the index of an element.
     * @param elementToFind The element that has to be searched for
     * @return Index of the element if found, else returns -1
     */
    public int indexOf(E elementToFind) {
        /*
         * This method is not in the interface of the AbstractQueue, however
         * this had to be added in order to more easily support undo operations
         * on the queue.
         * As a caveat, undo operations can only be performed on objects that
         * are declared as or typecasted to HeapPriorityQueue class, however
         * that is acceptable as it enforces the user of the Queue or
         * CommandProcessor to use the appropriate and supported data structure
         * implementations.
         */
        return heapArray.indexOf(elementToFind);
    }

    /**
     * Delete the element at the specified index.
     * @param index Index of the element that has to be removed
     */
    public void removeAtIndex(int index) {
        /*
         * This method has been added for reasons similar to indexOf(E). Please
         * refer to the comments in the source code for that method for more
         * details.
         */

        if(index >= heapLength) return;

        heapArray.set(index, heapArray.get(heapLength - 1));
        heapLength--;
        heapArray.remove(heapLength);
        E swappedElement = heapArray.get(index);
        E parent = heapArray.get(getParentIndexFor(index));

        double currentPriority = priorityStrategy.getPriority(swappedElement);
        double parentPriority = priorityStrategy.getPriority(parent);

        if(comparisonStrategy.apply(parentPriority, currentPriority)) {
            percolateDown(index);
        } else {
            percolateUp(index);
        }
    }


    /*
     * In this method, we have an element at the currentIndex and we have to
     * check if it satisfies the heap's priority. If it does not, then we have
     * to move it upwards by swapping it with its parent, and then we call
     * the percolateUp(int) method recursively.
     * This time, we pass in the parent index since we have swapped the current
     * element with its parent.
     * This goes on till we reach the root, or if the heap priority is
     * satisfied.
     */
    private void percolateUp(int currentIndex) {
        int parentIndex = getParentIndexFor(currentIndex);

        double parentPriority = priorityStrategy.getPriority(heapArray.get(parentIndex));
        double currentPriority = priorityStrategy.getPriority(heapArray.get(currentIndex));

        if((parentIndex != currentIndex) &&
                !(comparisonStrategy.apply(parentPriority, currentPriority))) {

            swapValuesAtIndices(parentIndex, currentIndex);
            percolateUp(parentIndex);
        }
    }

    /*
     * In this method, we have an element at the currentIndex and we have to
     * check if it satisfies the heap's priority. If it is not satisfied, then
     * we have to move it downwards by swapping it with its child, and then we
     * call the percolateDown(int) method recursively.
     * If both children do not satisfy the heap priority, then we choose the
     * right child.
     * This time, we pass in the child index since we have swapped the current
     * element with its child.
     */
    private void percolateDown(int currentIndex) {
        int leftChildIndex = getLeftChildIndexFor(currentIndex);
        int rightChildIndex = getRightChildIndexFor(currentIndex);
        int topPriorityIndex = currentIndex;

        double topPriority, leftChildPriority, rightChildPriority;

        // Confirm that we aren't checking a non existing element:
        if((leftChildIndex <= heapLength - 1)) {
            topPriority = priorityStrategy.getPriority(
                    heapArray.get(topPriorityIndex));
            leftChildPriority = priorityStrategy.getPriority(
                    heapArray.get(leftChildIndex));

            // If the priority is not satisfied, then save the index of the
            // child:
            if(!comparisonStrategy.apply(topPriority, leftChildPriority)) {
                topPriorityIndex = leftChildIndex;
            }
        }

        // Confirm that we aren't checking a non existing element:
        if((rightChildIndex <= heapLength - 1)) {
            topPriority = priorityStrategy.getPriority(
                    heapArray.get(topPriorityIndex));
            rightChildPriority = priorityStrategy.getPriority(
                    heapArray.get(rightChildIndex));

            // If the priority is not satisfied, then save the index of the
            // child:
            if(!comparisonStrategy.apply(topPriority, rightChildPriority)) {
                topPriorityIndex = rightChildIndex;
            }
        }

        // If the priority was found to be inconsistent then swap with the
        // child and then percolate it downwards:
        if(topPriorityIndex != currentIndex) {
            swapValuesAtIndices(topPriorityIndex, currentIndex);
            percolateDown(topPriorityIndex);
        }
    }

    private void swapValuesAtIndices(int indexOne, int indexTwo) {
        E elementToSwap = heapArray.get(indexOne);
        heapArray.set(indexOne, heapArray.get(indexTwo));
        heapArray.set(indexTwo, elementToSwap);
    }

    private int getParentIndexFor(int index) {
        return (int) Math.floor((index - 1) / 2);
    }

    private int getLeftChildIndexFor(int index) {
        return (2 * index) + 1;
    }

    private int getRightChildIndexFor(int index) {
        return (2 * index) + 2;
    }


    private class HeapPriorityQueueIterator implements Iterator<E> {
        // We'll create a copy of the queue's heap and remove elements from
        // the copy in order to iterate.
        AbstractQueue<E> heapCopy;

        HeapPriorityQueueIterator(HeapPriorityQueue<E> initializingHeap) {
            heapCopy = new HeapPriorityQueue<>(initializingHeap);
        }

        @Override
        public boolean hasNext() {
            return heapCopy.size() > 0;
        }

        @Override
        public E next() {
            return heapCopy.poll();
        }
    }
}
