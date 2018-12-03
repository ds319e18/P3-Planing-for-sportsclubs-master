package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tournament.Team;
import tournament.Tournament;
import tournament.pool.Pool;
import tournament.pool.bracket.StandardGroupPlay;

import java.io.IOException;


public class CreatingGroupController
{
    Tournament tournament;
    private final int stepNumber = 2;

    @FXML
    private VBox progressBox;

    @FXML
    GridPane teamsInPoolGridPane;

    @FXML
    GridPane poolStatusGridPane;

    @FXML
    Text poolNameText;

    @FXML
    Text amountOfTeamsText;

    @FXML
    ComboBox amountOfGroupsComboBox;

    @FXML
    ComboBox matchesPrGroupsComboBox;

    Text poolClicked;


    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        setPoolStatusGridPane();
        highlightProgressBox();
    }

    @FXML
    private void saveButton() {
        String poolClickedString = poolClicked.getText();
        int yearGroup = Integer.parseInt(poolClickedString.length() == 3 ? poolClickedString.substring(0, 2)
                : poolClickedString.substring(0, 1));
        String skillLevel = (poolClickedString.length() == 3 ? poolClickedString.substring(2, 3)
                : poolClickedString.substring(1, 2));
        tournament.findCorrectPool(yearGroup, skillLevel)
                .addGroupBracket(new StandardGroupPlay(Integer.parseInt(amountOfGroupsComboBox.getValue().toString())));
        tournament.findCorrectPool(yearGroup, skillLevel).getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(Integer.parseInt(matchesPrGroupsComboBox.getValue().toString()));

        for (int i = 0; i < poolStatusGridPane.getChildren().size(); i++) {
            Text poolTextObject = (Text) poolStatusGridPane.getChildren().get(i);

            if (poolTextObject.equals(poolClicked)) {
                Text statusTextObject = (Text) poolStatusGridPane.getChildren().get(i + 1);
                statusTextObject.setText("Færdig");
                break;
            }
        }
    }

    private void setPoolStatusGridPane() {
        poolStatusGridPane.getChildren().remove(0, poolStatusGridPane.getChildren().size());
        for(Pool pool : tournament.getPoolList() ) {
            Text text = new Text(pool.getYearGroup() + "" + pool.getSkillLevel());
            text.setWrappingWidth(80);                      // The width of the Text-object.
            text.setTextAlignment(TextAlignment.CENTER);    // The text of the Text-object is centered.
            boolean isDone = pool.getGroupBracket() != null
                    && pool.getGroupBracket().getAmountOfGroups() > 0
                    && pool.getGroupBracket().getMatchesPrTeamAgainstOpponentInGroup() > 0;
            Text status = (isDone ? new Text("Færdig") : new Text("Ikke færdig"));
            status.setWrappingWidth(80);                    // The width of the Text-object.
            status.setTextAlignment(TextAlignment.CENTER);  // The text of the Text-object is centered.
            GridPane.setMargin(text, new Insets(10,0,10,0));
            poolStatusGridPane.addRow(poolStatusGridPane.getRowCount(), text, status);
        }
        poolStatusGridPane.setGridLinesVisible(false);
        poolStatusGridPane.setGridLinesVisible(true);
    }

    @FXML
    public void nextButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/VerifyGroupsAndPools.FXML"));
        Parent newWindow = loader.load();

        VerifyGroupsAndPoolsController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/AddingTeams.FXML"));
        Parent newWindow = loader.load();

        AddingTeamsController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    private void mouseClicked(MouseEvent e) {
        for(Node node : poolStatusGridPane.getChildren())
            node.setStyle("-fx-font-weight: normal;");

        poolClicked = (Text) poolStatusGridPane.getChildren().get((int) Math.floor(e.getY() / 36) * 2);
        poolClicked.setStyle("-fx-font-weight: bold;");

        setComboBoxItemsAndLabels();
        drawTeamsInPoolGridPane();
    }

    @FXML
    void drawTeamsInPoolGridPane() {
        String poolClickedText = poolClicked.getText();
        teamsInPoolGridPane.getChildren().remove(0, teamsInPoolGridPane.getChildren().size());
        try {
            // The following int- og String-values are found to be able to search for the chosen pool via. findCorrectPool.
            // Finds the first part of the chosen pool's name. Etc, "10A" finds 10.
            int teamYearGroup = Integer.parseInt(poolClickedText.length() == 3 ? poolClickedText.substring(0, 2)
                    : poolClickedText.substring(0, 1));
            // Finds the last part of the chosen pool's name. Etc, "10A" finds A.
            String teamSkillLevel = (poolClickedText.length() == 3 ? poolClickedText.substring(2, 3)
                    : poolClickedText.substring(1, 2));

            // Runs through the teams in the chosen pool, in order to add them to a list in the GUI.
            for (Team team : tournament.findCorrectPool(teamYearGroup, teamSkillLevel).getTeamList()) {
                Text name = new Text(team.getName());
                teamsInPoolGridPane.addRow(teamsInPoolGridPane.getRowCount(), name);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        teamsInPoolGridPane.setGridLinesVisible(false);
        teamsInPoolGridPane.setGridLinesVisible(true);
    }

    private void setComboBoxItemsAndLabels() {
        String poolClickedText = poolClicked.getText();
        // The year group and skill level of the chosen pool are described.
        String yearGroup = (poolClickedText.length() == 3 ? poolClickedText.substring(0, 2) : poolClickedText.substring(0, 1));
        String skillLevel = (poolClickedText.length() == 3 ? poolClickedText.substring(2, 3) : poolClickedText.substring(1, 2));
        // The amount of teams in the pool is displayed.
        amountOfTeamsText.setText(String.valueOf(tournament.findCorrectPool(Integer.parseInt(yearGroup), skillLevel).getTeamList().size()));
        // The pool chosen is displayed.
        poolNameText.setText(poolClickedText);

        // The combobox for choosing the amount of matches each team will play against other teams is filled.
        ObservableList<String> matchesAgainstOpponentsInGroup = FXCollections.observableArrayList("1", "2", "3", "4", "5");
        matchesPrGroupsComboBox.setItems(matchesAgainstOpponentsInGroup);
        // The combobox for choosing the amount of groups
        ObservableList<String> amountOfGroups = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        amountOfGroupsComboBox.setItems(amountOfGroups);
    }

    private void highlightProgressBox() {
        VBox stepBox = (VBox) progressBox.getChildren().get(stepNumber);
        stepBox.setStyle("-fx-border-color: #0000CD");
        stepBox.setStyle("-fx-background-color: #A9A9A9");
    }
}
