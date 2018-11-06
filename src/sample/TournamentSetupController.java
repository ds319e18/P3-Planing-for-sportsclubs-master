package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tournament.Tournament;
import tournament.pool.Pool;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TournamentSetupController {
    private final int YEAR_GROUP_MAX = 15;
    private final int SKILL_LEVEL_MAX = 3;

    @FXML
    private Accordion poolAccordion;

    @FXML
    private TextField tournamentName;

    @FXML
    private ComboBox tournamentTypeCombobox;

    @FXML
    private ComboBox fieldNumberCombobox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    private Tournament tournament;

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

    @FXML
    private void nextButtonPressed() {
        tournament = new Tournament(tournamentName.getText(),startDatePicker.getValue(),
                endDatePicker.getValue(), tournamentTypeCombobox.getValue().toString(),
                Integer.parseInt(fieldNumberCombobox.getValue().toString()),
                getSelectedPools());
    }


    private ArrayList<Pool> getSelectedPools() {
        ArrayList<Pool> poolList = new ArrayList<>();

        String yearString;

        // the selected pools will be saved in a list
        for (int i = 0; i < YEAR_GROUP_MAX; i++) {
            TitledPane titledPane = poolAccordion.getPanes().get(i);
            AnchorPane anchorPane = (AnchorPane) titledPane.getContent();

            for (int j = 0; j < SKILL_LEVEL_MAX; j++) {
                CheckBox checkBox = (CheckBox) anchorPane.getChildren().get(j);

                if (checkBox.isSelected()) {

                    yearString = titledPane.getText().replace(String.valueOf
                            (titledPane.getText().charAt(0)), "");
                    poolList.add(new Pool(checkBox.getText(), Integer.parseInt(yearString)));
                }
            }
        }
        return poolList;
    }


}
