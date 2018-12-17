package tournament.pool.bracket;
import tournament.Match;
import tournament.Team;
import tournament.pool.Group;

import java.util.ArrayList;

public abstract class GroupBracket {
    private ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<Match> matches = new ArrayList<>();
    private int advancingTeamsPrGroup;
    private int matchesPrTeamAgainstOpponentInGroup = 0;
    private int amountOfGroups = 0;

    abstract public GroupBracket createGroupBracket(ArrayList<Team> poolTeamList);
    abstract public ArrayList<Team> advanceTeams();
    abstract public void createMatches(int matchDurationInMinutes);

    public int getAmountOfAdvancingTeamsPrGroup() {
        return this.advancingTeamsPrGroup;
    }

    public int getAmountOfGroups() {
        return this.amountOfGroups;
    }

    public ArrayList<Match> getMatches() {
        return this.matches;
    }

    public ArrayList<Group> getGroups() {
        return this.groups;
    }

    public int getMatchesPrTeamAgainstOpponentInGroup() {
        return this.matchesPrTeamAgainstOpponentInGroup;
    }

    public void setAdvancingTeamsPrGroup(int advancingTeamsPrGroup) {
        this.advancingTeamsPrGroup = advancingTeamsPrGroup;
    }
    public void setMatchesPrTeamAgainstOpponentInGroup(int matchesPrTeamAgainstOpponentInGroup) {
        this.matchesPrTeamAgainstOpponentInGroup = matchesPrTeamAgainstOpponentInGroup;
    }

    public void setAmountOfGroups(int amountOfGroups) {
        this.amountOfGroups = amountOfGroups;
    }
}
