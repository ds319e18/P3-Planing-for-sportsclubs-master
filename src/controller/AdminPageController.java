package controller;

import account.Administrator;
import account.User;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import tournament.Tournament;
import tournament.matchschedule.MatchDay;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class AdminPageController {
    // Laver nyt user objekt
    private String id = "Jetsmark";
    private User user = new Administrator(Objects.hash(id));

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
        user.setTournaments(tournamentSQL.getAllTournaments(user.getId()));
        setTournamentTableView();
        addTournamentsInTableView();
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
        TableColumn<Tournament, MenuButton> editTournamentColumn = new TableColumn<>("Rediger turnering");

        setWidthOfColumn(tournamentNameColumn);
        setWidthOfColumn(tournamentActiveColumn);
        setWidthOfColumn(tournamentTypeColumn);
        setWidthOfColumn(startDateColumn);
        setWidthOfColumn(endDateColumn);
        setWidthOfColumn(viewMatchScheduleColumn);
        setWidthOfColumn(editTournamentColumn);

        tournamentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tournamentActiveColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
        tournamentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        viewMatchScheduleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tournament, MenuButton>, ObservableValue<MenuButton>>() {
            @Override
            public ObservableValue<MenuButton> call(TableColumn.CellDataFeatures<Tournament, MenuButton> param) {
                MenuButton menuButton = new MenuButton();
                menuButton.setMinWidth(120);
                for (MatchDay matchDay : param.getValue().getMatchSchedule().getMatchDays()) {
                    MenuItem menuItem = new MenuItem(matchDay.getName());
                    menuItem.setStyle("-fx-padding: 0 50 0 50");
                    menuItem.setOnAction(event -> {
                        try {
                            viewMatchDaySelection(matchDay);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    menuButton.getItems().add(menuItem);
                }
                return new SimpleObjectProperty<>(menuButton);
            }
        });

        editTournamentColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tournament, MenuButton>, ObservableValue<MenuButton>>() {
            @Override
            public ObservableValue<MenuButton> call(TableColumn.CellDataFeatures<Tournament, MenuButton> param) {
                MenuButton menuButton = new MenuButton();
                menuButton.setMinWidth(120);
                for (MatchDay matchDay : param.getValue().getMatchSchedule().getMatchDays()) {
                    MenuItem menuItem = new MenuItem(matchDay.getName());
                    menuItem.setStyle("-fx-padding: 0 50 0 50");
                    menuItem.setOnAction(event -> {
                        try {
                            updateMatchDay(matchDay);
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
                startDateColumn, endDateColumn, viewMatchScheduleColumn, editTournamentColumn);
    }

    private void updateMatchDay(MatchDay matchDay) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/UpdateTournament.FXML"));
        Parent newWindow = loader.load();

        UpdateTournamentController controller = loader.getController();
        controller.setMatchDay(matchDay);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) logoutBtn.getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    private void viewMatchDaySelection(MatchDay matchDay) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/SpectatorView.FXML"));
        Parent newWindow = loader.load();

        SpectatorViewController controller = loader.getController();
        controller.setMatchDay(matchDay, user);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) logoutBtn.getScene().getWindow();

        window.setScene(newScene);
        window.show();

    }

    private void setWidthOfColumn(TableColumn tableColumn) {
        tableColumn.setMinWidth(128);
        tableColumn.setMaxWidth(128);
    }

    private void addTournamentsInTableView() {
        tournamentTableView.getItems().addAll(user.getTournaments());
    }

    @FXML
    public void setOnCreateTournamentButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/TournamentSetup.fxml"));
        Parent newWindow = loader.load();

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


}
