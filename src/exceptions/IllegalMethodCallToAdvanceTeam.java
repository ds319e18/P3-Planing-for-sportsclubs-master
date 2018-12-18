package exceptions;

public class IllegalMethodCallToAdvanceTeam extends RuntimeException {
    public IllegalMethodCallToAdvanceTeam() {
        super("Der forsøges at lave en anden runde i et turneringsformat hvor der ikke bør være anden runde.");
    }
}
