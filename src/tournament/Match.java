package tournament;

import tournament.matchschedule.Field;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Match implements Comparable<Match> {
    private String name;
    private int duration;
    private LocalTime timestamp;
    private boolean finished;
    private Field field;
    private Team firstTeam;
    private Team secondTeam;
    private Result result;
    private Team winner;
    private Team loser;
    private boolean drawn;

    // Setters for creating a match (used in the knockout stage)
    public void setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setField(Field field) {
        this.field = field;
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

    public int getDuration() {
        return duration;
    }

    public boolean isFinished() {
        return finished;
    }

    public void updateMatchResult(){

    }

    // Sorting matches based on year group to sort in the MatchDay class
    public int compareTo(Match o) {
        return this.firstTeam.getYearGroup() - o.firstTeam.getYearGroup();
    }

    // Inner Match-builder
    public static class Builder {
        private String name;
        private int duration;
        private LocalTime timestamp;
        private boolean finished;
        private Field field;
        private Team firstTeam;
        private Team secondTeam;

        public Builder(int duration) {
            this.duration = duration;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTimestamp(LocalTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }


        public Builder setFinished(boolean finished) {
            this.finished = finished;
            return this;
        }

        public Builder setField(Field field) {
            this.field = field;
            return this;
        }

        public Builder setFirstTeam(Team firstTeam) {
            this.firstTeam = firstTeam;
            return this;
        }

        public Builder setSecondTeam(Team secondTeam) {
            this.secondTeam = secondTeam;
            return this;
        }

        public Match build() {
            Match match = new Match();
            match.name = this.name;
            match.duration = this.duration;
            match.timestamp = this.timestamp;
            match.finished = this.finished;
            match.field = this.field;
            match.firstTeam = this.firstTeam;
            match.secondTeam = this.secondTeam;

            return match;
        }

    }
}
