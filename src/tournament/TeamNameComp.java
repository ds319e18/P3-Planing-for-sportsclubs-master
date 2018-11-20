package tournament;

import tournament.Team;

import java.util.Comparator;

public class TeamNameComp implements Comparator<Team> {
    @Override
    public int compare(Team o1, Team o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
