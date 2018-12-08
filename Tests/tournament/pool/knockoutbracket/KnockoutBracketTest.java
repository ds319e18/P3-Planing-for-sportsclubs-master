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
        tournament.findCorrectPool(6, "A").addPlayoffBracket(new KnockoutPlay());

        // Setting the time for match schedules
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime("09:00");
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime("16:00");
        tournament.getMatchSchedule().getMatchDays().get(0).setTimeBetweenMatches(10);
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime("09:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime("16:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setTimeBetweenMatches(10);
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getPoolList());

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

        tournament.findCorrectPool(6, "A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6, "A").getGroupBracket().advanceTeams());

        return tournament;
    }

    // This is round two in the finals
    private static Tournament afterGroupPlay01RoundTwo() {
        Tournament tournament = EvenTournamentAfterGroupPlay01();

        // Setting results for the two semi finals
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).setResult(new Result(1, 2));

        tournament.findCorrectPool(6, "A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6, "A").getPlayoffBracket().advanceTeams());

        return tournament;
    }

    // This is the final rounds in the finals
    private static Tournament afterGroupPlay01RoundThree() {
        Tournament tournament = afterGroupPlay01RoundTwo();

        // Setting result for the final and calculating the winners
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).setResult(new Result(1, 2));
        tournament.findCorrectPool(6, "A").getPlayoffBracket().calculateResults();

        return tournament;
    }

    // This test uses method createEvenTournament where, number of teams, number of groups and number of advancing teams
    // are even
    @Test
    void CreateKnockoutBracket01() {
        // 8 teams, 2 groups, 2 teams advancing from each group should give us two semifinals and a final: 3 matches
        assertEquals(createEvenTournament().findCorrectPool(6, "A").getPlayoffBracket().getMatches().size(), 3);
    }

    // Testing for first round in the finals, from method createEvenTournamentAfterGroup
    @Test
    void testCreateNextRound01() {
        // 8 teams, 2 groups, 2 teams advancing from each group should give us two semifinals and a final: 3 matches
        // Testing that after group play that the correct teams advance in the finals
        Tournament tournament = EvenTournamentAfterGroupPlay01();

        assertEquals("Jetsmark IF 7", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).getFirstTeam().getName());
        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).getSecondTeam().getName());

        assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).getFirstTeam().getName());
        assertEquals("Jetsmark IF 8", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).getSecondTeam().getName());
    }

    // Testing the second round in the finals
    @Test
    void testCreateNextRoundTwo01() {
        Tournament tournament = afterGroupPlay01RoundThree();

        // Checking that the correct teams advance to the final
        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).getFirstTeam().getName());
        assertEquals("Jetsmark IF 8", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).getSecondTeam().getName());

    }

    // Testing calculating results for the 1. and 2. place
    @Test
    void testCalculatingResult() {
        Tournament tournament = afterGroupPlay01RoundThree();

        // Checking that the teams have the correct position after the tournament
        assertEquals("Jetsmark IF 8", tournament.findCorrectPool(6, "A").getPlayoffBracket().getResults().get(1).getName());
        assertEquals("Jetsmark IF 6", tournament.findCorrectPool(6, "A").getPlayoffBracket().getResults().get(2).getName());

    }

    // This method creates a tournament with 1 pool consisting of 9 teams and 3 groups
    // 2 teams from each group advance to group play and no match has been played
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

        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(3));
        tournament.findCorrectPool(6, "A").getGroupBracket().setAdvancingTeamsPrGroup(2);

        // Inputting games between each opponent in group and the creating the matches for the group
        tournament.findCorrectPool(6, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(6, "A").getGroupBracket().createMatches(tournament.findCorrectPool(6, "A").getMatchDuration());

        // This test is for 2 groups and an even amount of teams in each group
        tournament.findCorrectPool(6, "A").addPlayoffBracket(new KnockoutPlay());

        // Setting the time for match schedules
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime("12:00");
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime("16:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime("12:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime("16:00");
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getPoolList());

        return tournament;
    }

    // This method continues on the tournamen created with method createUnEvenTournament02. This method plays
    // the group play and send the correct teams to the first final round
    private static Tournament afterGroupPlay02() {
        Tournament tournament = createUnEvenTournament02();

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

        tournament.findCorrectPool(6, "A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6, "A").getGroupBracket().advanceTeams());

        return tournament;
    }

    // This is round two in the finals
    private static Tournament afterFinalRoundOne02() {
        Tournament tournament = afterGroupPlay02();

        // Setting results for the three final matches
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11).setResult(new Result(1, 2));

        // Sending the correct team to the semi finals and a final.
        tournament.findCorrectPool(6, "A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6, "A").getPlayoffBracket().advanceTeams());

        for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }

        return tournament;
    }

    // This is round two and last round in the finals
    private static Tournament afterFinalRoundTwo02() {
        Tournament tournament = afterFinalRoundOne02();

        // Setting results for the one semi final
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).setResult(new Result(1, 2));
        tournament.findCorrectPool(6, "A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6, "A").getPlayoffBracket().advanceTeams());


        return tournament;
    }

    // Finishing the tournament with setting the results for 1. and 2. place
    private static Tournament calculatingResults() {
        Tournament tournament = afterFinalRoundTwo02();

        // Setting results for the final
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).setResult(new Result(1, 2));

        // Calculating the placement
        tournament.findCorrectPool(6, "A").getPlayoffBracket().calculateResults();

        return tournament;
    }

    // This test uses method createUnEvenTournament02, where the number of groups are uneven
    @Test
    void testCreateKnockoutBracket02() {
        // This test is for 9 teams, 3 groups and with 2 teams advancing in each group. We would like one team to skip a match, giving us
        // 5 matches in total
        assertEquals(createUnEvenTournament02().findCorrectPool(6, "A").getPlayoffBracket().getMatches().size(), 5);
    }

    // Testing that after group play that the correct teams advance in the finals, for createUnEvenTournament02
    @Test
    void TestCreateNextRound02() {
        Tournament tournament = afterGroupPlay02();

        assertEquals("Jetsmark IF 8", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).getFirstTeam().getName());
        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).getSecondTeam().getName());

        assertEquals("Jetsmark IF 4", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10).getFirstTeam().getName());
        assertEquals("Jetsmark IF 9", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10).getSecondTeam().getName());

        assertEquals("Jetsmark IF 7", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11).getFirstTeam().getName());
        assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11).getSecondTeam().getName());
    }

    // Testing first knockout round for createUnEvenTournament02
    @Test
    void testCreateNextRoundTwo02() {
        Tournament tournament = afterFinalRoundOne02();

        // Testing the semi finals teams, three teams from two groups continue to the finals. One of the team go directly to the finals.
        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).getFirstTeam().getName());
        assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).getSecondTeam().getName());

        assertEquals("Jetsmark IF 9", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).getFirstTeam().getName());
        assertEquals("TBD", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).getSecondTeam().getName());
    }

    // Testing second which is also the last knockout round for createUnEvenTournament02
    @Test
    void testCreateNextRoundThree02() {
        // Playing
        Tournament tournament = afterFinalRoundTwo02();

        // Testing the teams in the final, two teams should advance from the semi finals.
        assertEquals("Jetsmark IF 9", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).getFirstTeam().getName());
        assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).getSecondTeam().getName());
    }

    // Testing that the teams get their correct postion in the tournament after the final
    @Test
    void testCreateNextRoundFour02() {
        // Playing final match and setting position for the tournament final
        Tournament tournament = calculatingResults();

        // Checking the 1. place and 2. place of the tournament
        assertEquals("Jetsmark IF 5", tournament.findCorrectPool(6, "A").getPlayoffBracket().getResults().get(1).getName());
        assertEquals("Jetsmark IF 9", tournament.findCorrectPool(6, "A").getPlayoffBracket().getResults().get(2).getName());
    }

    // This method creates a tournament with 1 pool consisting of 9 teams and 3 groups
    // 2 teams from each group advance to group play and no match has been played
    private static Tournament createUnEvenTournament03() {
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
        tournament.findCorrectPool(6, "A").addTeam(new Team("FCK"));
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
        tournament.findCorrectPool(6, "A").addPlayoffBracket(new KnockoutPlay());

        // Setting the time for match schedules
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime("09:00");
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime("16:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime("09:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime("16:00");
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getPoolList());

        for (MatchDay day : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(day);
        }

        return tournament;
    }

    // This sets result for all group matches
    private static Tournament afterGroupPlay03() {
        Tournament tournament = createUnEvenTournament03();
        int k = 0;
        int j = 0;

        // 30 group matches, setting result for all
        for (int i = 0; i < 30; i++) {
            tournament.getMatchSchedule().getMatchDays().get(j).getMatches().get(k).setResult(new Result(1, 2));
            k++;

            // When we have to change to match day 2
            if (i == 20) {
                j = 1;
                k = 0;
            }
        }

        // Creates first final round
        tournament.findCorrectPool(6, "A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6, "A").getGroupBracket().advanceTeams());

        for (MatchDay day : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(day);
        }

        for (Group group : tournament.findCorrectPool(6,"A").getGroupBracket().getGroups()) {
            System.out.println(group);
            for (Team team : group.getTeamList()) {
                System.out.println(team);
            }
        }
        return tournament;
    }

    // This method sets result first final round and sending the correct teams to the next final round
    private static Tournament afterFinalRoundOne03() {
        Tournament tournament = afterGroupPlay03();

        // Setting result
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(9).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(10).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(11).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(12).setResult(new Result(1, 2));

        // Creates final round 2 and advancing the correct teams
        tournament.findCorrectPool(6,"A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6,"A").getPlayoffBracket().advanceTeams());

        for (MatchDay day : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(day);
        }

        return tournament;
    }

    // This method sets result for second final round and sending the correct teams to the next final round
    private static Tournament afterFinalRoundTwo03() {
        Tournament tournament = afterFinalRoundOne03();

        // Setting result
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(13).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(14).setResult(new Result(1, 2));

        // Creates final round 3 and advancing the correct teams
        tournament.findCorrectPool(6,"A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6,"A").getPlayoffBracket().advanceTeams());

        for (MatchDay day : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(day);
        }

        return tournament;
    }

    // This method sets result for third final round and sending the correct teams to the next final round
    private static Tournament afterFinalRoundThree03() {
        Tournament tournament = afterFinalRoundTwo03();

        // Setting result
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(15).setResult(new Result(1, 2));

        // Creates final round 4 and advancing the correct teams
        tournament.findCorrectPool(6,"A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6,"A").getPlayoffBracket().advanceTeams());

        return tournament;
    }

    // This method sets result for the final round and giving the two teams a position in the tournament
    private static Tournament afterFinalRoundFour03() {
        Tournament tournament = afterFinalRoundThree03();

        // Setting result
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(16).setResult(new Result(1, 2));

        // Calculate position for the teams in the final
        tournament.findCorrectPool(6,"A").getPlayoffBracket().calculateResults();

        return tournament;
    }

    // This test uses method createUnEvenTournament03, where the number of groups are uneven and advancing teams are uneven
    @Test
    void testCreateKnockoutBracket03() {
        // This test is for 15 teams, 3 groups and with 3 teams advancing in each group. This would give us
        // 8 matches in total
        assertEquals(createUnEvenTournament03().findCorrectPool(6, "A").getPlayoffBracket().getMatches().size(), 8);
    }

    // Testing that after group play that the correct teams advance in the first round in the final, for createUnEvenTournament03
    @Test
    void TestCreateNextRound03() {
        Tournament tournament = afterGroupPlay03();

        // Testing that teams are in the correct final matches
        assertEquals("Jetsmark IF 8", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(9).getFirstTeam().getName());
        assertEquals("Jetsmark IF 3", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(9).getSecondTeam().getName());
        assertEquals("Jetsmark IF 4", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(10).getFirstTeam().getName());
        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(10).getSecondTeam().getName());
        assertEquals("Jetsmark IF 1", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(11).getFirstTeam().getName());
        assertEquals("Jetsmark IF 9", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(11).getSecondTeam().getName());
        assertEquals("Jetsmark IF 7", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(12).getFirstTeam().getName());
        assertEquals("Jetsmark IF 2", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(12).getSecondTeam().getName());
    }

    // Testing second knockout round for createUnEvenTournament03, one team should proceed directly to next round
    @Test
    void testCreateNextRoundTwo03() {
        Tournament tournament = afterFinalRoundOne03();

        // Testing the knockout round 2
        assertEquals("Jetsmark IF 5", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(13).getFirstTeam().getName());
        assertEquals("Jetsmark IF 2", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(13).getSecondTeam().getName());

        assertEquals("Jetsmark IF 3", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(14).getFirstTeam().getName());
        assertEquals("Jetsmark IF 9", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(14).getSecondTeam().getName());

        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(15).getFirstTeam().getName());
        assertEquals("TBD", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(15).getSecondTeam().getName());
    }

    // Testing knockout round 3 for createUnEvenTournament03
    @Test
    void testCreateNextRoundThree03() {
        Tournament tournament = afterFinalRoundTwo03();

        // Testing the knockout round 3
        assertEquals("Jetsmark IF 6", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(15).getFirstTeam().getName());
        assertEquals("Jetsmark IF 9", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(15).getSecondTeam().getName());

        assertEquals("Jetsmark IF 2", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(16).getFirstTeam().getName());
        assertEquals("TBD", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(16).getSecondTeam().getName());

    }

    // Testing knockout round 4, which is also last round for tournament createUnevenTournament03 and testing method calculate result
    @Test
    void testCreateNextRoundThree04() {
        Tournament tournament = afterFinalRoundThree03();

        // Testing the knockout round 4
        assertEquals("Jetsmark IF 2", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(16).getFirstTeam().getName());
        assertEquals("Jetsmark IF 9", tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(16).getSecondTeam().getName());
    }

    // Testing method calculat result, that the two team in the final match get their correct position
    @Test
    void testCreateNextRoundFive04() {
        Tournament tournament = afterFinalRoundFour03();

        // Testing the knockout round 4
        assertEquals("Jetsmark IF 9", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(1).getName());
        assertEquals("Jetsmark IF 2", tournament.findCorrectPool(6,"A").getPlayoffBracket().getResults().get(2).getName());
    }

}
