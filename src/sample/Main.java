package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tournament.Team;
import tournament.Tournament;
import tournament.TournamentType;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.Pool;
import tournament.pool.bracket.StandardGroupPlay;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("TournamentSetup.FXML"));

        primaryStage.setTitle("Tournament planner");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.setResizable(true);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        //Database myDatabase = new Database();
        //myDatabase.connect();
        /*
        LocalDate local = LocalDate.now();

        Tournament tournament = new Tournament("asd",local, local, TournamentType.GroupAndKnockout);
        TournamentBuilder builder = new TournamentBuilder()
                .setPoolList(new Pool("A", 6), new Pool("B", 6))
                .setActive(true)
                .setEndDate(local)
                .setStartDate(local);
        Tournament tournament = new Tournament(builder);

        tournament.findCorrectPool("A", 6).addTeam(new Team("Jetsmark IF", 6, "A"));
        tournament.findCorrectPool("A", 6).addTeam(new Team("Jetsmark IF", 6, "A"));
        tournament.findCorrectPool("A", 6).addTeam(new Team("Slagelse IF", 6, "A"));
        tournament.findCorrectPool("A", 6).addTeam(new Team("Jetsmark IF", 6, "A"));

        System.out.println(tournament.getPoolList().get(0).getTeamList());
        */

        Tournament tournament = new Tournament.Builder("Frederiks Turnering")
                .setActive(true)
                .setType(TournamentType.Group)
                .build();

        Pool sad = new Pool("A", 6);
        ArrayList<Pool> test = new ArrayList<>();
        test.add(sad);
        Team testtwo = new Team("Vino", 6, "A");
        Team testtww = new Team("Fred", 6, "A");
        ArrayList<Team> ss = new ArrayList<>();
        ss.add(testtwo);
        ss.add(testtww);

        sad.addGroupBracket(new StandardGroupPlay());
        sad.addKnockoutBracket(new KnockoutPlay());
        //launch(args);

    }

}
