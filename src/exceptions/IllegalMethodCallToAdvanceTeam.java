package exceptions;

public class IllegalMethodCallToAdvanceTeam extends RuntimeException {
    public IllegalMethodCallToAdvanceTeam() {
        System.out.println("The advanceTeams()-method is called from GoldAndBronzePlay which should not happen.");
    }
}
