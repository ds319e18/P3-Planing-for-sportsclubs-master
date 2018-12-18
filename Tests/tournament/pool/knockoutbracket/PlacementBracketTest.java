package tournament.pool.knockoutbracket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import tournament.*;
import tournament.matchschedule.Field;
import tournament.pool.Group;
import tournament.pool.Pool;
import tournament.pool.bracket.PlacementPlay;
import tournament.pool.bracket.StandardGroupPlay;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PlacementBracketTest {

    // This method creates a tournament with one pool, 8 teams, two groups.
    // Also two teams from each group advance to end play.
    // No matches has been played
    private static Tournament createTournamentPlacement01() {
        Tournament tournament;
        ArrayList<Pool> pools = new ArrayList<>();
        ArrayList<Field> fields = new ArrayList<>();

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
        tournament.findCorrectPool(6, "A").addPlayoffBracket(new PlacementPlay());

        // Setting the time for match schedules
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime("12:00");
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime("16:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime("12:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime("16:00");
        tournament.getMatchSchedule().planNoMixedMatches(tournament.getPoolList());

        return tournament;
    }

    // This method sets the result for all group matches and advance the correct teams to the first final round
    private static Tournament afterGroupPlay01() {
        Tournament tournament = createTournamentPlacement01();

        // Sets result for all group matches
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(1).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(2).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(3).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(4).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(5).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(6).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(7).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(8).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11).setResult(new Result(1, 2));

        // Calling createNextRound to create the first final rounds
        tournament.findCorrectPool(6,"A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6,"A").getGroupBracket().advanceTeams());

        for (Group group : tournament.findCorrectPool(6,"A").getGroupBracket().getGroups()) {
            for (Team team : group.getTeamList()) {
                System.out.println(team);
            }
        }

        return tournament;
    }

    // This method sets result for all final matches
    private static Tournament afterFinalPlay01() {
        Tournament tournament = afterGroupPlay01();

        // Sets result for all final matches
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(2).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(3).setResult(new Result(1, 2));

        // Setting the position after the finals for the teams in the finals
        tournament.findCorrectPool(6,"A").getPlayoffBracket().calculateResults();

        return tournament;

    }

    // 8 teams, 2 groups - advancingTeams should be set as number of teams in each group, this should give us 4 final matches
    // This test uses createTournamentPlacement01.
    @Test
    void testPlacementPlayCreateKnockoutBracket01() {
        // Testing the amount of final matches
        assertEquals(4, createTournamentPlacement01().findCorrectPool(6, "A").getPlayoffBracket().getMatches().size());
    }

    // 8 teams, 2 groups - this test uses afterGroupPlay01 and tests that createNextRound
    // puts the correct teams in the final plays.
    @Test
    void testCreateNextRound() {
        Tournament tournament = afterGroupPlay01();

        // Testing that the final matches contains the correct teams
        assertEquals("Jetsmark IF 7", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).getFirstTeam().getName());
        assertEquals("Jetsmark IF 8", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).getSecondTeam().getName());

        assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).getFirstTeam().getName());
        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).getSecondTeam().getName());

        assertEquals("Jetsmark IF 3", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(2).getFirstTeam().getName());
        assertEquals("Jetsmark IF 4", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(2).getSecondTeam().getName());

        assertEquals("Jetsmark IF 1", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(3).getFirstTeam().getName());
        assertEquals("Jetsmark IF 2", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(3).getSecondTeam().getName());
    }

    // This test method calculateResul
    @Test
    void testCalculateResult() {
        Tournament tournament = afterFinalPlay01();

        // Testing that every team in the finals get the correct position after the finals has been played
        assertEquals("Jetsmark IF 8", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(1).getName());
        assertEquals("Jetsmark IF 7", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(2).getName());
        assertEquals("Jetsmark IF 6", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(3).getName());
        assertEquals("Jetsmark IF 5", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(4).getName());
        assertEquals("Jetsmark IF 4", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(5).getName());
        assertEquals("Jetsmark IF 3", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(6).getName());
        assertEquals("Jetsmark IF 2", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(7).getName());
        assertEquals("Jetsmark IF 1", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(8).getName());

    }
}
