package tournament.matchschedule;

import tournament.Match;
import tournament.Team;
import tournament.pool.Group;
import tournament.pool.Pool;

import java.util.ArrayList;

public class MatchSchedule {
    private int timeBetweenMatches;
    private ArrayList<Pool> poolList;
    private ArrayList<Match> matches;

    public MatchSchedule(int timeBetweenMatches, ArrayList<Pool> poolList) {
        this.timeBetweenMatches = timeBetweenMatches;
        this.poolList = poolList;
    }

    private void createMatches() { // VIRKER MÅSKE
        for (Pool pool : poolList) {
            for (Group group : pool.getGroupBracket().getGroupList()) {

                for (int i = 0; i < group.getTeamList().size(); i++) {

                    for (int j = i + 1; j < group.getTeamList().size(); j++) {
                        matches.add(new Match("MATCH-NAME", false, new Field("FIELD-NAME", false), group.getTeamList().get(i), group.getTeamList().get(j)));
                    }
                }
            }
        }
    }

    private void updateMatchSchedule() {

    }
}
