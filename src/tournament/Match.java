package tournament;

import tournament.matchschedule.Field;

public class Match {
    private String name;
    private int duration;
    private boolean finished;
    private Field field;
    private Team firstTeam;
    private Team secondTeam;
    private Result result;
    private Team winner;
    private Team loser;
    private boolean drawn;

    public Match(MatchBuilder builder) {
        this.name = builder.getName();
        this.duration = builder.getDuration();
        this.finished = builder.isFinished();
        this.field = builder.getField();
        this.firstTeam = builder.getFirstTeam();
        this.secondTeam = builder.getSecondTeam();
    }

    // Setters for creating a match (used in the knockout stage)
    public void setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
    }

    public void setSecondTeam(Team secondTeam) {
        this.secondTeam = secondTeam;
    }

    public void setResult(Result result) {
        this.result = result;
        if (result.getFirstTeamScore() > result.getSecondTeamScore()) {
            this.winner = firstTeam;
            this.loser = secondTeam;
        } else if (result.getFirstTeamScore() < result.getSecondTeamScore()) {
            this.winner = secondTeam;
            this.loser = firstTeam;
        } else {
            this.drawn = true;
        }
    }

    public Team getWinner() {
        return winner;
    }

    public Team getLoser() {
        return loser;
    }

    public boolean isFinished() {
        return finished;
    }

    public void updateMatchResult(){

    }

    public  void addFild() {

    }
}
