package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import tournament.Match;
import tournament.Tournament;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;
import tournament.pool.Pool;

import java.util.ArrayList;
import java.util.List;

public class AutogenerateMatchScheduleController {
    Tournament tournament;

    @FXML
    ScrollPane matchScheduleScrollPane;

    @FXML
    ComboBox comboBox;

    GridPane matchScheduleGridPane = new GridPane();

    void setTournament(Tournament tournament) {
        this.tournament = tournament;

        ObservableList observableList = FXCollections.observableArrayList();
        for (int i = 0; i < tournament.getMatchSchedule().getMatchDays().size(); i++)
            observableList.add("" + i);
        comboBox.setItems(observableList);
    }

    @FXML
    void drawMatchScheduleGridPane() {
        MatchDay matchDay = tournament.getMatchSchedule().getMatchDays().get(Integer.parseInt(comboBox.getValue().toString()));
        System.out.println(matchDay.toString()); //TODO FJERN

        for (int i = 0; i < tournament.getFieldList().size(); i++) {
            matchScheduleGridPane.add(new Text("Bane " + (i + 1)), i, 0);
        }

        int matchCounter = 1;
        List<Integer> xdList = new ArrayList<>();
        for (Field f : tournament.getFieldList())
            xdList.add(1);

        for (Match match : matchDay.getMatches()) {
            HBox matchHBox = createHBoxFromMatch(match, matchCounter);

            matchScheduleGridPane.add(matchHBox, Integer.parseInt(match.getField().getName().substring(5)) - 1
                    , xdList.get(Integer.parseInt(match.getField().getName().substring(5)) - 1));

            xdList.set(Integer.parseInt(match.getField().getName().substring(5)) - 1
            , xdList.get(Integer.parseInt(match.getField().getName().substring(5)) - 1) + 1);
            matchCounter++;
        }

        matchScheduleScrollPane.setContent(matchScheduleGridPane);
    }

    private HBox createHBoxFromMatch(Match match, int matchCounter) {
        HBox returnHBox = new HBox();

        // --- Venstre
        VBox vBox1 = new VBox();
        vBox1.setStyle("-fx-border-color: BLACK;");

        Text matchNameText = new Text("Kamp " + matchCounter);
        Text timeIntervalText = new Text("12.20 - 12.40");
        matchNameText.setWrappingWidth(80);
        timeIntervalText.setWrappingWidth(80);
        matchNameText.setTextAlignment(TextAlignment.CENTER);
        timeIntervalText.setTextAlignment(TextAlignment.CENTER);

        vBox1.getChildren().addAll(matchNameText, timeIntervalText);

        // ---
        HBox hBox = new HBox();

        VBox vBox2 = new VBox();
        vBox2.setStyle("-fx-border-color: RED;");

        Text poolText = new Text("U" + match.getFirstTeam().getYearGroup() + " - " + match.getFirstTeam().getSkillLevel());
        Text fieldText = new Text(match.getField().getName());
        poolText.setWrappingWidth(80);
        fieldText.setWrappingWidth(80);
        poolText.setTextAlignment(TextAlignment.CENTER);
        fieldText.setTextAlignment(TextAlignment.CENTER);
        vBox2.getChildren().addAll(poolText, fieldText);

        VBox vBox3 = new VBox();
        vBox3.setStyle("-fx-border-color: BLUE;");

        Text firstTeamText = new Text(match.getFirstTeam().getName());
        Text secondTeamText = new Text(match.getSecondTeam().getName());
        firstTeamText.setWrappingWidth(107);
        secondTeamText.setWrappingWidth(107);
        firstTeamText.setTextAlignment(TextAlignment.CENTER);
        secondTeamText.setTextAlignment(TextAlignment.CENTER);
        vBox3.getChildren().addAll(firstTeamText, secondTeamText);

        hBox.getChildren().addAll(vBox2, vBox3);
        hBox.setStyle("-fx-border-color: BLACK;");

        returnHBox.getChildren().add(vBox1);
        returnHBox.getChildren().add(hBox);

        returnHBox.setMargin(hBox, new Insets(0, 30, 0, 0));

        return returnHBox;
    }


}
