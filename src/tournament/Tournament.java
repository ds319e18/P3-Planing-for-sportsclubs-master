package tournament;

import tournament.pool.*;
import tournament.matchschedule.*;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class Tournament {
    private String name;
    private boolean active = false;
    private LocalDate startDate;
    private LocalDate endDate;
    private TournamentType type;
    private ArrayList<Field> fieldList;
    private ArrayList<Pool> poolList;
    private MatchSchedule matchSchedule;
    private int tournamentID;

    // Method to find the correct pool when adding or removing teams to the tournament
    public Pool findCorrectPool(int yearGroup, String skill) {
        for (Pool createdPools : poolList) {
            if (createdPools.getSkillLevel().equals(skill) && createdPools.getYearGroup() == yearGroup) {
                return createdPools;
            }
        }
        return null;
    }

    // Getters


    public int getTournamentID() {
        return tournamentID;
    }

    public TournamentType getType() {
        return type;
    }

    public MatchSchedule getMatchSchedule() {
        return matchSchedule;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }

    public ArrayList<Field> getFieldList() {
        return fieldList;
    }

    public ArrayList<Match> getAllMatches() {
        ArrayList<Match> allMatches = new ArrayList<>();

        for (Pool pool : this.poolList) {
            allMatches.addAll(pool.getGroupBracket().getMatches());
            allMatches.addAll(pool.getKnockoutBracket().getMatches());
        }

        return allMatches;
    }

    // Used to insert all group matches in the database
    public ArrayList<Match> getAllGroupMatches() {
        ArrayList<Match> allMatches = new ArrayList<>();

        for (Pool pool : this.poolList) {
            allMatches.addAll(pool.getGroupBracket().getMatches());
        }
        return allMatches;

    }

    // Used to insert all knockout matches in the database
    public ArrayList<Match> getAllPLayoffMatches() {
        ArrayList<Match> allMatches = new ArrayList<>();

        for (Pool pool : this.poolList) {
            allMatches.addAll(pool.getKnockoutBracket().getMatches());
        }
        return allMatches;
    }

    public ArrayList<Pool> getPoolList() {
        return poolList;
    }

    public String getName() {
        return name;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    // This next part of the class deals with updating the tournament while active

    // Inner Tournament-builder
    public static class Builder {
        private String name = "MyTournament";
        private boolean active = false;
        private LocalDate startDate;
        private LocalDate endDate;
        private TournamentType type;
        private ArrayList<Field> fieldList = new ArrayList<>();
        private ArrayList<Pool> poolList = new ArrayList<>();
        private MatchSchedule matchSchedule;

        public Builder(String name) {
            this.name = name;
        }


        public Builder setActive(boolean active) {
            this.active = active;
            return this;
        }

        public Builder setStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setType(TournamentType type) {
            this.type = type;
            return this;
        }

        public Builder addToFieldList(Field... fields) {
            ArrayList<Field> temp = new ArrayList<>(Arrays.asList(fields));
            this.fieldList.addAll(temp);
            return this;
        }

        public Builder createFieldList(int amountOfFields) {
            for (int i = 1; i <= amountOfFields; i++) {
                this.fieldList.add(new Field("Bane " + i, false));
            }
            return this;
        }

        public Builder setPoolList(ArrayList<Pool> poolList) {
            this.poolList.addAll(poolList);
            return this;
        }

        public Tournament build() {
            Tournament tournament = new Tournament();
            tournament.name = this.name;
            tournament.active = this.active;
            tournament.startDate = this.startDate;
            tournament.endDate = this.endDate;
            tournament.type = this.type;
            tournament.fieldList = this.fieldList;
            tournament.poolList = this.poolList;
            tournament.matchSchedule = new MatchSchedule(this.startDate, this.endDate);
            for (MatchDay matchDay : tournament.matchSchedule.getMatchDays()) {
                matchDay.setFieldList(this.fieldList);
            }

            return tournament;
        }
    }
}
