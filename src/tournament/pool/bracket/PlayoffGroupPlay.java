package tournament.pool.bracket;

import exceptions.IllegalAmountOfTeamsException;
import tournament.Match;
import tournament.Team;
import tournament.pool.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PlayoffGroupPlay extends PlayoffBracket {
    /*private HashMap<Integer, Integer> amountOfMatchesChoices = new HashMap<>();
    private ArrayList<Group> playoffGroups = new ArrayList<>();
    private int amountOfGroups;
    private int matchesPrTeam = 0;
    private int noOfTeams;*/

    // This object MUST be created with this constructor before calling createPlayoffBracket
    /*public PlayoffGroupPlay(GroupBracket groupBracket, int amountOfGroups) {
        calculateAmountOfMatchesChoices(groupBracket);
        this.amountOfGroups = amountOfGroups;
        this.noOfTeams = calculateNumberOfTeams(groupBracket);
        this.matchesPrTeam
    }*/

    @Override
    public PlayoffBracket createPlayoffBracket(GroupBracket groupBracket, int matchDurationInMinutes) {
        /*if (!groupBracket.areThereEqualAmountOfTeamsInEachGroup()) {
            throw new IllegalAmountOfTeamsException();
        } else if (false) {
            // Maybe throw exception if the number of teams in each group are uneven
        } else {
            // Create empty groups


            // Creating empty matches in the stage, we know how many matches should be created based on the choice
            int noOfMatches = this.matchesPrTeam * noOfTeams / 2;
            for (int matchCount = 0; matchCount < noOfMatches; matchCount++) {
                super.getMatches().add(new Match.Builder(matchDurationInMinutes)
                        .setFirstTeam(new Team("TBD"))
                        .setSecondTeam(new Team("TBD"))
                        .setFinished(false)
                        .setName("Playoff Match: ")
                        .build());
            }
        }*/
        return this;
    }

    private void createMatches() {
    }

    @Override
    public void createNextRound(ArrayList<Team> advancingTeams) {

    }

    @Override
    public void calculateResults() {
        /*int placement = 1;
        for (Group group : this.playoffGroups) {
            for (Team team : group.getTeamList()) {
                super.getResults().put(placement, team);
                placement++;
            }
        }*/
    }

    private int calculateNumberOfTeams(GroupBracket groupBracket) {
        int answer = 0;
        for (Group group : groupBracket.getGroups()) {
            answer += group.getTeamList().size();
        }
        return answer;
    }

    /*private void calculateAmountOfMatchesChoices(GroupBracket groupBracket) {
        int amountOfMatchesPrTeam;
        int amountOfTeamsInEachGroup;

        // Counting how many teams are in the pool
        amountOfTeamsInEachGroup = calculateNumberOfTeams(groupBracket);
        int amountOfTeamsInEachGroupAfter;

        // Calculating the amount of matches that is possible the be played and adding them to the list of choices
        for (int groups = 2; groups <= 5; groups++) {
            if (doesAmountOfMatchesMakeSense(amountOfTeamsInEachGroup, groups)) {
                amountOfTeamsInEachGroupAfter = amountOfTeamsInEachGroup / groups;
                amountOfMatchesPrTeam = amountOfTeamsInEachGroupAfter - 1;
                this.amountOfMatchesChoices.put(groups, amountOfMatchesPrTeam);
            }
        }
    }*/

    private boolean doesAmountOfMatchesMakeSense(int noOfTeams, int noOfGroups) {
        boolean answer = false;

        if ((noOfTeams % noOfGroups) == 0) {
            answer = true;
        }

        return answer;
    }

    /*public int getMatchesPrTeam() {
        return matchesPrTeam;
    }

    public void setMatchesPrTeam(int matchesPrTeam) {
        this.matchesPrTeam = matchesPrTeam;
    }
*/
}
