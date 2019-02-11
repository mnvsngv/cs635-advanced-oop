package exceptions;


/*
 * Exception to indicate an error in parsing the source code.
 * The message will indicate the error in more detail.
 */
public class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
