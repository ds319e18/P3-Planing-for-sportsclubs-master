package tournament;

import tournament.pool.*;
import tournament.matchschedule.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tournament {
    public String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active = false;
    private ArrayList<Pool> poolList;
    private MatchSchedule matchSchedule;
    private ArrayList<Field> fieldList;
    public int fieldNumber;
    private TournamentType type;

    //This first part of the class deals with creating the tournament

    // Create tournament
    public Tournament(String name, LocalDate startDate, LocalDate endDate , TournamentType type, int fieldNumber,
                      ArrayList<Pool> poolList) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.poolList = new ArrayList<>();
        this.fieldNumber = fieldNumber;
    }

    public void createPools(String skill, int year) {
        poolList.add(new Pool(skill, year));
    }

    // Method to find the correct pool when adding or removing teams to the tournament
    public Pool findCorrectPool(String skill, int yearGroup) {
        for (Pool createdPools : poolList) {
            if (createdPools.getSkillLevel().equals(skill) && createdPools.getYearGroup() == yearGroup) {
                return createdPools;
            }
        }
        return null;
    }

    public boolean isActive() {
        return active;
    }

    public ArrayList<Pool> getPoolList() {
        return poolList;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
