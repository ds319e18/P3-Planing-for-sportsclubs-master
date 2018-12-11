package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tournament.Match;
import tournament.Tournament;
import tournament.matchschedule.Field;
import tournament.matchschedule.GraphicalObjects.MatchContainer;
import tournament.matchschedule.MatchDay;
import tournament.pool.Pool;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class ViewMatchScheduleController {

    private String imageFile = null;
    private String logo = null;
    private ImageView iw = null;

    Tournament tournament;

    @FXML
    private TabPane matchDayTabPane;

    @FXML
    private ImageView logo1 = null;

    @FXML
    private ImageView logo2 = null;

    @FXML
    private ImageView logo3 = null;

    @FXML
    private ImageView logo4 = null;


    void setTournament(Tournament tournament) {
        this.tournament = tournament;
        setImages();
        //createMatchDayTabs();
        //createMatchScheduleGridpane();
    }

    private void createMatchDayTabs() {
        for (MatchDay matchDay : tournament.getMatchSchedule().getMatchDays()) {
            matchDayTabPane.getTabs().add(new Tab(matchDay.getName()));
        }

        for (Tab tab : matchDayTabPane.getTabs()) {
            tab.setStyle("-fx-pref-width: " +
                    String.valueOf(matchDayTabPane.getPrefWidth()/tournament.getMatchSchedule().
                            getMatchDays().size()-10));
        }
    }

    private void createMatchScheduleGridpane() {
        GridPane matchDayGridPane;
        ScrollPane scrollPane;
        int matchCounter = 1;

        for (Tab tab : matchDayTabPane.getTabs()) {
            matchDayGridPane = new GridPane();
            scrollPane = new ScrollPane();
            // Text-objects containing field numbers are inserted at the top of the GridPane.
            for (int i = 0; i < tournament.getFieldList().size(); i++) {
                Text fieldText = new Text("Bane " + (i + 1));
                fieldText.setWrappingWidth(267);
                fieldText.setTextAlignment(TextAlignment.CENTER);
                fieldText.setStyle("-fx-font-style: BOLD;");
                fieldText.setFont(Font.font(15));

                matchDayGridPane.add(fieldText, i, 0);
            }

            List<Integer> indexList = new ArrayList<>();

            for (Field f : tournament.getFieldList())
                indexList.add(1);

            MatchDay matchDay = tournament.getMatchSchedule().findMatchDay(tab.getText());

            for (Match match : matchDay.getMatches()) {
                MatchContainer matchContainer = new MatchContainer(match);

                HBox matchHBox = createHBoxFromMatch(match, matchCounter);

                matchDayGridPane.add(matchHBox, Integer.parseInt(match.getField().getName().substring(5)) - 1
                        , indexList.get(Integer.parseInt(match.getField().getName().substring(5)) - 1));

                indexList.set(Integer.parseInt(match.getField().getName().substring(5)) - 1
                        , indexList.get(Integer.parseInt(match.getField().getName().substring(5)) - 1) + 1);
                matchCounter++;
            }

            scrollPane.setContent(matchDayGridPane);
            tab.setContent(scrollPane);
        }
    }

    private HBox createHBoxFromMatch(Match match, int matchCounter) {
        HBox returnHBox = new HBox();

        // --- Venstre
        VBox vBox1 = new VBox();
        vBox1.setStyle("-fx-border-color: BLACK;");

        Text matchNameText = new Text("Kamp " + matchCounter);
        Text timeIntervalText = new Text(match.getTimeStamp() + " - " + match.getTimeStamp().plusMinutes(match.getDuration()));
        matchNameText.setWrappingWidth(80);
        timeIntervalText.setWrappingWidth(80);
        matchNameText.setTextAlignment(TextAlignment.CENTER);
        timeIntervalText.setTextAlignment(TextAlignment.CENTER);

        vBox1.getChildren().addAll(matchNameText, timeIntervalText);

        // ---
        HBox hBox = new HBox();

        VBox vBox2 = new VBox();
        //vBox2.setStyle("-fx-border-color: BLACK;");

        Text poolText = new Text(match.getFirstTeam().getName());
        Text fieldText = new Text(match.getSecondTeam().getName());
        poolText.setWrappingWidth(107);
        fieldText.setWrappingWidth(107);
        poolText.setTextAlignment(TextAlignment.CENTER);
        fieldText.setTextAlignment(TextAlignment.CENTER);
        vBox2.getChildren().addAll(poolText, fieldText);

        VBox vBox3 = new VBox();
        //vBox3.setStyle("-fx-border-color: BLACK;");

        Text firstTeamText = new Text("U" + match.getFirstTeam().getYearGroup() + " - " + match.getFirstTeam().getSkillLevel());
        Text secondTeamText = new Text(match.getField().getName());
        firstTeamText.setWrappingWidth(80);
        secondTeamText.setWrappingWidth(80);
        firstTeamText.setTextAlignment(TextAlignment.CENTER);
        secondTeamText.setTextAlignment(TextAlignment.CENTER);
        vBox3.getChildren().addAll(firstTeamText, secondTeamText);

        hBox.getChildren().addAll(vBox2, vBox3);
        hBox.setStyle("-fx-border-color: BLACK;");

        returnHBox.getChildren().add(vBox1);
        returnHBox.getChildren().add(hBox);

        returnHBox.setMargin(hBox, new Insets(0, 30, 0, 0));

        return returnHBox;
    }

    //Error!
    @FXML
    public void setMenuButtonPressed(ActionEvent event) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource("../View/AdminPage.FXML"));
        //FrontPageController atc = loader.getController();
        //atc.setTournament(tournament);


        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    /*@FXML
    public void setAddSponser(MouseEvent event) throws MalformedURLException {
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
            ImageView iw = (ImageView) event.getSource();
            iw.setImage(im);
        }
    }*/
    /*
    //set Image files
    //Needs to be save in a list
    String imageFile;
    ArrayList<String> imageFileList = new ArrayList<>();
    @FXML
    public String setAddSponser(MouseEvent event) throws MalformedURLException {
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
    */

    public void setImages() {
        for(int i = 1; i < 5; i++) {
            AdminVievPageController logoSetter = new AdminVievPageController();
            if (i == 1) {
                logo = ("logo1");
                imageFile = logoSetter.getlogo1();
            } else if (i == 2){
                logo = ("logo2");
                imageFile = logoSetter.getlogo2();
            } else if (i == 3) {
                logo = ("logo3");
                imageFile = logoSetter.getlogo3();
            } else if (i == 4) {
                logo = ("logo4");
                imageFile = logoSetter.getlogo4();
            } else {
                imageFile =  null;
            }
            getAddSponser(logo, imageFile);
        }
    }

    //get Image files
    public void getAddSponser(String fxId, String selectedFile) {
        if (selectedFile != null) {
            Image im = new Image(selectedFile);
            if(fxId == ("logo1")){
                iw = (ImageView) logo1;
            }
            else if(fxId == ("logo2")){
                iw = (ImageView) logo2;
            }
            else if(fxId == ("logo3")){
                iw = (ImageView) logo3;
            }
            else if(fxId == ("logo4")){
                iw = (ImageView) logo4;
            }
            else{
                System.out.println("Error fx:id Not found");
            }
            iw.setImage(im);

        }
        else {
            System.out.println("Error logo not found");
        }
    }
}
