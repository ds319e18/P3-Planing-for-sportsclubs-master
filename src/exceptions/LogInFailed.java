package exceptions;

public class LogInFailed extends RuntimeException{
    public LogInFailed() {
        super("Bruger navn eller kodeord forkert");
    }
}
