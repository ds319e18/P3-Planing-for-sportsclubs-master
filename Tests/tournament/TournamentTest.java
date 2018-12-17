package tournament;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;
import tournament.matchschedule.MatchSchedule;
import tournament.pool.Group;
import tournament.pool.Pool;
import tournament.pool.bracket.GoldAndBronzePlay;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.bracket.PlacementPlay;
import tournament.pool.bracket.StandardGroupPlay;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TournamentTest {
    private final int poolNumber = 6;

    private static Tournament createEmptyTournament() {
        Tournament tournament = new Tournament.Builder("Jetsmark IF tournament").setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now().plusDays(1)).build();
        return tournament;
    }

    @Test
    void findCorrectPool() {
        Tournament tournament = createEmptyTournament();
        Pool expectedPool = new Pool.Builder().setSkilllLevel("A").setYearGroup(11).build();

        for (int i = 0; i < poolNumber; i++) {
            tournament.getPoolList().add(new Pool.Builder().setSkilllLevel("A").setYearGroup(i+8).build());
        }

        assertEquals(expectedPool, tournament.findCorrectPool(11, "A"));
    }

    @Test
    void isInActive() {
        Tournament tournament = createEmptyTournament();
        assertFalse(tournament.isActive());
    }

    @Test
    void isActive() {
        Tournament tournament = createEmptyTournament();
        tournament.setActive(true);
        assertTrue(tournament.isActive());
    }
}