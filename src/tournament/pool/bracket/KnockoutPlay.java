package tournament.pool.bracket;

import tournament.Match;
import tournament.Team;
import tournament.pool.bracket.GroupBracket;
import tournament.pool.bracket.KnockoutBracket;

import java.util.ArrayList;

public class KnockoutPlay implements KnockoutBracket {
    ArrayList<Match> matches = new ArrayList<>();

    // This method creates the size of the match-array by creating empty matches
    @Override
    public KnockoutBracket createKnockoutBracket(GroupBracket groupBracket, int matchDurationInMinutes) {
        int numberOfMatches = (groupBracket.getAmountOfGroups() * groupBracket.getAmountOfAdvancingTeamsPrGroup()) - 1;

        for (int i = 1; i < numberOfMatches + 1; i++) {
            this.matches.add(new Match.Builder(matchDurationInMinutes)
                                                .setName("Knockout Match:")
                                                .setFinished(false)
                                                .setFirstTeam(new Team("TBD"))
                                                .setSecondTeam(new Team("TBD"))
                                                .build());
        }
        return this;
    }

    @Override
    public ArrayList<Match> getMatches() {
        return this.matches;
    }

    // This method creates each next round of the final stage by adding teams to the matches
    // This method should only be called when a round has finished
    // We can add the teams one at a time because they are already sorted correctly by the group stage
    @Override
    public void createNextRound(ArrayList<Team> advancingTeams) {
        int first = 0;
        int second = 1;
        for (Match match : this.matches) {
            if (!match.isFinished() && first < advancingTeams.size()) {
                match.setFirstTeam(advancingTeams.get(first));
                match.setSecondTeam(advancingTeams.get(second));
                first = first + 2;
                second = second + 2;
            }
        }
    }

    // This method finds the teams advancing onto the next round. Should be called as a parameter to createNextRound()
    @Override
    public ArrayList<Team> advanceTeams() {
        ArrayList<Team> advancingTeams = new ArrayList<>();

        for (Match match : this.matches) {
            if (match.isFinished()) {
                advancingTeams.add(match.getWinner());
            }
        }

        return advancingTeams;
    }

}
