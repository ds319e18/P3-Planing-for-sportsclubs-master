package exceptions;

import javafx.scene.control.Alert;

public class IllegalAmountOfGroupsException extends RuntimeException {
    public IllegalAmountOfGroupsException() {
        System.out.println("Du kan kun spille placeringsspil når der er 2 grupper.");
    }
}
