package exceptions;


public class IllegalAmountOfGroupsException extends RuntimeException {
    public IllegalAmountOfGroupsException(String play) {
        super("Du kan kun spille " + play + " når der er 1 eller 2 grupper.");
    }
}
