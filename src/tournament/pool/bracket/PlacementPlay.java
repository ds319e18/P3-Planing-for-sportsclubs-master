package tournament.pool.bracket;

import tournament.Match;
import tournament.Team;

import java.util.ArrayList;
import java.util.HashMap;

public class PlacementPlay implements KnockoutBracket {
    ArrayList<Match> matches = new ArrayList<>();
    HashMap<Integer, Team> result = new HashMap<>();

    // Hvis gruppestørrelsen er større end 2 skal der throwes en exception
    @Override
    public PlacementPlay createKnockoutBracket(GroupBracket groupBracket, int matchDurationInMinutes) {
        if (groupBracket.getAmountOfGroups() > 2) {
            // throw some exception
        } else {
            int iter = 0;

            while (iter < groupBracket.getGroups().get(0).getTeamList().size() && iter < groupBracket.getGroups().get(1).getTeamList().size()) {
                this.matches.add(new Match.Builder(matchDurationInMinutes)
                                                    .setName("Placement-Play Match" + iter + 1)
                                                    .setFinished(false)
                                                    .setFirstTeam(groupBracket.getGroups().get(0).getTeamList().get(iter))
                                                    .setSecondTeam(groupBracket.getGroups().get(1).getTeamList().get(iter))
                                                    .build());
                iter++;
            }
        }

        return this;
    }

    @Override
    public ArrayList<Match> getMatches() {
        return this.matches;
    }

    @Override
    public void createNextRound(ArrayList<Team> advancingTeams) {

    }

    @Override
    public ArrayList<Team> advanceTeams() {
        return null;
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
