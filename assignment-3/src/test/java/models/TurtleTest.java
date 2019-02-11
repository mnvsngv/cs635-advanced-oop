package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/*
 * Tests for equals() and hashCode() of Turtle class.
 */
class TurtleTest {

    @Test
    void equalsTest() {
        Turtle aTurtle = new Turtle(new Point(22.99, 23.75),
                0, false);
        Turtle anotherTurtle = new Turtle(new Point(22.99, 23.75),
                0, false);

        assertNotSame(aTurtle, anotherTurtle);
        assertEquals(aTurtle, anotherTurtle);
        assertEquals(aTurtle.hashCode(), anotherTurtle.hashCode());
    }

    @Test
    void notEqualsTest() {
        Turtle aTurtle = new Turtle(new Point(22.99, 23.75),
                0, false);
        String aString = "";

        assertNotSame(aTurtle, aString);
        assertNotEquals(aTurtle, aString);
        assertNotEquals(aTurtle.hashCode(), aString.hashCode());
    }
}