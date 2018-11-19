package tournament.pool.bracket;
import tournament.Match;
import tournament.Team;
import tournament.pool.Group;

import java.util.ArrayList;

public interface GroupBracket {
    GroupBracket createGroupBracket(ArrayList<Team> poolTeamList);
    ArrayList<Team> advanceTeams();
    ArrayList<Match> createMatches(int matchDurationInMinutes);
    int getAmountOfAdvancingTeamsPrGroup();
    int getAmountOfGroups();
    ArrayList<Group> getGroups();
}
