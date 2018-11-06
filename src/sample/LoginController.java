package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    public void initialize() {
        backBtn.setOnMouseClicked(e -> {
            System.out.println(usernameTextField.getText());
        });

        loginBtn.setOnMouseClicked(e -> {
            System.out.println(passwordTextField.getText());
        });
    }
}
