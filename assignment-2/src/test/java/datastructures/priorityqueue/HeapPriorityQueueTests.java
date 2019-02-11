package datastructures.priorityqueue;

import command.AddToPriorityQueueCommand;
import command.CommandProcessor;
import command.RemoveFromPriorityQueueCommand;
import exceptions.InvalidInputException;
import helper.TestStudent;
import models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.comparison.MaxComparisonStrategy;
import strategy.comparison.MinComparisonStrategy;
import strategy.priority.BasicStudentPriorityStrategy;
import strategy.priority.PriorityStrategy;
import strategy.priority.WeightedStudentPriorityStrategy;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

public class HeapPriorityQueueTests {

    private AbstractQueue<Student> basicStudentPriorityQueue;
    private AbstractQueue<Student> weightedStudentPriorityQueue;
    private PriorityStrategy<Student> basicPriorityStrategy;
    private BiFunction<Double, Double, Boolean> comparisonStrategy;
    private Student firstPriorityStudent;
    private Student secondPriorityStudent;
    private Student thirdPriorityStudent;
    private Student fourthPriorityStudent;


    private static final int NUM_STUDENTS = 1000;
    private static final int MAX_UNITS = 150;


    @BeforeEach
    void setUp() throws InvalidInputException {
        basicPriorityStrategy = new BasicStudentPriorityStrategy();
        comparisonStrategy = new MaxComparisonStrategy();
        basicStudentPriorityQueue = new HeapPriorityQueue<>(
                comparisonStrategy, basicPriorityStrategy);
        weightedStudentPriorityQueue = new HeapPriorityQueue<>(
                comparisonStrategy, new WeightedStudentPriorityStrategy());

        firstPriorityStudent = new Student(
                "Tony Stark", // Name
                "123456789", // Red ID
                "tstark@sdsu.edu", // Email
                3.9, // GPA
                120 // Units
        );
        // Basic Priority = (3.9 * 0.3) + (120 * 0.7) = 85.17
        // Weighted priority = 0.8525

        secondPriorityStudent = new Student(
                "Bruce Banner", // Name
                "987654321", // Red ID
                "bbanner@sdsu.edu", // Email
                3.8, // GPA
                115 // Units
        );
        // Basic Priority = (3.8 * 0.3) + (115 * 0.7) = 81.64
        // Weighted priority = 0.82167

        thirdPriorityStudent = new Student(
                "Clint Barton", // Name
                "789456123", // Red ID
                "cbarton@sdsu.edu", // Email
                2.8, // GPA
                90 // Units
        );
        // Basic Priority = (2.8 * 0.3) + (90 * 0.7) = 63.84
        // Weighted priority = 0.6299

        fourthPriorityStudent = new Student(
                "Peter Parker", // Name
                "321654987", // Red ID
                "pparker@sdsu.edu", // Email
                3.9, // GPA
                40 // Units
        );
        // Basic Priority = (3.9 * 0.3) + (40 * 0.7) = 29.17
        // Weighted priority = 0.4791

        basicStudentPriorityQueue.add(thirdPriorityStudent);
        basicStudentPriorityQueue.add(fourthPriorityStudent);
        basicStudentPriorityQueue.add(firstPriorityStudent);
        basicStudentPriorityQueue.add(secondPriorityStudent);

        weightedStudentPriorityQueue.add(thirdPriorityStudent);
        weightedStudentPriorityQueue.add(fourthPriorityStudent);
        weightedStudentPriorityQueue.add(firstPriorityStudent);
        weightedStudentPriorityQueue.add(secondPriorityStudent);
    }

    /*
     * Test to see if number of students added are as expected.
     */
    @Test
    void addStudentTest() {
        int expectedNumberOfStudents = 4;
        assertEquals(expectedNumberOfStudents,
                basicStudentPriorityQueue.size());
    }

    /*
     * Test to see if number of students added are as expected.
     */
    @Test
    void weightedAddStudentTest() {
        int expectedNumberOfStudents = 4;
        assertEquals(expectedNumberOfStudents,
                weightedStudentPriorityQueue.size());
    }

