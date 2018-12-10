package exceptions;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String correctValue, String placement) {
        super("Du kan skal indtaste " + correctValue + " under " + placement + ".");
    }
}
