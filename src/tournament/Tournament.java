package tournament;

import tournament.pool.*;
import tournament.matchschedule.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Tournament {
    private String name;
    private boolean active = false;
    private LocalDate startDate;
    private LocalDate endDate;
    private TournamentType type;
    private ArrayList<Field> fieldList;
    private ArrayList<Pool> poolList;
    private MatchSchedule matchSchedule;

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
    public boolean isActive() {
        return active;
    }

    public ArrayList<Pool> getPoolList() {
        return poolList;
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

        public Builder setPoolList(Pool... pools) {
            ArrayList<Pool> temp = new ArrayList<>(Arrays.asList(pools));
            this.poolList.addAll(temp);
            return this;
        }

        public Builder setMatchSchedule(MatchSchedule matchSchedule) {
            this.matchSchedule = matchSchedule;
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
            tournament.matchSchedule = this.matchSchedule;

            return tournament;
        }



    }




}
