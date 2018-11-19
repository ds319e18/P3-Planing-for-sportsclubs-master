package tournament;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tournament.pool.Pool;

import static org.junit.jupiter.api.Assertions.*;

class TournamentTest {
    private final int poolNumber = 6;

    @Test
    void findCorrectPool() {
        Tournament tournament = new Tournament.Builder("Jetsmark IF tournament").build();
        Pool expectedPool = new Pool.Builder().setSkilllLevel("A").setYearGroup(11).build();

        for (int i = 0; i < poolNumber; i++) {
            tournament.getPoolList().add(new Pool.Builder().setSkilllLevel("A").setYearGroup(i+8).build());
        }

        assertEquals(expectedPool, tournament.findCorrectPool(11, "A"));
    }

    @Test
    void isInActive() {
        Tournament tournament = new Tournament.Builder("Jetsmark IF tournament").build();
        assertFalse(tournament.isActive());

    }

    @Test
    void isActive() {
        Tournament tournament = new Tournament.Builder("Jetsmark IF tournament").setActive(true).build();
        assertTrue(tournament.isActive());

    }

}