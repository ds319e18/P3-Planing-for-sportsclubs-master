package exceptions;


public class IllegalAmountOfGroupsException extends RuntimeException {
    public IllegalAmountOfGroupsException() {
        super("Du kan kun spille placeringsspil når der er 2 grupper.");
    }
}
