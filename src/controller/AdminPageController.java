package controller;

import account.Administrator;
import database.DAO.TournamentDAO;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tournament.Tournament;
import tournament.pool.Pool;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminPageController {
    // Laver nyt user objekt
    private String id = "Jetsmark";
    private Administrator user = new Administrator(Objects.hash(id));

    private Boolean tournamentCreated = false;

    public void setTournamentCreated(Boolean s) {
        tournamentCreated = s;
    }

    @FXML
    private GridPane gp;

    Tournament tournament;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button createTournamentBtn;

    @FXML
    private TableView<Tournament> tournamentTableView;


    // TIL DATABASE
    public void initialize() {
        TournamentDAO tournamentSQL = new TournamentDAO();
        user.setTournamens(tournamentSQL.getAllTournaments(user.getId()));
        setTournamentTableView();
        addPoolsInTableView();
    }*/

    @FXML
    private void setTournamentTableView() {
        TableColumn<Tournament, String> tournamentNameColumn = new TableColumn<>("Turneringsnavn");
        TableColumn<Tournament, String> tournamentActiveColumn = new TableColumn<>("Aktiv");
        TableColumn<Tournament, String> tournamentTypeColumn = new TableColumn<>("Turneringstype");
        TableColumn<Tournament, LocalDate> startDateColumn = new TableColumn<>("Startdato");
        TableColumn<Tournament, LocalDate> endDateColumn = new TableColumn<>("Slutdato");
        TableColumn<Tournament, String> viewMatchScheduleColumn = new TableColumn<>("Se kampprogram");
        setWidthOfColumn(tournamentNameColumn);
        setWidthOfColumn(tournamentActiveColumn);
        setWidthOfColumn(tournamentTypeColumn);
        setWidthOfColumn(startDateColumn);
        setWidthOfColumn(endDateColumn);
        setWidthOfColumn(viewMatchScheduleColumn);

        tournamentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tournamentActiveColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
        tournamentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        tournamentTableView.getColumns().addAll(tournamentNameColumn, tournamentActiveColumn, tournamentTypeColumn,
                startDateColumn, endDateColumn, viewMatchScheduleColumn);
    }

    private void setWidthOfColumn(TableColumn tableColumn) {
        tableColumn.setMinWidth(150);
        tableColumn.setMaxWidth(150);
    }

    private void addPoolsInTableView() {
        tournamentTableView.getItems().addAll(user.getTournamens());

    }

    @FXML
    public void setOnCreateTournamentButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/TournamentSetup.fxml"));
        Parent newWindow = loader.load();

        TournamentSetupController atc = loader.getController();
        atc.setUser(user);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void setOnLogoutButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("../view/FrontPage.fxml"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void setViewButtonClicked(ActionEvent event, String tournamentName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/UpdateMatch.FXML"));
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

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }
}
