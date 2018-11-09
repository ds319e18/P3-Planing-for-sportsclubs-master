package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tournament.Team;
import tournament.Tournament;

import java.io.IOException;


public class AddingTeamsController {
    @FXML
    GridPane gp = new GridPane();
    @FXML
    TextField teamNameTextField = new TextField();
    @FXML
    ComboBox<String> yearGroupComboBox = new ComboBox<>();
    @FXML
    ComboBox<String> skillLevelComboBox = new ComboBox<>();
    @FXML
    TextField contactTextField = new TextField();
    @FXML
    Button backButton;
    @FXML
    Button nextButton;

    Tournament tournament;

    /*public AddingTeamsController(Tournament tournament) {
        this.tournament = tournament;
    }*/

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @FXML
    void initialize() { }

    @FXML
    public void setOnBackButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("TournamentSetup.FXML"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void setOnNextButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource(".FXML"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    void addTeam() {
        /*tournament.findCorrectPool(skillLevelComboBox.getValue(), Integer.parseInt(yearGroupComboBox.getValue()))
                .addTeam(new Team(teamNameTextField.getText(), skillLevelComboBox.getValue(),
                        Integer.parseInt(yearGroupComboBox.getValue()), contactTextField.getText()));*/

        Text name = new Text(teamNameTextField.getText());
        Text yearGroup = new Text(yearGroupComboBox.getValue());
        Text skillLevel = new Text(skillLevelComboBox.getValue());
        Text contact = new Text(contactTextField.getText());

        gp.addRow(gp.getRowCount() + 1, name, yearGroup, skillLevel, contact);
    }

}
