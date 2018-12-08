package tournament.pool.playoffbracket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import tournament.*;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;
import tournament.pool.Group;
import tournament.pool.Pool;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.bracket.PlayoffGroupPlay;
import tournament.pool.bracket.StandardGroupPlay;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PlayoffGroupPlayTest {

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
        tournament.findCorrectPool(6, "A").getGroupBracket().setAdvancingTeamsPrGroup(4);

        // Inputting games between each opponent in group and the creating the matches for the group
        tournament.findCorrectPool(6, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(6, "A").getGroupBracket().createMatches(tournament.findCorrectPool(6, "A").getMatchDuration());

        // This test is for 2 groups and an even amount of teams in each group
        PlayoffGroupPlay playoffGroupPlay = new PlayoffGroupPlay(tournament.findCorrectPool(6, "A").getGroupBracket(), 2);
        tournament.findCorrectPool(6, "A").addPlayoffBracket(playoffGroupPlay);

        // Setting the time for match schedules
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime("10:00");
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime("16:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime("10:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime("16:00");
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getPoolList());

        return tournament;
    }

    // This method continues on the tournament created by the method createEvenTournament which returns a tournament
    // with even teams and even groups. This method then plays the group play and return a tournament where the first
    // knockout round has been created.
    private static Tournament evenTournamentAfterGroupPlay01() {
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
        tournament.findCorrectPool(6, "A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6, "A").getGroupBracket().advanceTeams());
        return tournament;
    }




    @Test
    void CreateKnockoutBracket01() {
        // 8 teams, 2 groups, 2 teams advancing from each group should give us two semifinals and a final: 3 matches
        Tournament tournament = createEvenTournament();

        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(1).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(2).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(3).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(4).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(5).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(6).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(7).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(8).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11).setResult(new Result(2,1));
        /*tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(12).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(13).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(14).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(15).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(16).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(17).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(0).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(1).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(2).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(3).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(4).setResult(new Result(2,1));
        tournament.getMatchSchedule().getMatchDays().get(1).getMatches().get(5).setResult(new Result(2,1));*/
        //tournament.findCorrectPool(6, "A").getPlayoffBracket().createNextRound(tournament.findCorrectPool(6, "A").getGroupBracket().advanceTeams());
        //tournament.findCorrectPool(6, "A").getPlayoffBracket().calculateResults();

        // Printer kampe ud hvor de første to knockout matches er lavet.
        /*for (MatchDay dage : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(dage.toString());
            System.out.println();
        }

        //System.out.println(tournament.findCorrectPool(6, "A").getPlayoffBracket().getResults());
    }
}*/
