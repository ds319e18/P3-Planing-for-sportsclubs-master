package controller;

import database.DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tournament.Match;
import tournament.Result;
import tournament.Team;
import tournament.Tournament;
import tournament.matchschedule.Field;
import tournament.matchschedule.GraphicalObjects.MatchContainer;
import tournament.matchschedule.GraphicalObjects.ProgressBox;
import tournament.matchschedule.MatchDay;
import tournament.pool.Pool;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AutogenerateMatchScheduleController {
    Tournament tournament;
    private final int stepNumber = 7;

    @FXML
    private VBox progressBox;

    @FXML
    private TableView<Match> matchTableView;

    @FXML
    private ComboBox<MatchDay> matchDayComboBox;


    void setTournament(Tournament tournament) {
        this.tournament = tournament;
        progressBox.getChildren().add(new ProgressBox(stepNumber));
        setMatchDayComboBox();
        setMatchTable();
    }

    private void setMatchTable() {
        TableColumn<Match, String> matchNameColumn = new TableColumn<>("Kamp");
        matchNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        matchNameColumn.setPrefWidth(80);

        TableColumn<Match, LocalTime> timeColumn = new TableColumn<>("Tid");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        timeColumn.setPrefWidth(50);

        TableColumn<Match, Team> firstTeamColumn = new TableColumn<>("Hjemmehold");
        firstTeamColumn.setCellValueFactory(new PropertyValueFactory<>("firstTeam"));
        firstTeamColumn.setPrefWidth(106);

        TableColumn<Match, Team> secondTeamColumn = new TableColumn<>("Udehold");
        secondTeamColumn.setCellValueFactory(new PropertyValueFactory<>("secondTeam"));
        secondTeamColumn.setPrefWidth(106);

        TableColumn<Match, Result> resultColumn = new TableColumn<>("Resultat");
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        resultColumn.setPrefWidth(67);

        TableColumn<Match, Field> fieldColumn = new TableColumn<>("Bane");
        fieldColumn.setCellValueFactory(new PropertyValueFactory<>("field"));
        fieldColumn.setPrefWidth(55);

        matchTableView.getColumns().addAll(matchNameColumn, timeColumn, firstTeamColumn, secondTeamColumn, resultColumn, fieldColumn);
    }

    private void setMatchDayComboBox() {
        ObservableList<MatchDay> matchDayList = FXCollections.observableArrayList();

        for (MatchDay matchDay : tournament.getMatchSchedule().getMatchDays()) {
            matchDayList.add(matchDay);
        }
        matchDayComboBox.setItems(matchDayList);
    }

    @FXML
    private void setOnMatchDaySelected() {
        // Removes all matches from the table when selecting a new day
        matchTableView.getItems().clear();

        MatchDay selectedMatchDay = matchDayComboBox.getValue();

        ObservableList<Match> observableMatchList = FXCollections.observableArrayList();
        observableMatchList.addAll(selectedMatchDay.getMatches());

        matchTableView.getItems().addAll(observableMatchList);
    }


    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/MatchScheduleSetup.FXML"));
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
        //TODO TIL DATABASE
        loadTournamentInDatabase(tournament);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/AdminPage.FXML"));
        Parent newWindow = loader.load();

        // Sender boolean videre hvis tournament created succesfuld
        AdminPageController atc = loader.getController();
        atc.setTournamentCreated(true);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();

        Alert warning = new Alert(Alert.AlertType.INFORMATION, "Du har nu succesfuldt lavet din turnering!");
        warning.setHeaderText("Tillykke!");
        warning.setTitle("Succesfuld Turnering");
        warning.showAndWait();
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
