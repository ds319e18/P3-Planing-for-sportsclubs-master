package controller;

import database.DAO.AccountDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateAccountController {
    database.DAO.AccountDAO AccountDAO = new AccountDAO();

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
