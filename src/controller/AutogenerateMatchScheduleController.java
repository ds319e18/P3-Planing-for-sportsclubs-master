package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tournament.Match;
import tournament.Tournament;
import tournament.matchschedule.Field;
import tournament.matchschedule.GraphicalObjects.MatchContainer;
import tournament.matchschedule.GraphicalObjects.ProgressBox;
import tournament.matchschedule.MatchDay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AutogenerateMatchScheduleController {
    Tournament tournament;
    private final int stepNumber = 7;

    @FXML
    private VBox progressBox;

    @FXML
    private TabPane matchDayTabPane;

    void setTournament(Tournament tournament) {
        this.tournament = tournament;
        progressBox.getChildren().add(new ProgressBox(stepNumber));
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
        // Text-objects containing field numbers are inserted at the top of the GridPane.
            for (int i = 0; i < tournament.getFieldList().size(); i++) {
                Text fieldText = new Text("Bane " + (i + 1));
                fieldText.setWrappingWidth(267);
                fieldText.setTextAlignment(TextAlignment.CENTER);
                fieldText.setStyle("-fx-font-style: BOLD;");
                fieldText.setFont(Font.font(15));

                matchDayGridPane.add(fieldText, i, 0);
            }

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

    private HBox createHBoxFromMatch(Match match, int matchCounter) {
        HBox matchBoxContainer = new HBox();

        VBox matchTypeBox = new VBox();
        matchTypeBox.setStyle("-fx-border-color: BLACK;");

        Text matchNameText = new Text("Kamp " + matchCounter);
        Text timeIntervalText = new Text(match.getTimeStamp() + " - " + match.getTimeStamp().plusMinutes(match.getDuration()));
        matchNameText.setWrappingWidth(80);
        timeIntervalText.setWrappingWidth(80);
        matchNameText.setTextAlignment(TextAlignment.CENTER);
        timeIntervalText.setTextAlignment(TextAlignment.CENTER);

        matchTypeBox.getChildren().addAll(matchNameText, timeIntervalText);

        // match object Hbox
        HBox matchBox = new HBox();

        VBox teamTextBox = new VBox();
        //vBox2.setStyle("-fx-border-color: BLACK;");

        Text firstTeamText = new Text(match.getFirstTeam().getName());
        Text secondTeamText = new Text(match.getSecondTeam().getName());
        firstTeamText.setWrappingWidth(107);
        secondTeamText.setWrappingWidth(107);
        firstTeamText.setTextAlignment(TextAlignment.CENTER);
        secondTeamText.setTextAlignment(TextAlignment.CENTER);
        teamTextBox.getChildren().addAll(firstTeamText, secondTeamText);

        VBox teamNameBox = new VBox();
        //vBox3.setStyle("-fx-border-color: BLACK;");

        Text poolText = new Text("U" + match.getFirstTeam().getYearGroup() + " - " + match.getFirstTeam().getSkillLevel());
        Text fieldText = new Text(match.getField().getName());
        poolText.setWrappingWidth(80);
        fieldText.setWrappingWidth(80);
        poolText.setTextAlignment(TextAlignment.CENTER);
        fieldText.setTextAlignment(TextAlignment.CENTER);
        teamNameBox.getChildren().addAll(poolText, fieldText);

        matchBox.getChildren().addAll(teamTextBox, teamNameBox);
        matchBox.setStyle("-fx-border-color: BLACK;");

        matchBoxContainer.getChildren().add(matchTypeBox);
        matchBoxContainer.getChildren().add(matchBox);

        matchBoxContainer.setMargin(matchBox, new Insets(0, 30, 0, 0));

        return matchBoxContainer;
    }

    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/MatchScheduleSetup.FXML"));
        Parent newWindow = loader.load();

        MatchScheduleSetupController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }



    @FXML
    public void nextButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/FrontPage.FXML"));
        Parent newWindow = loader.load();

        FrontPageController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

}
