package tournament.pool.bracket;

import exceptions.IllegalAmountOfGroupsException;
import exceptions.IllegalAmountOfTeamsException;
import tournament.Match;
import tournament.Team;
import tournament.pool.Group;

import java.util.ArrayList;
import java.util.HashMap;

public class EqualMatchesPlay extends KnockoutBracket {
    private int amountOfMatchesPrTeam = 0;

    @Override
    public KnockoutBracket createKnockoutBracket(GroupBracket groupBracket, int matchDurationInMinutes) {
        int numberOfMatches = amountOfMatchesPrTeam * groupBracket.getGroups().get(0).getTeamList().size() * groupBracket.getGroups().size() / 2;
        int numberOfTeams = calculateNumberOfTeams(groupBracket);

        if (!groupBracket.areThereEqualAmountOfTeamsInEachGroup()) {
            throw new IllegalAmountOfTeamsException();
        } else if (true) {
            // what??
        } else {
            // Creating all matches
            for (int i = 0; i < numberOfMatches; i++) {
                super.getMatches().add(new Match.Builder(matchDurationInMinutes)
                        .setName("Match: ")
                        .setFinished(false)
                        .setFirstTeam(new Team("TBD"))
                        .setSecondTeam(new Team("TBD"))
                        .build());
            }
        }
        return this;
    }

    @Override
    public void createNextRound(ArrayList<Team> advancingTeams) {

    }

    @Override
    public void calculateResults() {

    }

    @Override
    public void setAmountOfMatchesPrTeam(int amountOfMatchesPrTeam) {
        this.amountOfMatchesPrTeam = amountOfMatchesPrTeam;
    }

    private int calculateNumberOfTeams(GroupBracket groupBracket) {
        int answer = 0;
        for (Group group : groupBracket.getGroups()) {
            answer += group.getTeamList().size();
        }
        return answer;
    }
}
