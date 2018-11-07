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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CreatingGroupController{

    @FXML
    private ListView<String> checkListView;
    @FXML
    private ListView<Cell> poolListView;
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
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Cell cell3 = new Cell();
        groupNrCombobax.setItems(groupNrList);
        vsNrCombobax.setItems(vsNrList);
        poolListView.setItems(FXCollections.observableArrayList(cell1));
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
            checkListObservable.set(poolListView.getSelectionModel().getSelectedIndex(), "Done");
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
        //checkListView.set(checkListView.getSelectionModel().select(0), "Done");
        //checkListView.getSelectionModel().getSelectedItem().setText("Done");

        //list.set(index,text)
        //checkListObservable.set(checkListView.getSelectionModel().getSelectedIndex(), "Done");
    }

    //Function for the list of pools
    static class Cell extends ListCell<String>{

        //Components for list
        HBox hbox = new HBox();
        Button editButton = new Button("Edit");
        Label poolLabel = new Label("Pool");
        Pane pane = new Pane();

        //Setup list
        public Cell(){
            super(); //?
            hbox.getChildren().addAll(poolLabel, pane, editButton);
            System.out.println(editButton);
            HBox.setHgrow(pane, Priority.ALWAYS);
            editButton.setOnAction(event -> poolLabel.setText("U16")); //Action on button
        }

        //Override method for updating list
        public void updateItem(String name, Boolean empty){
            setText(null);
            setGraphic(null);
            if(name != null && !empty){
                poolLabel.setText("Add pool");
                setGraphic(hbox);
            }
        }
    }

}
