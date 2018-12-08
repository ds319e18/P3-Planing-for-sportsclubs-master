package exceptions;

public class MissingPressingSaveException extends RuntimeException {
    public MissingPressingSaveException() {
        super("Du skal godkende med gem-knappen \nfor at gå videre.");
    }
}
