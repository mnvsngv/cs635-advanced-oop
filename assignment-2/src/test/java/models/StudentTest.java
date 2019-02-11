package models;

import exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class StudentTest {

    private Student romanoffStudent;

    @BeforeEach
    void setup() throws InvalidInputException {
        romanoffStudent = new Student("Natasha Romanoff", "789456123",
                "nromanoff@sdsu.edu", 4.0, 120);
    }

    @Test
    void equalStudentsTest() throws InvalidInputException {
        Student romanoffStudentAlternate = new Student("Natasha Romanoff",
                "789456123", "nromanoff@sdsu.edu", 4.0, 120);
        assertEquals(romanoffStudent, romanoffStudentAlternate);
    }

    @Test
    void unequalStudentsTest() throws InvalidInputException {
        Student danversStudent = new Student("Carol Danvers", "321654987",
                "cdanvers@sdsu.edu", 3.7, 40);
        assertNotEquals(danversStudent, romanoffStudent);
    }

    @Test
    void unequalStudentWithObjectTest() {
        assertNotEquals(romanoffStudent, 0);
    }

    @Test
    void hashCodeTest() {
        assertEquals(1339655061, romanoffStudent.hashCode());
    }
}