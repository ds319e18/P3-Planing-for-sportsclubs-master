package controller;

import account.Administrator;
import account.Spectator;
import account.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tournament.Tournament;

import java.io.IOException;
import java.util.Objects;

public class AdminPageController {
    String id = "Jetsmark";
    private Administrator user = new Administrator(Objects.hash(id));

    public void initialize() {
        //get the tournaments from the database
        user = new Administrator();
    }

    @FXML
    GridPane gp;

    @FXML
    Button logoutBtn;

    @FXML
    Button createTournamentBtn;

    @FXML
    public void setOnCreateTournamentButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/TournamentSetup.fxml"));
        Parent newWindow = loader.load();

        // Sending the object user to TournamentSetup
        TournamentSetupController atc = loader.getController();

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }
    @FXML
    public void setOnLogoutButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("../View/FrontPage.fxml"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void draw() {
        int i = 0;
        String str;
        for (ColumnConstraints column : gp.getColumnConstraints())
            column.setHalignment(HPos.CENTER);
            for (Tournament tournaments : user.getTournaments()) {
                Text txt = new Text(tournaments.getName());
                Text status = new Text(str = String.valueOf(tournaments.isActive()));
                Text date = new Text(tournaments.getStartDate().toString() + "\n" + tournaments.getEndDate().toString());
                Button btnView = new Button("View");
                Button btnEdit = new Button("Edit");
                gp.addRow(i, txt, status, date, btnView, btnEdit);
                i++;
            }
    }

    /*@FXML
    public void initialize() {
        String str;

        /*for (ColumnConstraints column : gp.getColumnConstraints())
            column.setHalignment(HPos.CENTER);
            for (Tournament tournaments : user.getTournamens()) {
                Text txt = new Text(tournaments.getName());
                Text status = new Text(str = String.valueOf(tournaments.isActive()));
                Text date = new Text(tournaments.getStartDate().toString() + "\n" + tournaments.getEndDate().toString());
                Button btnView = new Button("View");
                Button btnEdit = new Button("Edit");
                gp.addRow(i, txt, status, date, btnView, btnEdit);
                i++;
            }*/

        /*for (int i = 0; i < 10; i++) { // Iterates through a list of tournament-objects.
            Text txt = new Text("Tournament name " + i);
            Text status = new Text("ACTIVE");
            Text date = new Text("Start: 26/2/2008\nEnd: 27/2/2009");
            Button btnView = new Button("View");
            Button btnEdit = new Button("Edit");
            gp.addRow(i, txt, status, date, btnView, btnEdit);
        }*/
    /*}*/
}
