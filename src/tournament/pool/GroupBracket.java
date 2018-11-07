package tournament.pool;

import tournament.Team;

import java.util.ArrayList;
import java.util.List;

public class GroupBracket implements Bracket {
    private ArrayList<Group> groupList = new ArrayList<>();
    private int advancingTeamsPrGroup;
    private int matchesPrTeamAgainstOpponentInGroup;

    //This first part of the class deals with creating the tournament

    public GroupBracket(int amountOfGroups, ArrayList<Team> teamList) {
        createBracket(amountOfGroups, teamList);
    }

    // Use this method when choosing how many teams advance from each group
    public void setAdvancingTeamsPrGroup(int advancingTeamsPrGroup) {
        this.advancingTeamsPrGroup = advancingTeamsPrGroup;
    }

    // Use this method when choosing how many matches against each opponent each team should have
    public void setMatchesPrTeamAgainstOpponentInGroup(int matchesPrTeamAgainstOpponentInGroup) {
        this.matchesPrTeamAgainstOpponentInGroup = matchesPrTeamAgainstOpponentInGroup;
    }

    public ArrayList<Group> getGroupList() {
        return groupList;
    }

    @Override
    public void createBracket(int amountOfGroups, ArrayList<Team> teamListInPool) {
        // The desired number of groups is created
        for (int i = 0; i < amountOfGroups; i++) {
            groupList.add(new Group());
        }
        // The teams is distributed in groups as equally as possible, switching between each group adding one team at a time
        for (int i = 0; i < teamListInPool.size(); i++) {
            groupList.get(i % amountOfGroups).addTeam(teamListInPool.get(i));
        }
    }


    //This next part of the class deals with updating the tournament while active

    @Override
    public void editBracket() {

    }

    public int getGroupListSize() {
        return this.groupList.size();
    }

}
