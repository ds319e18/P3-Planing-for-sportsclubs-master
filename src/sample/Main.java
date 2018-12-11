package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../view/AdminPage.fxml"));

        primaryStage.setTitle("Tournament planner");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.setResizable(true);

        primaryStage.show();
    }

    public static void main(String[] args) {
        // TODO Når man logger ind skal dette accountID sættes fra den account man er logget ind på
       /* accountID = 1;


        // DAO Objekter
        TournamentDAO tournamentSQL = new TournamentDAO();
        TeamDAO teamSQL = new TeamDAO();
        GroupDAO groupSQL = new GroupDAO();
        GroupBracketDAO groupBracketSQL = new GroupBracketDAO();
        MatchDAO matchSQL = new MatchDAO();
        PlayoffBracketDAO playoffSQL = new PlayoffBracketDAO();
        ResultDAO resultSQL = new ResultDAO();
        MatchDayDAO matchDaySQL = new MatchDayDAO();
        MatchScheduleDAO matchScheduleSQL = new MatchScheduleDAO();

        // Hel turnering
        Tournament tournament;
        ArrayList<Pool> pools = new ArrayList<>();
        pools.add(new Pool.Builder()
                .setYearGroup(6)
                .setSkilllLevel("A")
                .setMatchDurationInMinutes(20)
                .build());

        pools.add(new Pool.Builder()
                .setYearGroup(7)
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
        tournament.findCorrectPool(7, "A").addTeam(new Team("FCK", 7, "A"));
        tournament.findCorrectPool(7, "A").addTeam(new Team("FCK", 7, "A"));
        tournament.findCorrectPool(7, "A").addTeam(new Team("FCK", 7, "A"));
        tournament.findCorrectPool(7, "A").addTeam(new Team("FCK", 7, "A"));

        // Indsætter team i databasen
        teamSQL.insertTeam(tournament);

        tournament.findCorrectPool(6, "A").addGroupBracket(new StandardGroupPlay(2));
        tournament.findCorrectPool(6, "A").getGroupBracket().setAdvancingTeamsPrGroup(1);


        tournament.findCorrectPool(7, "A").addGroupBracket(new StandardGroupPlay(2));
        tournament.findCorrectPool(7, "A").getGroupBracket().setAdvancingTeamsPrGroup(1);

        // Adding groups to database
        groupSQL.insertGroup(tournament);

        // Inputting games between each opponent in group and the creating the matches for the group
        tournament.findCorrectPool(6, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(6, "A").getGroupBracket().createMatches(tournament.findCorrectPool(6, "A").getMatchDuration());

        tournament.findCorrectPool(7, "A").getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(1);
        tournament.findCorrectPool(7, "A").getGroupBracket().createMatches(tournament.findCorrectPool(7, "A").getMatchDuration());

        // Adding groupbracket to database
        groupBracketSQL.insertGroupBracket(tournament);

        // Adding matches
        matchSQL.insertMatches(tournament, tournament.getAllGroupMatches());

        // This test is for 2 groups and an even amount of teams in each group
        tournament.findCorrectPool(6, "A").addKnockoutBracket(new KnockoutPlay());
        tournament.findCorrectPool(7, "A").addKnockoutBracket(new KnockoutPlay());

        // Adding both playoff bracket, TBD teams, and playoff matches to the database
        playoffSQL.insertPlayoffBracket(tournament);

        // Setting matchdays and calling auto generate "Nomix"
        tournament.getMatchSchedule().getMatchDays().get(0).setStartTime("09:00");
        tournament.getMatchSchedule().getMatchDays().get(0).setEndTime("09:40");
        tournament.getMatchSchedule().getMatchDays().get(0).setTimeBetweenMatches(10);
        tournament.getMatchSchedule().getMatchDays().get(1).setStartTime("09:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setEndTime("16:00");
        tournament.getMatchSchedule().getMatchDays().get(1).setTimeBetweenMatches(10);
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getPoolList());

        for (MatchDay match : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(match);
        }

        // Inserting matches
        matchDaySQL.insertMatchDay(tournament);

        // Inserting match schedule and updating matchScheduleID in MatchDay
        matchScheduleSQL.insertMatchSchedule(tournament);


        /*TournamentDAO tournamentSQL = new TournamentDAO();
        ArrayList<Tournament> tournaments = new ArrayList<>();

        //Skal kaldes med Jetsmark hash
        int accountID = 1;

        tournaments.addAll(tournamentSQL.getAllTournaments(accountID));
        // Pools, Fields,
        for (Tournament tournament : tournaments) {
            for (MatchDay day : tournament.getMatchSchedule().getMatchDays()) {
                System.out.println(day);
            }
        }/*


        // Setting all results for group play, now group play is done
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



        // Inserting all results in database
        resultSQL.insertResult(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0), tournament, tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore(), tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore());
        resultSQL.insertResult(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(1), tournament, tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore(), tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore());
        resultSQL.insertResult(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(2), tournament, tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore(), tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore());
        resultSQL.insertResult(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(3), tournament, tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore(), tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore());
        resultSQL.insertResult(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(4), tournament, tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore(), tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore());
        resultSQL.insertResult(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(5), tournament, tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore(), tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore());
        resultSQL.insertResult(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(6), tournament, tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore(), tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore());
        resultSQL.insertResult(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(7), tournament, tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore(), tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore());
        resultSQL.insertResult(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(8), tournament, tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore(), tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore());
        resultSQL.insertResult(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(9), tournament, tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore(), tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore());
        resultSQL.insertResult(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(10), tournament, tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore(), tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore());
        resultSQL.insertResult(tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(11), tournament, tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getFirstTeamScore(), tournament.getMatchSchedule().getMatchDays().get(0).getMatches().get(0).getResult().getSecondTeamScore());

        // Sorting the groups
        for (Group group : tournament.getPoolList().get(0).getGroupBracket().getGroups()) {
            group.getTeamList().sort(new TeamPointsComp());
            for (Team team : group.getTeamList()) {
                System.out.println(team);
            }
        }*/

        /*tournament.findCorrectPool(6, "A").getKnockoutBracket().createNextRound(tournament.findCorrectPool(6, "A").getGroupBracket().advanceTeams());

        for (Match match : tournament.getMatchSchedule().getMatchDays().get(0).getMatches()) {
            System.out.println(match);
        }*/

        launch(args);
    }

}
