package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tournament.Match;
import tournament.Result;
import tournament.Tournament;
import tournament.matchschedule.Field;
import tournament.matchschedule.GraphicalObjects.MatchContainer;
import tournament.matchschedule.MatchDay;
import tournament.pool.Pool;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.LinkOption;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UpdateMatchScheduleController {
    // Liste af Booleans. Hver pool har en Boolean, der beskriver om alle gruppekampene er spillet, og knockout fasen er begyndt.
    private ArrayList<Boolean> knockoutPhaseStarted = new ArrayList<>();

    Tournament tournament;

    MatchContainer selectedMatchContainer = new MatchContainer(LocalTime.now());

    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/AdminPage.FXML"));
        Parent newWindow = loader.load();

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        setMatchTableView();
    }

    private void setMatchTableView() {

    }

    @FXML
    public void setAddSponser(MouseEvent event) throws MalformedURLException {

        /*
        String imageFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif")); // limit fileChooser options to image files
        Node node = (Node) event.getSource();
        File selectedFile = fileChooser.showOpenDialog(node.getScene().getWindow());

        if (selectedFile != null) {
            imageFile = selectedFile.toURI().toURL().toString();
            Image im = new Image(imageFile);
            logo.setImage(im);
        }
        */

    }



}
