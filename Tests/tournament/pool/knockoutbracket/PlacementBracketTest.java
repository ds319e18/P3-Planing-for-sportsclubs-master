package tournament.pool.knockoutbracket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import tournament.*;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;
import tournament.pool.Group;
import tournament.pool.Pool;
import tournament.pool.bracket.PlacementPlay;
import tournament.pool.bracket.StandardGroupPlay;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PlacementBracketTest {
/*
    // This method creates a tournament with one pool, 8 teams, two groups.
    // Also two teams from each group advance to end play.
    // No matches has been played
    private static Tournament createTournament01() {
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
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new PlacementPlay());

        return tournament;
    }

    // This method creates the same tournament as createTournament01, but where the group play as played.
    private static Tournament createTournament02() {
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
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new PlacementPlay());

        // Setting the time for match schedules
        /*tournament.getMatchSchedule().getMatchDays().get(0).setStartTime(LocalTime.of(12, 0));
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime(LocalTime.of(16, 0));
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime(LocalTime.of(12, 0));
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime(LocalTime.of(16, 0));
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getAllMatches());*/

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


        // Printer kampe ud hvor gruppe kampe er lavet
        /*for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }

        // Sortere alle grupper i gruppespil efter point og kan printe grupper ud med point
        for (Group group : tournament.getPoolList().get(0).getGroupBracket().getGroups()) {
            group.getTeamList().sort(new TeamPointsComp());
            for (Team team : group.getTeamList()) {
                            System.out.println("Group: " + team.getGroupNumber() + " Name: " + team.getName() + " Point: " + team.getPoints());
            }
        }

        tournament.findCorrectPool(6,"A").getKnockoutBracket().createNextRound(tournament.findCorrectPool(6,"A").getGroupBracket().advanceTeams());

        return tournament;
    }

    @Test
    void testPlacementPlayCreateKnockoutBracket01() {
        // 8 teams, 2 groups - advancingTeams shoud be set as number of teams in each group, this should give us 4 matches
        // This test uses createTournament01.
        assertEquals(4, createTournament01().findCorrectPool(6, "A").getKnockoutBracket().getMatches().size());
    }

    @Test
    void testCreateNextRound() {
        // 8 teams, 2 groups - this test uses createTournament02 and tests that createNextRound
        // puts the correct teams in the final plays.
        assertEquals("Jetsmark IF 7", createTournament02().getMatchSchedule().getMatchDays().get(0).getMatches().get(12).getFirstTeam().getName());
        assertEquals("Jetsmark IF 8", createTournament02().getMatchSchedule().getMatchDays().get(0).getMatches().get(12).getSecondTeam().getName());

        assertEquals("Jetsmark IF 5", createTournament02().getMatchSchedule().getMatchDays().get(0).getMatches().get(13).getFirstTeam().getName());
        assertEquals("Jetsmark IF 6", createTournament02().getMatchSchedule().getMatchDays().get(0).getMatches().get(13).getSecondTeam().getName());

        assertEquals("Jetsmark IF 3", createTournament02().getMatchSchedule().getMatchDays().get(0).getMatches().get(14).getFirstTeam().getName());
        assertEquals("Jetsmark IF 4", createTournament02().getMatchSchedule().getMatchDays().get(0).getMatches().get(14).getSecondTeam().getName());

        assertEquals("Jetsmark IF 1", createTournament02().getMatchSchedule().getMatchDays().get(0).getMatches().get(15).getFirstTeam().getName());
        assertEquals("Jetsmark IF 2", createTournament02().getMatchSchedule().getMatchDays().get(0).getMatches().get(15).getSecondTeam().getName());
    }*/
}
