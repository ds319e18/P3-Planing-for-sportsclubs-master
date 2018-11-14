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
import tournament.Tournament;
import tournament.pool.Pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CreatingGroupController
{
    Tournament tournament;

    @FXML
    ComboBox poolComboBox;

    @FXML
    GridPane poolNamesGridPane;

    @FXML
    GridPane poolStatusGridPane;

    @FXML
    Text poolNameText;

    @FXML
    Text amountOfTeamsText;

    @FXML
    ComboBox amountOfGroupsComboBox;

    @FXML
    ComboBox matchesPrGroupsComboBox;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        setPoolComboBox();
    }

    private void setPoolComboBox() {
        ObservableList<String> allInOne = FXCollections.observableArrayList();
        for(Pool pool : tournament.getPoolList() ) {
            allInOne.add(Integer.toString(pool.getYearGroup()) + pool.getSkillLevel());
        }

        poolComboBox.setItems(allInOne);
    }


}
