package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import tournament.Match;
import tournament.Tournament;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;
import tournament.pool.Pool;

import java.time.LocalDate;

public class CreatingMatchScheduleController {
    private final int stepNumber = 5;
    private Tournament tournament;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        createMatchListView();
        createMatchDayTabs();
        createMatchDayGridpanes();

    }

    @FXML
    private VBox progressBox;

    @FXML
    private TabPane matchDayTabPane;

    @FXML
    private ListView<Match> matchListView;

    public void initialize() {
        highlightProgressBox();
    }

    private void highlightProgressBox() {
        VBox stepBox = (VBox) progressBox.getChildren().get(stepNumber);
        stepBox.setStyle("-fx-border-color: #0000CD");
        stepBox.setStyle("-fx-background-color: #A9A9A9");
    }

    private void createMatchListView() {
        for (Match match : tournament.getAllMatches()) {
            matchListView.getItems().add(match);
        }
    }

    private void createMatchDayTabs() {
        for (MatchDay matchDay : tournament.getMatchSchedule().getMatchDays()) {
            matchDayTabPane.getTabs().add(new Tab(matchDay.getName()));
        }

        for (Tab tab : matchDayTabPane.getTabs()) {
            tab.setStyle("-fx-pref-width: " +
                    String.valueOf(matchDayTabPane.getPrefWidth()/tournament.getMatchSchedule().
                            getMatchDays().size()-10));
        }
    }

    private void createMatchDayGridpanes() {
        GridPane matchDayGridPane;

        for (Tab tab : matchDayTabPane.getTabs()) {
            matchDayGridPane = new GridPane();
            matchDayGridPane.getColumnConstraints().addAll(createFieldColumns(tab));
            tab.setContent(matchDayGridPane);
        }
    }

    private ObservableList<ColumnConstraints> createFieldColumns(Tab tab) {
        ObservableList<ColumnConstraints> fieldColumnList = FXCollections.observableArrayList();

        MatchDay matchDay = tournament.getMatchSchedule().findMatchDay(tab.getText());

        for (Field field : matchDay.getFieldList()) {
            fieldColumnList.add(new ColumnConstraints());
        }


        return fieldColumnList;
    }

}
