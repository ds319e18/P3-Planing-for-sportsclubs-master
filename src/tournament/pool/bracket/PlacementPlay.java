package tournament.pool.bracket;

import exceptions.IllegalAmountOfGroupsException;
import exceptions.IllegalAmountOfTeamsException;
import exceptions.IllegalMethodCallToAdvanceTeam;
import tournament.Match;
import tournament.Team;
import tournament.TeamPointsComp;
import tournament.pool.Group;

import java.util.ArrayList;
import java.util.HashMap;

public class PlacementPlay extends KnockoutBracket {
    @Override
    public PlacementPlay createKnockoutBracket(GroupBracket groupBracket, int matchDurationInMinutes) {
        if (groupBracket.getAmountOfGroups() != 2) {
            throw new IllegalAmountOfGroupsException();
        }
        else if (groupBracket.getGroups().get(0).getTeamList().size() != groupBracket.getGroups().get(1).getTeamList().size()) {
            throw new IllegalAmountOfTeamsException();
        } else {
            int iter = 0;

            while (iter < groupBracket.getGroups().get(0).getTeamList().size() && iter < groupBracket.getGroups().get(1).getTeamList().size()) {
                super.getMatches().add(new Match.Builder(matchDurationInMinutes)
                        .setName("Placement Match " + (iter + 1) + ":")
                        .setFinished(false)
                        .setFirstTeam(new Team("TBD"
                                , groupBracket.getGroups().get(0).getTeamList().get(0).getYearGroup()
                                , groupBracket.getGroups().get(0).getTeamList().get(0).getSkillLevel()))
                        .setSecondTeam(new Team("TBD"
                                , groupBracket.getGroups().get(0).getTeamList().get(0).getYearGroup()
                                , groupBracket.getGroups().get(0).getTeamList().get(0).getSkillLevel()))
                        .build());
                iter++;
            }
        }
        return this;
    }


    @Override
    public void createNextRound(ArrayList<Team> advancingTeams) {
        advancingTeams.sort(new TeamPointsComp());

        for (Match match : super.getMatches()) {
            match.setFirstTeam(advancingTeams.get(0));

            // Remove the team from the list after it is added
            advancingTeams.remove(advancingTeams.get(0));
            for (int i = 0; i < advancingTeams.size(); i++) {
                if (advancingTeams.get(i).getGroupNumber() != match.getFirstTeam().getGroupNumber()) {
                    match.setSecondTeam(advancingTeams.get(i));

                    // Remove the team from the list after it is added
                    advancingTeams.remove(advancingTeams.get(i));
                    break;
                }
            }
        }
    }

    @Override
    // Should only be called when the matches have been played
    public void calculateResults() {
        // The match-list should already be sorted in a way such that first is the match for 1. place, then for 3. place and so on.
        int winnerPlacement = 1;
        int loserPlacement = 2;
        for (Match match : super.getMatches()) {
            if (match.isFinished()) {
                super.getResults().put(winnerPlacement, match.getWinner());
                super.getResults().put(loserPlacement, match.getLoser());
            }
            winnerPlacement = winnerPlacement + 2;
            loserPlacement = loserPlacement + 2;
        }
    }
}