    /*
     * Test to ensure that the highest priority student is returned before any
     * other student, and is not removed from the heap. We call it twice to
     * ensure that the same student is retrieved both times, and it was not
     * destroyed or manipulated by the first call.
     */
    @Test
    void getMaxPriorityStudentTest() {
        Student maxPriorityStudent = basicStudentPriorityQueue.peek();
        assertEquals(firstPriorityStudent.getName(),
                maxPriorityStudent.getName());

        maxPriorityStudent = basicStudentPriorityQueue.peek();
        assertEquals(firstPriorityStudent.getName(),
                maxPriorityStudent.getName());
    }

    /*
     * Test similar to getMaxPriorityStudent(), except that it uses a different
     * PriorityStrategy.
     */
    @Test
    void getMaxWeightedPriorityStudentTest() {
        Student maxPriorityStudent = weightedStudentPriorityQueue.peek();
        assertEquals(firstPriorityStudent.getName(),
                maxPriorityStudent.getName());

        maxPriorityStudent = weightedStudentPriorityQueue.peek();
        assertEquals(firstPriorityStudent.getName(),
                maxPriorityStudent.getName());
    }

    /*
     * Test to fetch minimum priority student from a priority queue made with
     * a minimum priority comparison strategy.
     */
    @Test
    void getMinPriorityStudentTest() {
        AbstractQueue<Student> minPriorityQueue = new HeapPriorityQueue<>(
                new MinComparisonStrategy(), basicPriorityStrategy);

        minPriorityQueue.add(firstPriorityStudent);
        minPriorityQueue.add(thirdPriorityStudent);
        minPriorityQueue.add(fourthPriorityStudent);
        minPriorityQueue.add(secondPriorityStudent);

        Student minPriorityStudent = minPriorityQueue.peek();
        assertEquals(fourthPriorityStudent.getName(),
                minPriorityStudent.getName());

        minPriorityStudent = minPriorityQueue.peek();
        assertEquals(fourthPriorityStudent.getName(),
                minPriorityStudent.getName());
    }

    /*
     * Test to see if students are removed in the expected order.
     */
    @Test
    void removeStudentsTest() {
        testRemovedStudents(basicStudentPriorityQueue);
    }

    /*
     * Test to see if students are removed in the expected order.
     */
    @Test
    void weightedRemoveStudentsTest() {
        testRemovedStudents(weightedStudentPriorityQueue);
    }

    private void testRemovedStudents(
            AbstractQueue<Student> studentPriorityQueue) {
        assertEquals(firstPriorityStudent.getName(),
                studentPriorityQueue.poll().getName());
        assertEquals(3, studentPriorityQueue.size());

        assertEquals(secondPriorityStudent.getName(),
                studentPriorityQueue.poll().getName());
        assertEquals(2, studentPriorityQueue.size());

        assertEquals(thirdPriorityStudent.getName(),
                studentPriorityQueue.poll().getName());
        assertEquals(1, studentPriorityQueue.size());

        assertEquals(fourthPriorityStudent.getName(),
                studentPriorityQueue.poll().getName());
        assertEquals(0, studentPriorityQueue.size());
    }

    /*
     * Test to check the behavior when we try to get from an empty queue.
     */
    @Test
    void getFromEmptyQueueTest() {
        // Empty out the queue:
        while(basicStudentPriorityQueue.size() > 0) {
            basicStudentPriorityQueue.poll();
        }

        assertNull(basicStudentPriorityQueue.peek());
    }

    /*
     * Test to check the behavior when we try to remove from an empty queue.
     */
    @Test
    void removeFromEmptyQueueTest() {
        // Empty out the queue:
        while(basicStudentPriorityQueue.size() > 0) {
            basicStudentPriorityQueue.poll();
        }

        assertNull(basicStudentPriorityQueue.poll());
    }

    /*
     * Test to simulate printing students, and verifying if it prints data as
     * expected. This is run twice to confirm that the queue is not cleared
     * out because of the call to getAll().
     */
    @Test
    void printAllStudentsUsingArrayTest() {
        String expectedString =
                "Name: Tony Stark :: Red ID: 123456789\n" +
                "Name: Bruce Banner :: Red ID: 987654321\n" +
                "Name: Clint Barton :: Red ID: 789456123\n" +
                "Name: Peter Parker :: Red ID: 321654987\n";

        // Pass 1:
        Student[] studentArray = basicStudentPriorityQueue.toArray(new Student[4]);

        StringBuilder studentStringBuilder = new StringBuilder();
        for(Student currentStudent : studentArray) {
            studentStringBuilder.append(currentStudent).append("\n");
        }

        assertEquals(expectedString, studentStringBuilder.toString());

        // Pass 2:
        studentArray = basicStudentPriorityQueue.toArray(new Student[0]);

        studentStringBuilder = new StringBuilder();
        for(Student currentStudent : studentArray) {
            studentStringBuilder.append(currentStudent).append("\n");
        }

        assertEquals(expectedString, studentStringBuilder.toString());

    }

