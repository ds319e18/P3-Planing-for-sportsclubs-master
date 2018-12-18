package exceptions;

public class MatchNotFinishedException extends RuntimeException {
    public MatchNotFinishedException() {
        super("Kampen er ikke færdigspillet endnu.");
    }
}
