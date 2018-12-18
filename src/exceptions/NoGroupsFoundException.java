package exceptions;

public class NoGroupsFoundException extends RuntimeException {
    public NoGroupsFoundException() {
        super("Der findes ingen grupper.");
    }
}
