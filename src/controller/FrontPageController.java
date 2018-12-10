package controller;

import account.Administrator;
import database.DAO.AccountDAO;
import database.DAO.TournamentDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tournament.Tournament;

import java.io.IOException;
import java.util.Objects;

public class FrontPageController {
    @FXML
    GridPane gp;

    @FXML
    Button loginBtn;

    Tournament tournament;

    Administrator user = new Administrator();

    //TODO DENNE ER TIL UDEN DATABASE
    void setTournament(Tournament tournament) {
        this.tournament = tournament;
        for (ColumnConstraints column : gp.getColumnConstraints())
            column.setHalignment(HPos.CENTER);

        for (int i = 0; i < 5; i++) { // Iterates through a list of tournament-objects.
            Text txt = new Text(tournament.getName());
            Text status = new Text("NOT ACTIVE");
            if (tournament.isActive()) {
                status = new Text("ACTIVE");
            }
            Text date = new Text(tournament.getStartDate().toString() + "\n" + tournament.getEndDate().toString() );
            Button btnView = new Button("View");
            btnView.setOnAction(event -> {
                try {
                    setViewButtonClicked(event, txt.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            gp.addRow(i, txt, status, date, btnView);
        }
    }

    @FXML
    public void setOnLoginButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    // TODO DENNE ER TIL DATABASE
    /*@FXML
    public void initialize() {
        TournamentDAO tournemantSQL = new TournamentDAO();

        // Initiliasere user objektet
        String idToBeHashed = "Jetsmark";
        int id = Objects.hash(idToBeHashed);
        user.setId(id);

        user.setTournamens(tournemantSQL.getAllTournaments(user.getId()));

        for (Tournament tournament : user.getTournamens()) {
            for (ColumnConstraints column : gp.getColumnConstraints()) {
                column.setHalignment(HPos.CENTER);
            }
                Text txt = new Text(tournament.getName());
                Text status = new Text(String.valueOf(tournament.isActive()));
                Text date = new Text(tournament.getStartDate().toString() + "\n" + tournament.getEndDate().toString());
                Button btnView = new Button("View");
                btnView.setOnAction(event -> {
                    try {
                        setViewButtonClicked(event, txt.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                gp.addRow(gp.getRowCount(), txt, status, date, btnView);

        }
    }*/


    @FXML
    public void setViewButtonClicked(ActionEvent event, String tournamentName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/ViewPage.FXML"));
        Parent newWindow = loader.load();

        ViewMatchScheduleController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();

    }

}
