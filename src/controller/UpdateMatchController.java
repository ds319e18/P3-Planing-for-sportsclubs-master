package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import tournament.Match;
import tournament.Result;
import tournament.Tournament;
import tournament.matchschedule.Field;
import tournament.matchschedule.GraphicalObjects.MatchContainer;
import tournament.matchschedule.MatchDay;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UpdateMatchController {

    Tournament tournament;

    MatchContainer selectedMatchContainer = new MatchContainer(LocalTime.now());

    @FXML
    private VBox progressBox;

    @FXML
    private TabPane matchDayTabPane;

    @FXML
    private Text firstTeamText;

    @FXML
    private Text secondTeamText;

    @FXML
    private TextField firstTeamResultTextField;

    @FXML
    private TextField secondTeamResultTextField;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        createMatchDayTabs();
        createMatchScheduleGridpanes();
    }

    private void createMatchDayTabs() {
        for (MatchDay matchDay : tournament.getMatchSchedule().getMatchDays()) {
            matchDayTabPane.getTabs().add(new Tab(matchDay.getName()));
        }

        for (Tab tab : matchDayTabPane.getTabs()) {
            tab.setStyle("-fx-pref-width: " +
                    String.valueOf(matchDayTabPane.getPrefWidth() / tournament.getMatchSchedule()
                            .getMatchDays().size() - 10));
        }
    }

    private void createMatchScheduleGridpanes() {
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
                MatchContainer matchContainer = new MatchContainer(match, true);
                matchContainer.setOnMouseClicked(event -> handleMatchContainerSelection(event));

                matchDayGridPane.add(matchContainer, Integer.parseInt(match.getField().getName().substring(5)) - 1
                        , indexList.get(Integer.parseInt(match.getField().getName().substring(5)) - 1));

                indexList.set(Integer.parseInt(match.getField().getName().substring(5)) - 1
                        , indexList.get(Integer.parseInt(match.getField().getName().substring(5)) - 1) + 1);
                matchCounter++;
            }

            scrollPane.setContent(matchDayGridPane);
            tab.setContent(scrollPane);
        }
    }
    private void handleMatchContainerSelection(MouseEvent event) {
        selectedMatchContainer.setSelected(false);

        selectedMatchContainer = (MatchContainer) event.getSource();

        selectedMatchContainer.setSelected(true);


    }

    @FXML
    private void updateButton() {
        if(!firstTeamResultTextField.getText().isEmpty() &&
                !secondTeamText.getText().isEmpty()) {

            Result result = new Result(
                    Integer.parseInt(firstTeamResultTextField.getText()),
                    Integer.parseInt(secondTeamResultTextField.getText()));

            selectedMatchContainer.getMatch().setResult(result);

            for (Match match : tournament.getAllMatches()) {
                if (match.equals(selectedMatchContainer.getMatch())) {
                    match.setResult(result);
                }
            }

            GridPane gridPane = (GridPane) selectedMatchContainer.getParent();

            MatchContainer newMatchContainer = new MatchContainer(selectedMatchContainer.getMatch(),
                    true);
            newMatchContainer.setOnMouseClicked(event -> handleMatchContainerSelection(event));

            gridPane.add(newMatchContainer, GridPane.getColumnIndex(selectedMatchContainer),
                    GridPane.getRowIndex(selectedMatchContainer));

            gridPane.getChildren().remove(selectedMatchContainer);
        }
    }
}
