package controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import tournament.matchschedule.MatchDay;
import tournament.Tournament;
import tournament.pool.Pool;

import java.io.IOException;
import java.time.LocalTime;

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
        setPoolsTable();
        setMatchDaysTable();
        createGroupMatches();
    }

    public void initialize() {
        highlightProgressBox();
        /*
        tournament = new Tournament.Builder("Jetsmark IF tournament").
                setStartDate(LocalDate.of(2018,6,1)).
                setEndDate(LocalDate.of(2018,6,2)).createFieldList(3)
                .build();

        for (int i = 0; i < 4; i++) {
            tournament.getPoolList().add(new Pool.Builder().setSkilllLevel("A").setYearGroup(i+8).build());
        } */

        //setPoolsTable();
        //setMatchDaysTable();
        //createGroupMatches();
    }

    private void createGroupMatches() {
        for (Pool pool : tournament.getPoolList()) {
           pool.getGroupBracket().createMatches(pool.getMatchDuration());
        }
    }

    private void setPoolsTable() {
        poolTableView.setEditable(true);

        TableColumn<Pool, ?> poolNameColumn = poolTableView.getColumns().get(0);
        poolNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Pool, Integer> matchDurationColumn = (TableColumn<Pool, Integer>) poolTableView.getColumns().get(1);
        matchDurationColumn.setCellValueFactory(new PropertyValueFactory<>("matchDuration"));

        matchDurationColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        ObservableList<Pool> poolObservableList = FXCollections.observableArrayList();
        poolObservableList.addAll(tournament.getPoolList());

        poolTableView.setItems(poolObservableList);
    }

    //inputs match days into the tableView
    private void setMatchDaysTable() {
        matchDayTableView.setEditable(true);

        TableColumn<MatchDay, ?> matchDateColumn = matchDayTableView.getColumns().get(0);
        matchDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<MatchDay, LocalTime> startTimeColumn =
                (TableColumn<MatchDay, LocalTime>) matchDayTableView.getColumns().get(1);
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        startTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));
        //startTimeColumn.setCellFactory();

        TableColumn<MatchDay, LocalTime> endTimeColumn =
                (TableColumn<MatchDay, LocalTime>) matchDayTableView.getColumns().get(2);
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));

        ObservableList<MatchDay> matchDayList = FXCollections.observableArrayList();
        matchDayList.addAll(tournament.getMatchSchedule().getMatchDays());

        matchDayTableView.setItems(matchDayList);

        matchDayTableView.getSelectionModel().selectedItemProperty().addListener(new RowSelectInvalidationListener());
    }

    @FXML
    private void autogenerateMixedMatches() {
        tournament.getMatchSchedule().setTimeBetweenMatchDays(Integer.parseInt(timeBetweenMatches.getText()));

    }

    @FXML
    private void autogenerateNoMixedMatches(ActionEvent event) throws IOException {
        tournament.getMatchSchedule().setTimeBetweenMatchDays(Integer.parseInt(timeBetweenMatches.getText()));
        tournament.getMatchSchedule().setNoMixedMatches(tournament.getPoolList());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/AutogenerateMatchSchedule.fxml"));
        Parent newWindow = loader.load();

        AutogenerateMatchScheduleController msc = loader.getController();
        msc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)(timeBetweenMatches).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }


    @FXML
    private void manuallyCreateMatchSchedule(ActionEvent event) throws IOException {
        tournament.getMatchSchedule().setTimeBetweenMatchDays(Integer.parseInt(timeBetweenMatches.getText()));

        System.out.println(String.valueOf(tournament.getMatchSchedule().getMatchDays().size()));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/CreatingMatchSchedule.fxml"));
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

    @FXML
    private void changeStartTimeCell(TableColumn.CellEditEvent editEvent) {
        MatchDay matchDaySelected = matchDayTableView.getSelectionModel().getSelectedItem();
        matchDaySelected.setStartTime(editEvent.getNewValue().toString());

    }

    @FXML
    private void changeEndTimeCell(TableColumn.CellEditEvent editEvent) {
        MatchDay matchDaySelected = matchDayTableView.getSelectionModel().getSelectedItem();
        matchDaySelected.setEndTime(editEvent.getNewValue().toString());
    }

    @FXML
    private void changeMatchDurationCell(TableColumn.CellEditEvent editEvent) {
        Pool poolSelected = poolTableView.getSelectionModel().getSelectedItem();
        poolSelected.setMatchDuration(editEvent.getNewValue().toString());
    }
    /*
    private Callback<TableColumn<MatchDay, LocalTime>, TableCell<MatchDay, LocalTime>> getCellColorFactory() {
        Callback<TableColumn<MatchDay, LocalTime>, TableCell<MatchDay, LocalTime>> cellFactory;
        TableCell<MatchDay, LocalTime> tableCell;

        tableCell.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue ==

                )
        });

        return cellFactory;

    } */
    private class RowSelectChangeListener implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            MatchDay matchDay = (MatchDay) observable.getValue();
            MatchDay newStartTime = (MatchDay) newValue;
            System.out.println(matchDay.getStartTime().toString());
            System.out.println(newStartTime.getStartTime().toString());
        }
    }

    private class RowSelectInvalidationListener implements InvalidationListener {

        @Override
        public void invalidated(Observable observable) {
        }
    }
}
