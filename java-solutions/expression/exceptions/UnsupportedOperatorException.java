package expression.exceptions;

public class UnsupportedOperatorException extends ParseException {
    public UnsupportedOperatorException(String message) {
        super(message);
    }

    public UnsupportedOperatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
