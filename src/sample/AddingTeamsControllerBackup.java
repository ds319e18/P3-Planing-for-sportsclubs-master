package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tournament.Team;
import tournament.Tournament;
import tournament.pool.Pool;

import java.io.IOException;

public class AddingTeamsControllerBackup {
    @FXML
    GridPane gp = new GridPane();
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
        /*tournament.createPools("A", 9);
        tournament.createPools("B", 9);
        tournament.createPools("C", 9);*/
        tournament.findCorrectPool(9, "A").addTeam(new Team("name1", 9, "A"));
        tournament.findCorrectPool(9, "C").addTeam(new Team("name2", 9, "C"));
        tournament.findCorrectPool(9, "B").addTeam(new Team("name3", 9, "B"));
        tournament.findCorrectPool(9, "A").addTeam(new Team("name4", 9, "A"));
        tournament.findCorrectPool(9, "B").addTeam(new Team("name4", 9, "B"));
        tournament.findCorrectPool(9, "A").addTeam(new Team("name4", 9, "A"));
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
    void drawGridPane() {
        gp.getChildren().remove(0, gp.getChildren().size());

        try {
            for (Team team : tournament.findCorrectPool(Integer.parseInt(teamParticipants.getValue().substring(0, 1)),
                    teamParticipants.getValue().substring(1, 2)).getTeamList()) {
                Text name = new Text(team.getName());
                Text yearGroup = new Text(Integer.toString(team.getYearGroup()));
                Text skillLevel = new Text(team.getSkillLevel());
                Text contact = new Text(team.getContact());

                gp.addRow(gp.getRowCount(), name, yearGroup, skillLevel, contact);
            }
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
    }

    void setComboBoxItems() {
        ObservableList<String> YearGroupComboBoxlist = FXCollections.observableArrayList();
        ObservableList<String> SkillLevelComboBoxlist = FXCollections.observableArrayList();
        for (Pool pool : tournament.getPoolList()) {
            YearGroupComboBoxlist.add(Integer.toString(pool.getYearGroup()));
            SkillLevelComboBoxlist.add(pool.getSkillLevel());
        }
        yearGroupComboBox.setItems(YearGroupComboBoxlist);
        skillLevelComboBox.setItems(SkillLevelComboBoxlist);

    }

    void setTeamparticipant(){
        ObservableList<String> allInOne = FXCollections.observableArrayList();
        for(Pool pool : tournament.getPoolList() ) {
            allInOne.add(Integer.toString(pool.getYearGroup()) + pool.getSkillLevel());
        }

        teamParticipants.setItems(allInOne);


    }
}
