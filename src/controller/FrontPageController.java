package controller;

import account.Spectator;
import database.DAO.TournamentDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import tournament.Tournament;
import tournament.matchschedule.MatchDay;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class FrontPageController {
    private String id = "Jetsmark";
    private Spectator user = new Spectator(Objects.hash(id));

    // Laver nyt user objekt
    private String id = "Jetsmark";
    private Spectator user = new Spectator(Objects.hash(id));

    @FXML
    private Button loginBtn;

    @FXML
    private TableView<Tournament> tournamentTableView;

    public void initialize() {
        TournamentDAO tournamentSQL = new TournamentDAO();
        user.setTournaments(tournamentSQL.getAllTournaments(user.getId()));
        setTournamentTableView();
        addTournamentsInTableView();
    }

    private void addTournamentsInTableView() {
        tournamentTableView.getItems().addAll(user.getTournaments());
    }

    // TIL DATABASE
    public void initialize() {
        TournamentDAO tournamentSQL = new TournamentDAO();
        user.setTournaments(tournamentSQL.getAllTournaments(user.getId()));
        setTournamentTableView();
        addTournamentssInTableView();
    }

    private void addTournamentssInTableView() {
        tournamentTableView.getItems().addAll(user.getTournaments());
    }

    @FXML
    private void setTournamentTableView() {
        tournamentTableView.setEditable(true);

        TableColumn<Tournament, String> tournamentNameColumn = new TableColumn<>("Turneringsnavn");
        TableColumn<Tournament, String> tournamentActiveColumn = new TableColumn<>("Aktiv");
        TableColumn<Tournament, String> tournamentTypeColumn = new TableColumn<>("Turneringstype");
        TableColumn<Tournament, LocalDate> startDateColumn = new TableColumn<>("Startdato");
        TableColumn<Tournament, LocalDate> endDateColumn = new TableColumn<>("Slutdato");
        TableColumn<Tournament, MenuButton> viewMatchScheduleColumn = new TableColumn<>("Se kampprogram");

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

        viewMatchScheduleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tournament, MenuButton>, ObservableValue<MenuButton>>() {
            @Override
            public ObservableValue<MenuButton> call(TableColumn.CellDataFeatures<Tournament, MenuButton> param) {
                MenuButton menuButton = new MenuButton();
                menuButton.setMinWidth(145);
                for (MatchDay matchDay : param.getValue().getMatchSchedule().getMatchDays()) {
                    MenuItem menuItem = new MenuItem(matchDay.getName());
                    menuItem.setStyle("-fx-padding: 0 40 0 40");
                    menuItem.setOnAction(event -> {
                        try {
                            handleMatchDaySelection(matchDay);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    menuButton.getItems().add(menuItem);
                }
                return new SimpleObjectProperty<>(menuButton);
            }
        });



        tournamentTableView.getColumns().addAll(tournamentNameColumn, tournamentActiveColumn, tournamentTypeColumn,
                startDateColumn, endDateColumn, viewMatchScheduleColumn);
    }

    private void handleMatchDaySelection(MatchDay matchDay) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/UpdateTournament.FXML"));
        Parent newWindow = loader.load();

        UpdateTournamentController controller = loader.getController();
        controller.setMatchDay(matchDay);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) loginBtn.getScene().getWindow();

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
}
