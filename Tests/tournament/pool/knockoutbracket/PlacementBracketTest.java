package tournament.pool.knockoutbracket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import tournament.Team;
import tournament.Tournament;
import tournament.TournamentType;
import tournament.matchschedule.Field;
import tournament.pool.Pool;
import tournament.pool.bracket.PlacementPlay;
import tournament.pool.bracket.StandardGroupPlay;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PlacementBracketTest {

    Tournament tournament;
    ArrayList<Pool> pools = new ArrayList<>();
    ArrayList<Field> fields = new ArrayList<>();

    @BeforeEach
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

        // There must be 2 groups and an even amount of teams to play this type of knockout bracket
        tournament.findCorrectPool(6,"A").addKnockoutBracket(new PlacementPlay());
    }


    @Test
    void testPlacementPlayCreateKnockoutBracket01() {
        // 8 teams, 2 groups - advancingTeams shoud be set as number of teams in each group, this should give us 4 matches
        assertEquals(tournament.findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 4);

    }
}
