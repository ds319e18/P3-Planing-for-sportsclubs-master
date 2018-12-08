package exceptions;

public class NotEnoughTeamsAddedException extends RuntimeException {
    public NotEnoughTeamsAddedException() {
        super("Der skal tilføjes minimum 2 hold til alle puljer.");
    }
}
