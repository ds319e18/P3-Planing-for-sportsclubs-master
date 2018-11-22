package tournament.pool.bracket;

import exceptions.IllegalAmountOfGroupsException;
import exceptions.IllegalAmountOfTeamsException;
import exceptions.IllegalMethodCallToAdvanceTeam;
import exceptions.MatchNotFinishedException;
import tournament.Match;
import tournament.Team;
import tournament.TeamPointsComp;
import tournament.pool.Group;

import java.util.ArrayList;
import java.util.HashMap;

public class GoldAndBronzePlay implements KnockoutBracket {
    private Match goldMatch;
    private Match bronzeMatch;
    private HashMap<Integer, Team> result = new HashMap<>();
    private GroupBracket groupBracket;

    @Override
    public KnockoutBracket createKnockoutBracket(GroupBracket groupBracket, int matchDurationInMinutes) {
        if (groupBracket.getAmountOfGroups() != 2) {
            throw new IllegalAmountOfGroupsException();
        }
        else if (groupBracket.getGroups().get(0).getTeamList().size() != groupBracket.getGroups().get(1).getTeamList().size()) {
            throw new IllegalAmountOfTeamsException();
        } else {
            goldMatch = new Match.Builder(matchDurationInMinutes)
                    .setName("Final Match:" + '\t')
                    .setFirstTeam(new Team("TBD"))
                    .setSecondTeam(new Team("TBD"))
                    .setFinished(false)
                    .build();

            bronzeMatch = new Match.Builder(matchDurationInMinutes)
                    .setName("Bronze Match:" + '\t')
                    .setFirstTeam(new Team("TBD"))
                    .setSecondTeam(new Team("TBD"))
                    .setFinished(false)
                    .build();
        }

        this.groupBracket = groupBracket;
        return this;
    }

    @Override
    public ArrayList<Match> getMatches() {
        ArrayList<Match> matches = new ArrayList<>();
        matches.add(goldMatch);
        matches.add(bronzeMatch);
        return matches;
    }

    @Override
    public void createNextRound(ArrayList<Team> advancingTeams) {
        goldMatch.setFirstTeam(advancingTeams.get(0));
        goldMatch.setSecondTeam(advancingTeams.get(2));
        bronzeMatch.setFirstTeam(advancingTeams.get(1));
        bronzeMatch.setSecondTeam(advancingTeams.get(3));
    }

    // Should not be called actually
    @Override
    public ArrayList<Team> advanceTeams() {
        //Throw exception
        throw new IllegalMethodCallToAdvanceTeam();
    }

    @Override
    public void calculateResults() {
        if (goldMatch.isFinished()) {
            this.result.put(1, goldMatch.getWinner());
            this.result.put(2, goldMatch.getLoser());
        } else {
            throw new MatchNotFinishedException();
        }

        if (bronzeMatch.isFinished()) {
            this.result.put(3, bronzeMatch.getWinner());
            this.result.put(4, bronzeMatch.getLoser());
        } else {
            throw new MatchNotFinishedException();
        }
    }

    @Override
    public HashMap<Integer, Team> getResults() {
        return this.result;
    }
}
