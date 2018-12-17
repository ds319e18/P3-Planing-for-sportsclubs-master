package tournament.pool.bracket;

import exceptions.IllegalAmountOfGroupsException;
import exceptions.IllegalAmountOfTeamsException;
import exceptions.MatchNotFinishedException;
import tournament.Match;
import tournament.Team;
import tournament.TeamPointsComp;

import java.util.ArrayList;

public class PlacementPlay extends PlayoffBracket {
    @Override
    public PlacementPlay createPlayoffBracket(GroupBracket groupBracket, int matchDurationInMinutes) {
        if (groupBracket.getAmountOfGroups() > 2) {
            throw new IllegalAmountOfGroupsException();
        } else if (groupBracket.getAmountOfGroups() == 2) {
            if (groupBracket.getGroups().get(0).getTeamList().size() != groupBracket.getGroups().get(1).getTeamList().size()) {
                throw new IllegalAmountOfTeamsException();
            }
        } else {
            // Hvis der er ulige hold og kun én gruppe
            if ((groupBracket.getGroups().get(0).getTeamList().size() % 2) != 0) {
                throw new IllegalAmountOfTeamsException();
            }

            int iter;
            groupBracket.setAdvancingTeamsPrGroup(groupBracket.getGroups().get(0).getTeamList().size());
            if (groupBracket.getAmountOfGroups() == 1) {
                iter = groupBracket.getAmountOfAdvancingTeamsPrGroup() / 2;
            } else {
                iter = groupBracket.getAmountOfAdvancingTeamsPrGroup();
            }

            while (iter < groupBracket.getAmountOfAdvancingTeamsPrGroup()) {
                super.getMatches().add(new Match.Builder(matchDurationInMinutes)
                        .setName("Placement Match " + (iter + 1))
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
            } else {
                throw new MatchNotFinishedException();
            }
            winnerPlacement = winnerPlacement + 2;
            loserPlacement = loserPlacement + 2;
        }
    }

    @Override
    public String toString() {
        return "Placeringsspil";
    }
}
