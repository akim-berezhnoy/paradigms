package expression.exceptions;

public class UnsupportedBinaryOperatorException extends UnsupportedOperatorException {
    public UnsupportedBinaryOperatorException(String message) {
        super(message);
    }

    public UnsupportedBinaryOperatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
