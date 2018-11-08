package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import tournament.Team;
import tournament.Tournament;


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

    Tournament tournament;

    /*public AddingTeamsController(Tournament tournament) {
        this.tournament = tournament;
    }*/

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @FXML
    void initialize() {

    }

    @FXML
    void addTeam() {
        /*tournament.findCorrectPool(skillLevelComboBox.getValue(), Integer.parseInt(yearGroupComboBox.getValue()))
                .addTeam(new Team(teamNameTextField.getText(), skillLevelComboBox.getValue(),
                        Integer.parseInt(yearGroupComboBox.getValue()), contactTextField.getText()));*/

        Text name = new Text(teamNameTextField.getText());
        Text contact = new Text(contactTextField.getText());
        Text yearGroup = new Text(yearGroupComboBox.getValue());
        Text skillLevel = new Text(skillLevelComboBox.getValue());

        gp.addRow(gp.getRowCount() + 1, name, contact, yearGroup, skillLevel);
    }

}
