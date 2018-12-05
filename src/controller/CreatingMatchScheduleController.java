package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tournament.Match;
import tournament.Tournament;
import tournament.matchschedule.Field;
import tournament.matchschedule.GraphicalObjects.MatchContainer;
import tournament.matchschedule.MatchDay;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CreatingMatchScheduleController {
    private final int stepNumber = 5;
    private Tournament tournament;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        createMatchListView();
        createMatchDayTabs();
        createMatchScheduleGridpanes();
    }

    @FXML
    private VBox progressBox;

    @FXML
    private TabPane matchDayTabPane;

    @FXML
    private ListView<MatchContainer> matchListView;

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
            MatchContainer matchContainer = new MatchContainer(match);
            matchListView.getItems().add(matchContainer);
            matchContainer.setOnMouseClicked(event -> handleMatchContainerSelection(event));
        }
    }

    private void createMatchDayTabs() {
        for (MatchDay matchDay : tournament.getMatchSchedule().getMatchDays()) {
            matchDayTabPane.getTabs().add(new Tab(matchDay.getName()));
        }

        for (Tab tab : matchDayTabPane.getTabs()) {
            tab.setStyle("-fx-pref-width: " +
                    String.valueOf(matchDayTabPane.getPrefWidth() / tournament.getMatchSchedule().
                            getMatchDays().size() - 10));
        }
    }

    private void createMatchScheduleGridpanes() {
        GridPane matchDayGridPane;
        ScrollPane scrollPane;
        int matchCounter = 1;
        HBox emptyMatchContainer;
        int columnIndex = 0;

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

            MatchDay matchDay = tournament.getMatchSchedule().findMatchDay(tab.getText());

            LocalTime matchDayStartTime = matchDay.getStartTime();

            for (Field field : matchDay.getFieldList()) {
                emptyMatchContainer = new MatchContainer(matchDayStartTime);

                emptyMatchContainer.setOnMouseClicked(event -> handleEmptyMatchContainerSelection(event));

                matchDayGridPane.add(emptyMatchContainer, Integer.parseInt(field.getName().substring(5)) - 1
                        , 1);

            }

            scrollPane.setContent(matchDayGridPane);
            tab.setContent(scrollPane);
        }
    }

    private GridPane getGridPaneFromSelectedTab() {
        Tab selectedTab = matchDayTabPane.getSelectionModel().getSelectedItem();
        ScrollPane scrollPane = (ScrollPane) selectedTab.getContent();
        GridPane gridPane = (GridPane) scrollPane.getContent();
        return gridPane;
    }

    private MatchContainer getSelectedMatchContainer() {
        MatchContainer matchContainer1 = getSelectedMatchContainerInListView();
        MatchContainer matchContainer2 = getSelectedMatchContainerInGridPane(getGridPaneFromSelectedTab());

        if ( matchContainer1 != null)
            return matchContainer1;
        else if (matchContainer2 != null)
            return matchContainer2;
        else
            return null;
    }

    private MatchContainer getSelectedMatchContainerInListView() {
        for (MatchContainer matchContainer : matchListView.getItems()) {
            if (matchContainer.isSelected()) {
                matchContainer.setSelected(false);
                return matchContainer;
            }
        }
        return null;
    }

    private MatchContainer getSelectedMatchContainerInGridPane(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof MatchContainer) {
                MatchContainer matchContainer = (MatchContainer) node;
                if (matchContainer.isSelected()) {
                    matchContainer.setSelected(false);
                    return matchContainer;
                }
            }

        }
        return null;
    }

    public void handleEmptyMatchContainerSelection(MouseEvent event) {
        MatchContainer emptyMatchContainer = (MatchContainer) event.getSource();
        GridPane gridPane = (GridPane) emptyMatchContainer.getParent();

        MatchContainer selectedMatchContainer = getSelectedMatchContainer();

        // checks if a matchContainer was selected before
        if (selectedMatchContainer != null) {

            gridPane.getChildren().remove(emptyMatchContainer);
            // remove the old matchContainer and replace with empty MatchContainer
            replaceMatchContainerWithEmptyMatchContainer(selectedMatchContainer);

            if (selectedMatchContainer.getParent() instanceof ListView){
                matchListView.getItems().remove(selectedMatchContainer);
            }

            MatchContainer newMatchContainer =
                    new MatchContainer(selectedMatchContainer.getMatch(), emptyMatchContainer);
            // add the new matchContainer
            gridPane.add(newMatchContainer, GridPane.getColumnIndex(emptyMatchContainer),
                    GridPane.getRowIndex(emptyMatchContainer));

            newMatchContainer.setOnMouseClicked(event1 -> handleMatchContainerSelection(event1));


            // add a new empty matchContainer below the new matchContainer
            if (newMatchContainer.getMatchEndTime().plusMinutes(newMatchContainer.getMatch().getDuration()).
                    isBefore(getMatchDayEndTimeFromSelectedTab()) &&
                    getMatchContainerFromGridPane(GridPane.getColumnIndex(emptyMatchContainer),
                            GridPane.getRowIndex(emptyMatchContainer) +1, gridPane) == null) {

                int timeBetweenMatches = getMatchDayFromSelectedTab().getTimeBetweenMatches();

                MatchContainer newEmptyMatchContainer = new MatchContainer(newMatchContainer.getMatchEndTime().
                        plusMinutes(timeBetweenMatches));

                gridPane.add(newEmptyMatchContainer,
                        GridPane.getColumnIndex(emptyMatchContainer),
                        GridPane.getRowIndex(emptyMatchContainer) + 1);

                newEmptyMatchContainer.setOnMouseClicked(event1 -> handleEmptyMatchContainerSelection(event1));
            }

        }

    }

    private void handleMatchContainerSelection(MouseEvent event) {
        MatchContainer otherMatchContainer = (MatchContainer) event.getSource();
        MatchContainer selectedMatchContainer;

        selectedMatchContainer = getSelectedMatchContainer();


        // checks if a matchContainer was selected before
        if (selectedMatchContainer != null && !selectedMatchContainer.equals(otherMatchContainer)) {

            // the matchContainers in the gridPane will be swapped
            if (selectedMatchContainer.getParent() instanceof GridPane) {
                GridPane gridPane = (GridPane) selectedMatchContainer.getParent();
                if (otherMatchContainer.getParent() instanceof GridPane) {
                    gridPane.getChildren().remove(selectedMatchContainer);
                    gridPane.getChildren().remove(otherMatchContainer);

                    MatchContainer newSelectedMatchContainer = new MatchContainer(otherMatchContainer.getMatch(),
                            selectedMatchContainer);

                    MatchContainer newOtherMatchContainer = new MatchContainer(selectedMatchContainer.getMatch(),
                            otherMatchContainer);

                    newOtherMatchContainer.setOnMouseClicked(event1 -> handleMatchContainerSelection(event1));
                    newSelectedMatchContainer.setOnMouseClicked(event1 -> handleMatchContainerSelection(event1));

                    gridPane.add(newSelectedMatchContainer, GridPane.getColumnIndex(selectedMatchContainer),
                            GridPane.getRowIndex(selectedMatchContainer));

                    gridPane.add(newOtherMatchContainer, GridPane.getColumnIndex(otherMatchContainer),
                            GridPane.getRowIndex(otherMatchContainer));
                } else if (otherMatchContainer.getParent() instanceof ListView) {
                    swapMatchContainerListAndGrid(otherMatchContainer, selectedMatchContainer);
                }

            // the matchContainers in the gridPane and ListView will be swapped
            } else {

                if (otherMatchContainer.getParent() instanceof GridPane) {
                    swapMatchContainerListAndGrid(selectedMatchContainer, otherMatchContainer);
                }
            }
        } else {
            otherMatchContainer.setSelected(true);
        }

    }

    private LocalTime getMatchDayEndTimeFromSelectedTab() {
        MatchDay matchDay =getMatchDayFromSelectedTab();
        LocalTime endTime = matchDay.getEndTime();
        return matchDay.getEndTime();
    }

    private MatchDay getMatchDayFromSelectedTab() {
        Tab selectedTab = matchDayTabPane.getSelectionModel().getSelectedItem();
        MatchDay matchDay = tournament.getMatchSchedule().findMatchDay(selectedTab.getText());
        return matchDay;
    }

    private MatchContainer getMatchContainerFromGridPane(int col, int row, GridPane gridPane) {
        MatchContainer matchContainer= null;

        for (Node node : gridPane.getChildren()) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col &&
                    node instanceof MatchContainer) {
                matchContainer = (MatchContainer) node;
            }
        }
        return matchContainer;
    }

    private void swapMatchContainerListAndGrid(MatchContainer matchContainerInListView, MatchContainer matchContainerInGridPane) {
        GridPane gridPane = (GridPane) matchContainerInGridPane.getParent();
        gridPane.getChildren().remove(matchContainerInGridPane);
        matchListView.getItems().remove(matchContainerInListView);

        MatchContainer newListViewMatchContainer = new MatchContainer(matchContainerInGridPane.getMatch());
        newListViewMatchContainer.setOnMouseClicked(event1 -> handleMatchContainerSelection(event1));

        matchListView.getItems().add(newListViewMatchContainer);

        MatchContainer newGridPaneMatchContainer = new MatchContainer(matchContainerInListView.getMatch(),
                matchContainerInGridPane);
        newGridPaneMatchContainer.setOnMouseClicked(event1 -> handleMatchContainerSelection(event1));

        gridPane.add(newGridPaneMatchContainer, GridPane.getColumnIndex(matchContainerInGridPane),
                GridPane.getRowIndex(matchContainerInGridPane));
    }

    @FXML
    private void finishMatchScheduleButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/UpdateMatch.FXML"));
        Parent newWindow = loader.load();

        UpdateMatchController msc = loader.getController();
        msc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    private void backButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/MatchScheduleSetup.FXML"));
        Parent newWindow = loader.load();

        MatchScheduleSetupController msc = loader.getController();
        msc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    private void RemoveMatchButtonClicked() {
        MatchContainer selectedMatchContainer = getSelectedMatchContainer();
        GridPane gridPane = (GridPane) selectedMatchContainer.getParent();

        gridPane.getChildren().remove(selectedMatchContainer);
        matchListView.getItems().add(selectedMatchContainer);

        //add empty match container
        MatchContainer emptyMatchContainer = new MatchContainer(selectedMatchContainer.getMatch().getTimeStamp());
        emptyMatchContainer.setOnMouseClicked(event -> handleEmptyMatchContainerSelection(event));
        gridPane.add(emptyMatchContainer, GridPane.getColumnIndex(selectedMatchContainer),
                GridPane.getRowIndex(selectedMatchContainer));
    }

    public void replaceMatchContainerWithEmptyMatchContainer(MatchContainer matchContainer) {
        if (matchContainer.getParent() instanceof GridPane) {
            GridPane gridPane = (GridPane) matchContainer.getParent();

            gridPane.getChildren().remove(matchContainer);

            //Check for empty matchContainers above and below the matchContainer
            if (getMatchContainerFromGridPane(GridPane.getColumnIndex(matchContainer),
                    GridPane.getRowIndex(matchContainer) +1, gridPane) != null && (
                    !getMatchContainerFromGridPane(GridPane.getColumnIndex(matchContainer),
                    GridPane.getRowIndex(matchContainer) +1, gridPane).hasMatch() ||
                     !getMatchContainerFromGridPane(GridPane.getColumnIndex(matchContainer),
                    GridPane.getRowIndex(matchContainer) -1, gridPane).hasMatch())) {

                int rowCounter = 1;
                MatchContainer movingMatchContainer = getMatchContainerFromGridPane(GridPane.getColumnIndex(matchContainer),
                        GridPane.getRowIndex(matchContainer) + rowCounter, gridPane);
                MatchContainer tempEmptyMatchContainer = new MatchContainer(matchContainer.getMatch().getTimeStamp());
                //Move all the matchContainer in the specific column
                ColumnConstraints column = gridPane.getColumnConstraints().
                        get(GridPane.getColumnIndex(matchContainer));

                while (movingMatchContainer != null) {
                    gridPane.add(tempEmptyMatchContainer, GridPane.getColumnIndex(movingMatchContainer),
                            GridPane.getRowIndex(movingMatchContainer) -1);
                    gridPane.getChildren().remove(movingMatchContainer);

                    movingMatchContainer = new MatchContainer(movingMatchContainer.getMatch(), tempEmptyMatchContainer);
                    gridPane.add(movingMatchContainer, GridPane.getColumnIndex(movingMatchContainer),
                            GridPane.getRowIndex(movingMatchContainer) -1);
                    movingMatchContainer.setOnMouseClicked(event -> handleMatchContainerSelection(event));

                    gridPane.getChildren().remove(tempEmptyMatchContainer);

                    tempEmptyMatchContainer = new MatchContainer(movingMatchContainer.getMatch().getTimeStamp());

                    rowCounter++;
                    movingMatchContainer = getMatchContainerFromGridPane(GridPane.getColumnIndex(matchContainer),
                            GridPane.getRowIndex(matchContainer) + rowCounter, gridPane);
                }

            } else {
                MatchContainer emptyMatchContainer = new MatchContainer(matchContainer.getMatch().getTimeStamp());
                emptyMatchContainer.setOnMouseClicked(event -> handleEmptyMatchContainerSelection(event));
                gridPane.add(emptyMatchContainer, GridPane.getColumnIndex(matchContainer),
                        GridPane.getRowIndex(matchContainer));
            }
        }
    }

}
