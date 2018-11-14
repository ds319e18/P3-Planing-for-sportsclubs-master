package tournament.pool;

import tournament.Match;
import tournament.Team;

import java.util.ArrayList;
import java.util.HashMap;

public class PlacementAndKnockout implements KnockoutBracket {
    ArrayList<Match> matches = new ArrayList<>();
    HashMap<Integer, Team> result = new HashMap<>();

    @Override
    public KnockoutBracket createKnockoutBracket(GroupBracket groupBracket) {



        return this;
    }
}
