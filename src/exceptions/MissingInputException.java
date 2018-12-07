package exceptions;

public class MissingInputException extends RuntimeException {
    public MissingInputException() {
        super("Der mangler at udfyldes nødvendige input-felter før du kan fortsætte til næste step.");
    }
}
