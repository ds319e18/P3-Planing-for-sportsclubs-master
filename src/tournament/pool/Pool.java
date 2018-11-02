package tournament.pool;

import tournament.*;

import java.util.ArrayList;
import java.util.List;

public class Pool {
    private ArrayList<Team> teamList = new ArrayList<>();
    private GroupBracket groupBracket;
    private KnockoutBracket knockoutBracket;
    private String skillLevel;
    private int yearGroup;

    public Pool(String skillLevel, int yearGroup) {
        this.skillLevel = skillLevel;
        this.yearGroup = yearGroup;
    }

    public Pool() {

    }

    public void addTeam(Team team) {
        this.teamList.add(team);
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public int getYearGroup() {
        return yearGroup;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public GroupBracket getGroupBracket() {
        return groupBracket;
    }
}
