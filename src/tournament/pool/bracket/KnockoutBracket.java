package tournament.pool.bracket;

import tournament.Match;

import java.util.ArrayList;

public interface KnockoutBracket {
    KnockoutBracket createKnockoutBracket(GroupBracket groupBracket, int matchDurationInMinutes);
    ArrayList<Match> getMatches();
}
