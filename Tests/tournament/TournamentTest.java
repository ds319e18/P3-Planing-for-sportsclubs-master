package tournament;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;

class TournamentTest {
    private final int poolNumber = 6;

    @Test
    void findCorrectPool() {
        Tournament tournament = new Tournament.Builder("Jetsmark IF tournament").build();
        Pool expectedPool = new Pool.Builder().setSkilllLevel("A").setYearGroup(11).build();

        for (int i = 0; i < poolNumber; i++) {
            tournament.getPoolList().add(new Pool.Builder().setSkilllLevel("A").setYearGroup(i+8).build());
        }

        assertEquals(expectedPool, tournament.findCorrectPool(11, "A"));
    }

    @Test
    void isInActive() {
        Tournament tournament = new Tournament.Builder("Jetsmark IF tournament").build();
        assertFalse(tournament.isActive());

    }

    @Test
    void isActive() {
        Tournament tournament = new Tournament.Builder("Jetsmark IF tournament").setActive(true).build();
        assertTrue(tournament.isActive());

    }

    // IKKE SLET DENNE TEST
    @Test
    void fredOgNicosTestAfProgramMedKnockout() {
        Field et = new Field("Bane 1", false);
        Field to = new Field("Bane 2", false);

        Pool puljeEt = new Pool.Builder()
                .setSkilllLevel("A")
                .setYearGroup(6)
                .setMatchDurationInMinutes(20)
                .build();

        Pool puljeTo = new Pool.Builder()
                .setSkilllLevel("B")
                .setYearGroup(6)
                .setMatchDurationInMinutes(20)
                .build();

        ArrayList<Pool> puljer = new ArrayList<>();
        puljer.add(puljeEt); puljer.add(puljeTo);

        Tournament tournament = new Tournament.Builder("Frederiks Turnering")
                .setType(TournamentType.GroupAndKnockout)
                .setActive(true)
                .setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now().plusDays(1))
                .addToFieldList(et, to)
                .setPoolList(puljer)
                .build();

        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("B1938", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("B1938", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("B1938", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("B1938", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));


        puljeTo.addTeam(new Team("Aabybro IF", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("Aabybro IF", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("Aabybro IF", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("Aabybro IF", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("FCK", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("FCK", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("FCK", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("FCK", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));

        // Virker indtil her

        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(2));
        tournament.findCorrectPool(6, "B").addGroupBracket(new StandardGroupPlay(2));

        for (Pool pool : tournament.getPoolList()) {
            pool.getGroupBracket().setAdvancingTeamsPrGroup(4);
            pool.getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
            pool.getGroupBracket().createMatches(20);
        }

        tournament.findCorrectPool(6,"A").addKnockoutBracket(new KnockoutPlay());
        tournament.findCorrectPool(6, "B").addKnockoutBracket(new KnockoutPlay());

        // Virker indtil her

        /*for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }*/

        // Virker indtil her

        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(1).setResult(new Result(3, 1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(2).setResult(new Result(3, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(3).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(4).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(5).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(6).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(7).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(8).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(19).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(20).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(21).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(22).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(23).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(24).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(25).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(26).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(27).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(2).setResult(new Result(2, 3));


        for (Pool pool : tournament.getPoolList()) {
            pool.getKnockoutBracket().createNextRound(pool.getGroupBracket().advanceTeams());
        }

        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(14).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(15).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(3).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(4).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(5).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(6).setResult(new Result(2, 3));

        for (Pool pool : tournament.getPoolList()) {
            pool.getKnockoutBracket().createNextRound(pool.getKnockoutBracket().advanceTeams());
            System.out.println(pool.getKnockoutBracket().advanceTeams());
        }

        for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }

        System.out.println(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(15).isFinished());
    }

    @Test
    void fredOgNicosTestAfProgramMedPlacement() {
        Field et = new Field("Bane 1", false);
        Field to = new Field("Bane 2", false);

        Pool puljeEt = new Pool.Builder()
                .setSkilllLevel("A")
                .setYearGroup(6)
                .setMatchDurationInMinutes(20)
                .build();

        Pool puljeTo = new Pool.Builder()
                .setSkilllLevel("B")
                .setYearGroup(6)
                .setMatchDurationInMinutes(20)
                .build();

        ArrayList<Pool> puljer = new ArrayList<>();
        puljer.add(puljeEt); puljer.add(puljeTo);

        Tournament tournament = new Tournament.Builder("Frederiks Turnering")
                .setType(TournamentType.GroupAndKnockout)
                .setActive(true)
                .setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now().plusDays(1))
                .addToFieldList(et, to)
                .setPoolList(puljer)
                .build();

        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("B1938", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("B1938", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("B1938", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("B1938", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));


        puljeTo.addTeam(new Team("Aabybro IF", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("Aabybro IF", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("Aabybro IF", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("Aabybro IF", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("FCK", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("FCK", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("FCK", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("FCK", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));

        // Virker indtil her

        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(2));
        tournament.findCorrectPool(6, "B").addGroupBracket(new StandardGroupPlay(2));

        for (Pool pool : tournament.getPoolList()) {
            pool.getGroupBracket().setAdvancingTeamsPrGroup(4);
            pool.getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
            pool.getGroupBracket().createMatches(20);
        }

        tournament.findCorrectPool(6,"A").addKnockoutBracket(new PlacementPlay());
        tournament.findCorrectPool(6, "B").addKnockoutBracket(new PlacementPlay());

        // Virker indtil her


        /*for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }*/

        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(1).setResult(new Result(3, 1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(2).setResult(new Result(3, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(3).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(4).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(5).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(6).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(7).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(8).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(16).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(17).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(18).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(19).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(20).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(21).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(22).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(23).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(24).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(25).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(26).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(27).setResult(new Result(2, 3));

        for (Pool pool : tournament.getPoolList()) {
            pool.getKnockoutBracket().createNextRound(pool.getGroupBracket().advanceTeams());
        }

        for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }

        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(14).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(15).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(2).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(3).setResult(new Result(2, 3));

        for (Pool pool : tournament.getPoolList()) {
            pool.getKnockoutBracket().calculateResults();
        }


        for (Pool pool : tournament.getPoolList()) {
            System.out.println(pool.getKnockoutBracket().getResults().toString());
        }
    }

    @Test
    void fredOgNicosTestAfProgramMedGoldAndBronze() {
        Field et = new Field("Bane 1", false);
        Field to = new Field("Bane 2", false);

        Pool puljeEt = new Pool.Builder()
                .setSkilllLevel("A")
                .setYearGroup(6)
                .setMatchDurationInMinutes(20)
                .build();

        Pool puljeTo = new Pool.Builder()
                .setSkilllLevel("B")
                .setYearGroup(6)
                .setMatchDurationInMinutes(20)
                .build();

        ArrayList<Pool> puljer = new ArrayList<>();
        puljer.add(puljeEt); puljer.add(puljeTo);

        Tournament tournament = new Tournament.Builder("Frederiks Turnering")
                .setType(TournamentType.GroupAndKnockout)
                .setActive(true)
                .setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now().plusDays(1))
                .addToFieldList(et, to)
                .setPoolList(puljer)
                .build();

        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("B1938", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("B1938", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("B1938", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("B1938", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));


        puljeTo.addTeam(new Team("Aabybro IF", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("Aabybro IF", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("Aabybro IF", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("Aabybro IF", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("FCK", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("FCK", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("FCK", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));
        puljeTo.addTeam(new Team("FCK", puljeTo.getYearGroup(), puljeTo.getSkillLevel()));

        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(2));
        tournament.findCorrectPool(6, "B").addGroupBracket(new StandardGroupPlay(2));

        for (Pool pool : tournament.getPoolList()) {
            pool.getGroupBracket().setAdvancingTeamsPrGroup(2);
            pool.getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
            pool.getGroupBracket().createMatches(20);
        }

        tournament.findCorrectPool(6,"A").addKnockoutBracket(new GoldAndBronzePlay());
        tournament.findCorrectPool(6, "B").addKnockoutBracket(new GoldAndBronzePlay());

        /*for (int i = 0; i < tournament.getMatchSchedule().getNumberOfMatchDays(); i++) {
            tournament.getMatchSchedule().getMatchDays().add(new MatchDay.Builder(LocalTime.of(10, 0), LocalTime.of(16, 0))
                    .setName("Day " + (i + 1))
                    .setDate(LocalDate.now().plusDays(i))
                    .setFieldsToUse(tournament.getFieldList())
                    .setTimeBetweenMatches(5)
                    .setMatchesNoMix(tournament.getAllMatches())
                    .build());
        }*/

        for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }


        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(1).setResult(new Result(3, 1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(2).setResult(new Result(3, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(3).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(4).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(5).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(6).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(7).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(8).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(14).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(15).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(16).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(17).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(18).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(19).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(20).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(21).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(22).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(23).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(24).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(25).setResult(new Result(2, 3));

        for (Pool pool : tournament.getPoolList()) {
            pool.getKnockoutBracket().createNextRound(pool.getGroupBracket().advanceTeams());
            for (Group group : pool.getGroupBracket().getGroups()) {
                group.getTeamList().sort(new TeamPointsComp());
                for (Team team : group.getTeamList()) {
                    System.out.println(team.getName() + " (" + team.getGroupNumber() + ")" + " : \t" + team.getPoints());
                }
            }
            System.out.println();
        }

        for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }

        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(26).setResult(new Result(2, 3));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(27).setResult(new Result(2, 3));

        for (Pool pool : tournament.getPoolList()) {
            pool.getKnockoutBracket().calculateResults();
        }


        for (Pool pool : tournament.getPoolList()) {
            System.out.println(pool.getKnockoutBracket().getResults().toString());
        }
    }

    @Test
    void fredOgNicosTestAfProgramMedUligeAntalGrupper() {
        Field et = new Field("Bane 1", false);
        Field to = new Field("Bane 2", false);

        Pool puljeEt = new Pool.Builder()
                .setSkilllLevel("A")
                .setYearGroup(6)
                .setMatchDurationInMinutes(20)
                .build();

        ArrayList<Pool> puljer = new ArrayList<>();
        puljer.add(puljeEt);

        Tournament tournament = new Tournament.Builder("Frederiks Turnering")
                .setType(TournamentType.GroupAndKnockout)
                .setActive(true)
                .setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now().plusDays(1))
                .addToFieldList(et, to)
                .setPoolList(puljer)
                .build();

        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("Jetsmark IF", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("FCK", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("FCK", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("FCK", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));
        puljeEt.addTeam(new Team("FCK", puljeEt.getYearGroup(), puljeEt.getSkillLevel()));

        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(3));

        for (Pool pool : tournament.getPoolList()) {
            pool.getGroupBracket().setAdvancingTeamsPrGroup(2);
            pool.getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
            pool.getGroupBracket().createMatches(20);
        }

        tournament.findCorrectPool(6,"A").addKnockoutBracket(new KnockoutPlay());

        // Print af grupper
        for (Pool pool : tournament.getPoolList()) {
            for (Group group : pool.getGroupBracket().getGroups()) {
                for (Team team : group.getTeamList()) {
                    System.out.println(team.getName() + " " +  team.getPoints());
                }
                System.out.println();
            }
        }

        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(1).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(2).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(3).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(4).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(5).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(6).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(7).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(8).setResult(new Result(1, 2));

        for (Pool pool : tournament.getPoolList()) {
            pool.getKnockoutBracket().createNextRound(pool.getGroupBracket().advanceTeams());
        }


        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10).setResult(new Result(1, 2));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11).setResult(new Result(1, 2));

        for (Pool pool : tournament.getPoolList()) {
            pool.getKnockoutBracket().createNextRound(pool.getKnockoutBracket().advanceTeams());
        }

        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).setResult(new Result(1, 2));

        for (Pool pool : tournament.getPoolList()) {
            pool.getKnockoutBracket().createNextRound(pool.getKnockoutBracket().advanceTeams());
        }

        for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }
    }

}