package tournament.pool.knockoutbracket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tournament.*;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;
import tournament.pool.Group;
import tournament.pool.Pool;
import tournament.pool.bracket.GoldAndBronzePlay;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.bracket.StandardGroupPlay;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoldAndBronzeTest {
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
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new GoldAndBronzePlay());
    }

    @Test
    void testCreateKnockoutBracket01() {

        // This knockout type should always give us 2 matches, a gold match and a bronze match
        assertEquals(tournament.findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 2);
    }

    @Test
    void testCreateNextRound01(){
        // This test is for 2 groups and 2 advancing teams
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new GoldAndBronzePlay());
        /*
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime(LocalTime.of(12, 0));
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime(LocalTime.of(16, 0));
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime(LocalTime.of(12, 0));
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime(LocalTime.of(16, 0));
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getAllMatches());
        */
        // Printer kampe ud hvor gruppe kampe er lavet
        /*for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
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
                        System.out.println("Group: " + team.getGroupNumber() + " Name: " + team.getName() + " Point: " + team.getPoints());
            }
        }

        // Asserts til hvem der ligger øverst måske.
        assertEquals("Jetsmark IF 7", tournament.findCorrectPool(6, "A").getGroupBracket().getGroups().get(0).getTeamList().get(0).getName());
        assertEquals("Jetsmark IF 4", tournament.findCorrectPool(6, "A").getGroupBracket().getGroups().get(1).getTeamList().get(0).getName());

        tournament.findCorrectPool(6, "A").getKnockoutBracket().createNextRound(tournament.findCorrectPool(6, "A").getGroupBracket().advanceTeams());

        // Printer kampe ud hvor de første to knockout matches er lavet.
        for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }

        // Asserts til hvem der går videre i de to første knockout kampe
        assertEquals("Jetsmark IF 7", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(8).getFirstTeam().getName());
        assertEquals("Jetsmark IF 4", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(8).getSecondTeam().getName());
        assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).getFirstTeam().getName());
        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).getSecondTeam().getName());

        // Spiller anden runde så vi finder en finalist
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).setResult(new Result(1, 2));


    }
}
