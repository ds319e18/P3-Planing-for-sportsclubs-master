package exceptions;

public class DuplicateTournamentNameException extends RuntimeException {
    public DuplicateTournamentNameException() {
        System.out.println("Der findes allerede en turnering med dette navn.");
    }
}
