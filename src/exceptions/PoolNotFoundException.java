package exceptions;

public class PoolNotFoundException extends RuntimeException {
    public PoolNotFoundException() {
        super("Puljen der søges efter kunne ikke findes.");
    }
}
