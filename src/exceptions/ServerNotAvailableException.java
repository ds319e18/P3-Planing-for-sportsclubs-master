package exceptions;

public class ServerNotAvailableException extends RuntimeException {
    public ServerNotAvailableException() {
        super("Der kunne ikke oprettes forbindelse til databasen. Venligst kontakt en tekniker.");
    }
}
