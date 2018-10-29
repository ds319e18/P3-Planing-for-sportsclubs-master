package tournament;

import tournament.Team;
import tournament.matchschedule.Field;

public class Match {
    private String name;
    private boolean finished;
    private Field field;
    private Team firstTeam;
    private Team secondTeam;
    private int duration;

    public Match(String name, boolean finished, Field field, Team firstTeam, Team secondTeam) {
        this.name = name;
        this.finished = finished;
        this.field = field;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
    }

    public boolean isFinished() {
        return finished;
    }

    public void updateMatchResult(){

    }
}
