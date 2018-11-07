package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CreatingGroupController{

    @FXML
    private ListView<String> checkListView;
    @FXML
    private ListView<String> poolListView;
    @FXML
    private ListView<String> groupListView;
    @FXML
    private ListView<String> teamListView;
    @FXML
    private ComboBox groupNrCombobax;
    @FXML
    private ComboBox vsNrCombobax;
    @FXML
    private Label poolLabel;
    @FXML
    private Label amountOfTeamsLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button backButton;
    @FXML
    private Button exitButton;

    public void initialize(){
        groupNrCombobax.setItems(groupNrList);
        vsNrCombobax.setItems(vsNrList);
        poolListView.setItems(poolListObservable);
        checkListView.setItems(checkListObservable);
        groupListView.setItems(groupListObservable);
        //teamListView.setItems(teamListObservable);
        saveButton.setOnAction(this::saveButtonAction);
        nextButton.setOnAction(this::nextButtonAction);
        backButton.setOnAction(this::backButtonAction);
        exitButton.setOnAction(this::exitButtonAction);
    }

    @FXML
    private ObservableList<String> groupNrList = FXCollections.observableArrayList(
            "1", "2","3","4","5","6"
    );
    private ObservableList<String> vsNrList = FXCollections.observableArrayList(
            "1", "2","3","4","5"
    );
    private ObservableList<String> poolListObservable = FXCollections.observableArrayList(
            "Pool 1", "Pool 2"
    );
    private ObservableList<String> checkListObservable = FXCollections.observableArrayList(
            "Check 1", "Check 2"
    );
    private ObservableList<String> groupListObservable = FXCollections.observableArrayList(
            "Group 1", "Group 2"
    );
    private ObservableList<String> teamListObservable = FXCollections.observableArrayList(
            "Team 1", "Team 2", "Team 3", "Team 4"
    );
    @FXML
    private void saveButtonAction(ActionEvent event) {
        if(event.getSource()== saveButton){ //Check if the event is called with the saveButton
            System.out.println("0");//action
        }
    }
    @FXML
    private void nextButtonAction(ActionEvent event) {
        if(event.getSource()== nextButton){ //Check if the event is called with the saveButton
            System.out.println("1");//action
        }
    }
    @FXML
    private void backButtonAction(ActionEvent event) {
        if(event.getSource()== backButton){ //Check if the event is called with the saveButton
            System.out.println("2");//action
        }
    }
    @FXML
    private void exitButtonAction(ActionEvent event) {
        if(event.getSource()== exitButton){ //Check if the event is called with the saveButton
            System.out.println("3");//action
        }
    }
    @FXML public void checkListMouseClick(MouseEvent arg0) {
        // System.out.println("clicked on " + checkListView.getSelectionModel().getSelectedItem());
        //checkListView.get(0).nameProperty().set("Changing");
        checkListView.set(checkListView.getSelectionModel().getSelectedItems(), "Done");
    }
}
