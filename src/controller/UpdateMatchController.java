package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.net.MalformedURLException;
import java.nio.file.LinkOption;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UpdateMatchController {
    // Liste af Booleans. Hver pool har en Boolean, der beskriver om alle gruppekampene er spillet, og knockout fasen er begyndt.
    private ArrayList<Boolean> knockoutPhaseStarted = new ArrayList<>();

    Tournament tournament;

    MatchContainer selectedMatchContainer = new MatchContainer(LocalTime.now());

    @FXML
    private VBox progressBox;

    @FXML
    private TabPane matchDayTabPane;

    @FXML
    private Text firstTeamText;

    @FXML
    private Text secondTeamText;

    @FXML
    private TextField firstTeamResultTextField;

    @FXML
    private TextField secondTeamResultTextField;

    @FXML
    private ImageView logo;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        createMatchDayTabs();
        createMatchScheduleGridpanes();
        for (Pool pool : tournament.getPoolList()) {
            knockoutPhaseStarted.add(false);
        }
    }

    private void createMatchDayTabs() {
        for (MatchDay matchDay : tournament.getMatchSchedule().getMatchDays()) {
            matchDayTabPane.getTabs().add(new Tab(matchDay.getName()));
        }

        for (Tab tab : matchDayTabPane.getTabs()) {
            tab.setStyle("-fx-pref-width: " +
                    String.valueOf(matchDayTabPane.getPrefWidth() / tournament.getMatchSchedule()
                            .getMatchDays().size() - 10));
        }
    }

    private void createMatchScheduleGridpanes() {
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
                MatchContainer matchContainer = new MatchContainer(match, true);
                matchContainer.setOnMouseClicked(event -> handleMatchContainerSelection(event));

                matchDayGridPane.add(matchContainer, Integer.parseInt(match.getField().getName().substring(5)) - 1
                        , indexList.get(Integer.parseInt(match.getField().getName().substring(5)) - 1));

                indexList.set(Integer.parseInt(match.getField().getName().substring(5)) - 1
                        , indexList.get(Integer.parseInt(match.getField().getName().substring(5)) - 1) + 1);
                matchCounter++;
            }

            scrollPane.setContent(matchDayGridPane);
            tab.setContent(scrollPane);
        }
    }

    private void handleMatchContainerSelection(MouseEvent event) {
        selectedMatchContainer.setSelected(false);

        selectedMatchContainer = (MatchContainer) event.getSource();

        selectedMatchContainer.setSelected(true);


    }

    @FXML
    private void updateButton() {
        if(!firstTeamResultTextField.getText().isEmpty() &&
                !secondTeamResultTextField.getText().isEmpty()) {

            Result result = new Result(
                    Integer.parseInt(firstTeamResultTextField.getText()),
                    Integer.parseInt(secondTeamResultTextField.getText()));

            selectedMatchContainer.getMatch().setResult(result);

            for (Match match : tournament.getAllMatches()) {
                if (match.equals(selectedMatchContainer.getMatch())) {
                    match.setResult(result);
                }
            }

            // TODO: Kan kun kaldes ved ændring af resultat på gruppe kamp.
            advanceTeamsBlahBlah(selectedMatchContainer.getMatch().getFirstTeam().getYearGroup(),
                    selectedMatchContainer.getMatch().getFirstTeam().getSkillLevel());

            updateGroupMatchContainer(selectedMatchContainer);
        }
    }

    private void advanceTeamsBlahBlah(int yearGroup, String skillLevel) {
        boolean allGroupMatchesHaveResults = true;
        boolean allKnouckoutMatchesHaveResults = true;

        int i;
        for (i = 0; i < tournament.getPoolList().size(); i++) {
            if (tournament.getPoolList().get(i).getYearGroup() == yearGroup &&
                    tournament.getPoolList().get(i).getSkillLevel().equals(skillLevel)) {
                break;
            }
        }

        if (!knockoutPhaseStarted.get(i)) {
            for (Match match : tournament.findCorrectPool(yearGroup, skillLevel).getGroupBracket().getMatches()) {
                if (!match.isFinished()) {
                    allGroupMatchesHaveResults = false;
                }
            }

            // TODO: KUN EN GANG - should work
            if (allGroupMatchesHaveResults) {
                tournament.findCorrectPool(yearGroup, skillLevel).getKnockoutBracket()
                        .createNextRound(tournament.findCorrectPool(yearGroup, skillLevel).getGroupBracket().advanceTeams());
                knockoutPhaseStarted.add(i, true);
                createMatchScheduleGridpanes();
            }

        } else {

            for (Match match : tournament.findCorrectPool(yearGroup, skillLevel).getKnockoutBracket().getMatches()) {
                if (!match.isFinished() && (!match.getFirstTeam().getName().equals("TBD") &&
                        !match.getSecondTeam().getName().equals("TBD"))) {
                    allKnouckoutMatchesHaveResults = false;
                }
            }


            if (allKnouckoutMatchesHaveResults) {
                if (tournament.findCorrectPool(yearGroup, skillLevel).getKnockoutBracket().getClass().getSimpleName()
                        .equals("KnockoutPlay")) {
                    tournament.findCorrectPool(yearGroup, skillLevel).getKnockoutBracket()
                            .createNextRound(tournament.findCorrectPool(yearGroup, skillLevel).getKnockoutBracket().advanceTeams());
                } else if (tournament.findCorrectPool(yearGroup, skillLevel).getKnockoutBracket().getClass().getSimpleName()
                        .equals("PlacementPlay")) {
                    tournament.findCorrectPool(yearGroup, skillLevel).getKnockoutBracket().calculateResults();
                } else if (tournament.findCorrectPool(yearGroup, skillLevel).getKnockoutBracket().getClass().getSimpleName()
                        .equals("GoldAndBronzePlay")) {
                    tournament.findCorrectPool(yearGroup, skillLevel).getKnockoutBracket().calculateResults();
                }
                createMatchScheduleGridpanes();
            }

        }
    }

    private void updateGroupMatchContainer(MatchContainer matchContainer) {
        GridPane gridPane = (GridPane) matchContainer.getParent();

        MatchContainer newMatchContainer = new MatchContainer(matchContainer.getMatch(),
                true);
        newMatchContainer.setOnMouseClicked(event -> handleMatchContainerSelection(event));

        // TODO: What the fuck is going on? Crashes with one remove, works with two :-) Magic
        try {
            gridPane.getChildren().remove(matchContainer);
        } catch (IllegalArgumentException e) {
            System.out.println("TODO: What the fuck is going on? Crashes with one remove, works with two :-) Magic");
        }

        gridPane.getChildren().remove(matchContainer);

        gridPane.add(newMatchContainer, GridPane.getColumnIndex(matchContainer),
                GridPane.getRowIndex(matchContainer));
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
            logo.setImage(im);
        }

    }



}
