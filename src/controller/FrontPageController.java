package controller;

import account.Administrator;
import account.Spectator;
import database.DAO.AccountDAO;
import database.DAO.TournamentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tournament.Tournament;
import tournament.pool.Pool;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class FrontPageController {

    @FXML
    private Button loginBtn;

    @FXML
    private TableView<Tournament> tournamentTableView;

    private Tournament tournament;

    //TODO DENNE ER TIL UDEN DATABASE
    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        setTournamentTableView();
    }

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
        tableColumn.setMinWidth(150);
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
        spectator.setId(id);

        spectator.setTournaments(tournemantSQL.getAllTournaments(spectator.getId()));

        for (Tournament tournament : spectator.getTournaments()) {
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

        //TODO Dette er til hvis man ikke har database på
        UpdateMatchController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }
}
