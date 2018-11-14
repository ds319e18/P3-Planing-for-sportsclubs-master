package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import tournament.Database;
import tournament.Team;
import tournament.Tournament;
import tournament.TournamentType;
import tournament.*;
import tournament.pool.Knockout;
import tournament.pool.Pool;
import tournament.pool.StdGroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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
        launch(args);

        Pool sad = new Pool("A", 6);
        ArrayList<Pool> test = new ArrayList<>();
        test.add(sad);
        Team testtwo = new Team("Vino", 6, "A");
        Team testtww = new Team("Fred", 6, "A");
        ArrayList<Team> ss = new ArrayList<>();
        ss.add(testtwo);
        ss.add(testtww);

        sad.addGroupBracket(new StdGroup());
        sad.addKnockoutBracket(new Knockout());
        //launch(args);

    }

}
