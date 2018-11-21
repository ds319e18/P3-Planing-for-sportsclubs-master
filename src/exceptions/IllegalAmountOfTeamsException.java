package exceptions;

public class IllegalAmountOfTeamsException extends RuntimeException {
    public IllegalAmountOfTeamsException() {
        System.out.println("Du kan kun spille placeringsspil når der er lige mange hold i grupperne.");
    }
}
