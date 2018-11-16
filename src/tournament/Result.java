package tournament;

public class Result {
    private int firstTeamScore;
    private int secondTeamScore;

    public Result(int firstTeam, int secondTeam) {
        this.firstTeamScore = firstTeam;
        this.secondTeamScore = secondTeam;
    }

    public int getFirstTeamScore() {
        return firstTeamScore;
    }

    public int getSecondTeamScore() {
        return secondTeamScore;
    }
}
