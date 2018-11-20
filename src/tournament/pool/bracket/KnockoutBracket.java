package tournament.pool.bracket;

import tournament.Match;
import tournament.Team;

import java.util.ArrayList;

public interface KnockoutBracket {
    KnockoutBracket createKnockoutBracket(GroupBracket groupBracket, int matchDurationInMinutes);
    ArrayList<Match> getMatches();
    void createNextRound(ArrayList<Team> advancingTeams);
    ArrayList<Team> advanceTeams();
}
