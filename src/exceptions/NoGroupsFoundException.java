package exceptions;

public class NoGroupsFoundException extends RuntimeException {
    public NoGroupsFoundException() {
        System.out.println("Der findes ingen grupper.");
    }
}
