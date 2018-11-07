package tournament.pool;

import tournament.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Pool {
    private ArrayList<Team> teamList;
    private GroupBracket groupBracket;
    private KnockoutBracket knockoutBracket;
    private String skillLevel;
    private int yearGroup;

    //This first part of the class deals with creating the tournament

    public Pool(String skillLevel, int yearGroup) {
        this.skillLevel = skillLevel;
        this.yearGroup = yearGroup;
        teamList = new ArrayList<>();
    }

    public void addKnockoutBracket() {
        //this.knockoutBracket = new KnockoutBracket();
    }

    // Adding team to the correct pool
    public void addTeam(Team team) {
        int count = 1;
        for (Team createdTeams : this.teamList) {
            if (createdTeams.getName().equals(team.getName() + " " + Integer.toString(count))) {
                count++;
            }
        }

        team.setName(team.getName() + " " + Integer.toString(count));
        this.teamList.add(team);
        Collections.sort(this.teamList);
    }


    // Removes a team by first finding the correct pool, then after removing the correct team in the pool.
    public void removeTeam(String name) {

        for (Team teams : this.teamList) {
            if (teams.getName().equals(name)) {
                this.teamList.remove(teams);
            }
        }
        Collections.sort(this.teamList);

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

    //This next part of the class deals with updating the tournament while active
}
