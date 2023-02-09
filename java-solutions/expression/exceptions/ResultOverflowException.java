package expression.exceptions;

public class ResultOverflowException extends EvaluationException {
    public ResultOverflowException(String message) {
        super(message);
    }

    public ResultOverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
