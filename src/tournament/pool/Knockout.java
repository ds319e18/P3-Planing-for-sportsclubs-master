package tournament.pool;

import tournament.Match;
import tournament.MatchBuilder;
import tournament.Team;

import java.util.ArrayList;

public class Knockout implements KnockoutBracket {
    ArrayList<Match> matches = new ArrayList<>();

    // This method creates the size of the match-array by creating empty matches
    @Override
    public KnockoutBracket createKnockoutBracket(GroupBracket groupBracket) {
        MatchBuilder builder = new MatchBuilder();
        int numberOfMatches = (groupBracket.getAmountOfGroups() * groupBracket.getAmountOfAdvancingTeamsPrGroup()) - 1;
        for (int i = 0; i < numberOfMatches; i++) {
            builder
                    .setName("Knockout Match" + i + 1)
                    .setFinished(false);

            this.matches.add(new Match(builder));
        }
        return this;
    }

    // This method creates each next round of the final stage by adding teams to the matches
    // This method should only be called when a round has finished
    // We can add the teams one at a time because they are already sorted correctly by the group stage
    public void createNextRound(ArrayList<Team> advancingTeams) {
        int first = 0;
        int second = 1;
        for (Match match : this.matches) {
            if (!match.isFinished()) {
                match.setFirstTeam(advancingTeams.get(first));
                match.setSecondTeam(advancingTeams.get(second));
                first = first + 2;
                second = second + 2;
            }
        }
    }

    // This method finds the teams advancing onto the next round. Should be called as a parameter to createNextRound()
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
