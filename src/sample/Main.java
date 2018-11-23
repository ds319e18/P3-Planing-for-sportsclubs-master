package sample;

import database.Database;
import exceptions.IllegalAmountOfGroupsException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tournament.*;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;
import tournament.matchschedule.MatchSchedule;
import tournament.pool.Group;
import tournament.pool.Pool;
import tournament.pool.bracket.GoldAndBronzePlay;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.bracket.PlacementPlay;
import tournament.pool.bracket.StandardGroupPlay;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../View/TournamentSetup.fxml"));

        primaryStage.setTitle("Tournament planner");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.setResizable(true);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        //Database myDatabase = new Database();
        //myDatabase.connect();
        int k = 0;

        for(int i = 1; i < 6-1; i++) {
            for(int j = 6; j >= i; j--){
                System.out.println(i);
            }
        }

        //launch(args);
    }

}
