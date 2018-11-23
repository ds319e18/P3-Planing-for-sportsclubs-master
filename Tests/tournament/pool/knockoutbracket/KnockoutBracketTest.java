package tournament.pool.knockoutbracket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import tournament.*;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;
import tournament.matchschedule.MatchSchedule;
import tournament.pool.Group;
import tournament.pool.Pool;
import tournament.pool.bracket.GoldAndBronzePlay;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.bracket.PlacementPlay;
import tournament.pool.bracket.StandardGroupPlay;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class KnockoutBracketTest {
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

        // This test is for 2 groups and an even amount of teams in each group
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new KnockoutPlay());
    }

    @Test
    void testKnockoutPlayCreateKnockoutBracket01() {
        // 8 teams, 2 groups, 2 teams advancing from each group should give us two semifinals and a final: 3 matches
        assertEquals(tournament.findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 3);
    }

    @Test
    void testKnockoutPlayCreateKnockoutBracket02() {
        // This test is for 3 groups and with 3 teams advancing in each group. We would like one team to skip a match, giving us
        // 8 matches in total
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));

        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(3));
        tournament.findCorrectPool(6, "A").getGroupBracket().setAdvancingTeamsPrGroup(3);

        // Inputting games between each opponent in group and the creating the matches for the group
        tournament.findCorrectPool(6, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(6, "A").getGroupBracket().createMatches(tournament.findCorrectPool(6, "A").getMatchDuration());
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new KnockoutPlay());

        assertEquals(tournament.findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 8);

    }

    @Test
    void testKnockoutPlayCreateKnockoutBracket03() {
        // This test is for 3 groups and with 2 teams advancing in each group. We would like one team to skip a match, giving us
        // 5 matches in total
        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(3));
        tournament.findCorrectPool(6, "A").getGroupBracket().setAdvancingTeamsPrGroup(2);

        // Inputting games between each opponent in group and the creating the matches for the group
        tournament.findCorrectPool(6, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(6, "A").getGroupBracket().createMatches(tournament.findCorrectPool(6, "A").getMatchDuration());
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new KnockoutPlay());

        assertEquals(tournament.findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 5);

    }

    @Test
    void testKnockoutPlayCreateKnockoutBracket04() {
        // This test is for 2 groups and with 3 teams advancing in each group. We would like one team to skip a match, giving us
        // 5 matches in total
        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(2));
        tournament.findCorrectPool(6, "A").getGroupBracket().setAdvancingTeamsPrGroup(3);

        // Inputting games between each opponent in group and the creating the matches for the group
        tournament.findCorrectPool(6, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(6, "A").getGroupBracket().createMatches(tournament.findCorrectPool(6, "A").getMatchDuration());
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new KnockoutPlay());

        assertEquals(tournament.findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 5);

    }

    @Test
    void testKnockoutPlayCreateNextRound01() {
        // This test is for 2 groups and 2 advancing teams
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new KnockoutPlay());
        /*
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime(LocalTime.of(12, 0));
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime(LocalTime.of(16, 0));
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime(LocalTime.of(12, 0));
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime(LocalTime.of(16, 0));
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getAllMatches());
        */
       /* // Printer kampe ud hvor gruppe kampe er lavet
        for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();*/


            // Setting result for group play
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

            // Sortere alle grupper i gruppespil efter point og kan printe grupper ud med point
            for (Group group : tournament.getPoolList().get(0).getGroupBracket().getGroups()) {
                group.getTeamList().sort(new TeamPointsComp());
                for (Team team : group.getTeamList()) {
                //            System.out.println("Group: " + team.getGroupNumber() + " Name: " + team.getName() + " Point: " + team.getPoints());
                }
            }

            // Asserts til hvem der ligger øverst måske.
            assertEquals("Jetsmark IF 7", tournament.findCorrectPool(6, "A").getGroupBracket().getGroups().get(0).getTeamList().get(0).getName());
            assertEquals("Jetsmark IF 8", tournament.findCorrectPool(6, "A").getGroupBracket().getGroups().get(1).getTeamList().get(0).getName());

            tournament.findCorrectPool(6, "A").getKnockoutBracket().createNextRound(tournament.findCorrectPool(6, "A").getGroupBracket().advanceTeams());

            // Printer kampe ud hvor de første to knockout matches er lavet.
        /*for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }*/

            // Asserts til hvem der går videre i de to første knockout kampe
            assertEquals("Jetsmark IF 7", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).getFirstTeam().getName());
            assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).getSecondTeam().getName());
            assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).getFirstTeam().getName());
            assertEquals("Jetsmark IF 8", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).getSecondTeam().getName());

            // Spiller anden runde så vi finder en finalist
            tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).setResult(new Result(1, 2));
            tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).setResult(new Result(1, 2));

            tournament.findCorrectPool(6, "A").getKnockoutBracket().createNextRound(tournament.findCorrectPool(6, "A").getKnockoutBracket().advanceTeams());

            assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(14).getFirstTeam().getName());
            assertEquals("Jetsmark IF 8", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(14).getSecondTeam().getName());

            // Printer kampe ud hvor de første to knockout matches har spillet.
        /*for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }*/

            tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(14).setResult(new Result(1, 2));

            tournament.findCorrectPool(6, "A").getKnockoutBracket().calculateResults();

            assertEquals("Jetsmark IF 8", tournament.findCorrectPool(6, "A").getKnockoutBracket().getResults().get(1).getName());

            //System.out.print(tournament.findCorrectPool(6, "A").getKnockoutBracket().getResults());
        }


}
