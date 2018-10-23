package tournament.pool;

import tournament.matchschedule.Match;

import java.util.List;

public class KnockoutBracket implements Bracket{
    private List<Match> matchList;


    @Override
    public String getType() {
        return null;
    }
}
