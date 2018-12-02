package controller;

import database.AccountDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateAccountController {
    AccountDAOImpl AccountDAO = new AccountDAOImpl();

    @FXML
    TextField name;

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    Button createUserBtn;

    @FXML
    void setOnCreateAccountBtmClicked() {
        AccountDAO.createAccount(username.getText(), password.getText(), name.getText());
    }
}
