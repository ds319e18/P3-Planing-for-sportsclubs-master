package controller;

import account.Administrator;
import database.AccountDAO;
import database.AccountDAOImpl;
import database.TournamentDAO;
import database.TournamentDAOImpl;
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

        Parent newWindow = FXMLLoader.load(getClass().getResource("../View/FrontPage.fxml"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void setOnCreateButtonClicked(ActionEvent event) throws IOException {

        Parent newWindow = FXMLLoader.load(getClass().getResource("../View/CreateAccount.fxml"));
        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void setOnLogInButtonClicked(ActionEvent event) throws IOException {
        AccountDAO accountSQL = new AccountDAOImpl();
        TournamentDAO tournamentSQL = new TournamentDAOImpl();

        // Finding the correct user and the tournaments the user has created
        user = accountSQL.findAccount(username.getText(), password.getText());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/AdminPage.fxml"));
        Parent newWindow = loader.load();

        AdminPageController atc = loader.getController();
        atc.setUser(user);

        Scene newScene = new Scene(newWindow);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        atc.draw();
        window.setScene(newScene);
        window.show();

    }

    @FXML
    public void checkPassword(ActionEvent event) throws IOException {
        if(username.getText().equals("username") && password.getText().equals("password")) {
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
