package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import tournament.Tournament;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TournamentSetupController {

    @FXML
    private TextField tournamentName;

    @FXML
    private ComboBox tournamentTypeCombobox;

    @FXML
    private ComboBox fieldNumberCombobox;

    @FXML
    private Button poolChoosingButton;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    private Tournament tournament;
    private LocalDate startDate;
    private LocalDate endDate;

    public TournamentSetupController() {
    }

    // this method will be called after loading the FxML document
    public void initialize() {
        tournamentTypeCombobox.setItems(tournamentList);
        fieldNumberCombobox.setItems(fieldList);
    }

    private ObservableList<String> tournamentList = FXCollections.observableArrayList(
            "Only group",
            "Only knockout",
            "Group + knockout");

    private ObservableList<String> fieldList = FXCollections.observableArrayList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    /*@FXML
    private void nextButtonPressed() {
        tournament = new Tournament(tournamentName.getText(), startDatePicker.getValue(), endDatePicker.getValue()
        ,tournamentTypeCombobox.getValue().toString());
    }*/




}
