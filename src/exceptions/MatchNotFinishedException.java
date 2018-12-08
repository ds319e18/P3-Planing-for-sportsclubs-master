package exceptions;

public class MatchNotFinishedException extends RuntimeException {
    public MatchNotFinishedException() {
        System.out.println("Kampen er ikke færdigspillet endnu.");
    }
}
