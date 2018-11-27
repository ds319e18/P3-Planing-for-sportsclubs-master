package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import tournament.Match;
import tournament.Tournament;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;

import java.util.ArrayList;
import java.util.List;

public class AutogenerateMatchScheduleController {
    Tournament tournament;

    @FXML
    private TabPane matchDayTabPane;

    // This GridPane will display the matches sorted by fields.
    GridPane matchScheduleGridPane = new GridPane();

    void setTournament(Tournament tournament) {
        this.tournament = tournament;
        createMatchDayTabs();
        createMatchScheduleGridpane();
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

    private void createMatchScheduleGridpane() {
        GridPane matchDayGridPane;
        ScrollPane scrollPane;
        int matchCounter = 1;

        for (Tab tab : matchDayTabPane.getTabs()) {
            matchDayGridPane = new GridPane();
            scrollPane = new ScrollPane();

            createFieldColumns(matchDayGridPane, tab);

            List<Integer> indexList = new ArrayList<>();

            for (Field f : tournament.getFieldList())
                indexList.add(1);

            MatchDay matchDay = tournament.getMatchSchedule().findMatchDay(tab.getText());

            for (Match match : matchDay.getMatches()) {
                HBox matchHBox = createHBoxFromMatch(match, matchCounter);

                matchDayGridPane.add(matchHBox, Integer.parseInt(match.getField().getName().substring(5)) - 1
                        , indexList.get(Integer.parseInt(match.getField().getName().substring(5)) - 1));

                indexList.set(Integer.parseInt(match.getField().getName().substring(5)) - 1
                        , indexList.get(Integer.parseInt(match.getField().getName().substring(5)) - 1) + 1);
                matchCounter++;
            }

            scrollPane.setContent(matchDayGridPane);
            tab.setContent(scrollPane);
        }
    }

    private void createFieldColumns(GridPane matchDayGridPane, Tab tab) {
        // Text-objects containing field numbers are inserted at the top of the GridPane.
        for (int i = 0; i < tournament.getFieldList().size(); i++) {
            matchDayGridPane.add(new Text("Bane " + (i + 1)), i*2, 0);
            //matchScheduleGridPane.add(new VLineTo(), i+1, 0);

        }

    }

    private HBox createHBoxFromMatch(Match match, int matchCounter) {
        HBox returnHBox = new HBox();

        // --- Venstre
        VBox vBox1 = new VBox();
        vBox1.setStyle("-fx-border-color: BLACK;");

        Text matchNameText = new Text("Kamp " + matchCounter);
        Text timeIntervalText = new Text(match.getTimeStamp() + " - " + match.getTimeStamp().plusMinutes(match.getDuration()));
        matchNameText.setWrappingWidth(80);
        timeIntervalText.setWrappingWidth(80);
        matchNameText.setTextAlignment(TextAlignment.CENTER);
        timeIntervalText.setTextAlignment(TextAlignment.CENTER);

        vBox1.getChildren().addAll(matchNameText, timeIntervalText);

        // ---
        HBox hBox = new HBox();

        VBox vBox2 = new VBox();
        //vBox2.setStyle("-fx-border-color: BLACK;");

        Text poolText = new Text(match.getFirstTeam().getName());
        Text fieldText = new Text(match.getSecondTeam().getName());
        poolText.setWrappingWidth(80);
        fieldText.setWrappingWidth(80);
        poolText.setTextAlignment(TextAlignment.CENTER);
        fieldText.setTextAlignment(TextAlignment.CENTER);
        vBox2.getChildren().addAll(poolText, fieldText);

        VBox vBox3 = new VBox();
        //vBox3.setStyle("-fx-border-color: BLACK;");

        Text firstTeamText = new Text("U" + match.getFirstTeam().getYearGroup() + " - " + match.getFirstTeam().getSkillLevel());
        Text secondTeamText = new Text(match.getField().getName());
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
