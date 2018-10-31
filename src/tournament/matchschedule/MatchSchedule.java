package tournament.matchschedule;

import tournament.Match;
import tournament.pool.Pool;

import java.util.List;

public class MatchSchedule {
    private int timeBetweenMatches;
    private List<Pool> poolList;

    public MatchSchedule(int timeBetweenMatches, List<Pool> poolList) {
        this.timeBetweenMatches = timeBetweenMatches;
        this.poolList = poolList;
    }

    private void createMatches() {
        for (Pool pool : poolList) {
            // do something
        }
    }

    private void updateMatchSchedule() {

    }
}
