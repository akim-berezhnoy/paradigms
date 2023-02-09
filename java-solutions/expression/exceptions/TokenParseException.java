package expression.exceptions;

public class TokenParseException extends ParseException {
    public TokenParseException(String message) {
        super(message);
    }

    public TokenParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
