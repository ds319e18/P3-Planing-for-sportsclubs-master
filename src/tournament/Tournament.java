package tournament;

import exceptions.PoolNotFoundException;
import javafx.scene.control.Alert;
import tournament.pool.*;
import tournament.matchschedule.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

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
        try {
            for (Pool createdPools : poolList) {
                if (createdPools.getSkillLevel().equals(skill) && createdPools.getYearGroup() == yearGroup) {
                    return createdPools;
                }
            }
            throw new PoolNotFoundException();
        } catch (PoolNotFoundException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Der opstod en fejl mens der søgtes efter en pulje.");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }

        return null;
    }

    public Pool findCorrectPool(String name) {
        try {
            for (Pool createdPool : poolList) {
                if (createdPool.getName().equals(name)) {
                    return createdPool;
                }
            }
            throw new PoolNotFoundException();
        } catch (PoolNotFoundException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Der opstod en fejl mens der søgtes efter en pulje.");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }

        throw new PoolNotFoundException();
    }

    // Getters
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
            allMatches.addAll(pool.getPlayoffBracket().getMatches());
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
            allMatches.addAll(pool.getPlayoffBracket().getMatches());
        }
        return allMatches;
    }

    public ArrayList<Pool> getPoolList() {
        return poolList;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    // Inner Tournament-builder
    public static class Builder {
        private String name = "MyTournament";
        private boolean active = false;
        private LocalDate startDate;
        private LocalDate endDate;
        private TournamentType type;
        private ArrayList<Field> fieldList = new ArrayList<>();
        private ArrayList<Pool> poolList = new ArrayList<>();

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
