package controller;

import account.Administrator;
import account.Spectator;
import database.DAO.AccountDAO;
import database.DAO.TournamentDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Callback;
import tournament.Tournament;
import tournament.matchschedule.MatchDay;
import tournament.pool.Pool;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class FrontPageController {
    private String id = "Jetsmark";
    private Administrator user = new Administrator(Objects.hash(id));


    @FXML
    private Button loginBtn;

    @FXML
    private TableView<Tournament> tournamentTableView;

    private Tournament tournament;

    public void initialize() {
        TournamentDAO tournamentSQL = new TournamentDAO();
        user.setTournamens(tournamentSQL.getAllTournaments(user.getId()));
        setTournamentTableView();
        addTournamentssInTableView();
    }

    private void addTournamentssInTableView() {
        tournamentTableView.getItems().addAll(user.getTournamens());
    }

    @FXML
    private void setTournamentTableView() {
        TableColumn<Tournament, String> tournamentNameColumn = new TableColumn<>("Turneringsnavn");
        TableColumn<Tournament, String> tournamentActiveColumn = new TableColumn<>("Aktiv");
        TableColumn<Tournament, String> tournamentTypeColumn = new TableColumn<>("Turneringstype");
        TableColumn<Tournament, LocalDate> startDateColumn = new TableColumn<>("Startdato");
        TableColumn<Tournament, LocalDate> endDateColumn = new TableColumn<>("Slutdato");
        TableColumn<Tournament, ComboBox> viewMatchScheduleColumn = new TableColumn<>("Se kampprogram");
        TableColumn<Tournament, Button> editTournamentColumn = new TableColumn<>("Rediger turnering");

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
        viewMatchScheduleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tournament, ComboBox>, ObservableValue<ComboBox>>() {
            @Override
            public ObservableValue<ComboBox> call(TableColumn.CellDataFeatures<Tournament, ComboBox> param) {
                ComboBox<MatchDay> matchDayComboBox = new ComboBox<>();
                matchDayComboBox.getItems().addAll(param.getValue().getMatchSchedule().getMatchDays());
                return new SimpleObjectProperty<>(matchDayComboBox);
            }
        });

        tournamentTableView.getColumns().addAll(tournamentNameColumn, tournamentActiveColumn, tournamentTypeColumn,
                startDateColumn, endDateColumn, viewMatchScheduleColumn);
    }

    @FXML
    private void changeWatchTournamentCell(TableColumn.CellEditEvent editEvent, ActionEvent event) throws IOException {
        Tournament tournamentSelected = tournamentTableView.getSelectionModel().getSelectedItem();
        MatchDay matchDaySelected = (MatchDay) (editEvent.getNewValue());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/SpectatorView.fxml"));
        Parent newWindow = loader.load();

        ViewSpectatorController msc = loader.getController();
        msc.setMatchDay(tournamentSelected, matchDaySelected);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();

    }

    private void setWidthOfColumn(TableColumn tableColumn) {
        tableColumn.setMinWidth(150);
        tableColumn.setMinWidth(150);
    }

    @FXML
    public void setOnLoginButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
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
        spectator.setId(id);

        spectator.setTournaments(tournemantSQL.getAllTournaments(spectator.getId()));

        for (Tournament tournament : spectator.getTournaments()) {
            for (ColumnConstraints column : gp.getColumnConstraints()) {
                column.setHalignment(HPos.CENTER);
            }
                Text txt = new Text(tournament.getName());
                Text status = new Text(String.valueOf(tournament.isActive()));
                Text date = new Text(tournament.getStartDate().toString() + "\n" + tournament.getEndDate().toString());
                Button btnView = new Button("view");
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

    /*
    @FXML
    public void setViewButtonClicked(ActionEvent event, String tournamentName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/ViewPage.FXML"));
        Parent newWindow = loader.load();

        //TODO Dette er til database
        /*for (Tournament tournament : spectator.getTournaments()) {
            if (tournament.getName().equals(tournamentName)) {
                ViewMatchScheduleController atc = loader.getController();
                atc.setTournament(tournament);

                Scene newScene = new Scene(newWindow);

                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                window.setScene(newScene);
                window.show();
            }
        }*/
        /*
        //TODO Dette er til hvis man ikke har database på
        UpdateMatchController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    } */

}
