package tournament.pool.bracket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tournament.Team;
import tournament.Tournament;
import tournament.TournamentType;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchSchedule;
import tournament.pool.Pool;

import java.time.LocalDate;
import java.util.ArrayList;

public class GoldAndBronzePlayTest {
    Tournament tournament;
    ArrayList<Pool> pools = new ArrayList<>();
    ArrayList<Field> fields = new ArrayList<>();

    @BeforeAll
    public void beforeAll() {
        tournament.getFieldList().add(new Field("Bane 1", false));
        tournament.getFieldList().add(new Field("Bane 2", false));

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

        // Adding teams
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));

        // Adding a group bracket to the pool, when playing GoldAndBronzePlay there will always be 2 groups
        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(2));

        // When playing GoldAndBronzePlay there will always be 2 teams advancing to the final stage
        tournament.findCorrectPool(6, "A").getGroupBracket().setAdvancingTeamsPrGroup(2);

        // Inputting games between each opponent in group and the creating the matches for the group
        tournament.findCorrectPool(6, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(6, "A").getGroupBracket().createMatches(tournament.findCorrectPool(6, "A").getMatchDuration());
    }

    /*@Test
    void public test*/

}
