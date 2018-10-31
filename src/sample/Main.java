package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("AddingTeams.fxml"));
        primaryStage.setTitle("Tournament planner");
        primaryStage.setScene(new Scene(root, 300, 275));
        Parent root = FXMLLoader.load(getClass().getResource("CreatingGroups.fxml"));
        primaryStage.setTitle("CreatingGroup");
        primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
