package helper;

import exceptions.InvalidInputException;
import models.Student;

/*
 * This is a convenience utility class to simplify making Student
 * objects for test cases.
 * The Student class requires us to input 5 parameters.
 * However, for some tests, we are only interested in 2 parameters.
 * Hence this class has been created to allow us to create test
 * Student objects more easily and quickly.
 */
public class TestStudent extends Student {
    public TestStudent(double gpa, int units) throws InvalidInputException {
        super(
           "Dummy Name", // Name
           "Dummy Red ID", // Red ID
           "Dummy Email", // Email
            gpa,
            units
        );
    }
}
