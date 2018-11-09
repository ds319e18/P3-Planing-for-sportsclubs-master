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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tournament.Tournament;
import tournament.TournamentType;
import tournament.pool.Pool;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static tournament.TournamentType.Group;
import static tournament.TournamentType.GroupAndKnockout;
import static tournament.pool.KnockoutChoice.Knockout;

public class TournamentSetupController {
    private final int YEAR_GROUP_MAX = 15;
    private final int SKILL_LEVEL_MAX = 3;

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

    public TournamentSetupController() {
    }

    // this method will be called after loading the FxML document
    public void initialize() {
        tournamentTypeCombobox.setItems(FXCollections.observableArrayList(
                TournamentType.values()));
        fieldNumberCombobox.setItems(fieldList);
    }

    private ObservableList<String> fieldList = FXCollections.observableArrayList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    @FXML
    private void nextButtonPressed(ActionEvent event) throws IOException {
        tournament = new Tournament(tournamentName.getText(),startDatePicker.getValue(),
                endDatePicker.getValue(), tournamentTypeCombobox.getValue(),
                Integer.parseInt(fieldNumberCombobox.getValue().toString()),
                getSelectedPools());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddingTeams.FXML"));
        Parent newWindow = loader.load();

        AddingTeamsController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void setOnBackButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("AdminPage.FXML"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void setOnNextButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("AddingTeams.FXML"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    private ArrayList<Pool> getSelectedPools() {
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
                    poolList.add(new Pool(checkBox.getText(), Integer.parseInt(yearString)));
                }
            }
        }
        return poolList;
    }


}
