package controller;

import exceptions.NotAllTeamsAreVerified;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tournament.Match;
import tournament.Team;
import tournament.Tournament;
import view.GraphicalObjects.ProgressBox;
import tournament.pool.Pool;
import tournament.pool.bracket.GoldAndBronzePlay;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.bracket.PlayoffBracket;
import tournament.pool.bracket.PlacementPlay;

import java.io.IOException;

public class VerifyFinalStageController {

    Tournament tournament;
    private final int stepNumber = 5;

    @FXML
    private TableView<Pool> poolTableView;

    @FXML
    private VBox progressBox;

    @FXML
    private GridPane finalStageGridPane;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        progressBox.getChildren().add(new ProgressBox(stepNumber));
        setPoolTableView();

    }

    private void setPoolTableView() {
        TableColumn<Pool, String> poolNameColumn = new TableColumn<>("Puljenavn");
        poolNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        poolNameColumn.setMinWidth(93.25);
        poolNameColumn.setMaxWidth(93.25);

        TableColumn<Pool, String> playOffTypeColumn = new TableColumn<>("Slutspilstype");
        playOffTypeColumn.setCellValueFactory(new PropertyValueFactory<>("playOffType"));
        playOffTypeColumn.setMaxWidth(93.25);
        playOffTypeColumn.setMinWidth(93.25);

        TableColumn<Pool, String> poolStatusColumn = new TableColumn<>("Status");
        poolStatusColumn.setCellValueFactory(new PropertyValueFactory<>("playOffVerificationStatus"));
        poolStatusColumn.setMaxWidth(93.25);
        poolStatusColumn.setMinWidth(93.25);

        poolTableView.getColumns().addAll(poolNameColumn, playOffTypeColumn, poolStatusColumn);
        //add pools to tableView
        addPoolsInTableView();
    }

    private void addPoolsInTableView() {
        poolTableView.getItems().addAll(tournament.getPoolList());

        //handle row selection for each pool in tableView
        poolTableView.setRowFactory(table -> {
            TableRow<Pool> row = new TableRow<>();
            row.setOnMouseClicked(event -> handleRowSelection());
            return row;
        });
    }

    private void handleRowSelection() {
        Pool selectedPool = poolTableView.getSelectionModel().getSelectedItem();

        if (selectedPool.getPlayoffBracket().getClass().equals(PlacementPlay.class)) {
            drawPlacementStageGridPane();
        } else if (selectedPool.getPlayoffBracket().getClass().equals(KnockoutPlay.class)) {
            drawKnockoutStageGridPane();
        } else if (selectedPool.getPlayoffBracket().getClass().equals(GoldAndBronzePlay.class)) {
            drawGoldAndBronzeStageGridPane();

        }
    }

    private void drawPlacementStageGridPane() {
        Pool selectedPool = poolTableView.getSelectionModel().getSelectedItem();

        finalStageGridPane.getChildren().clear();
        finalStageGridPane.setVgap(30);
        finalStageGridPane.setHgap(30);

        int counter = 1;
        for (Match match : selectedPool.getPlayoffBracket().getMatches()) {
            GridPane gridPane = new GridPane();
            Text groupNumberText = new Text("  Placeringspil  ");
            groupNumberText.setStyle("-fx-font-weight: bold;");
            gridPane.add(groupNumberText, 0, 0);
            gridPane.add(new Text("  " + counter + ". plads af gruppe 1" + "  "), 1, 0);
            gridPane.add(new Text("  " + counter + ". plads af gruppe 2" + "  "), 1, 1);
            counter++;
            finalStageGridPane.add(gridPane, 0, finalStageGridPane.getRowCount());
        }


        finalStageGridPane.setGridLinesVisible(false);
        finalStageGridPane.setGridLinesVisible(true);
    }

    private void drawGoldAndBronzeStageGridPane() {
        Pool selectedPool = poolTableView.getSelectionModel().getSelectedItem();

        finalStageGridPane.getChildren().clear();
        finalStageGridPane.setVgap(30);
        finalStageGridPane.setHgap(30);

        int counter = 1;
        for (Match match : selectedPool.getPlayoffBracket().getMatches()){
            GridPane gridPane = new GridPane();
            Text groupNumberText = new Text("  Guld og Bronze  ");
            groupNumberText.setStyle("-fx-font-weight: bold;");
            gridPane.add(groupNumberText, 0, 0);
            gridPane.add(new Text("  " + counter + ". plads af gruppe 1" + "  "), 1, 0);
            gridPane.add(new Text("  " + counter + ". plads af gruppe 2" + "  "), 1, 1);
            if(counter <= 2) {
                counter++;
            }
            finalStageGridPane.add(gridPane, 0,finalStageGridPane.getRowCount());
        }



        finalStageGridPane.setGridLinesVisible(false);
        finalStageGridPane.setGridLinesVisible(true);
    }

    private void drawKnockoutStageGridPane() {
        Pool selectedPool = poolTableView.getSelectionModel().getSelectedItem();

        int amountOfMatches = selectedPool.getPlayoffBracket().getMatches().size();
        PlayoffBracket playoffBracket = selectedPool.getPlayoffBracket();
        finalStageGridPane.getChildren().clear();
        finalStageGridPane.setVgap(30);
        finalStageGridPane.setHgap(30);

        int iter = amountOfMatches - 1;
        int columnCount = 0;
        if (iter > 2) {
            int rowCount = 1;
            for (iter = iter; iter >= 3; iter--) {
                Team team1 = playoffBracket.getMatches().get(iter).getFirstTeam();
                Team team2 = playoffBracket.getMatches().get(iter).getSecondTeam();
                GridPane gridPane = new GridPane();
                Text groupNumberText = new Text("  Kvartfinale " + iter + "  ");
                groupNumberText.setStyle("-fx-font-weight: bold;");
                gridPane.add(groupNumberText, 0, 0);
                gridPane.add(new Text("  " + team1.getName() + "  "), 1, 0);
                gridPane.add(new Text("  " + team2.getName() + "  "), 1, 1);

                finalStageGridPane.add(gridPane, columnCount, rowCount++);
            }
            columnCount++;
        }
        if (3 > iter && iter > 0) {
            int rowCount = 1;
            for (iter = iter; iter >= 1; iter--) {
                Team team1 = playoffBracket.getMatches().get(iter).getFirstTeam();
                Team team2 = playoffBracket.getMatches().get(iter).getSecondTeam();
                GridPane gridPane = new GridPane();
                Text groupNumberText = new Text(" Semifinale " + iter + "  ");
                groupNumberText.setStyle("-fx-font-weight: bold;");
                gridPane.add(groupNumberText, 0, 0);
                gridPane.add(new Text("  " + team1.getName() + "  "), 1, 0);
                gridPane.add(new Text("  " + team2.getName() + "  "), 1, 1);

                finalStageGridPane.add(gridPane, columnCount, rowCount++);
            }
            columnCount++;

        }
        if (iter == 0) {
            int rowCount = 1;
            Team team1 = playoffBracket.getMatches().get(0).getFirstTeam();
            Team team2 = playoffBracket.getMatches().get(0).getSecondTeam();
            GridPane gridPane = new GridPane();
            Text groupNumberText = new Text("  Finale " + "  ");
            groupNumberText.setStyle("-fx-font-weight: bold;");
            gridPane.add(groupNumberText, 0, 0);
            gridPane.add(new Text("  " + team1.getName() + "  "), 1, 0);
            gridPane.add(new Text("  " + team2.getName() + "  "), 1, 1);

            finalStageGridPane.add(gridPane, columnCount, rowCount++);
        }
        finalStageGridPane.setGridLinesVisible(false);
        finalStageGridPane.setGridLinesVisible(true);
    }

    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/CreatingFinalStage.FXML"));
        Parent newWindow = loader.load();

        CreatingFinalStageController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    private void verifyButtonPressed() {
        Pool selectedPool = poolTableView.getSelectionModel().getSelectedItem();
        selectedPool.setPlayOffVerificationStatus("Færdig");

        poolTableView.getItems().clear();
        addPoolsInTableView();
    }

    @FXML
    public void nextButtonClicked(ActionEvent event) throws IOException {
        try {
            checkVerifyException();
        } catch (NotAllTeamsAreVerified e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input-fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/MatchScheduleSetup.FXML"));
        Parent newWindow = loader.load();

        MatchScheduleSetupController mss = loader.getController();
        mss.setTournament(tournament);

        Scene newScene = new Scene(newWindow);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();

    }

    public void checkVerifyException() {
        for (Pool pool : tournament.getPoolList()) {
            if (pool.getPlayOffVerificationStatus().equals("Ikke færdig")) {
                throw new NotAllTeamsAreVerified("slutspil");
            }
        }
    }
}
