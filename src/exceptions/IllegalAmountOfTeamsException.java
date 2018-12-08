package exceptions;

public class IllegalAmountOfTeamsException extends RuntimeException {
    public IllegalAmountOfTeamsException() {
        super("Du kan kun spille placeringsspil når der er \nlige mange hold i grupperne.");
    }
}
