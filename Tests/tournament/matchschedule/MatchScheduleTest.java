package tournament.matchschedule;

import org.junit.jupiter.api.Test;
import tournament.Tournament;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MatchScheduleTest {

    private static Tournament createEmptyTournament() {
        Tournament tournament = new Tournament.Builder("Jetsmark IF tournament").setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now().plusDays(2)).build();
        return tournament;
    }
    @Test
    void getMatchDays() {
        Tournament tournament = createEmptyTournament();

        for (int i = 0; i < tournament.getMatchSchedule().getMatchDays().size(); i++) {
            assertEquals("Dag "+ (i+1), tournament.getMatchSchedule().getMatchDays().get(i).getName());
        }
    }

    @Test
    void testNumberOfMatchDays() {
        Tournament tournament = createEmptyTournament();
        assertEquals(3, tournament.getMatchSchedule().getMatchDays().size());
    }

    @Test
    void setTimeBetweenMatchDays() {
        Tournament tournament = createEmptyTournament();
        tournament.getMatchSchedule().setTimeBetweenMatchDays(10);

        for (MatchDay matchDay: tournament.getMatchSchedule().getMatchDays()) {
            assertEquals(10, matchDay.getTimeBetweenMatches());
        }
    }

    @Test
    void findMatchDay() {
        Tournament tournament = createEmptyTournament();

        assertEquals("Dag 3", tournament.getMatchSchedule().findMatchDay("Dag 3").getName());
    }

    @Test
    void setMixedMatches() {

    }


}