package tournament.pool.bracket;

import org.junit.jupiter.api.Test;
import tournament.Team;
import tournament.pool.Group;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class KnockoutPlayTest {

    @Test
    void createKnockoutBracket() {
        private ArrayList<Group> testgroups = new ArrayList<>();
        StandardGroupPlay testGroupBracket = new StandardGroupPlay();

        testGroupBracket.setAdvancingTeamsPrGroup(1);
        testGroupBracket.setMatchesPrTeamAgainstOpponentInGroup(1);

        testGroupBracket.createGroupBracket();
        //assertEquals(KnockoutBracket);
    }
}