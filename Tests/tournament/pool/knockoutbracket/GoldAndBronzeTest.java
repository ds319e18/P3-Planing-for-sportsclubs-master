package tournament.pool.knockoutbracket;

import org.junit.jupiter.api.Test;
import tournament.*;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;
import tournament.pool.Group;
import tournament.pool.Pool;
import tournament.pool.bracket.GoldAndBronzePlay;
import tournament.pool.bracket.StandardGroupPlay;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoldAndBronzeTest {

    private static Tournament createTournamentGoldAndBronze01() {
        Tournament tournament;
        ArrayList<Pool> pools = new ArrayList<>();

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
        tournament.findCorrectPool(6, "A").addPlayoffBracket(new GoldAndBronzePlay());

        // Setting the time for match schedules
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime("12:00");
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime("16:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime("12:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime("16:00");
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getPoolList());

        return tournament;
    }

    // This method sets result for all group matches and advance the two correct teams
    private static Tournament afterGroupPlay01() {
        Tournament tournament = createTournamentGoldAndBronze01();

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

        // Creates final round after the group play has been played
        tournament.findCorrectPool(6, "A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6, "A").getGroupBracket().advanceTeams());

        return tournament;
    }

    // This method plays the two final matches
    private static Tournament afterFinal01() {
        Tournament tournament = afterGroupPlay01();

        // Sets result for the matches in the finals
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).setResult(new Result(1, 2));

        // Gives the correct position in the tournament after the finals for the teams in the finals
        tournament.findCorrectPool(6,"A").getPlayoffBracket().calculateResults();

        return tournament;
    }


        // This tests that Gold and Bronze type should always give us 2 matches, a gold match and a bronze match
        @Test
        void testCreateKnockoutBracket01 () {
            Tournament tournament = createTournamentGoldAndBronze01();

            // Testing amount of final matches
            assertEquals(tournament.findCorrectPool(6, "A").getPlayoffBracket().getMatches().size(), 2);
        }

        // This test that the correct team advance to the finals
        @Test
        void testCreateNextRound01 () {
            Tournament tournament = afterFinal01();

            // Testing that the correct teams in in the correct match
            assertEquals("Jetsmark IF 7", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).getFirstTeam().getName());
            assertEquals("Jetsmark IF 8", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).getSecondTeam().getName());
            assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).getFirstTeam().getName());
            assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).getSecondTeam().getName());

            // Testing method calculate result gives the correct teams their position in the tournament
            assertEquals("Jetsmark IF 8", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(1).getName());
            assertEquals("Jetsmark IF 7", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(2).getName());
            assertEquals("Jetsmark IF 6", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(3).getName());
            assertEquals("Jetsmark IF 5", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(4).getName());

        }
    }
