package controller;

import account.Administrator;
import database.DAO.TournamentDAO;
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
    // Laver nyt user objekt
    String id = "Jetsmark";
    private Administrator user = new Administrator(Objects.hash(id));

    @FXML
    GridPane gp;

    Tournament tournament;

    @FXML
    Button logoutBtn;

    @FXML
    Button createTournamentBtn;

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
    public void setOnCreateTournamentButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/TournamentSetup.fxml"));
        Parent newWindow = loader.load();

        TournamentSetupController atc = loader.getController();
        atc.setUser(user);

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

    //TODO DENNE ER TIL DATABASE
    /*public void initialize() {
        // DAO for tournament
        TournamentDAO tournamentSQL = new TournamentDAO();

        // Getting all tournaments from database
        user.setTournamens(tournamentSQL.getAllTournaments(user.getId()));

        for (Tournament tournament : user.getTournamens()) {
            // Dette skal er til hvis man ikke har database
            //this.tournament = tournament;
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
        loader.setLocation(getClass().getResource("../View/UpdateMatch.FXML"));
        Parent newWindow = loader.load();

        //TODO Dette er til database
        /*for (Tournament tournament : user.getTournamens()) {
            if (tournament.getName().equals(tournamentName)) {
                UpdateMatchController atc = loader.getController();
                atc.setTournament(tournament);

                Scene newScene = new Scene(newWindow);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(newScene);
                window.show();
            }
        }*/

        //TODO Dette er til hvis man ikke har database på
        UpdateMatchController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }
}
