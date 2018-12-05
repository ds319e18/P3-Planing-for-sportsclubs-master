package sample;

import database.DAO.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tournament.*;
import tournament.matchschedule.Field;
import tournament.pool.Pool;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.bracket.StandardGroupPlay;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main extends Application {
    static int accountID;
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../View/AdminPage.fxml"));

        primaryStage.setTitle("Tournament planner");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.setResizable(true);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // TODO Når man logger ind skal dette accountID sættes fra den account man er logget ind på
        accountID = 1;

        // DAO Objekter
        TournamentDAO tournamentSQL = new TournamentDAO();
        TeamDAO teamSQL = new TeamDAO();
        GroupDAO groupSQL = new GroupDAO();
        GroupBracketDAO groupBracketSQL = new GroupBracketDAO();
        MatchDAO matchSQL = new MatchDAO();
        PlayoffBracketDAO playoffSQL = new PlayoffBracketDAO();
        ResultDAO resultSQL = new ResultDAO();
        MatchDayDAO matchDaySQL = new MatchDayDAO();

        // Hel turnering
        Tournament tournament;
        ArrayList<Pool> pools = new ArrayList<>();
        pools.add(new Pool.Builder()
                .setYearGroup(6)
                .setSkilllLevel("A")
                .setMatchDurationInMinutes(20)
                .build());

        tournament = new Tournament.Builder("A")
                .setType(TournamentType.GroupAndKnockout)
                .setActive(true)
                .setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now().plusDays(1))
                .setPoolList(pools)
                .build();

        tournament.getFieldList().add(new Field("Bane 1", false));
        tournament.getFieldList().add(new Field("Bane 2", false));

        // Indsætter turnering i databasen
        tournamentSQL.insertTournament(tournament, accountID);

        // Adding teams
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF", 6, "A"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF", 6, "A"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF", 6, "A"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF", 6, "A"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF",6, "A"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF", 6, "A"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF", 6, "A"));
        tournament.findCorrectPool(6, "A").addTeam(new Team("Jetsmark IF", 6, "A"));

        // Indsætter team i databasen
        teamSQL.insertTeam(tournament);

        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(2));
        tournament.findCorrectPool(6, "A").getGroupBracket().setAdvancingTeamsPrGroup(2);

        // Adding groups to database
        groupSQL.insertGroup(tournament);

        // Inputting games between each opponent in group and the creating the matches for the group
        tournament.findCorrectPool(6, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(6, "A").getGroupBracket().createMatches(tournament.findCorrectPool(6, "A").getMatchDuration());

        // Adding groupbracket to database
        groupBracketSQL.insertGroupBracket(tournament);

        // Adding matches
        matchSQL.insertMatches(tournament, accountID, tournament.getAllGroupMatches());

        // This test is for 2 groups and an even amount of teams in each group
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new KnockoutPlay());

        // Adding both playoff bracket, TBD teams, and playoff matches to the database
        playoffSQL.insertPlayoffBracket(tournament, accountID);

        // Setting matchdays and calling auto generate "Nomix"
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime("09:00");
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime("16:00");
        tournament.getMatchSchedule().getMatchDays().get(0).setTimeBetweenMatches(10);
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime("09:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime("16:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setTimeBetweenMatches(10);
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getAllMatches());

        for (Match match : tournament.getMatchSchedule().getMatchDays().get(0).getMatches()) {
            System.out.println(match.getName());
        }
       // base matchDaySQL.insertMatchDay(tournament);


        tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).setResult(new Result(1, 2));
        Match match = tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0);
        int score1 = tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore();
        int score2 =tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore();

        //resultSQL.insertResult(match, tournament, score1, score2);





        /* PoolDAO poolSQL = new PoolDAO();
        TournamentDAO tournamentSQL = new TournamentDAO();
        TeamDAO teamSQL = new TeamDAO();

        ArrayList<Pool> poolList = new ArrayList<>();
        poolList.add(new Pool.Builder()
                                        .setSkilllLevel("A")
                                        .setYearGroup(6)
                                        .setMatchDurationInMinutes(20)
                                        .build());

        Tournament tournament = new Tournament.Builder("Z")
                .setType(TournamentType.GroupAndKnockout)
                .setActive(true)
                .setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now().plusDays(1))
                .addToFieldList(new Field("Bane 1", false), new Field("Bane 2", false))
                .setPoolList(poolList)
                .build();

        tournamentSQL.insertTournament(tournament, 1);

        tournament.getPoolList().get(0).getTeamList().add(new Team("Frederik", "A", 6, "40431582"));

        teamSQL.insertTeam(tournament);
        Connection con = Database.connect();

        // adding group
        int s = teamSQL.findTeamID(tournament.getPoolList().get(0).getTeamList().get(0), con);*/



        //launch(args);
    }

}
