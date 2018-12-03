package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tournament.Tournament;

import java.io.IOException;

public class FrontPageController {
    @FXML
    GridPane gp;

    @FXML
    Button loginBtn;

    Tournament tournament;

    void setTournament(Tournament tournament) {
        this.tournament = tournament;

    }

    @FXML
    public void setOnLoginButtonClicked(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();

        /*Parent root = FXMLLoader.load(getClass().getResource("Login.FXML"));

        Stage loginWindow = new Stage();
        loginWindow.setScene(new Scene(root));
        loginWindow.show();*/

    }

    @FXML
    public void initialize() {
        for (ColumnConstraints column : gp.getColumnConstraints())
            column.setHalignment(HPos.CENTER);

        for (int i = 0; i < 5; i++) { // Iterates through a list of tournament-objects.
            Text txt = new Text("Tournament " + i);
            Text status = new Text("ACTIVE");
            Text date = new Text("Start: 26/2/2008\nEnd: 27/2/2009");
            Button btnView = new Button("View");
            btnView.setOnAction(event -> {
                try {
                    setViewButtonClicked(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            gp.addRow(i, txt, status, date, btnView);
        }

    }


    @FXML
    public void setViewButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/ViewPage.FXML"));
        Parent newWindow = loader.load();

        ViewMatchScheduleController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();

    }

}
