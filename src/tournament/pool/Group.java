package tournament.pool;

import tournament.*;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private ArrayList<Team> teamList = new ArrayList<>();

    public Group() { }

    public Group(String name) {
        this.name = name;
    }

    public void sortPoints() {
        this.getTeamList().sort(new TeamPointsComp());
    }

    public void addTeam(Team team) {
        teamList.add(team);
    }

    public void removeTeam(Team team) {
        teamList.remove(team);
    }

    @Override
    public String toString() {
        return "grupperne";
    }

    // Getters
    public ArrayList<Team> getTeamList() {
        return teamList;
    }

    public String getName() {
        return name;
    }

    public int getAmountOfTeams() {
        return teamList.size();
    }
}
