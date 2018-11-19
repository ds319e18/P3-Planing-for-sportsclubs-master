package tournament.pool.bracket;

import org.junit.jupiter.api.Test;
import tournament.Team;
import tournament.pool.Group;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class KnockoutPlayTest {

    @Test
    void createKnockoutBracket() {
        ArrayList<Group> testgroups = new ArrayList<>();
        StandardGroupPlay testGroupBracket = new StandardGroupPlay(2);

        testGroupBracket.setAdvancingTeamsPrGroup(1);
        testGroupBracket.setMatchesPrTeamAgainstOpponentInGroup(1);

        //testGroupBracket.createGroupBracket();
        //assertEquals(KnockoutBracket);
    }
}