    /*
     * Test the toString() method of the queue.
     */
    @Test
    void toStringTest() {
        String expectedString =
                "Name: Tony Stark :: Red ID: 123456789, " +
                "Name: Bruce Banner :: Red ID: 987654321, " +
                "Name: Clint Barton :: Red ID: 789456123, " +
                "Name: Peter Parker :: Red ID: 321654987, ";

        // Pass 1:
        assertEquals(expectedString, basicStudentPriorityQueue.toString());

        // Pass 2:
        assertEquals(expectedString, basicStudentPriorityQueue.toString());

    }

    /*
     * Test to simulate printing students, and verifying if it prints data as
     * expected. This is run twice to confirm that the queue is not cleared
     * out because of the call to getAll().
     */
    @Test
    void iteratorTest() {
        String expectedString =
                "Name: Tony Stark :: Red ID: 123456789\n" +
                "Name: Bruce Banner :: Red ID: 987654321\n" +
                "Name: Clint Barton :: Red ID: 789456123\n" +
                "Name: Peter Parker :: Red ID: 321654987\n";

        // Pass 1:

        StringBuilder studentStringBuilder = new StringBuilder();
        for(Student currentStudent : basicStudentPriorityQueue) {
            studentStringBuilder.append(currentStudent).append("\n");
        }

        assertEquals(expectedString, studentStringBuilder.toString());

        // Pass 2:

        studentStringBuilder = new StringBuilder();
        for(Student currentStudent : basicStudentPriorityQueue) {
            studentStringBuilder.append(currentStudent).append("\n");
        }

        assertEquals(expectedString, studentStringBuilder.toString());

    }

    /*
     * Scalable test to confirm highest priority students are always removed
     * first.
     */
    @Test
    void addAndRemoveRandomStudentsTest() throws InvalidInputException {
        basicStudentPriorityQueue = new HeapPriorityQueue<>(comparisonStrategy,
                basicPriorityStrategy);
        List<Student> randomStudentList = new ArrayList<>();

        for(int i = 0; i < NUM_STUDENTS; i++) {
            randomStudentList.add(generateStudent());
        }

        List<Student> sortedStudentList = sortStudents(randomStudentList,
                basicPriorityStrategy);

        basicStudentPriorityQueue.addAll(randomStudentList);

        for(Student currentStudent : sortedStudentList) {
            Student maxPriorityStudent =
                    basicStudentPriorityQueue.poll();

            assertEquals(basicPriorityStrategy.getPriority(currentStudent),
                    basicPriorityStrategy.getPriority(maxPriorityStudent));
        }
    }

    /*
     * Test to check behavior and confirm that undoing a single element addition
     * works as expected.
     */
    @Test
    void undoAddTest() throws InvalidInputException {
        CommandProcessor processor = new CommandProcessor();
        Student manavStudent = new Student("Manav Sanghavi", "822549761",
                "msanghavi7071@sdsu.edu", 4.0, 150);

        processor.execute(new AddToPriorityQueueCommand<>(
                (HeapPriorityQueue<Student>) basicStudentPriorityQueue,
                manavStudent));

        assertEquals(manavStudent, basicStudentPriorityQueue.peek());
        processor.undo();
        assertEquals(firstPriorityStudent, basicStudentPriorityQueue.peek());
    }

    /*
     * Test to check behavior and confirm that undoing a single element deletion
     * works as expected.
     */
    @Test
    void undoRemoveTest() throws InvalidInputException {
        CommandProcessor processor = new CommandProcessor();
        Student manavStudent = new Student("Manav Sanghavi", "822549761",
                "msanghavi7071@sdsu.edu", 4.0, 150);

        basicStudentPriorityQueue.add(manavStudent);
        processor.execute(new RemoveFromPriorityQueueCommand<>(
                (HeapPriorityQueue<Student>) basicStudentPriorityQueue));

        assertEquals(firstPriorityStudent, basicStudentPriorityQueue.peek());
        processor.undo();
        assertEquals(manavStudent, basicStudentPriorityQueue.peek());
    }

