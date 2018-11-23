package tournament.pool.bracket;

import tournament.Match;
import tournament.Team;

import java.util.ArrayList;
import java.util.HashMap;

public class EqualMatchesPlay extends KnockoutBracket {
    private int amountOfMatches;

    @Override
    public KnockoutBracket createKnockoutBracket(GroupBracket groupBracket, int matchDurationInMinutes) {
        return null;
    }

    @Override
    public ArrayList<Match> getMatches() {
        return null;
    }

    @Override
    public void createNextRound(ArrayList<Team> advancingTeams) {

    }

    @Override
    public void calculateResults() {

    }

    @Override
    public HashMap<Integer, Team> getResults() {

        return null;
    }
}
