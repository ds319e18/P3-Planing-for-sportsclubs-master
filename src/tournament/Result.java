package tournament;

public class Result {
    private int firstTeamScore;
    private int secondTeamScore;

    public Result(int firstTeamScore, int secondTeamScore) {
        this.firstTeamScore = firstTeamScore;
        this.secondTeamScore = secondTeamScore;
    }

    public int getFirstTeamScore() {
        return firstTeamScore;
    }

    public int getSecondTeamScore() {
        return secondTeamScore;
    }

    public void setFirstTeamScore(int firstTeamScore) {
        this.firstTeamScore = firstTeamScore;
    }

    public void setSecondTeamScore(int secondTeamScore) {
        this.secondTeamScore = secondTeamScore;
    }

    @Override
    public String toString() {
        if (firstTeamScore == 100 && secondTeamScore == 100) {
            return "-";
        } else {
            return Integer.toString(firstTeamScore) + "-" + Integer.toString(secondTeamScore);
        }
    }
}
