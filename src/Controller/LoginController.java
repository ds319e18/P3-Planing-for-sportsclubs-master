package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordTextField;

    @FXML
    Button loginBtn;

    @FXML
    Button backBtn;

    @FXML
    public void setOnBackButtonClicked(ActionEvent event) throws IOException {

        Parent newWindow = FXMLLoader.load(getClass().getResource("../View/FrontPage.fxml"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void checkPassword(ActionEvent event) throws IOException {
        if(usernameTextField.getText().equals("username") && passwordTextField.getText().equals("password")) {
            Parent newWindow = FXMLLoader.load(getClass().getResource("../View/AdminPage.fxml"));
            Scene newScene = new Scene(newWindow);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(newScene);
            window.show();
        }
    }

    @FXML
    public void initialize() {

    }
}
