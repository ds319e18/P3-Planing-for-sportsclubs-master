package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tournament.Match;
import tournament.matchschedule.MatchDay;
import tournament.Tournament;
import tournament.pool.Pool;

import java.io.IOException;
import java.time.LocalDate;

public class MatchScheduleSetupController {
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
        tournament = new Tournament.Builder("Jetsmark IF tournament").
                setStartDate(LocalDate.of(2018,5,29)).
                setEndDate(LocalDate.of(2018,6,5))
                .build();

        for (int i = 0; i < 4; i++) {
            tournament.getPoolList().add(new Pool.Builder().setSkilllLevel("A").setYearGroup(i+8).build());
        }

        setPoolsTable();
        setMatchDaysTable();

    }

    private void setPoolsTable() {
        TableColumn<Pool, ?> poolNameColumn = poolTableView.getColumns().get(0);
        poolNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Pool, ?> matchDurationColumn = poolTableView.getColumns().get(1);
        matchDurationColumn.setCellValueFactory(new PropertyValueFactory<>("matchDuration"));

        ObservableList<Pool> poolObservableList = FXCollections.observableArrayList();
        poolObservableList.addAll(tournament.getPoolList());

        poolTableView.setItems(poolObservableList);
    }

    //inputs match days into the tableView
    private void setMatchDaysTable() {
        TableColumn<MatchDay, ?> matchDateColumn = matchDayTableView.getColumns().get(0);
        matchDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<MatchDay, ?> startTimeColumn = matchDayTableView.getColumns().get(1);
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTimeTextField"));

        TableColumn<MatchDay, ?> endTimeColumn = matchDayTableView.getColumns().get(2);
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTimeTextField"));

        ObservableList<MatchDay> matchDayList = FXCollections.observableArrayList();
        matchDayList.addAll(tournament.getMatchSchedule().getMatchDays());

        matchDayTableView.setItems(matchDayList);
    }

    @FXML
    private void autogenerateMixedMatches() {
        tournament.getMatchSchedule().setTimeBetweenMatchDays(Integer.parseInt(timeBetweenMatches.getText()));


    }

    @FXML
    private void autogenerateNoMixedMatches() {
        tournament.getMatchSchedule().setTimeBetweenMatchDays(Integer.parseInt(timeBetweenMatches.getText()));
    }


    @FXML
    private void manuallyCreateMatchSchedule(ActionEvent event) throws IOException {
        tournament.getMatchSchedule().setTimeBetweenMatchDays(Integer.parseInt(timeBetweenMatches.getText()));

        setTimeForMatchDays();
        /*
        for (MatchDay matchDay : tournament.getMatchSchedule().getMatchDays()) {
            System.out.println(String.valueOf(matchDay.getStartTime()) + "-" + String.valueOf(matchDay.getEndTime()));
        } */

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/CreatingMatchSchedule.FXML"));
        Parent newWindow = loader.load();

        CreatingMatchScheduleController msc = loader.getController();
        msc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("../View/VerifyFinalStage.fxml"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    private void highlightProgressBox() {
        VBox stepBox = (VBox) progressBox.getChildren().get(stepNumber);
        stepBox.setStyle("-fx-border-color: #0000CD");
        stepBox.setStyle("-fx-background-color: #A9A9A9");
    }

    private void setTimeForMatchDays() {
        for (MatchDay matchDay : matchDayTableView.getItems()) {

        }
        TableColumn<MatchDay, ?> tableColumn = matchDayTableView.getColumns().get(1);

        TextField textField = (TextField) tableColumn.getCellObservableValue(1).getValue();
        System.out.println(textField.getText());

    }
}
