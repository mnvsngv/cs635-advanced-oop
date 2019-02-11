package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/*
 * Tests for equals() and hashCode() of Point class.
 */
class PointTests {

    @Test
    void basicEqualsTest() {
        Point aPoint = new Point(22.99, 23.75);
        Point anotherPoint = new Point(22.99, 23.75);

        assertNotSame(aPoint, anotherPoint);
        assertEquals(aPoint, anotherPoint);
        assertEquals(aPoint.hashCode(), anotherPoint.hashCode());
    }

    @Test
    void constructedPointsEqualsTest() {
        Point aPoint = new Point(300, 150);
        Point anotherPoint = new Point(30, 15);
        anotherPoint = new Point(
                anotherPoint.getX() * 10,
                anotherPoint.getY() * 10
        );

        assertNotSame(aPoint, anotherPoint);
        assertEquals(aPoint, anotherPoint);
        assertEquals(aPoint.hashCode(), anotherPoint.hashCode());
    }

    @Test
    void notEqualsTest() {
        Point aPoint = new Point(22.99, 23.75);
        String aString = "";

        assertNotSame(aPoint, aString);
        assertNotEquals(aPoint, aString);
        assertNotEquals(aPoint.hashCode(), aString.hashCode());
    }
}