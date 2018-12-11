package controller;

import account.Administrator;
import exceptions.BackInDateException;
import exceptions.InvalidInputException;
import exceptions.MissingInputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tournament.Tournament;
import tournament.TournamentType;
import tournament.matchschedule.GraphicalObjects.ProgressBox;
import tournament.pool.Pool;

import java.io.IOException;
import java.util.ArrayList;

public class TournamentSetupController implements CheckInput {
    private final int YEAR_GROUP_MAX = 16;
    private final int SKILL_LEVEL_MAX = 3;
    private final int stepNumber = 0;

    private Administrator user;

    public void setUser(Administrator user) {
        this.user = user;
    }

    @FXML
    private VBox progressBox;

    @FXML
    private Accordion poolAccordion;

    @FXML
    private TextField tournamentName;

    @FXML
    private ComboBox<TournamentType> tournamentTypeCombobox;

    @FXML
    private ComboBox fieldNumberCombobox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    private Tournament tournament;

    // this method will be called after loading the FxML document
    public void initialize() {
        tournamentTypeCombobox.setItems(FXCollections.observableArrayList(
                TournamentType.values()));
        fieldNumberCombobox.setItems(fieldList);
        progressBox.getChildren().add(new ProgressBox(stepNumber));
    }

    private ObservableList<String> fieldList = FXCollections.observableArrayList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    public void checkAllInput() {
        if (tournamentName.getText().trim().isEmpty() || startDatePicker.getEditor().getText().isEmpty()
                || endDatePicker.getEditor().getText().isEmpty() || tournamentTypeCombobox.getSelectionModel().isEmpty()
                || fieldNumberCombobox.getSelectionModel().isEmpty() || getSelectedPoolsAndMatchLengths().isEmpty()) {
            throw new MissingInputException();
        } else if(startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            throw new BackInDateException();
        }
    }

    @FXML
    private void nextButtonPressed(ActionEvent event) throws IOException {
        try {
            // Tjekker om alt input er indtastet
            checkAllInput();
            Tournament tournament = new Tournament.Builder(tournamentName.getText())
                    .setStartDate(startDatePicker.getValue())
                    .setEndDate(endDatePicker.getValue())
                    .setType(tournamentTypeCombobox.getValue())
                    .createFieldList(Integer.parseInt(fieldNumberCombobox.getValue().toString()))
                    .setPoolList(getSelectedPoolsAndMatchLengths())
                    .build();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/AddingTeams.FXML"));
            Parent newWindow = loader.load();

            AddingTeamsController atc = loader.getController();
            atc.setTournament(tournament);

            Scene newScene = new Scene(newWindow);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(newScene);
            window.show();
        } catch (MissingInputException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        } catch (InvalidInputException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Ugyldigt input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        } catch (BackInDateException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Ugyldigt input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }


    }

    @FXML
    public void setOnBackButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("../View/AdminPage.FXML"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    /*private ArrayList<Pool> getSelectedPools() {
        ArrayList<Pool> poolList = new ArrayList<>();

        String yearString;

        // the selected pools will be saved in a list
        for (int i = 0; i < YEAR_GROUP_MAX; i++) {
            TitledPane titledPane = poolAccordion.getPanes().get(i);
            HBox hbox = (HBox) titledPane.getContent();

            for (int j = 0; j < SKILL_LEVEL_MAX; j++) {
                CheckBox checkBox = (CheckBox) hbox.getChildren().get(j);

                if (checkBox.isSelected()) {

                    yearString = titledPane.getText().replace(String.valueOf
                            (titledPane.getText().charAt(0)), "");
                    poolList.add(new Pool.Builder()
                                                    .setSkilllLevel(checkBox.getText())
                                                    .setYearGroup(Integer.parseInt(yearString))
                                                    .build());
                }
            }
        }
        return poolList;
    }*/

    private ArrayList<Pool> getSelectedPoolsAndMatchLengths() { // and match lengths
        ArrayList<Pool> poolList = new ArrayList<>();

        String yearString;

        // the selected pools will be saved in a list
        for (int i = 0; i < YEAR_GROUP_MAX; i++) {
            TitledPane titledPane = poolAccordion.getPanes().get(i);
            VBox vbox = (VBox) titledPane.getContent();

            HBox hboxWithCheckboxes = (HBox) vbox.getChildren().get(0);
            HBox hboxWithMatchDuration = (HBox) vbox.getChildren().get(1);
            TextField matchDurationTextField = (TextField) hboxWithMatchDuration.getChildren().get(1);

            for (int j = 0; j < SKILL_LEVEL_MAX; j++) {
                CheckBox checkBox = (CheckBox) hboxWithCheckboxes.getChildren().get(j);

                if (checkBox.isSelected() && !matchDurationTextField.getText().isEmpty()) {
                    if (!(matchDurationTextField.getText().matches("\\d+"))) {
                        throw new InvalidInputException("heltal", "kamplængde");
                    }
                    yearString = titledPane.getText().replace(String.valueOf
                            (titledPane.getText().charAt(0)), "");
                    poolList.add(new Pool.Builder()
                            .setSkilllLevel(checkBox.getText())
                            .setYearGroup(Integer.parseInt(yearString))
                            .setMatchDurationInMinutes(Integer.parseInt(matchDurationTextField.getText()))
                            .build());
                }
            }


        }
        return poolList;
    }

}
