package tournament;

import tournament.pool.*;
import tournament.matchschedule.*;
import java.util.Date;
import java.util.List;

public class Tournament {
    private Date date;
    private boolean active;
    private List<Pool> poolList;
    private MatchSchedule matchSchedule;


    // Create tournament
    public Tournament(Date date) {
        this.date = date;
    }

    private void editTournament() {

    }

    private void addTournament() {

    }

    public boolean isActive() {
        return active;
    }

    public void createPools(String skill, int year) {
        poolList.add(new Pool(skill, year));
    }

    public void createTeam(String name, int year, String skill) {
        for (Pool pools : poolList) {
            if (pools.getSkillLevel().equals(skill) && pools.getYearGroup() == year) {
                pools.addTeam(new Team(name, year, skill));
            }
        }
    }
}
