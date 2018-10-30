package tournament.pool;

import tournament.*;

import java.util.List;

public class Pool {
    private List<Team> teamList;
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
}
