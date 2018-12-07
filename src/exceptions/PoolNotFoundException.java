package exceptions;

public class PoolNotFoundException extends RuntimeException {
    public PoolNotFoundException() {
        System.out.println("Puljen der søges efter kunne ikke findes.");
    }
}
