package controller;

import controller.AddingTeamsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tournament.Team;
import tournament.Tournament;
import tournament.pool.Pool;

import java.io.IOException;


public class CreatingGroupController
{
    Tournament tournament;

    @FXML
    GridPane poolNamesGridPane;

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


    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        setPoolStatusGridPane();
    }

    private void setPoolStatusGridPane() {
        for(Pool pool : tournament.getPoolList() ) {
            Text text = new Text(pool.getYearGroup() + "" + pool.getSkillLevel());
            text.setWrappingWidth(80);
            text.setTextAlignment(TextAlignment.CENTER);
            Text status = new Text("Not done");
            status.setWrappingWidth(80);
            status.setTextAlignment(TextAlignment.CENTER);
            GridPane.setMargin(text, new Insets(10,0,10,0));
            poolStatusGridPane.addRow(poolStatusGridPane.getRowCount(), text, status);
        }
    }

    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddingTeams.FXML"));
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

        Text poolClicked = (Text) poolStatusGridPane.getChildren().get((int) Math.floor(e.getY() / 36) * 2 + 1);
        poolClicked.setStyle("-fx-font-weight: bold;");

        setComboBoxItemsAndLabels(poolClicked.getText());
        drawGridPane(poolClicked.getText());
    }

    @FXML
    void drawGridPane(String poolClicked) {
        poolNamesGridPane.getChildren().remove(0, poolNamesGridPane.getChildren().size());
        try {
            String teamYearGroup = (poolClicked.length() == 3 ? poolClicked.substring(0, 2)
                    : poolClicked.substring(0, 1));
            String teamSkillLevel = (poolClicked.length() == 3 ? poolClicked.substring(2, 3)
                    : poolClicked.substring(1, 2));
            for (Team team : tournament.findCorrectPool(Integer.parseInt(teamYearGroup), teamSkillLevel).getTeamList()) {
                Text name = new Text(team.getName());
                poolNamesGridPane.addRow(poolNamesGridPane.getRowCount(), name);
            }
        } catch (Exception e) {
            System.out.println("Error drawing GridPane");
        }
        poolNamesGridPane.setGridLinesVisible(false);
        poolNamesGridPane.setGridLinesVisible(true);
    }

    private void setComboBoxItemsAndLabels(String poolClickedText) {
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

}
