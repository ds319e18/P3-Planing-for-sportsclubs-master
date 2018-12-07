package tournament.pool;

import tournament.*;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private ArrayList<Team> teamList = new ArrayList<>();

    public Group() { }

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
}
