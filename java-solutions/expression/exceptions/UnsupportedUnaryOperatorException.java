package expression.exceptions;

public class UnsupportedUnaryOperatorException extends UnsupportedOperatorException {
    public UnsupportedUnaryOperatorException(String message) {
        super(message);
    }

    public UnsupportedUnaryOperatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
