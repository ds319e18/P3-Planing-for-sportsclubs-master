package exceptions;

public class BackInDateException extends RuntimeException {
    public BackInDateException() {
        super("Den valgte dato går tilbage i tiden \n Vælg en anden dato.");
    }
}
