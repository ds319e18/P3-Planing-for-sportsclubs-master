package controller;

import exceptions.IllegalAmountOfGroupsException;
import exceptions.IllegalAmountOfTeamsException;
import exceptions.MissingInputException;
import exceptions.MissingPressingSaveException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tournament.Tournament;
import tournament.matchschedule.GraphicalObjects.ProgressBox;
import tournament.pool.Group;
import tournament.pool.Pool;
import tournament.pool.bracket.GoldAndBronzePlay;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.bracket.PlacementPlay;

import java.io.IOException;

public class CreatingFinalStageController implements CheckInput {

    private Tournament tournament;
    private final int stepNumber = 4;

    @FXML
    private VBox progressBox;

    @FXML
    private RadioButton knockoutRadioButton;

    @FXML
    private RadioButton placementRadioButton;

    @FXML
    private RadioButton goldAndBronzeRadioButton;

    @FXML
    private ComboBox advancingComboBox;

    @FXML
    private TableView<Pool> poolTableView;

    @FXML
    private TableView<Group> groupTableView;

    @FXML
    private Label advancingTeamsLabel;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        setPoolTableView();
        setGroupTableView();
        setRadioButtonListener();
        progressBox.getChildren().add(new ProgressBox(stepNumber));


    }



    private void setPoolTableView() {
        TableColumn<Pool, String> poolNameColumn = new TableColumn<>("Puljenavn");
        poolNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        setColumnWidth(poolNameColumn);

        TableColumn<Pool, String> poolStatusColumn = new TableColumn<>("Status");
        poolStatusColumn.setCellValueFactory(new PropertyValueFactory<>("playOffCreationStatus"));
        setColumnWidth(poolStatusColumn);

        poolTableView.getColumns().addAll(poolNameColumn, poolStatusColumn);
        //add pools to tableView
        addPoolsInTableView();
    }

    private void setColumnWidth(TableColumn tableColumn) {
        tableColumn.setMaxWidth(128);
        tableColumn.setMinWidth(128);
    }
    private void setGroupTableView() {
        TableColumn<Group, String> groupNameColumn = new TableColumn<>("Gruppenavn");
        groupNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        setColumnWidth(groupNameColumn);

        TableColumn<Group, String> amountOfTeamsColumn = new TableColumn<>("Antal hold");
        amountOfTeamsColumn.setCellValueFactory(new PropertyValueFactory<>("amountOfTeams"));
        setColumnWidth(amountOfTeamsColumn);

        groupTableView.getColumns().addAll(groupNameColumn, amountOfTeamsColumn);
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
            groupTableView.getItems().clear();
            groupTableView.getItems().addAll(selectedPool.getGroupBracket().getGroups());
            setComboBoxItemsAndLabels(selectedPool);
        }
    }

    @Override
    public void checkAllInput() {
        if((!knockoutRadioButton.isSelected() || advancingComboBox.getSelectionModel().isEmpty())  && !placementRadioButton.isSelected() && !goldAndBronzeRadioButton.isSelected()){
            throw new MissingInputException();
        }


    }
    @FXML
    private void saveButton() {
        try {
            checkAllInput();
        } catch (MissingInputException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }

        Pool selectedPool = poolTableView.getSelectionModel().getSelectedItem();
            // The amount of teams to advance to the final stage is set
            if (knockoutRadioButton.isSelected()) {
                    selectedPool.getGroupBracket().setAdvancingTeamsPrGroup(Integer.parseInt(advancingComboBox.getValue().toString()));
                    selectedPool.addPlayoffBracket(new KnockoutPlay());
                    System.out.println("if " + selectedPool.getPlayoffBracket().getMatches().size());
            } else if (placementRadioButton.isSelected()) {
                try {
                    selectedPool.addPlayoffBracket(new PlacementPlay());
                } catch (IllegalAmountOfTeamsException e) {
                    Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
                    warning.setHeaderText("Manglende input fejl");
                    warning.setTitle("Fejl");
                    warning.showAndWait();
                } catch (IllegalAmountOfGroupsException e) {
                    Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
                    warning.setHeaderText("Manglende input fejl");
                    warning.setTitle("Fejl");
                    warning.showAndWait();
                }
            } else if (goldAndBronzeRadioButton.isSelected()) {
                try {
                    selectedPool.addPlayoffBracket(new GoldAndBronzePlay());
                }catch (IllegalAmountOfGroupsException e) {
                    Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
                    warning.setHeaderText("Manglende input fejl");
                    warning.setTitle("Fejl");
                    warning.showAndWait();
                }catch (IllegalAmountOfTeamsException e) {
                    Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
                    warning.setHeaderText("Manglende input fejl");
                    warning.setTitle("Fejl");
                    warning.showAndWait();
                }
            }

            poolTableView.getItems().clear();
            addPoolsInTableView();
    }

    @FXML
    private void setOnKnockoutChosen() {
        advancingTeamsLabel.setVisible(true);
        advancingComboBox.setVisible(true);
    }


    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/VerifyGroupsAndPools.FXML"));
        Parent newWindow = loader.load();

        VerifyGroupsAndPoolsController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }



    @FXML
    public void nextButtonClicked(ActionEvent event) throws IOException {
        try {
            checkSaveException();
        } catch (MissingPressingSaveException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input-fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/VerifyFinalStage.FXML"));
        Parent newWindow = loader.load();

        VerifyFinalStageController atc = loader.getController();

        atc.setTournament(tournament);
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    private void setComboBoxItemsAndLabels(Pool pool) {
        // The combobox for choosing the amount of groups
        ObservableList<String> amountOfTeamsInGroup = FXCollections.observableArrayList();
        int teams = 0;

        for (Group group : pool.getGroupBracket().getGroups()) {
            teams += group.getAmountOfTeams();
        }
        teams = (teams / pool.getGroupBracket().getAmountOfGroups());

        for (int i = 0; i < teams; i++) {
            amountOfTeamsInGroup.add(Integer.toString(i + 1));
        }
        advancingComboBox.setItems(amountOfTeamsInGroup);
    }

    private void setRadioButtonListener() {
        knockoutRadioButton.selectedProperty().addListener(new javafx.beans.value.ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (knockoutRadioButton.isSelected()) {
                    advancingTeamsLabel.setVisible(true);
                    advancingComboBox.setVisible(true);
                } else {
                    advancingTeamsLabel.setVisible(false);
                    advancingComboBox.setVisible(false);
                }
            }
        });
    }

    public void checkSaveException() {
        for (Pool pool : tournament.getPoolList()) {
            if (pool.getPlayOffCreationStatus().equals("Ikke færdig")) {
                throw new MissingPressingSaveException();
            }

        }
    }

}
