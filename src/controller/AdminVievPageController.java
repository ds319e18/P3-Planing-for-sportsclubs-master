package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import tournament.Tournament;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class AdminVievPageController {

    ArrayList<String> imageFileList = new ArrayList<>();

    Tournament tournament;
    //private final int stepNumber = 6;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @FXML
    public void setImages(MouseEvent event) throws MalformedURLException {
        imageFileList.add(setAddSponser(event));
    }

    //set Image files
    public String setAddSponser(MouseEvent event) throws MalformedURLException {
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
            return imageFile;
        }
        else {
            return null;
        }

    }

}
