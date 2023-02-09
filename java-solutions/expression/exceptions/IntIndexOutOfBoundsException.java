package expression.exceptions;

public class IntIndexOutOfBoundsException extends EvaluationException {
    public IntIndexOutOfBoundsException(String message) {
        super(message);
    }

    public IntIndexOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
