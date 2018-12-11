package controller;

import exceptions.InvalidInputException;
import exceptions.MissingInputException;
import exceptions.TooManyMatchesInTheMatchDay;
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
import tournament.Match;
import tournament.matchschedule.GraphicalObjects.ProgressBox;
import tournament.matchschedule.MatchDay;
import tournament.Tournament;
import tournament.pool.Pool;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalTime;

public class MatchScheduleSetupController implements CheckInput {
    private final int stepNumber = 6;
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
        progressBox.getChildren().add(new ProgressBox(stepNumber));
        setPoolsTable();
        setMatchDaysTable();
        createGroupMatches();
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
        poolNameColumn.setMinWidth(60);
        poolNameColumn.setMinWidth(60);

        TableColumn<Pool, Integer> matchDurationColumn = (TableColumn<Pool, Integer>) poolTableView.getColumns().get(1);
        matchDurationColumn.setCellValueFactory(new PropertyValueFactory<>("matchDuration"));
        matchDurationColumn.setMinWidth(178);
        matchDurationColumn.setMaxWidth(178);

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
        matchDateColumn.setMinWidth(200);
        matchDateColumn.setMaxWidth(200);

        TableColumn<MatchDay, LocalTime> startTimeColumn =
                (TableColumn<MatchDay, LocalTime>) matchDayTableView.getColumns().get(1);
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        startTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));
        startTimeColumn.setMinWidth(115);
        startTimeColumn.setMaxWidth(115);

        TableColumn<MatchDay, LocalTime> endTimeColumn =
                (TableColumn<MatchDay, LocalTime>) matchDayTableView.getColumns().get(2);
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));

        ObservableList<MatchDay> matchDayList = FXCollections.observableArrayList();
        matchDayList.addAll(tournament.getMatchSchedule().getMatchDays());

        matchDayTableView.setItems(matchDayList);

        matchDayTableView.getSelectionModel().selectedItemProperty().addListener(new RowSelectInvalidationListener());
    }

    @Override
    public void checkAllInput() {
        if (timeBetweenMatches.getText().trim().isEmpty()) {
            throw new MissingInputException();
        }

        for (MatchDay matchDay : tournament.getMatchSchedule().getMatchDays()) {
            if (matchDay.getStartTime().equals(LocalTime.MIN)) {
                throw new MissingInputException();
            }
        }

        if (!(timeBetweenMatches.getText().matches("\\d+"))) {
            throw new InvalidInputException("heltal", "tid i mellem kampene");
        }
        checkMatchScheduleTime();
    }

    private void checkMatchScheduleTime() {
        int wantedTime = 0;
        int actualTime = 0;

        for (MatchDay matchDay : tournament.getMatchSchedule().getMatchDays()) {
            if (matchDay.getStartTime().getMinute() == 0 && matchDay.getEndTime().getMinute() == 0) {
                wantedTime += (matchDay.getEndTime().getHour() - matchDay.getStartTime().getHour()) * 60;
            } else {
                wantedTime += (60 - matchDay.getStartTime().getMinute()) + matchDay.getEndTime().getMinute();
            }
        }
            for (Match match : tournament.getAllMatches()) {
                actualTime += match.getDuration();
            }
            actualTime += (tournament.getAllMatches().size() - 1) * Integer.parseInt(timeBetweenMatches.getText());

        if (actualTime > wantedTime) {
            throw new TooManyMatchesInTheMatchDay();
        }
    }

    @FXML
    private void autogenerateMixedMatches() {
        try {
            checkAllInput();
            tournament.getMatchSchedule().setTimeBetweenMatchDays(Integer.parseInt(timeBetweenMatches.getText()));
        } catch (MissingInputException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        } catch (InvalidInputException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Ugyldigt input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        } catch (TooManyMatchesInTheMatchDay e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Logistisk fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }
    }

    @FXML
    private void autogenerateNoMixedMatches(ActionEvent event) throws IOException {
        try {
            checkAllInput();
            tournament.getMatchSchedule().setTimeBetweenMatchDays(Integer.parseInt(timeBetweenMatches.getText()));
            tournament.getMatchSchedule().setNoMixedMatches(tournament.getPoolList());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/AutoMatchSchedule.fxml"));
            Parent newWindow = loader.load();

            AutogenerateMatchScheduleController msc = loader.getController();
            msc.setTournament(tournament);

            Scene newScene = new Scene(newWindow);

            Stage window = (Stage) (timeBetweenMatches).getScene().getWindow();

            window.setScene(newScene);
            window.show();
        } catch (MissingInputException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        } catch (InvalidInputException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Ugyldigt input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        } catch (TooManyMatchesInTheMatchDay e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Logistisk fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }
    }


    @FXML
    private void manuallyCreateMatchSchedule(ActionEvent event) throws IOException {
        try {
            checkAllInput();
            tournament.getMatchSchedule().setTimeBetweenMatchDays(Integer.parseInt(timeBetweenMatches.getText()));

            System.out.println(String.valueOf(tournament.getMatchSchedule().getMatchDays().size()));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/CreatingMatchSchedule.fxml"));
            Parent newWindow = loader.load();

            CreatingMatchScheduleController msc = loader.getController();
            msc.setTournament(tournament);

            Scene newScene = new Scene(newWindow);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(newScene);
            window.show();
        } catch (MissingInputException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        } catch (InvalidInputException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Ugyldigt input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        } catch (TooManyMatchesInTheMatchDay e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Logistisk fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }
    }

    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/VerifyFinalStage.fxml"));
        Parent newWindow = loader.load();

        VerifyFinalStageController vfc = loader.getController();
        vfc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
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
