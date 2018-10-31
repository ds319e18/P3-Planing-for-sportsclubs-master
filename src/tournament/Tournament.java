package tournament;

import tournament.pool.*;
import tournament.matchschedule.*;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Tournament {
    private String name;
    private Date date;
    private boolean active = false;
    private String tournamentType;
    private List<Pool> poolList;
    private MatchSchedule matchSchedule;


    // Create tournament
    public Tournament(String name, Date date, String tournamentType) {
        this.name = name;
        this.date = date;
        this.tournamentType = tournamentType;
    }

    public boolean isActive() {
        return active;
    }

    public void createPools(String skill, int year) {
        poolList.add(new Pool(skill, year));
    }

    public void createTeam(String name, int year, String skill, String contact) {
        for (Pool pools : poolList) {
            if (pools.getSkillLevel().equals(skill) && pools.getYearGroup() == year) {
                pools.addTeam(new Team(name, skill, year, contact));
            }
        }
    }

    public void createMatchSchedule(int timeBetweenMatches, List<Pool> poolList) {
        this.matchSchedule = new MatchSchedule(timeBetweenMatches, poolList);
    }
}
