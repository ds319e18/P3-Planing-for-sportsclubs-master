package controller;

import database.DAO.AccountDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateAccountController {
    @FXML
    TextField name;

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    Button createUserBtn;

}
