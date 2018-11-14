package tournament.pool;

import tournament.Match;
import tournament.MatchBuilder;
import tournament.Team;

import java.util.ArrayList;
import java.util.HashMap;

public class Placement implements KnockoutBracket {
    ArrayList<Match> matches = new ArrayList<>();
    HashMap<Integer, Team> result = new HashMap<>();

    // Hvis gruppestørrelsen er større end 2 skal der throwes en exception
    @Override
    public Placement createKnockoutBracket(GroupBracket groupBracket) {
        if (groupBracket.getAmountOfGroups() > 2) {
            // throw some exception
        } else {
            MatchBuilder builder = new MatchBuilder();
            int iter = 0;

            while (iter < groupBracket.getGroups().get(0).getTeamList().size() && iter < groupBracket.getGroups().get(1).getTeamList().size()) {
                builder
                        .setName("Placement match " + iter + 1)
                        .setFinished(false)
                        .setFirstTeam(groupBracket.getGroups().get(0).getTeamList().get(iter))
                        .setSecondTeam(groupBracket.getGroups().get(1).getTeamList().get(iter));

                this.matches.add(new Match(builder));
            }
        }

        return this;
    }

    // Should only be called when the matches have been played
    public void calculateResults() {
        // The match-list should already be sorted in a way such that first is the match for 1. place, then for 3. place and so on.
        int winnerPlacement = 1;
        int loserPlacement = 2;
        for (Match match : this.matches) {
            if (match.isFinished()) {
                this.result.put(winnerPlacement, match.getWinner());
                this.result.put(loserPlacement, match.getLoser());
            }
            winnerPlacement = winnerPlacement + 2;
            loserPlacement = loserPlacement + 2;
        }
    }
}
