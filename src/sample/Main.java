package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tournament.Database;
import tournament.Team;
import tournament.Tournament;
import tournament.TournamentType;

import java.time.LocalDate;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TournamentSetup.fxml"));
        primaryStage.setTitle("Tournament planner");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        //Database myDatabase = new Database();
        //myDatabase.connect();
        /*
        LocalDate local = LocalDate.now();

        Tournament tournament = new Tournament("asd",local, local, TournamentType.GroupAndKnockout);

        tournament.createPools("A", 6);

        tournament.findCorrectPool("A", 6).addTeam(new Team("Jetsmark IF", 6, "A"));
        tournament.findCorrectPool("A", 6).addTeam(new Team("Jetsmark IF", 6, "A"));
        tournament.findCorrectPool("A", 6).addTeam(new Team("Slagelse IF", 6, "A"));
        tournament.findCorrectPool("A", 6).addTeam(new Team("Jetsmark IF", 6, "A"));

        System.out.println(tournament.getPoolList().get(0).getTeamList());
        */
        launch(args);

    }

}
