package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import tournament.Match;
import tournament.Tournament;
import tournament.matchschedule.MatchDay;
import tournament.pool.Pool;

import java.time.LocalDate;

public class CreatingMatchScheduleController {
    private final int stepNumber = 5;
    private Tournament tournament;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        createMatchDayTabs();
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

    private void createMatchDayTabs() {
        for (int i = 1; i <= tournament.getMatchSchedule().getMatchDays().size(); i++) {
            matchDayTabPane.getTabs().add(new Tab("Dag " + String.valueOf(i)));
        }

        for (Tab tab : matchDayTabPane.getTabs()) {
            tab.setStyle("-fx-pref-width: " +
                    String.valueOf(matchDayTabPane.getPrefWidth()/tournament.getMatchSchedule().
                            getMatchDays().size()-10));
        }
    }
}
