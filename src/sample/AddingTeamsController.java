package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
import java.util.stream.Collectors;


public class AddingTeamsController {
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

    }

    @FXML
    public void setOnBackButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("TournamentSetup.FXML"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML //TODO: Is not connected to SceneBuilder.
    public void setOnNextButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource(".FXML"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
    void test() {
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
            System.out.println("Error occurred");
        }
        gridPane.setGridLinesVisible(false);
        gridPane.setGridLinesVisible(true);
    }

    @FXML
    void removeTeams() {
        // Goes through every row of the GridPane.
        for (int i = gridPane.getRowCount() - 1; i >= 0; i--) {
            // 3 Children in each Row. This finds the 3rd child in the row.
            CheckBox checkBox = (CheckBox) gridPane.getChildren().get((i+1)*3 - 1);

            if (checkBox.isSelected()) {
                System.out.println(i);
                gridPane.getChildren().remove((i)*3, (i)*3 + 3);
            }
        }

    }

    void setComboBoxItems() {
        int i = 0;
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
        ObservableList<String> allInOne = FXCollections.observableArrayList();
        for(Pool pool : tournament.getPoolList() ) {
            allInOne.add(Integer.toString(pool.getYearGroup()) + pool.getSkillLevel());
        }

        teamParticipants.setItems(allInOne);


    }


}
