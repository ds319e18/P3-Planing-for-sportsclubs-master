package tournament;

import tournament.pool.*;
import tournament.matchschedule.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
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

    // This first part of the class deals with creating the tournament

    // Create tournament
    public Tournament(TournamentBuilder builder) {
        this.name = builder.getName();
        this.active = builder.isActive();
        this.startDate = builder.getStartDate();
        this.endDate = builder.getEndDate();
        this.type = builder.getType();
        this.fieldList = builder.getFieldList();
        this.poolList = builder.getPoolList();
        this.matchSchedule = builder.getMatchSchedule();
    }

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
}
