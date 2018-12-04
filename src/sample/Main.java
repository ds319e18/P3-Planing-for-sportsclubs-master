package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../View/AdminPage.fxml"));

        primaryStage.setTitle("Tournament planner");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        /*Connection con = Database.connect();

        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now().plusDays(3);

        Date dateStart = Date.valueOf(date1);
        Date dateEnd = Date.valueOf(date2);

        try {
            String query = "INSERT INTO Tournament (name, startDate, endDate, active, fieldNumber, Account_id) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, "Turnering");
            stmt.setDate(2, dateStart);
            stmt.setDate(3, dateEnd);
            stmt.setBoolean(4, false);
            stmt.setInt(5, 3);
            stmt.setInt(6, 1);

            stmt.executeUpdate();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
*/
        launch(args);
    }

}
