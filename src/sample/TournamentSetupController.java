package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.List;

public class TournamentSetupController {

    public void initialize() {
        tournamentTypeCombobox.setItems(tournamentList);
        fieldNumberCombobox.setItems(fieldList);
    }

    @FXML
    private ObservableList<String> tournamentList = FXCollections.observableArrayList(
            "Only group",
            "Only knockout",
            "Group + knockout");

    @FXML
    private ObservableList<String> fieldList = FXCollections.observableArrayList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    @FXML
    private ComboBox tournamentTypeCombobox;

    @FXML
    private ComboBox fieldNumberCombobox;

    @FXML
    private Button poolChoosingButton;



}
