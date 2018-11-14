package tournament;

import tournament.matchschedule.Field;

public class MatchBuilder {
    private String name;
    private int duration;
    private boolean finished;
    private Field field;
    private Team firstTeam;
    private Team secondTeam;

    public MatchBuilder() {
    }

    public MatchBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MatchBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public MatchBuilder setFinished(boolean finished) {
        this.finished = finished;
        return this;
    }

    public MatchBuilder setField(Field field) {
        this.field = field;
        return this;
    }

    public MatchBuilder setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
        return this;
    }

    public MatchBuilder setSecondTeam(Team secondTeam) {
        this.secondTeam = secondTeam;
        return this;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isFinished() {
        return finished;
    }

    public Field getField() {
        return field;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }
}
