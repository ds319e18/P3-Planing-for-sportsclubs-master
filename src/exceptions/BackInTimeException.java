package exceptions;

public class BackInTimeException extends RuntimeException {
    public BackInTimeException() {
        super("Man kan ikke gå tilbage i tiden");
    }
}
