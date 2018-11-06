package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class FrontPageController {
    @FXML
    GridPane gp;

    @FXML
    Button loginBtn;

    @FXML
    public void setOnLoginButtonClicked(ActionEvent event) throws IOException {

        Parent newWindow = FXMLLoader.load(getClass().getResource("Login.FXML"));
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
        gp.setVgap(10);
        for (int i = 0; i < 10; i++) { // Iterates through a list of tournament-objects.
            Text txt = new Text("Tournament name " + i);
            Text status = new Text("ACTIVE");
            Text date = new Text("Start: 26/2/2008\nEnd: 27/2/2009");
            Button btn = new Button("View");
            gp.addRow(i, txt, status, date, btn);
        }
    }
}
