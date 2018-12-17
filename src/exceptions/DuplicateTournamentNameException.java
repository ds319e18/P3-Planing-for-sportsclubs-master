package exceptions;

public class DuplicateTournamentNameException extends RuntimeException { // Future work
    public DuplicateTournamentNameException() {
        super("Der findes allerede en turnering med dette navn.");
    }
}
