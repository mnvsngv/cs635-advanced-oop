package exceptions;

/*
 * Simple exception class to indicate invalid inputs
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
