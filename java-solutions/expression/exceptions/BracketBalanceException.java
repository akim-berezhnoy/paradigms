package expression.exceptions;

public class BracketBalanceException extends ParseException {
    public BracketBalanceException(String message) {
        super(message);
    }

    public BracketBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
