package tournament.pool.bracket;

import exceptions.IllegalAmountOfGroupsException;
import exceptions.IllegalAmountOfTeamsException;
import exceptions.MatchNotFinishedException;
import tournament.Match;
import tournament.Team;

import java.util.ArrayList;

public class GoldAndBronzePlay extends PlayoffBracket {
    private Match goldMatch;
    private Match bronzeMatch;

    @Override
    public PlayoffBracket createPlayoffBracket(GroupBracket groupBracket, int matchDurationInMinutes) {
        if (groupBracket.getAmountOfGroups() != 2) {
            throw new IllegalAmountOfGroupsException();
        }
        else if (groupBracket.getGroups().get(0).getTeamList().size() != groupBracket.getGroups().get(1).getTeamList().size()) {
            throw new IllegalAmountOfTeamsException();
        } else {
            goldMatch = new Match.Builder(matchDurationInMinutes)
                    .setName("Final Match 1")
                    .setFirstTeam(new Team("TBD", groupBracket.getGroups().get(0).getTeamList().get(0).getYearGroup(), groupBracket.getGroups().get(0).getTeamList().get(0).getSkillLevel()))
                    .setSecondTeam(new Team("TBD", groupBracket.getGroups().get(0).getTeamList().get(0).getYearGroup(), groupBracket.getGroups().get(0).getTeamList().get(0).getSkillLevel()))
                    .setFinished(false)
                    .build();

            super.getMatches().add(goldMatch);

            bronzeMatch = new Match.Builder(matchDurationInMinutes)
                    .setName("Bronze Match 1")
                    .setFirstTeam(new Team("TBD", groupBracket.getGroups().get(0).getTeamList().get(0).getYearGroup(), groupBracket.getGroups().get(0).getTeamList().get(0).getSkillLevel()))
                    .setSecondTeam(new Team("TBD", groupBracket.getGroups().get(0).getTeamList().get(0).getYearGroup(), groupBracket.getGroups().get(0).getTeamList().get(0).getSkillLevel()))
                    .setFinished(false)
                    .build();

            super.getMatches().add(bronzeMatch);
        }

        return this;
    }

    @Override
    public void createNextRound(ArrayList<Team> advancingTeams) {
        goldMatch.setFirstTeam(advancingTeams.get(0));
        goldMatch.setSecondTeam(advancingTeams.get(2));
        bronzeMatch.setFirstTeam(advancingTeams.get(1));
        bronzeMatch.setSecondTeam(advancingTeams.get(3));
    }

    @Override
    public void calculateResults() {
        if (goldMatch.isFinished()) {
            super.getResults().put(1, goldMatch.getWinner());
            super.getResults().put(2, goldMatch.getLoser());
        } else {
            throw new MatchNotFinishedException();
        }

        if (bronzeMatch.isFinished()) {
            super.getResults().put(3, bronzeMatch.getWinner());
            super.getResults().put(4, bronzeMatch.getLoser());
        } else {
            throw new MatchNotFinishedException();
        }
    }
}
