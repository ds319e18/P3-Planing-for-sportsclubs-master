package tournament.pool.bracket;

import exceptions.IllegalMethodCallToAdvanceTeam;
import tournament.Match;
import tournament.Team;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class KnockoutBracket {
    private ArrayList<Match> matches = new ArrayList<>();
    private HashMap<Integer, Team> result = new HashMap<>();

    abstract public KnockoutBracket createKnockoutBracket(GroupBracket groupBracket, int matchDurationInMinutes);

    abstract public void calculateResults();

    abstract public void createNextRound(ArrayList<Team> advancingTeams);

    public ArrayList<Team> advanceTeams() {
        // Should be overridden in KnockoutPlay-class
        throw new IllegalMethodCallToAdvanceTeam();
    }

    public void setAmountOfMatchesPrTeam(int amountOfMatchesPrTeam) {
        //Write own exception
        throw new IllegalMethodCallToAdvanceTeam();
    }

    public ArrayList<Match> getMatches() {
        return this.matches;
    }

    public HashMap<Integer, Team> getResults() {
        return this.result;
    }

}