    /*
     * A more in depth test to confirm multiple additions and removals are
     * executed and undone correctly as expected.
     */
    @Test
    void undoAddAndRemoveTest() throws InvalidInputException {
        CommandProcessor processor = new CommandProcessor();
        Student manavStudent = new Student("Manav Sanghavi", "822549761",
                "msanghavi7071@sdsu.edu", 4.0, 150);

        processor.execute(
                new AddToPriorityQueueCommand<>(
                        (HeapPriorityQueue<Student>) basicStudentPriorityQueue,
                        manavStudent));
        processor.execute(
                new RemoveFromPriorityQueueCommand<>(
                        (HeapPriorityQueue<Student>) basicStudentPriorityQueue));
        processor.execute(
                new RemoveFromPriorityQueueCommand<>(
                        (HeapPriorityQueue<Student>) basicStudentPriorityQueue));

        assertEquals(secondPriorityStudent, basicStudentPriorityQueue.peek());
        processor.undo();
        assertEquals(firstPriorityStudent, basicStudentPriorityQueue.peek());
        processor.undo();
        assertEquals(manavStudent, basicStudentPriorityQueue.peek());
    }

    /*
     * Test to confirm that a student removed from the middle of the Priority
     * Queue, using an index
     */
    @Test
    void removeStudentAtIndexTest() throws InvalidInputException {
        basicStudentPriorityQueue = new HeapPriorityQueue<>(comparisonStrategy,
                basicPriorityStrategy);
        List<Student> randomStudentList = new ArrayList<>();
        int random_index = new Random().nextInt(NUM_STUDENTS);

        for(int i = 0; i < NUM_STUDENTS; i++) {
            randomStudentList.add(generateStudent());
        }

        List<Student> sortedStudentList = sortStudents(randomStudentList,
                basicPriorityStrategy);

        basicStudentPriorityQueue.addAll(randomStudentList);

        Student randomStudent = sortedStudentList.get(random_index);

        sortedStudentList.remove(random_index);
        int index = ((HeapPriorityQueue<Student>) basicStudentPriorityQueue).indexOf(randomStudent);
        ((HeapPriorityQueue<Student>) basicStudentPriorityQueue).removeAtIndex(index);

        for(Student currentStudent : sortedStudentList) {
            Student maxPriorityStudent =
                    basicStudentPriorityQueue.poll();

            assertEquals(basicPriorityStrategy.getPriority(currentStudent),
                    basicPriorityStrategy.getPriority(maxPriorityStudent));
        }
    }

    /*
     * Test to confirm an exception is raised if invalid GPA input is passed.
     */
    @Test
    void invalidGpaInputExceptionTest() {
        assertThrows(InvalidInputException.class, () ->
                new TestStudent(5.8, 120));
        assertThrows(InvalidInputException.class, () ->
                new TestStudent(-2.8, 120));
    }

    /*
     * Test to confirm an exception is raised if invalid Units input is passed.
     */
    @Test
    void invalidUnitsInputExceptionTest() {
        assertThrows(InvalidInputException.class, () ->
                new TestStudent(3.8, 160));
        assertThrows(InvalidInputException.class, () ->
                new TestStudent(3.8, -20));
    }


    /*
     * Helper method to generate random test data. Useful for
     * creating scalable tests with large amounts of data.
     */
    private Student generateStudent() throws InvalidInputException {
        Random randomValueGenerator = new Random();

        int randomGpaSeed = randomValueGenerator.nextInt(41);
        double randomGpa = randomGpaSeed / 10;

        int randomUnits = randomValueGenerator.nextInt(MAX_UNITS + 1);

        return new TestStudent(randomGpa, randomUnits);
    }

    private List<Student> sortStudents(List<Student> randomStudentList,
                                       PriorityStrategy<Student> strategy) {
        List<Student> sortedStudentList = new ArrayList<>(randomStudentList);

        sortedStudentList.sort((studentOne, studentTwo) -> {
            int comparisonResult;
            double difference = strategy.getPriority(studentOne)
                    - strategy.getPriority(studentTwo);

            if (difference > 0) {
                comparisonResult = -1;
            } else if (difference == 0) {
                comparisonResult = 0;
            } else {
                comparisonResult = 1;
            }

            return comparisonResult;
        });

        return sortedStudentList;
    }

}