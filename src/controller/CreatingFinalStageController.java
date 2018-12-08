package controller;

import exceptions.IllegalAmountOfGroupsException;
import exceptions.IllegalAmountOfTeamsException;
import exceptions.MissingInputException;
import exceptions.MissingPressingSaveException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tournament.Tournament;
import tournament.matchschedule.GraphicalObjects.ProgressBox;
import tournament.pool.Group;
import tournament.pool.Pool;
import tournament.pool.bracket.GoldAndBronzePlay;
import tournament.pool.bracket.KnockoutPlay;
import tournament.pool.bracket.PlacementPlay;

import java.io.IOException;

public class CreatingFinalStageController implements CheckInput {


    Tournament tournament;
    String poolClicked;
    private final int stepNumber = 4;

    @FXML
    private VBox progressBox;

    @FXML
    GridPane poolStatusGridPane;

    @FXML
    GridPane groupsAndTeamsGridPane;

    @FXML
    Text poolName;

    @FXML
    Text amountOfGroups;

    @FXML
    RadioButton knockoutRadioButton;

    @FXML
    RadioButton placementRadioButton;

    @FXML
    RadioButton goldAndBronzeRadioButton;

    @FXML
    ComboBox advancingComboBox;



    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        setPoolStatusGridPane();
        progressBox.getChildren().add(new ProgressBox(stepNumber));
    }

    @Override
    public void checkAllInput() {
        if (advancingComboBox.getSelectionModel().isEmpty() || !(placementRadioButton.isSelected() ||
            knockoutRadioButton.isSelected() || goldAndBronzeRadioButton.isSelected())) {
            throw new MissingInputException();
        }
    }

    @FXML
    private void saveButton() {
        try {
            checkAllInput();
            int yearGroup = Integer.parseInt(poolClicked.length() == 3 ? poolClicked.substring(0, 2)
                    : poolClicked.substring(0, 1));
            String skillLevel = (poolClicked.length() == 3 ? poolClicked.substring(2, 3)
                    : poolClicked.substring(1, 2));

            // The amount of teams to advance on the the final stage is set
            tournament.findCorrectPool(yearGroup, skillLevel).getGroupBracket().setAdvancingTeamsPrGroup(Integer.parseInt(advancingComboBox.getValue().toString()));

            if (knockoutRadioButton.isSelected()) {
                tournament.findCorrectPool(yearGroup, skillLevel).addPlayoffBracket(new KnockoutPlay());
                System.out.println("if " + tournament.findCorrectPool(yearGroup, skillLevel).getPlayoffBracket().getMatches().size());

            } else if (placementRadioButton.isSelected()) {
                try {
                    tournament.findCorrectPool(yearGroup, skillLevel).addPlayoffBracket(new PlacementPlay());
                } catch (IllegalAmountOfGroupsException e) {
                    Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
                    warning.setHeaderText("Manglende input-fejl");
                    warning.setTitle("Fejl");
                    warning.showAndWait();
                } catch (IllegalAmountOfTeamsException e) {
                    Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
                    warning.setHeaderText("Manglende input-fejl");
                    warning.setTitle("Fejl");
                    warning.showAndWait();
                }
            } else if (goldAndBronzeRadioButton.isSelected()) {
                try {
                    tournament.findCorrectPool(yearGroup, skillLevel).addPlayoffBracket(new GoldAndBronzePlay());
                } catch (IllegalAmountOfGroupsException e) {
                    Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
                    warning.setHeaderText("Manglende input-fejl");
                    warning.setTitle("Fejl");
                    warning.showAndWait();
                } catch (IllegalAmountOfTeamsException e) {
                    Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
                    warning.setHeaderText("Manglende input-fejl");
                    warning.setTitle("Fejl");
                    warning.showAndWait();
                }
            }
            setPoolStatusGridPane();
        } catch (MissingInputException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input-fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }
    }


    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/VerifyGroupsAndPools.FXML"));
        Parent newWindow = loader.load();

        VerifyGroupsAndPoolsController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }



    @FXML
    public void nextButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/VerifyFinalStage.FXML"));
        Parent newWindow = loader.load();

        VerifyFinalStageController atc = loader.getController();
        try {
            atc.setTournament(tournament);
            Scene newScene = new Scene(newWindow);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(newScene);
            window.show();
        } catch (MissingPressingSaveException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende input-fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }

    }

    @FXML
    private void setPoolStatusGridPane() {
        poolStatusGridPane.getChildren().remove(0, poolStatusGridPane.getChildren().size());
        for(Pool pool : tournament.getPoolList() ) {
            Text text = new Text(pool.getYearGroup() + "" + pool.getSkillLevel());
            text.setWrappingWidth(80);
            text.setTextAlignment(TextAlignment.CENTER);
            boolean isDone = pool.getPlayoffBracket() != null
                    && pool.getGroupBracket().getAmountOfAdvancingTeamsPrGroup() > 0;
            Text status = (isDone ? new Text("Done") : new Text("Not done"));
            status.setWrappingWidth(80);
            status.setTextAlignment(TextAlignment.CENTER);
            GridPane.setMargin(text, new Insets(10,0,10,0));
            poolStatusGridPane.addRow(poolStatusGridPane.getRowCount(), text, status);
        }

        poolStatusGridPane.setGridLinesVisible(false);
        poolStatusGridPane.setGridLinesVisible(true);
    }

    @FXML
    void drawGroupsAndTeamsGridPane() {
        int i = 1 ;
        groupsAndTeamsGridPane.getChildren().remove(0, groupsAndTeamsGridPane.getChildren().size());
        try {
            int teamYearGroup = Integer.parseInt(poolClicked.length() == 3 ? poolClicked.substring(0, 2)
                    : poolClicked.substring(0, 1));
            String teamSkillLevel = (poolClicked.length() == 3 ? poolClicked.substring(2, 3)
                    : poolClicked.substring(1, 2));
            for (Group group : tournament.findCorrectPool(teamYearGroup, teamSkillLevel).getGroupBracket().getGroups()) {
                Text groupName = new Text("Group" + i );
                i++;
                Text teamsInGroup = new Text(Integer.toString(group.getTeamList().size()));
                groupsAndTeamsGridPane.addRow(groupsAndTeamsGridPane.getRowCount(), groupName,teamsInGroup);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        groupsAndTeamsGridPane.setGridLinesVisible(false);
        groupsAndTeamsGridPane.setGridLinesVisible(true);
    }

    @FXML
    private void mouseClicked(MouseEvent e) {
        for(Node node : poolStatusGridPane.getChildren())
            node.setStyle("-fx-font-weight: normal;");

        Text poolClickedText = (Text) poolStatusGridPane.getChildren().get((int) Math.floor(e.getY() / 36) * 2);

        poolClickedText.setStyle("-fx-font-weight: bold;");

        poolClicked = poolClickedText.getText();

        setComboBoxItemsAndLabels();
        drawGroupsAndTeamsGridPane();
    }

    private void setComboBoxItemsAndLabels() {
        String poolClickedText = poolClicked;
        // The year group and skill level of the chosen pool are described.
        String yearGroup = (poolClickedText.length() == 3 ? poolClickedText.substring(0, 2) : poolClickedText.substring(0, 1));
        String skillLevel = (poolClickedText.length() == 3 ? poolClickedText.substring(2, 3) : poolClickedText.substring(1, 2));
        // The amount of teams in the pool is displayed.
        amountOfGroups.setText(String.valueOf(tournament.findCorrectPool(Integer.parseInt(yearGroup), skillLevel).getGroupBracket().getGroups().size()));
        // The pool chosen is displayed.
        poolName.setText(poolClickedText);
        // The combobox for choosing the amount of groups
        ObservableList<String> amountOfGroups = FXCollections.observableArrayList("1", "2", "3");
        advancingComboBox.setItems(amountOfGroups);
    }

    private void highlightProgressBox() {
        VBox stepBox = (VBox) progressBox.getChildren().get(stepNumber);
        Text text1 = (Text) stepBox.getChildren().get(0);
        Text text2 = (Text) stepBox.getChildren().get(1);
        text1.setFill(Color.WHITE);
        text2.setFill(Color.WHITE);
        stepBox.setStyle("-fx-background-color: #6E83CA");
    }
}
