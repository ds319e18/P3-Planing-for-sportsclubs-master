package tournament.pool;
import tournament.Team;
import java.util.ArrayList;

public interface GroupBracket {
    GroupBracket createGroupBracket(ArrayList<Team> poolTeamList);
    ArrayList<Team> advanceTeams();
    int getAmountOfAdvancingTeamsPrGroup();
    int getAmountOfGroups();
    ArrayList<Group> getGroups();
}
