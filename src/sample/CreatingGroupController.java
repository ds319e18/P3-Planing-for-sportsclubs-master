package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tournament.Tournament;
import tournament.pool.Pool;

import java.io.IOException;


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

    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
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
    private void mouseClicked(MouseEvent e) {
        Node source = (Node)e.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.println("Mouse clicked cell [" + colIndex + ", " + rowIndex + "].");
        System.out.println();
    }

}
