package exceptions;

public class MatchNotFinishedException extends RuntimeException {
    public MatchNotFinishedException() {
        System.out.println("The match have not been played yet.");
    }
}
