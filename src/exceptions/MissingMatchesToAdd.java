package exceptions;

public class MissingMatchesToAdd extends RuntimeException {
    public MissingMatchesToAdd() {
        super("Du mangler at tilføje kampe til kampprogrammet");
    }
}
