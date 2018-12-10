package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ViewMatchScheduleController {

    Tournament tournament;

    @FXML
    private TabPane matchDayTabPane;

    void setTournament(Tournament tournament) {
        this.tournament = tournament;
        createMatchDayTabs();
        createMatchScheduleGridpane();
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

    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/FrontPage.FXML"));
        Parent newWindow = loader.load();

        FrontPageController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
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
    }

}
