import exceptions.ParseException;
import models.Point;
import org.junit.jupiter.api.Test;
import program.TurtleProgram;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class TurtleProgramTests {

    private final String SOURCE_CODE_FILE_BASIC = "basic.txt";
    private final String SOURCE_CODE_FILE_REPEAT = "repeat_variables.txt";
    private final String SOURCE_CODE_FILE_INVALID = "invalid_program.txt";
    private final String SOURCE_CODE_FILE_REPEAT_REASSIGNED =
            "repeat_variables_reassigned.txt";


    /*
     * Used to check if the TurtleProgram constructor successfully picks
     * files from the file system instead of the classpath.
     */
    @Test
    void initializeFromFileTest() throws IOException {
        TurtleProgram program = new TurtleProgram("./src/test/resources" +
                "/basic.txt");
        assertTrue(program.isAlive());
    }

    /*
     * Basic test to verify core functionality.
     */
    @Test
    void basicProgramTest() throws IOException, ParseException {
        TurtleProgram program = new TurtleProgram(SOURCE_CODE_FILE_BASIC);

        while(program.isAlive()) {
            program.interpret();
        }

        assertEquals(program.getTurtle().location(),
                new Point(22.99, 27.5));
        assertEquals(30, program.getTurtle().direction());
    }

    /*
     * Test to verify if an exception is thrown after all lines of the
     * program have been executed.
     */
    @Test
    void basicProgramExecutionEndedTest() throws IOException, ParseException {
        TurtleProgram program = new TurtleProgram(SOURCE_CODE_FILE_BASIC);

        while(program.isAlive()) {
            program.interpret();
        }

        assertEquals(program.getTurtle().location(),
                new Point(22.99, 27.5));
        assertEquals(30, program.getTurtle().direction());

        assertThrows(RuntimeException.class, program::interpret);

    }

    /*
     * Tests to verify REPEAT statement behavior.
     */
    @Test
    void repeatProgramTest() throws IOException, ParseException {
        TurtleProgram program = new TurtleProgram(SOURCE_CODE_FILE_REPEAT);

        while(program.isAlive()) {
            program.interpret();
        }

        assertEquals(program.getTurtle().location(),
                new Point(0, 10));
        assertEquals(270, program.getTurtle().direction());
    }

    @Test
    void reassignedVariablesTest() throws IOException, ParseException {
        TurtleProgram program = new TurtleProgram(
                SOURCE_CODE_FILE_REPEAT_REASSIGNED);

        while(program.isAlive()) {
            program.interpret();
        }

        assertEquals(program.getTurtle().location(),
                new Point(0, 10));
        assertEquals(270, program.getTurtle().direction());
    }

    /*
     * Test to verify if direction is updated correctly when it is over 360
     * degrees.
     */
    @Test
    void directionRolloverTest() throws IOException, ParseException {
        TurtleProgram program =
                new TurtleProgram("direction_rollover.txt");

        program.interpret();
        assertEquals(90, program.getTurtle().direction());
        program.interpret();
        assertEquals(90, program.getTurtle().direction());
        program.interpret();
        assertEquals(120, program.getTurtle().direction());
    }

    /*
     * Test to verify if ParseException is thrown for an invalid input line
     * in the source code.
     */
    @Test
    void invalidProgramTest() throws IOException {
        TurtleProgram program = new TurtleProgram(SOURCE_CODE_FILE_INVALID);

        assertThrows(ParseException.class,
                () -> {
                    while (program.isAlive()) {
                        program.interpret();
                    }
                }
        );
    }
}