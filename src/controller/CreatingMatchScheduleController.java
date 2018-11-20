package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import tournament.MatchDay;
import tournament.Tournament;
import tournament.pool.Pool;

import java.time.LocalDate;

public class CreatingMatchScheduleController {
    private final int stepNumber = 4;
    private Tournament tournament;

    @FXML
    private VBox progressBox;

    @FXML
    private TextField timeBetweenMatches;

    @FXML
    private TableView<MatchDay> matchDayTableView;

    @FXML
    private TableView<Pool> poolTableView;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        //setMatchDays();
        //setPools();
    }

    public void initialize() {
        highlightProgressBox();
        tournament = new Tournament.Builder("Jetsmark IF tournament").build();

        for (int i = 0; i < 4; i++) {
            tournament.getPoolList().add(new Pool.Builder().setSkilllLevel("A").setYearGroup(i+8).build());
        }
        setPools();
        setMatchDays();

    }

    private void setPools() {
        TableColumn<Pool, ?> poolNameColumn = poolTableView.getColumns().get(0);
        poolNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Pool, ?> matchDurationColumn = poolTableView.getColumns().get(1);
        matchDurationColumn.setCellValueFactory(new PropertyValueFactory<>("matchDurationTextField"));

        ObservableList<Pool> poolObservableList = FXCollections.observableArrayList();
        poolObservableList.addAll(tournament.getPoolList());

        poolTableView.setItems(poolObservableList);
    }

    //inputs match days into the tableView
    private void setMatchDays() {
        ObservableList<Pool> matchDayList = FXCollections.observableArrayList();
        matchDayList.addAll(tournament.getMatchSchedule().getMatchDays());


        TableColumn<MatchDay, ?> poolNameColumn = matchDayTableView.getColumns().get(0);
        poolNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    private void autogenerateMatchSchedule() {

    }

    @FXML
    private void manuallyCreateMatchSchedule() {

    }

    private void highlightProgressBox() {
        VBox stepBox = (VBox) progressBox.getChildren().get(stepNumber);
        stepBox.setStyle("-fx-border-color: #0000CD");
        stepBox.setStyle("-fx-background-color: #A9A9A9");
    }

}
