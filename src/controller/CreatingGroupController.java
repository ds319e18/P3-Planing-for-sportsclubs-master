package controller;

import exceptions.MissingInputException;
import exceptions.MissingPressingSaveException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tournament.Team;
import tournament.Tournament;
import view.GraphicalObjects.ProgressBox;
import tournament.pool.Pool;
import tournament.pool.bracket.StandardGroupPlay;

import java.io.IOException;


public class CreatingGroupController implements CheckInput {
    private Tournament tournament;
    private final int stepNumber = 2;

    @FXML
    private VBox progressBox;

    @FXML
    private ComboBox amountOfGroupsComboBox;

    @FXML
    private ComboBox matchesPrGroupsComboBox;

    @FXML
    private TableView<Pool> poolTableView;

    @FXML
    private ListView<String> teamListView;


    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        setPoolTableView();
        progressBox.getChildren().add(new ProgressBox(stepNumber));
    }

    private void setPoolTableView() {
        TableColumn<Pool, String> poolNameColumn = new TableColumn<>("Puljenavn");
        poolNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        poolNameColumn.setMinWidth(93.25);
        poolNameColumn.setMaxWidth(93.25);

        TableColumn<Pool, String> poolStatusColumn = new TableColumn<>("Status");
        poolStatusColumn.setCellValueFactory(new PropertyValueFactory<>("groupCreationStatus"));
        poolStatusColumn.setMaxWidth(93.25);
        poolStatusColumn.setMinWidth(93.25);

        poolTableView.getColumns().addAll(poolNameColumn, poolStatusColumn);
        //add pools to tableView
        addPoolsInTableView();
    }

    private void addPoolsInTableView() {
        poolTableView.getItems().addAll(tournament.getPoolList());

        //handle row selection for each pool in tableView
        poolTableView.setRowFactory( table -> {
            TableRow<Pool> row = new TableRow<>();
            row.setOnMouseClicked(event -> handleRowSelection());
            return row;
        });
    }

    private void handleRowSelection() {
        Pool selectedPool = poolTableView.getSelectionModel().getSelectedItem();

        if (selectedPool != null) {
            teamListView.getItems().clear();
            for (Team team : selectedPool.getTeamList()) {
                teamListView.getItems().add(team.getName());
            }
        }

        setComboBoxes();
    }


    @FXML
    private void saveButtonPressed() {
        try {
            checkAllInput();
            Pool selectedPool = poolTableView.getSelectionModel().getSelectedItem();

            selectedPool.addGroupBracket(new StandardGroupPlay(
                    Integer.parseInt(amountOfGroupsComboBox.getValue().toString())));
            selectedPool.getGroupBracket().setMatchesPrTeamAgainstOpponentInGroup(
                    Integer.parseInt(matchesPrGroupsComboBox.getValue().toString()));

        poolTableView.getItems().clear();
        addPoolsInTableView();
        } catch (MissingInputException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }

    }

    @Override
    public void checkAllInput() {
        if (amountOfGroupsComboBox.getSelectionModel().isEmpty() || matchesPrGroupsComboBox.getSelectionModel().isEmpty()) {
            throw new MissingInputException();
        }
    }

    private void checkStatusForEachPool() {
        for (Pool pool : poolTableView.getItems()) {
            if (pool.getGroupCreationStatus().equals("Ikke færdig")) {
                throw new MissingPressingSaveException();
            }
        }
    }

    @FXML
    public void nextButtonClicked(ActionEvent event) throws IOException {
        try {
            checkStatusForEachPool();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/VerifyGroupsAndPools.FXML"));
            Parent newWindow = loader.load();

            VerifyGroupsAndPoolsController atc = loader.getController();
            atc.setTournament(tournament);

            Scene newScene = new Scene(newWindow);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(newScene);
            window.show();
        } catch (MissingPressingSaveException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }
    }

    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/AddingTeams.FXML"));
        Parent newWindow = loader.load();

        AddingTeamsController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    private void setComboBoxes() {
        Pool selectedPool = poolTableView.getSelectionModel().getSelectedItem();
        // The combobox for choosing the amount of matches each team will play against other teams is filled.
        ObservableList<String> matchesAgainstOpponentsInGroup = FXCollections.observableArrayList("1", "2", "3", "4", "5");
        matchesPrGroupsComboBox.setItems(matchesAgainstOpponentsInGroup);
        // The combobox for choosing the amount of groups
        ObservableList<String> amountOfGroups = FXCollections.observableArrayList();
        // Secures that there is not created a group with less than 2 teams in it
        if (selectedPool != null) {
            for (int i = 0; i < (selectedPool.getTeamList().size()) / 2; i++) {
                amountOfGroups.add(Integer.toString(i + 1));
            }
        }
        amountOfGroupsComboBox.setItems(amountOfGroups);
    }
}
