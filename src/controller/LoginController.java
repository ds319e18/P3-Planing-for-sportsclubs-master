package controller;

import account.Administrator;
import database.DAO.AccountDAO;
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
    Administrator user;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Button loginBtn;

    @FXML
    Button backBtn;

    @FXML
    public void setOnBackButtonClicked(ActionEvent event) throws IOException {

        Parent newWindow = FXMLLoader.load(getClass().getResource("../view/FrontPage.fxml"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void setOnCreateButtonClicked(ActionEvent event) throws IOException {

        Parent newWindow = FXMLLoader.load(getClass().getResource("../view/CreateAccount.fxml"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void setOnLogInButtonClicked(ActionEvent event) throws IOException {
        // DAO object for account
        AccountDAO accountSQL = new AccountDAO();
        Boolean logInSucess;

        // Finding the correct user and the tournaments the user has created
        logInSucess = accountSQL.findAccount(username.getText(), password.getText());

        //TODO lav en try catch hvor vi tjekker om login er rigtigt.
        if (logInSucess) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/AdminPage.fxml"));
            Parent newWindow = loader.load();

            Scene newScene = new Scene(newWindow);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(newScene);
            window.show();
        }
    }

    @FXML
    public void checkPassword(ActionEvent event) throws IOException {
        if(username.getText().equals("username") && password.getText().equals("password")) {
            Parent newWindow = FXMLLoader.load(getClass().getResource("../view/AdminPage.fxml"));
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
