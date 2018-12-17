package tournament.team;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import tournament.Team;
import tournament.TeamPointsComp;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeamPointSort {
    private ArrayList<Team> teams;

    @BeforeEach
    void createTwoTeams() {
        teams = new ArrayList<>();
        teams.add(new Team("name1", 5, "A"));
        teams.add(new Team("name2", 6, "A"));

    }

    @Test
    void testSortPoint01() {
        teams.get(0).setPoints(10);
        teams.get(1).setPoints(12);

        teams.sort(new TeamPointsComp());

        assertEquals(teams.get(0).getName(), "name2");
        assertEquals(teams.get(1).getName(), "name1");
    }

    @Test
    void testSortPoint02() {
        teams.get(0).setPoints(10);
        teams.get(1).setPoints(10);

        teams.sort(new TeamPointsComp());

        assertEquals(teams.get(0).getName(), "name1");
        assertEquals(teams.get(1).getName(), "name2");
    }
}
