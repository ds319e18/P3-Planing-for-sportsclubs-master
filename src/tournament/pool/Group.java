package tournament.pool;

import tournament.*;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private ArrayList<Team> teamList = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }

    public void addTeam(Team team) {
        teamList.add(team);
    }

    public void removeTeam(Team team) {
        teamList.remove(team);
    }

    public ArrayList<Team> getTeamList() {
        return teamList;
    }

    public void sortPoints() {
        this.getTeamList().sort(new TeamPointsComp());
    }

    public String getName() {
        return name;
    }

    public int getAmountOfTeams() {
        return teamList.size();
    }

    @Override
    public String toString() {
        return "grupperne";
    }
}
