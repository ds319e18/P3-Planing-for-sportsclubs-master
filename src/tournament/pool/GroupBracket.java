package tournament.pool;

import tournament.Team;

import java.util.ArrayList;
import java.util.List;

public class GroupBracket implements Bracket{
    private ArrayList<Group> groupList = new ArrayList<>();
    private int matchesPrTeamAgainstOpponentInGroup;

    public GroupBracket(int amountOfGroups, List<Team> teamList, int matchesPrTeamAgainstOpponentInGroup) {
        this.matchesPrTeamAgainstOpponentInGroup = matchesPrTeamAgainstOpponentInGroup;
        createGroups(amountOfGroups, teamList);
    }

    private void createGroups(int amountOfGroups, List<Team> teamListInPool) {
        for (int i = 0; i < amountOfGroups; i++) {                              // Det ønskede antal grupper oprettes
            groupList.add(new Group());
        }

        for (int i = 0; i < teamListInPool.size(); i++) {
            groupList.get(i % amountOfGroups).addTeam(teamListInPool.get(i));   // Holdene fordeles så ligeligt som muligt i grupperne
        }
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void editBracket() {

    }
}
