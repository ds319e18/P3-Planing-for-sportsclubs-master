package tournament;

import java.util.Comparator;

public class TeamPointsComp implements Comparator<Team> {
    @Override
    public int compare(Team o1, Team o2) {
        return o2.getPoints() - o1.getPoints();
    }
}
