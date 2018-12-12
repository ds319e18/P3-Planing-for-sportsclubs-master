package controller;

import database.DAO.*;
import exceptions.InvalidInputException;
import exceptions.MissingMatchesToAdd;
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
import View.GraphicalObjects.MatchContainer;
import View.GraphicalObjects.ProgressBox;
import tournament.matchschedule.MatchDay;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class CreatingMatchScheduleController {
    private final int stepNumber = 6;
    private Tournament tournament;
    private int timeBetweenMatches;

    @FXML
    private VBox progressBox;

    @FXML
    private TabPane matchDayTabPane;

    @FXML
    private ListView<MatchContainer> matchListView;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        progressBox.getChildren().add(new ProgressBox(stepNumber));
        timeBetweenMatches = tournament.getMatchSchedule().getMatchDays().get(0).getTimeBetweenMatches();
        createMatchListView();
        createMatchDayTabs();
        createMatchScheduleGridpanes();
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

            //For each field in each matchDay, an empty matchContainer is created
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

        // checks if a matchContainer was selected before and if the match fits the empty matchContainer
        if (selectedMatchContainer != null && emptyMatchContainer.fitsForMatch(selectedMatchContainer.getMatch(),
                timeBetweenMatches)) {

            // remove the selected matchContainer and the empty matchContainer
            gridPane.getChildren().remove(emptyMatchContainer);
            if (selectedMatchContainer.getParent() instanceof GridPane) {
                gridPane.getChildren().remove(selectedMatchContainer);

                MatchContainer secondEmptyMatchContainer = new MatchContainer(selectedMatchContainer.getMatch().getTimeStamp());
                secondEmptyMatchContainer.setOnMouseClicked(event1 -> handleEmptyMatchContainerSelection(event1));
                gridPane.add(secondEmptyMatchContainer, GridPane.getColumnIndex(selectedMatchContainer),
                        GridPane.getRowIndex(selectedMatchContainer));
            }
            if (matchListView.getItems().contains(selectedMatchContainer)){
                matchListView.getItems().remove(selectedMatchContainer);
            }

            //create the new matchContainer
            MatchContainer newMatchContainer =
                    new MatchContainer(selectedMatchContainer.getMatch(), emptyMatchContainer);
            gridPane.add(newMatchContainer, GridPane.getColumnIndex(emptyMatchContainer),
                    GridPane.getRowIndex(emptyMatchContainer));
            newMatchContainer.setOnMouseClicked(event1 -> handleMatchContainerSelection(event1));


            // add a new empty matchContainer below the new matchContainer
            if (newMatchContainer.getMatchEndTime().plusMinutes(newMatchContainer.getMatch().getDuration()).
                    isBefore(getMatchDayEndTimeFromSelectedTab()) &&
                    newMatchContainer.getNextMatchContainerInGridPane() == null) {

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
                } else if (matchListView.getItems().contains(otherMatchContainer)) {
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

    public static MatchContainer getMatchContainerFromGridPane(int col, int row, GridPane gridPane) {
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
        for (Tab tab : matchDayTabPane.getTabs()) {
            setAllMatchesFromTab(tab);
        }

        FXMLLoader loader = new FXMLLoader();

        try {
            checkAllMatches();

            //TODO TIL DATABASE
            loadTournamentInDatabase(tournament);

            loader.setLocation(getClass().getResource("../View/UpdateMatch.FXML"));
            Parent newWindow = loader.load();

            UpdateMatchController msc = loader.getController();
            msc.setTournament(tournament);

            Scene newScene = new Scene(newWindow);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(newScene);
            window.show();
        } catch (MissingMatchesToAdd e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }
    }

    private void checkAllMatches() {
        if (!matchListView.getItems().isEmpty()) {
            throw new MissingMatchesToAdd();
        }
    }

    @FXML
    private void backButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/MatchScheduleSetup.FXML"));
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

    private void setAllMatchesFromTab(Tab tab) {
        ScrollPane tabScrollPane = (ScrollPane) tab.getContent();
        GridPane tabGridPane = (GridPane) tabScrollPane.getContent();
        MatchDay matchDay = tournament.getMatchSchedule().findMatchDay(tab.getText());

        matchDay.setMatches();
        matchDay.getMatches().addAll(getAllMatchesFromGridPane(tabGridPane));
    }

    private ArrayList<Match> getAllMatchesFromGridPane(GridPane gridPane) {
        ArrayList<Match> matchList = new ArrayList<>();
        for (Node node : gridPane.getChildren()) {
            if (node instanceof MatchContainer) {
                MatchContainer matchContainer = (MatchContainer) node;
                if (matchContainer.hasMatch())
                    matchList.add(matchContainer.getMatch());
            }
        }
        return matchList;
    }


    // Bruges til at hente alle turneringer for en bruger
    public void loadTournamentInDatabase(Tournament tournament) {
        String idToBeHashed = "Jetsmark";
        int userID = Objects.hash(idToBeHashed);

        // DAO for tournament
        TournamentDAO tournamentSQL = new TournamentDAO();

        // Inserting tournament in the database, this method also calls field DAO and pool DAO which
        // inserts all pool and fields for the corrosponding tournament in the database
        tournamentSQL.insertTournament(tournament, userID);

        // DAO for team
        TeamDAO teamSQl = new TeamDAO();

        // Inserting all teams in the tournament in the database
        teamSQl.insertTeam(tournament);

        // DAO for group and groupbracket
        GroupDAO groupSQL = new GroupDAO();
        GroupBracketDAO groupBracketSQL = new GroupBracketDAO();

        // Inserting groups and groupbracketin database
        groupSQL.insertGroup(tournament);
        groupBracketSQL.insertGroupBracket(tournament);

        // DAO objects for playoff and match
        PlayoffBracketDAO playoffBracketSQL = new PlayoffBracketDAO();
        MatchDAO matchSQL = new MatchDAO();


        // Inserting playoff bracket into database, this method also makes sure playoff matches will be added 0
        playoffBracketSQL.insertPlayoffBracket(tournament);

        // Inserting all group matches in database
        matchSQL.insertMatches(tournament, tournament.getAllGroupMatches());

        // DAO objects for match schedule and match day
        MatchScheduleDAO matchScheduleSQL = new MatchScheduleDAO();
        MatchDayDAO matchDaySQL = new MatchDayDAO();

        // Adding matchdays and matchschedule to database.
        matchDaySQL.insertMatchDay(tournament);
        matchScheduleSQL.insertMatchSchedule(tournament);
    }

}
