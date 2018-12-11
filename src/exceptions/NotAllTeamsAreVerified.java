package exceptions;

public class NotAllTeamsAreVerified extends RuntimeException {
    public NotAllTeamsAreVerified(String messeaage){
        super("Du mangler at godkende " + messeaage + ".");
    }
}
