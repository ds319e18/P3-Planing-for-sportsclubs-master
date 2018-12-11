package exceptions;

public class TooManyMatchesInTheMatchDay extends RuntimeException {
    public TooManyMatchesInTheMatchDay() {
        super("Der er for mange kampe til at de kan nå at spilles på de givne kampdage.");
    }
}
