package expression.exceptions;

public class ConstantFormatException extends ParseException {
    public ConstantFormatException(String message) {
        super(message);
    }

    public ConstantFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
