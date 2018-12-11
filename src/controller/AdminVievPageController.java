package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import tournament.Tournament;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.control.Control;

public class AdminVievPageController {

    private String logo1 = null;
    private String logo2 = null;
    private String logo3 = null;
    private String logo4 = null;

    Tournament tournament;
    //private final int stepNumber = 6;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @FXML
    public void setImages(MouseEvent event) throws MalformedURLException {
        String tempLogo = ((Control)event.getSource()).getId();
        if(tempLogo == ("logo1")){
            logo1 = setAddSponser(event);
        }
        else if(tempLogo == ("logo2")){
            logo2 = setAddSponser(event);
        }
        else if(tempLogo == ("logo3")){
            logo3 = setAddSponser(event);
        }
        else if(tempLogo == ("logo4")){
            logo4 = setAddSponser(event);
        }
        else{
            System.out.println("Error " + tempLogo + " Not found");
        }
    }

    //set Image files
    public String setAddSponser(MouseEvent event) throws MalformedURLException {
        String imageFile = null;
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

    public String getlogo1(){
        return logo1;
    }
    public String getlogo2(){
        return logo2;
    }
    public String getlogo3(){
        return logo3;
    }
    public String getlogo4(){
        return logo4;
    }

}
