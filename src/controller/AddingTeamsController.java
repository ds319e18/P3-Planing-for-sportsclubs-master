package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tournament.Team;
import tournament.Tournament;
import tournament.pool.Pool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class AddingTeamsController {
    private final int stepNumber = 1;

    @FXML
    private VBox progressBox;
    @FXML
    GridPane gridPane;
    @FXML
    TextField teamNameTextField = new TextField();
    @FXML
    ComboBox<String> yearGroupComboBox = new ComboBox<>();
    @FXML
    ComboBox<String> skillLevelComboBox = new ComboBox<>();
    @FXML
    ComboBox<String>  teamParticipants = new ComboBox<>();
    @FXML
    TextField contactTextField = new TextField();

    private Tournament tournament;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        setComboBoxItems();
        setTeamparticipant();
    }

    @FXML
    void initialize() {
        highlightProgressBox();
    }

    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("../View/TournamentSetup.fxml"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void nextButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/CreatingGroups.FXML"));
        Parent newWindow = loader.load();

        CreatingGroupController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    void addTeam() {
        tournament.findCorrectPool(Integer.parseInt(yearGroupComboBox.getValue()), skillLevelComboBox.getValue())
                .addTeam(new Team(teamNameTextField.getText(), skillLevelComboBox.getValue(),
                        Integer.parseInt(yearGroupComboBox.getValue()), contactTextField.getText()));
        drawGridPane();
    }


    @FXML
    void setSkillLevelComboBoxItems() {
        ObservableList<String> SkillLevelComboBoxlist = FXCollections.observableArrayList();
        for (Pool pool : tournament.getPoolList() ) {
            if (yearGroupComboBox.getValue().equals(Integer.toString(pool.getYearGroup()))) {
                SkillLevelComboBoxlist.add(pool.getSkillLevel());
            }

        }
        skillLevelComboBox.setItems(SkillLevelComboBoxlist);
    }

    @FXML
    void drawGridPane() {
        gridPane.getChildren().remove(0, gridPane.getChildren().size());

        try {
            String teamYearGroup = (teamParticipants.getValue().length() == 3 ? teamParticipants.getValue().substring(0, 2)
            : teamParticipants.getValue().substring(0, 1));
            String teamSkillLevel = (teamParticipants.getValue().length() == 3 ? teamParticipants.getValue().substring(2, 3)
                    : teamParticipants.getValue().substring(1, 2));
            for (Team team : tournament.findCorrectPool(Integer.parseInt(teamYearGroup), teamSkillLevel).getTeamList()) {
                Text name = new Text(team.getName());
                Text contact = new Text(team.getContact());
                CheckBox checkBox = new CheckBox();

                gridPane.addRow(gridPane.getRowCount(), name, contact, checkBox);
            }
        } catch (Exception e) {
            System.out.println("Error drawing GridPane");
        }
        gridPane.setGridLinesVisible(false);
        gridPane.setGridLinesVisible(true);
    }

    @FXML
    void removeTeams() {
        // Goes through every row of the GridPane.
        for (int i = gridPane.getRowCount() - 1; i >= 0; i--) {
            // 3 Children in each Row. This finds the 3rd child in the row.
            CheckBox checkBox = (CheckBox) gridPane.getChildren().get((i)*3 + 2);

            if (checkBox.isSelected()) {
                String teamYearGroup = (teamParticipants.getValue().length() == 3 ? teamParticipants.getValue().substring(0, 2)
                        : teamParticipants.getValue().substring(0, 1));
                String teamSkillLevel = (teamParticipants.getValue().length() == 3 ? teamParticipants.getValue().substring(2, 3)
                        : teamParticipants.getValue().substring(1, 2));
                Text teamName = (Text) gridPane.getChildren().get(i*3);

                tournament.findCorrectPool(Integer.parseInt(teamYearGroup), teamSkillLevel)
                        .removeTeam(teamName.getText());
            }
        }
        drawGridPane();
    }

    void setComboBoxItems() {
        Set<Integer> YearGroupComboSet = new HashSet<>();
        ObservableList<String> YearGroupComboBoxlist = FXCollections.observableArrayList();

        for (Pool pool : tournament.getPoolList()) {
            YearGroupComboSet.add(pool.getYearGroup());
        }

        Collections.sort(YearGroupComboBoxlist);

        ArrayList<Integer> list = new ArrayList<>();
        for (Integer yearGroup : YearGroupComboSet) {
            list.add(yearGroup);
        }

        Collections.sort(list);

        for (Integer yearGroup : list) {
            YearGroupComboBoxlist.add(Integer.toString(yearGroup));
        }

        yearGroupComboBox.setItems(YearGroupComboBoxlist);

    }

    void setTeamparticipant(){
        ObservableList<String> poolList = FXCollections.observableArrayList();
        for(Pool pool : tournament.getPoolList() ) {
            poolList.add(Integer.toString(pool.getYearGroup()) + pool.getSkillLevel());
        }

        teamParticipants.setItems(poolList);


    }


    private void highlightProgressBox() {
        VBox stepBox = (VBox) progressBox.getChildren().get(stepNumber);
        stepBox.setStyle("-fx-border-color: #0000CD");
        stepBox.setStyle("-fx-background-color: #A9A9A9");
    }
}