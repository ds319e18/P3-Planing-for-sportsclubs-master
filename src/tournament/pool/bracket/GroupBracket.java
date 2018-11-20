package tournament.pool.bracket;
import tournament.Match;
import tournament.Team;
import tournament.pool.Group;

import java.util.ArrayList;

public interface GroupBracket {
    GroupBracket createGroupBracket(ArrayList<Team> poolTeamList);
    ArrayList<Team> advanceTeams();
    void createMatches(int matchDurationInMinutes);
    int getAmountOfAdvancingTeamsPrGroup();
    int getAmountOfGroups();
    ArrayList<Match> getMatches();
    ArrayList<Group> getGroups();
    void setAdvancingTeamsPrGroup(int advancingTeamsPrGroup);
    void setMatchesPrTeamAgainstOpponentInGroup(int matchesPrTeamAgainstOpponentInGroup);
    int getMatchesPrTeamAgainstOpponentInGroup();
}
