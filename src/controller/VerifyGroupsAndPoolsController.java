package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tournament.Team;
import tournament.Tournament;
import tournament.pool.Group;
import tournament.pool.Pool;

import java.io.IOException;

public class VerifyGroupsAndPoolsController {
    Tournament tournament;
    boolean isBeingEdited = false;
    Text team1, team2;
    String poolClicked;
    private final int stepNumber = 3;

    @FXML
    private VBox progressBox;

    @FXML
    GridPane poolStatusGridPane;

    @FXML
    GridPane groupsGridPane;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        setPoolStatusGridPane();
        highlightProgressBox();
    }

    private void setPoolStatusGridPane() {
        for(Pool pool : tournament.getPoolList() ) {
            Text text = new Text(pool.getYearGroup() + "" + pool.getSkillLevel());
            text.setWrappingWidth(80);
            text.setTextAlignment(TextAlignment.CENTER);
            Text status = new Text("Not done");
            status.setWrappingWidth(80);
            status.setTextAlignment(TextAlignment.CENTER);
            GridPane.setMargin(text, new Insets(10,0,10,0));
            poolStatusGridPane.addRow(poolStatusGridPane.getRowCount(), text, status);
        }
    }

    @FXML
    private void mouseClicked(MouseEvent e) {
        for(Node node : poolStatusGridPane.getChildren())
            node.setStyle("-fx-font-weight: normal;");

        Text poolClickedText = (Text) poolStatusGridPane.getChildren().get((int) Math.floor(e.getY() / 36) * 2 + 1);

        poolClickedText.setStyle("-fx-font-weight: bold;");

        poolClicked = poolClickedText.getText();

        drawGroupsGridPane();
    }

    @FXML
    void drawGroupsGridPane() {
        int teamYearGroup = Integer.parseInt(poolClicked.length() == 3 ? poolClicked.substring(0, 2)
                : poolClicked.substring(0, 1));
        String teamSkillLevel = (poolClicked.length() == 3 ? poolClicked.substring(2, 3)
                : poolClicked.substring(1, 2));
        int amountOfGroups = tournament.findCorrectPool(teamYearGroup, teamSkillLevel).getGroupBracket().getAmountOfGroups();

        groupsGridPane.getChildren().remove(0, groupsGridPane.getChildren().size());
        groupsGridPane.setVgap(30);
        groupsGridPane.setHgap(30);
        for (int i = 0; i < amountOfGroups; i++) {

            Group group = tournament.findCorrectPool(teamYearGroup, teamSkillLevel).getGroupBracket().getGroups().get(i);


            GridPane gridPane = new GridPane();
            Text groupNumberText = new Text("  Gruppe " + (i + 1) + "  ");
            groupNumberText.setStyle("-fx-font-weight: bold;");
            gridPane.add(groupNumberText, 0,0);

            int rowCount = 1;
            for (Team team : group.getTeamList()) {
                gridPane.add(new Text("  " + team.getName() + "  "), 0, rowCount++);
            }

            gridPane.setGridLinesVisible(false);
            gridPane.setGridLinesVisible(true);

            groupsGridPane.add(gridPane, i % 4, (int)(Math.floor(i / 4)));

        }
    }

    @FXML
    private void verifyButton() {
        for (int i = 1; i < poolStatusGridPane.getChildren().size(); i++) {
            Text text = (Text) poolStatusGridPane.getChildren().get(i);

            if (text.getText().equals(poolClicked)) {
                Text status = (Text) poolStatusGridPane.getChildren().get(i + 1);
                status.setText("Done");
                break;
            }
        }
    }

    @FXML
    void setEditButton() {
        isBeingEdited = true;
    }

     @FXML
     void mouseClickedEdit(MouseEvent e) {
        if (isBeingEdited) {
            if (team1 == null) {
                team1 = (Text) groupsGridPane.getChildren().get((int) Math.floor(e.getY() / 36) * 2 + 1);

                team1.setStyle("-fx-font-weight: bold;");
            } else {
                team2 = (Text) groupsGridPane.getChildren().get((int) Math.floor(e.getY() / 36) * 2 + 1);

                team1.setStyle("-fx-font-weight: normal;");
            }
            drawGroupsGridPane();
        }
    }

    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/CreatingGroups.FXML"));
        Parent newWindow = loader.load();

        CreatingGroupController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void nextButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/CreatingFinalStage.FXML"));
        Parent newWindow = loader.load();

        CreatingFinalStageController atc = loader.getController();
        atc.setTournament(tournament);

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
    }

    private void highlightProgressBox() {
        VBox stepBox = (VBox) progressBox.getChildren().get(stepNumber);
        stepBox.setStyle("-fx-border-color: #0000CD");
        stepBox.setStyle("-fx-background-color: #A9A9A9");
    }
}
