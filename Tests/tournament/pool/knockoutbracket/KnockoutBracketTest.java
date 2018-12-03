package tournament.pool.knockoutbracket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import tournament.*;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;
import tournament.pool.Group;
import tournament.pool.Pool;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.bracket.StandardGroupPlay;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class KnockoutBracketTest {

    // This method creates a tournament with 1 pool consisting of 8 teams and 2 groups - no matches has been played
    private static Tournament createEvenTournament() {
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

        // This test is for 2 groups and an even amount of teams in each group
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new KnockoutPlay());

        // Setting the time for match schedules
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime(LocalTime.of(12, 0));
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime(LocalTime.of(16, 0));
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime(LocalTime.of(12, 0));
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime(LocalTime.of(16, 0));
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getAllMatches());

        // Printer kampe ud hvor de første to knockout matches er lavet.
        for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }

        return tournament;
    }

    // This method continues on the tournament created by the method createEvenTournament which returns a tournament
    // with even teams and even groups. This method then plays the group play and return a tournament where the first
    // knockout round has been created.
    private static Tournament EvenTournamentAfterGroupPlay01() {
        Tournament tournament = createEvenTournament();

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
            }
        }

        tournament.findCorrectPool(6, "A").getKnockoutBracket().createNextRound(tournament.findCorrectPool(6, "A").getGroupBracket().advanceTeams());

        return tournament;
    }

    // This is round two in the finals
    private static Tournament afterGroupPlay01RoundTwo() {
        Tournament tournament = EvenTournamentAfterGroupPlay01();

        // Setting results for the two semi finals
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).setResult(new Result(1, 2));

        tournament.findCorrectPool(6, "A").getKnockoutBracket().createNextRound(tournament.findCorrectPool(6, "A").getKnockoutBracket().advanceTeams());

        return tournament;
    }

    // This is the final rounds in the finals
    private static Tournament afterGroupPlay01RoundThree() {
        Tournament tournament = afterGroupPlay01RoundTwo();

        // Setting result for the final and calculating the winners
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(14).setResult(new Result(1, 2));
        tournament.findCorrectPool(6, "A").getKnockoutBracket().calculateResults();

        return tournament;
    }

    // This test uses method createEvenTournament where, number of teams, number of groups and number of advancing teams
    // are even
    @Test
    void CreateKnockoutBracket01() {
        // 8 teams, 2 groups, 2 teams advancing from each group should give us two semifinals and a final: 3 matches
        assertEquals(createEvenTournament().findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 3);
    }

    // Testing for first round in the finals, from method createEvenTournamentAfterGroup
    @Test
    void testCreateNextRound() {
        // 8 teams, 2 groups, 2 teams advancing from each group should give us two semifinals and a final: 3 matches
        // Testing that after group play that the correct teams advance in the finals
        Tournament tournament = EvenTournamentAfterGroupPlay01();

        assertEquals("Jetsmark IF 7", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).getFirstTeam().getName());
        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).getSecondTeam().getName());

        assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).getFirstTeam().getName());
        assertEquals("Jetsmark IF 8", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).getSecondTeam().getName());
    }

    // Testing the secound round in the finals
    @Test
    void testCreateNextRoundTwo01() {
        Tournament tournament = afterGroupPlay01RoundTwo();

        // Checking that the correct teams advance to the final
        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(14).getFirstTeam().getName());
        assertEquals("Jetsmark IF 8", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(14).getSecondTeam().getName());

    }

    // Testing calculating results for the 1. and 2. place
    @Test
    void testCalculatingResult() {
        Tournament tournament = afterGroupPlay01RoundThree();

        // Checking that the teams have the correct position after the tournament
        assertEquals("Jetsmark IF 8", tournament.findCorrectPool(6, "A").getKnockoutBracket().getResults().get(1).getName());
        assertEquals("Jetsmark IF 6", tournament.findCorrectPool(6, "A").getKnockoutBracket().getResults().get(2).getName());

    }

    // This method creates a tournament with 1 pool consisting of 9 teams and 3 groups
    // 2 teams from each group advance to group play and no match has been played
    private static Tournament createUnEvenTournament01() {
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
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));

        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(3));
        tournament.findCorrectPool(6, "A").getGroupBracket().setAdvancingTeamsPrGroup(2);

        // Inputting games between each opponent in group and the creating the matches for the group
        tournament.findCorrectPool(6, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(6, "A").getGroupBracket().createMatches(tournament.findCorrectPool(6, "A").getMatchDuration());

        // This test is for 2 groups and an even amount of teams in each group
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new KnockoutPlay());

        // Setting the time for match schedules
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime(LocalTime.of(12, 0));
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime(LocalTime.of(16, 0));
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime(LocalTime.of(12, 0));
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime(LocalTime.of(16, 0));
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getAllMatches());

        // Printer kampe ud hvor de første to knockout matches er lavet.
        for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }

        return tournament;
    }


    // This method continues on the tournamen created with method createUnEvenTournament01. This method plays
    // the group play and send the correct teams to the first final round
    private static Tournament unEvenTournamentAfterGroupPlay01() {
        Tournament tournament = createUnEvenTournament01();

        // 9 group matches, setting result for all
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(1).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(2).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(3).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(4).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(5).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(6).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(7).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(8).setResult(new Result(1, 2));

        // Sortere alle grupper i gruppespil efter point og kan printe grupper ud med point
        for (Group group : tournament.getPoolList().get(0).getGroupBracket().getGroups()) {
            group.getTeamList().sort(new TeamPointsComp());
            for (Team team : group.getTeamList()) {
            }
        }

        tournament.findCorrectPool(6, "A").getKnockoutBracket().createNextRound(tournament.findCorrectPool(6, "A").getGroupBracket().advanceTeams());

        return tournament;
    }

    // This is round two in the finals
    private static Tournament afterGroupPlay02RoundTwo() {
        Tournament tournament = unEvenTournamentAfterGroupPlay01();

        // Setting results for the three final matches
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11).setResult(new Result(1, 2));

        // Sending the correct team to the semi finals and a final.
        tournament.findCorrectPool(6, "A").getKnockoutBracket().createNextRound(tournament.findCorrectPool(6, "A").getKnockoutBracket().advanceTeams());

        return tournament;
    }

    // This is round three in the finals
    private static Tournament afterGroupPlay02RoundThree() {
        Tournament tournament = afterGroupPlay02RoundTwo();

        // Setting results for the one semi final
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).setResult(new Result(1, 2));
        tournament.findCorrectPool(6, "A").getKnockoutBracket().createNextRound(tournament.findCorrectPool(6, "A").getKnockoutBracket().advanceTeams());

        return tournament;
    }

    // Finishing the tournament with setting the results for 1. and 2. place
    private static Tournament afterGroupPlay02Results() {
        Tournament tournament = afterGroupPlay02RoundThree();

        // Setting results for the final
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).setResult(new Result(1, 2));

        // Calculating the placement
        tournament.findCorrectPool(6, "A").getKnockoutBracket().calculateResults();

        return tournament;
    }

    @Test
    void TestCreateNextRound02() {
        // 9 teams, 3 groups, 2 teams advancing from each group should give us two semifinals and a final: 5 matches
        // Testing that after group play that the correct teams advance in the finals
        Tournament tournament = unEvenTournamentAfterGroupPlay01();

        assertEquals("Jetsmark IF 8", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).getFirstTeam().getName());
        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).getSecondTeam().getName());

        assertEquals("Jetsmark IF 4", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10).getFirstTeam().getName());
        assertEquals("Jetsmark IF 9", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10).getSecondTeam().getName());

        assertEquals("Jetsmark IF 7", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11).getFirstTeam().getName());
        assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11).getSecondTeam().getName());
    }

    @Test
    void testCreateNextRoundTwo02() {
        Tournament tournament = afterGroupPlay02RoundTwo();

        // Setting the semi finals teams, three teams from two groups continue to the finals. One of the team go directly to the finals.
        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).getFirstTeam().getName());
        assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).getSecondTeam().getName());

        assertEquals("Jetsmark IF 9", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).getFirstTeam().getName());
        assertEquals("TBD", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).getSecondTeam().getName());
    }

    @Test
    void testCreateNextRoundThree02() {
        Tournament tournament = afterGroupPlay02RoundThree();

        // Setting the semi finals teams, three teams from two groups continue to the finals. One of the team go directly to the finals.
        assertEquals("Jetsmark IF 9", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).getFirstTeam().getName());
        assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).getSecondTeam().getName());
    }

    @Test
    void testCreateNextRoundFour02() {
        Tournament tournament = afterGroupPlay02Results();

        // Checking the 1. place and 2. place of the tournament
        assertEquals("Jetsmark IF 5", tournament.findCorrectPool(6, "A").getKnockoutBracket().getResults().get(1).getName());
        assertEquals("Jetsmark IF 9", tournament.findCorrectPool(6, "A").getKnockoutBracket().getResults().get(2).getName());
    }

    // This test uses method createUnEvenTournament01, where the number of groups are uneven
    @Test
    void testCreateKnockoutBracket02() {
        // This test is for 9 teams, 3 groups and with 2 teams advancing in each group. We would like one team to skip a match, giving us
        // 5 matches in total
        assertEquals(createUnEvenTournament01().findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 5);
    }

    // This method creates a tournament with 1 pool consisting of 15 teams and 3 groups
    // 3 teams from each group advance to group play and no match has been played
    private static Tournament createUnEvenTournament02() {
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
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("FCK"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("FCK"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("FCK"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("FCK"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("FCK"));

        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(3));
        tournament.findCorrectPool(6, "A").getGroupBracket().setAdvancingTeamsPrGroup(3);

        // Inputting games between each opponent in group and the creating the matches for the group
        tournament.findCorrectPool(6, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(6, "A").getGroupBracket().createMatches(tournament.findCorrectPool(6, "A").getMatchDuration());

        // This test is for 2 groups and an even amount of teams in each group
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new KnockoutPlay());

        // Setting the time for match schedules
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime(LocalTime.of(12, 0));
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime(LocalTime.of(16, 0));
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime(LocalTime.of(12, 0));
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime(LocalTime.of(16, 0));
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getAllMatches());

        // Printer kampe ud hvor de første to knockout matches er lavet.
        for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }

        return tournament;

    }



    // This test uses method createUnEvenTournament02, where both number of teams and groups are uneven
    @Test
    void testCreateKnockoutBracket03() {
        // This test is for 15 teams, 3 groups and with 3 teams advancing in each group. We would like two team to skip a match, giving us
        // 8 matches in total
        assertEquals(createUnEvenTournament02().findCorrectPool(6, "A").getKnockoutBracket().getMatches().size(), 8);
    }

    @Test
    void unEvenTournamentAfterGroupPlay02() {
        createUnEvenTournament02();
    }



    // This method creates a uneven tournament with one pool, 9 teams, 3 groups.
    // Also 2 teams from each group advance to end play.
    // Group play has been played













    /*@Test
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
            /*tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).setResult(new Result(1, 2));
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
            /*assertEquals("Jetsmark IF 7", tournament.findCorrectPool(6, "A").getGroupBracket().getGroups().get(0).getTeamList().get(0).getName());
            assertEquals("Jetsmark IF 8", tournament.findCorrectPool(6, "A").getGroupBracket().getGroups().get(1).getTeamList().get(0).getName());

            tournament.findCorrectPool(6, "A").getKnockoutBracket().createNextRound(tournament.findCorrectPool(6, "A").getGroupBracket().advanceTeams());

            // Printer kampe ud hvor de første to knockout matches er lavet.
        /*for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }*/

    // Asserts til hvem der går videre i de to første knockout kampe
           /* assertEquals("Jetsmark IF 7", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).getFirstTeam().getName());
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

           /* tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(14).setResult(new Result(1, 2));

            tournament.findCorrectPool(6, "A").getKnockoutBracket().calculateResults();

            assertEquals("Jetsmark IF 8", tournament.findCorrectPool(6, "A").getKnockoutBracket().getResults().get(1).getName());

            //System.out.print(tournament.findCorrectPool(6, "A").getKnockoutBracket().getResults());

        }*/


}
