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
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active = false;
    private String tournamentType;
    private ArrayList<Pool> poolList;
    private MatchSchedule matchSchedule;
    private ArrayList<Field> fieldList;
    private int fieldNumber;

    // Create tournament
    public Tournament(String name, LocalDate startDate, LocalDate endDate ,String tournamentType, int fieldNumber,
                      ArrayList<Pool> poolList) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tournamentType = tournamentType;
        this.fieldNumber = fieldNumber;
        this.poolList = poolList;
    }

    public boolean isActive() {
        return active;
    }

    public void createPools(String skill, int year) {
        poolList.add(new Pool(skill, year));
    }

    // Method that creates a team object when the button "Add team" is pressed. It also calls a method "Add team" in
    // correct pool object, and also finds out how many teams from the same club is already in the tournament.
    public void createTeam(String name, int year, String skill) {
        int count = 1;
        for (Pool pools : poolList) {
            for (Team teams : pools.getTeamList()) {
                if (teams.getName().equals(name + " "  + Integer.toString(count))) {
                    count++;
                }
            }
                pools.addTeam(new Team(name + " " + Integer.toString(count), year, skill));
            }
        }

    public List<Pool> getPoolList() {
        return poolList;
    }

    public void createMatchSchedule(int timeBetweenMatches, ArrayList<Pool> poolList) {
        this.matchSchedule = new MatchSchedule(timeBetweenMatches, poolList);
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTournamentType(String tournamentType) {
        this.tournamentType = tournamentType;
    }

    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public String getName() {
        return name;
    }
}
