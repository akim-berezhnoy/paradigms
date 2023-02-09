package expression.exceptions;

public class WhitespaceException extends ParseException {
    public WhitespaceException(String message) {
        super(message);
    }

    public WhitespaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
