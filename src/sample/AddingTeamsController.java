package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tournament.Team;


public class AddingTeamsController {
    @FXML
    TableView<Team> tw = new TableView<>();
    @FXML
    TextField teamNameTextField = new TextField();
    @FXML
    ComboBox<String> yearGroupTextField = new ComboBox<>();
    @FXML
    ComboBox<String> skillLevelTextField = new ComboBox<>();
    @FXML
    TextField contactTextField = new TextField();
    @FXML
    TableColumn<Team, String> teamNameColumn;
    @FXML
    TableColumn<Team, String> yearGroupColumn;
    @FXML
    TableColumn<Team, String> skillLevelColumn;
    @FXML
    TableColumn<Team, String> contactColumn;

    private ObservableList<Team> teamList = FXCollections.observableArrayList(
            new Team("Team-A", "skillLevel", 123, "contact"),
            new Team("Team-B", "skillLevel", 123, "contact")
    );

    @FXML
    void initialize() {
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<Team, String>("name"));
        yearGroupColumn.setCellValueFactory(new PropertyValueFactory<Team, String>("yearGroup"));
        skillLevelColumn.setCellValueFactory(new PropertyValueFactory<Team, String>("skillLevel"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<Team, String>("contact"));
        tw.setItems(teamList);
    }

    @FXML
    void addTeam() {
        teamList.add(new Team(teamNameTextField.getText(), "Skill", 12, contactTextField.getText()));
    }

}
