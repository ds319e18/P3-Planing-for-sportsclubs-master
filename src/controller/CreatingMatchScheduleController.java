package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ListView;
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
    }
    @FXML
    private VBox progressBox;

    @FXML
    private ButtonBar matchDaysButtonBar;

    @FXML
    private ListView<Match> matchListView;

    public void initialize() {
        highlightProgressBox();

        tournament = new Tournament.Builder("Jetsmark IF tournament").
                setStartDate(LocalDate.of(2018,5,29)).
                setEndDate(LocalDate.of(2018,6,5))
                .build();

        for (int i = 0; i < 4; i++) {
            tournament.getPoolList().add(new Pool.Builder().setSkilllLevel("A").setYearGroup(i+8).build());
        }

        highlightProgressBox();
        createMatchDayButtons();
    }

    private void highlightProgressBox() {
        VBox stepBox = (VBox) progressBox.getChildren().get(stepNumber);
        stepBox.setStyle("-fx-border-color: #0000CD");
        stepBox.setStyle("-fx-background-color: #A9A9A9");
    }

    private void createMatchDayButtons() {
        for (int i = 0; i < tournament.getMatchSchedule().getMatchDays().size(); i++) {
            matchDaysButtonBar.getButtons().add(new Button("Day " + String.valueOf(i)));
        }
    }

}
