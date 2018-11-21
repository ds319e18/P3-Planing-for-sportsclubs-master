package tournament.pool.bracket;

import tournament.Match;
import tournament.Team;

import java.util.ArrayList;
import java.util.HashMap;

public interface KnockoutBracket {
    KnockoutBracket createKnockoutBracket(GroupBracket groupBracket, int matchDurationInMinutes);
    ArrayList<Match> getMatches();
    void createNextRound(ArrayList<Team> advancingTeams);
    ArrayList<Team> advanceTeams();
    void calculateResults();
    HashMap<Integer, Team> getResults();
}
