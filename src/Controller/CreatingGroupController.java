package Controller;

import Controller.AddingTeamsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tournament.Tournament;
import tournament.pool.Pool;

import java.io.IOException;


public class CreatingGroupController
{
    Tournament tournament;

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
        setPoolStatusGridPane();
    }

    private void setPoolStatusGridPane() {
        for(Pool pool : tournament.getPoolList() ) {
            Text text = new Text(pool.getYearGroup() + "" + pool.getSkillLevel());
            text.setWrappingWidth(80);
            text.setTextAlignment(TextAlignment.CENTER);
            Text status = new Text("Not done");
            status.setWrappingWidth(80);
            status.setTextAlignment(TextAlignment.CENTER);
            GridPane.setMargin(text, new Insets(10,0,10,0));
            poolStatusGridPane.addRow(poolStatusGridPane.getRowCount(), text, status);
        }
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
        for(Node node : poolStatusGridPane.getChildren())
            node.setStyle("-fx-font-weight: normal;");

        Text poolClicked = (Text) poolStatusGridPane.getChildren().get((int) Math.floor(e.getY() / 36) * 2 + 1);
        poolClicked.setStyle("-fx-font-weight: bold;");
    }

}
