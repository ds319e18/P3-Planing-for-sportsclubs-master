package exceptions;


public class LogInFailed extends RuntimeException{
    public LogInFailed() {
        super("Ups! Forkert brugernavn eller kodeord.");
    }
}
