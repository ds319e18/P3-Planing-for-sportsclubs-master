package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tournament.Tournament;
import View.GraphicalObjects.MatchContainer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalTime;
import java.util.ArrayList;

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
