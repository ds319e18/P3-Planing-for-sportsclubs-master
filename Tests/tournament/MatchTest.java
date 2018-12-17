package tournament;

import org.junit.jupiter.api.Test;
import tournament.matchschedule.Field;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    private static Match createUnfinishedMatch() {
        Match match = new Match.Builder(30).setFirstTeam(new Team("Jetsmark IF 1")).
                setSecondTeam(new Team("Jetsmark IF 2")).setField(new Field("Field 1", true)).build();
        return match;
    }

    private static Match createFinishedMatch() {
        Match match = new Match.Builder(30).setFirstTeam(new Team("Jetsmark IF 1")).
                setSecondTeam(new Team("Jetsmark IF 2")).setField(new Field("Field 1", true)).build();
        match.setResult(new Result(3,1));
        return match;
    }

    @Test
    void getResult() {
        Match match = createFinishedMatch();
        assertEquals("3-1", match.getResult().toString());
    }

    @Test
    void getWinner() {
        Match match = createFinishedMatch();
        assertEquals("Jetsmark IF 1", match.getWinner().getName());
    }

    @Test
    void getLoser() {
        Match match = createFinishedMatch();
        assertEquals("Jetsmark IF 2", match.getLoser().getName());
    }

    @Test
    void isFinished() {
        Match match = createFinishedMatch();
        assertTrue(match.isFinished());
    }

    @Test
    void isNotFinished() {
        Match match = createUnfinishedMatch();
        assertFalse(match.isFinished());
    }


}