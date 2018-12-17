package tournament.pool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tournament.Team;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PoolTest {
    private Pool pool;

    @BeforeEach
    void addToTeamList() {
        pool = new Pool.Builder().setSkilllLevel("A").setYearGroup(16).build();
        pool.addTeam(new Team("Jetsmark IF", 16,"A"));
        pool.addTeam(new Team("Jetsmark IF", 16,"A"));
        pool.addTeam(new Team("Jetsmark IF", 16,"A"));
        pool.addTeam(new Team("Silkeborg IF", 16,"A"));
    }

    @Test
    void addTeam() {
        ArrayList<Team> teamList = new ArrayList<>();
        teamList.add( new Team("Jetsmark IF 1", 16,"A"));
        teamList.add( new Team("Jetsmark IF 2", 16,"A"));
        teamList.add( new Team("Jetsmark IF 3", 16,"A"));
        teamList.add( new Team("Silkeborg IF 1", 16,"A"));

        assertEquals(teamList, pool.getTeamList());
    }

    @Test
    void removeTeam() {
        ArrayList<Team> teamList = new ArrayList<>();
        teamList.add( new Team("Jetsmark IF 1", 16,"A"));
        teamList.add( new Team("Jetsmark IF 2", 16,"A"));

        pool.removeTeam("Jetsmark IF 3");
        pool.removeTeam("Silkeborg IF 1");

        assertEquals(teamList, pool.getTeamList());
    }
}