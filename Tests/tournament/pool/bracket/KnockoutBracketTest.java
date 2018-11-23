package tournament.pool.bracket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import tournament.Team;
import tournament.Tournament;
import tournament.TournamentType;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchSchedule;
import tournament.pool.Pool;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KnockoutBracketTest {
    Tournament tournament;
    ArrayList<Pool> pools = new ArrayList<>();
    ArrayList<Field> fields = new ArrayList<>();

    @BeforeAll
    public void beforeAll() {
        pools.add(new Pool.Builder()
                    .setYearGroup(6)
                    .setSkilllLevel("A")
                    .setMatchDurationInMinutes(20)
                    .build());

        tournament = new Tournament.Builder("Test Tournament")
                .setType(TournamentType.GroupAndKnockout)
                .setActive(true)
                .setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now().plusDays(1))
                .setPoolList(pools)
                .build();

        tournament.getFieldList().add(new Field("Bane 1", false));
        tournament.getFieldList().add(new Field("Bane 2", false));

        // Adding teams
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));

        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(2));
        tournament.findCorrectPool(6, "A").getGroupBracket().setAdvancingTeamsPrGroup(2);

        // Inputting games between each opponent in group and the creating the matches for the group
        tournament.findCorrectPool(6, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(6, "A").getGroupBracket().createMatches(tournament.findCorrectPool(6, "A").getMatchDuration());
    }

    @Test
    void testKnockoutPlayCreateKnockoutBracket01() {
        // This test is for 2 groups and an even amount of teams in each group
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new KnockoutPlay());

        // 8 teams, 2 groups, 2 teams advancing from each group should give us two semifinals and a final: 3 matches
        assertEquals(tournament.findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 3);
    }

    /*@Test
    void testKnockoutPlayCreateKnockoutBracket02() {
        // This test is for 3 groups and with 2 teams advancing in each group. We would like one team to skip a match, giving us
        // 5 matches in total
        /*tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(3));
        tournament.findCorrectPool(6, "A").getGroupBracket().setAdvancingTeamsPrGroup(2);

        // Inputting games between each opponent in group and the creating the matches for the group
        tournament.findCorrectPool(6, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(6, "A").getGroupBracket().createMatches(tournament.findCorrectPool(6, "A").getMatchDuration());
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new KnockoutPlay());*/

        //assertEquals(tournament.findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 5);

    //}

    @Test
    void testPlacementPlayCreateKnockoutBracket01() {
        // There must be 2 groups and an even amount of teams to play this type of knockout bracket
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new PlacementPlay());
        // 8 teams, 2 groups - advancingTeams shoud be set as number of teams in each group, this should give us 4 matches
        assertEquals(tournament.findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 4);

    }

    @Test
    void testGoldAndBronzePlayCreateKnockoutBracket01() {
        // There must be 2 groups and an even amount of teams to play this type of knockout bracket
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new GoldAndBronzePlay());
        // This knockout type should always give us 2 matches, a gold match and a bronze match
        assertEquals(tournament.findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 2);
    }

}
