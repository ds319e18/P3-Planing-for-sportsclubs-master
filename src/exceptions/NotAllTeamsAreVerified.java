package exceptions;

public class NotAllTeamsAreVerified extends RuntimeException {
    public NotAllTeamsAreVerified(Object o){
        super("Du mangler at godkende " + o.toString() + ".");
    }
}
