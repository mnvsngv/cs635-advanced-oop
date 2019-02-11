import exceptions.ParseException;
import expressions.IExpression;
import models.Point;
import models.Turtle;
import org.junit.jupiter.api.Test;
import program.Context;
import program.TurtleProgram;
import visitors.DistanceCalculationVisitor;
import visitors.MementoCreationVisitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VisitorTests {

    /*
     * Tests to verify behavior of the distance calculation visitor.
     */
    @Test
    void distanceCalculationVisitorBasicTest()
            throws IOException, ParseException {
        testDistanceVisitor("basic.txt", 45);
    }

    @Test
    void distanceCalculationVisitorRepeatVariablesTest()
            throws IOException, ParseException {
        testDistanceVisitor("repeat_variables.txt", 30);
    }

    @Test
    void distanceCalculationVisitorRepeatVariablesReassignedTest()
            throws IOException, ParseException {
        testDistanceVisitor("repeat_variables_reassigned.txt", 30);
    }

    private void testDistanceVisitor(String fileName, int expectedDistance)
            throws IOException, ParseException {
        TurtleProgram program = new TurtleProgram(fileName);
        Context context = program.getContext();

        DistanceCalculationVisitor visitor =
                new DistanceCalculationVisitor(context);

        List<IExpression> expressions = program.getExpressions();

        for(IExpression expression : expressions) {
            expression.accept(visitor);
        }

        assertEquals(expectedDistance, visitor.getDistance());
    }

    /*
     * Tests to verify memento creation visitor behavior.
     */
    @Test
    void mementoCreationVisitorBasicTest() throws IOException, ParseException {
        Turtle turtle1 = new Turtle(new Point(10, 0), 0, true);
        Turtle turtle2 = new Turtle(new Point(10, 0), 90, true);
        Turtle turtle3 = new Turtle(new Point(10, 20), 90, true);
        Turtle turtle4 = new Turtle(new Point(10, 20), 30, true);
        Turtle turtle5 = new Turtle(new Point(22.99, 27.5), 30, true);

        List<Turtle> expectedTurtles = new ArrayList<>();
        expectedTurtles.add(turtle1);
        expectedTurtles.add(turtle2);
        expectedTurtles.add(turtle3);
        expectedTurtles.add(turtle4);
        expectedTurtles.add(turtle5);

        testMementoVisitor("basic.txt", expectedTurtles);
    }

    @Test
    void mementoCreationVisitorRepeatedVariablesTest()
            throws IOException, ParseException {
        Turtle turtle1 = new Turtle(new Point(0, 0), 0, true);
        Turtle turtle2 = new Turtle(new Point(0, 0), 0, false);
        Turtle turtle3 = new Turtle(new Point(10, 0), 0, false);
        Turtle turtle4 = new Turtle(new Point(10, 0), 90, false);
        Turtle turtle5 = new Turtle(new Point(10, 10), 90, false);
        Turtle turtle6 = new Turtle(new Point(10, 10), 180, false);
        Turtle turtle7 = new Turtle(new Point(0, 10), 180, false);
        Turtle turtle8 = new Turtle(new Point(0, 10), 270, false);

        List<Turtle> expectedTurtles = new ArrayList<>();
        expectedTurtles.add(turtle1);
        expectedTurtles.add(turtle2);
        expectedTurtles.add(turtle3);
        expectedTurtles.add(turtle4);
        expectedTurtles.add(turtle5);
        expectedTurtles.add(turtle6);
        expectedTurtles.add(turtle7);
        expectedTurtles.add(turtle8);

        testMementoVisitor("repeat_variables.txt", expectedTurtles);
    }

    @Test
    void mementoCreationVisitorRepeatedVariablesReassignedTest()
            throws IOException, ParseException {
        Turtle turtle1 = new Turtle(new Point(0, 0), 0, true);
        Turtle turtle2 = new Turtle(new Point(0, 0), 0, false);
        Turtle turtle3 = new Turtle(new Point(10, 0), 0, false);
        Turtle turtle4 = new Turtle(new Point(10, 0), 90, false);
        Turtle turtle5 = new Turtle(new Point(10, 10), 90, false);
        Turtle turtle6 = new Turtle(new Point(10, 10), 180, false);
        Turtle turtle7 = new Turtle(new Point(0, 10), 180, false);
        Turtle turtle8 = new Turtle(new Point(0, 10), 270, false);

        List<Turtle> expectedTurtles = new ArrayList<>();
        expectedTurtles.add(turtle1);
        expectedTurtles.add(turtle2);
        expectedTurtles.add(turtle3);
        expectedTurtles.add(turtle4);
        expectedTurtles.add(turtle5);
        expectedTurtles.add(turtle6);
        expectedTurtles.add(turtle7);
        expectedTurtles.add(turtle8);

        testMementoVisitor("repeat_variables_reassigned.txt", expectedTurtles);
    }

    private void testMementoVisitor(String fileName,
                                    List<Turtle> expectedTurtles)
            throws IOException, ParseException {

        TurtleProgram program = new TurtleProgram(fileName);
        Context context = program.getContext();

        MementoCreationVisitor visitor = new MementoCreationVisitor(context);

        List<IExpression> expressions = program.getExpressions();

        for(IExpression expression : expressions) {
            expression.accept(visitor);
        }

        List<Turtle.TurtleMemento> mementos = visitor.getMementoList();
        assertEquals(expectedTurtles.size(), mementos.size());

        Turtle mementoTurtle = new Turtle();
        for(int i = 0; i < mementos.size(); i++) {
            mementoTurtle.restoreFromMemento(mementos.get(i));
            assertEquals(expectedTurtles.get(i), mementoTurtle);
        }
    }
}
