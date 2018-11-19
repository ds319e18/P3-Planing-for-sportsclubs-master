package tournament.pool.bracket;

import tournament.Match;

import java.util.ArrayList;

public class EqualMatchesPlay implements KnockoutBracket {
    private int amountOfMatches;

    @Override
    public KnockoutBracket createKnockoutBracket(GroupBracket groupBracket, int matchDurationInMinutes) {
        return null;
    }


    @Override
    public ArrayList<Match> getMatches() {
        return null;
    }
}
