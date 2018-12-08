package controller;

import exceptions.MissingInputException;
import exceptions.NotEnoughTeamsAddedException;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tournament.Team;
import tournament.Tournament;
import tournament.matchschedule.GraphicalObjects.DeleteButtonTableCell;
import tournament.matchschedule.GraphicalObjects.ProgressBox;
import tournament.pool.Pool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class AddingTeamsController implements CheckInput {
    private final int stepNumber = 1;

    @FXML
    private VBox progressBox;
    @FXML
    private TextField teamNameTextField = new TextField();
    @FXML
    private ComboBox<String> yearGroupComboBox = new ComboBox<>();
    @FXML
   private ComboBox<String> skillLevelComboBox = new ComboBox<>();
    @FXML
    private ComboBox<String>  poolNameComboBox = new ComboBox<>();
    @FXML
    private TextField phoneNumTextField = new TextField();
    @FXML
    private TableView<Team> teamTableView;

    private Tournament tournament;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        progressBox.getChildren().add(new ProgressBox(stepNumber));
        setPoolNameComboBox();
        setYearGroupComboBox();
        setTeamTable();
    }

    private void setTeamTable() {
        TableColumn<Team, String> teamNameColumn = new TableColumn<>("Holdnavn");
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teamNameColumn.setPrefWidth(190);

        TableColumn<Team, String> phoneNumColumn = new TableColumn<>("Telefon nr.");
        phoneNumColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        phoneNumColumn.setMaxWidth(100);

        TableColumn<Team, Button> deleteButtonColumn = new TableColumn<>("");
        deleteButtonColumn.setCellValueFactory(new PropertyValueFactory<>(""));
        deleteButtonColumn.setCellFactory(DeleteButtonTableCell.forTableColumn((Team team) -> {
            teamTableView.getItems().remove(team);
            tournament.findCorrectPool(team.getYearGroup(), team.getSkillLevel()).getTeamList().remove(team);
            return null;
        }));
        deleteButtonColumn.setMinWidth(110);

        teamTableView.getColumns().addAll(teamNameColumn, phoneNumColumn, deleteButtonColumn);
    }


    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("../View/TournamentSetup.fxml"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    private void checkThatAllPoolsHaveMinimumTeams() {
        for (Pool pool : tournament.getPoolList()) {
            if (pool.getTeamList().isEmpty() || pool.getTeamList().size() < 2) {
                throw new NotEnoughTeamsAddedException();
            }
        }
    }

    @FXML
    public void nextButtonClicked(ActionEvent event) throws IOException {

        try {
            checkThatAllPoolsHaveMinimumTeams();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/CreatingGroups.FXML"));
            Parent newWindow = loader.load();

            CreatingGroupController atc = loader.getController();
            atc.setTournament(tournament);

            Scene newScene = new Scene(newWindow);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(newScene);
            window.show();
        } catch (NotEnoughTeamsAddedException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }
    }

    @Override
    public void checkAllInput() {
        if (teamNameTextField.getText().isEmpty() || skillLevelComboBox.getSelectionModel().isEmpty()
            || yearGroupComboBox.getSelectionModel().isEmpty()) {
            throw new MissingInputException();
        }
        // Maybe check if phoneNumTextField.getText is a valid phone number
    }

    @FXML
    void addTeam() {
        try {
            checkAllInput();
            Pool selectedPool = tournament.findCorrectPool(Integer.parseInt(yearGroupComboBox.getValue()),
                    skillLevelComboBox.getValue());

            selectedPool.addTeam(new Team(teamNameTextField.getText(), skillLevelComboBox.getValue(),
                    Integer.parseInt(yearGroupComboBox.getValue()), phoneNumTextField.getText()));

            poolNameComboBox.getSelectionModel().select(selectedPool.getName());

            // add teams to the table that have not been added before
            for (Team team : selectedPool.getTeamList()) {
                if (!teamTableView.getItems().contains(team))
                    teamTableView.getItems().add(team);
            }
        } catch (MissingInputException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }
    }

    @FXML
    void setSkillLevelComboBoxItems() {
        ObservableList<String> SkillLevelComboBoxlist = FXCollections.observableArrayList();
        for (Pool pool : tournament.getPoolList() ) {
            if (yearGroupComboBox.getValue().equals(Integer.toString(pool.getYearGroup()))) {
                SkillLevelComboBoxlist.add(pool.getSkillLevel());
            }

        }
        skillLevelComboBox.setItems(SkillLevelComboBoxlist);
    }

    @FXML
    void removeTeams() {
    }

    void setPoolNameComboBox() {
        ObservableList<String> poolNameList = FXCollections.observableArrayList();

        for (Pool pool : tournament.getPoolList()) {
            poolNameList.add(pool.getName());
        }
        poolNameComboBox.setItems(poolNameList);
    }

    void setYearGroupComboBox() {
        Set<Integer> YearGroupComboSet = new HashSet<>();
        ObservableList<String> YearGroupComboBoxlist = FXCollections.observableArrayList();

        for (Pool pool : tournament.getPoolList()) {
            YearGroupComboSet.add(pool.getYearGroup());
        }

        Collections.sort(YearGroupComboBoxlist);

        ArrayList<Integer> list = new ArrayList<>(YearGroupComboSet);

        Collections.sort(list);

        for (Integer yearGroup : list) {
            YearGroupComboBoxlist.add(Integer.toString(yearGroup));
        }

        yearGroupComboBox.setItems(YearGroupComboBoxlist);
    }

    @FXML
    private void setOnPoolSelected() {
        //Remove all teams from the table before showing teams from another pool
        teamTableView.getItems().clear();

        String selectedPoolName = poolNameComboBox.getValue();

        Pool selectedPool = tournament.findCorrectPool(selectedPoolName);

        ObservableList<Team> observableTeamList = FXCollections.observableArrayList();
        observableTeamList.addAll(selectedPool.getTeamList());
        teamTableView.getItems().addAll(observableTeamList);

    }
}
