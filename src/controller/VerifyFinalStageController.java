package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tournament.Team;
import tournament.Tournament;
import tournament.pool.Pool;
import tournament.pool.bracket.GroupBracket;
import tournament.pool.bracket.KnockoutBracket;

import java.io.IOException;

public class VerifyFinalStageController {

    Tournament tournament;

    @FXML
    GridPane poolKnockoutStatusGridPane;

    @FXML
    GridPane finalStageGridPane;

    String poolClicked;



    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        drawPoolKnockoutStatusGridPane();
    }

    @FXML
    private void mouseClicked(MouseEvent e) {
        for (Node node : poolKnockoutStatusGridPane.getChildren())
            node.setStyle("-fx-font-weight: normal;");
        Text poolClickedText = (Text) poolKnockoutStatusGridPane.getChildren().get((int) Math.floor(e.getY() / 36) * 3);

        poolClickedText.setStyle("-fx-font-weight: bold;");

        poolClicked = poolClickedText.getText();

        drawFinalStageGridPane();
    }

    void drawPoolKnockoutStatusGridPane() {
        poolKnockoutStatusGridPane.getChildren().remove(0, poolKnockoutStatusGridPane.getChildren().size());
        for (Pool pool : tournament.getPoolList()) {
            Text poolName = new Text(pool.getYearGroup() + pool.getSkillLevel());
            poolName.setTextAlignment(TextAlignment.CENTER);
            poolName.setWrappingWidth(82.93);

            Text knockoutType = new Text(pool.getKnockoutBracket().getClass().getSimpleName());
            knockoutType = new Text(knockoutType.getText().substring(0, knockoutType.getText().length() - 4));
            knockoutType.setTextAlignment(TextAlignment.CENTER);
            knockoutType.setWrappingWidth(82.93);

            Text status = new Text("Not done");
            status.setWrappingWidth(82.93);
            status.setTextAlignment(TextAlignment.CENTER);
            GridPane.setMargin(poolName, new Insets(10, 0, 10, 0));
            poolKnockoutStatusGridPane.addRow(poolKnockoutStatusGridPane.getRowCount(), poolName, knockoutType, status);
        }
        poolKnockoutStatusGridPane.setGridLinesVisible(false);
        poolKnockoutStatusGridPane.setGridLinesVisible(true);
        System.out.println(poolKnockoutStatusGridPane.getChildren());

    }

    @FXML
    void drawFinalStageGridPane() {
        int poolYearGroup = Integer.parseInt(poolClicked.length() == 3 ? poolClicked.substring(0, 2)
                : poolClicked.substring(0, 1));
        String poolSkillLevel = (poolClicked.length() == 3 ? poolClicked.substring(2, 3)
                : poolClicked.substring(1, 2));

        int amountOfMatches = tournament.findCorrectPool(poolYearGroup, poolSkillLevel).getKnockoutBracket().getMatches().size();
        KnockoutBracket knockoutBracket = tournament.findCorrectPool(poolYearGroup, poolSkillLevel).getKnockoutBracket();
        finalStageGridPane.getChildren().remove(0, finalStageGridPane.getChildren().size());
        finalStageGridPane.setVgap(30);
        finalStageGridPane.setHgap(30);

        int iter = amountOfMatches - 1;
        int columnCount = 0;
        if (iter > 2) {
            int rowCount = 1;
            for (iter = iter; iter >= 3; iter--) {
                Team team1 = knockoutBracket.getMatches().get(iter).getFirstTeam();
                Team team2 = knockoutBracket.getMatches().get(iter).getSecondTeam();
                GridPane gridPane = new GridPane();
                Text groupNumberText = new Text("  Kvartfinale " + iter + "  ");
                groupNumberText.setStyle("-fx-font-weight: bold;");
                gridPane.add(groupNumberText, 0, 0);
                gridPane.add(new Text("  " + team1 + "  "), 1, 0);
                gridPane.add(new Text("  " + team2 + "  "), 1, 1);

                finalStageGridPane.add(gridPane, columnCount, rowCount++);
            }
            columnCount++;
        }
        if (3 > iter && iter > 0) {
            int rowCount = 1;
            for (iter = iter; iter >= 1; iter--) {
                Team team1 = knockoutBracket.getMatches().get(iter).getFirstTeam();
                Team team2 = knockoutBracket.getMatches().get(iter).getSecondTeam();
                GridPane gridPane = new GridPane();
                Text groupNumberText = new Text(" Semifinale " + iter + "  ");
                groupNumberText.setStyle("-fx-font-weight: bold;");
                gridPane.add(groupNumberText, 0, 0);
                gridPane.add(new Text("  " + team1 + "  "), 1, 0);
                gridPane.add(new Text("  " + team2 + "  "), 1, 1);

                finalStageGridPane.add(gridPane, columnCount, rowCount++);
            }
            columnCount++;

        }
        if (iter == 0) {
            int rowCount = 1;
            Team team1 = knockoutBracket.getMatches().get(0).getFirstTeam();
            Team team2 = knockoutBracket.getMatches().get(0).getSecondTeam();
            GridPane gridPane = new GridPane();
            Text groupNumberText = new Text("  Finale "  + "  ");
            groupNumberText.setStyle("-fx-font-weight: bold;");
            gridPane.add(groupNumberText, 0, 0);
            gridPane.add(new Text("  " + team1 + "  "), 1, 0);
            gridPane.add(new Text("  " + team2 + "  "), 1, 1);

            finalStageGridPane.add(gridPane, columnCount, rowCount++);
        }
        for (int i = amountOfMatches - 1; i >= 0; i--) {

        }
        finalStageGridPane.setGridLinesVisible(false);
        finalStageGridPane.setGridLinesVisible(true);
    }

    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException {
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

    @FXML
    private void verifyButton() {
        for (int i = 0; i < poolKnockoutStatusGridPane.getChildren().size(); i++) {
            Text text = (Text) poolKnockoutStatusGridPane.getChildren().get(i);

            if (text.getText().equals(poolClicked)) {
                Text status = (Text) poolKnockoutStatusGridPane.getChildren().get(i + 2);
                status.setText("Done");
                break;
            }
        }
    }

    @FXML
    public void nextButtonClicked(ActionEvent event) throws IOException {
        boolean value = true;
        Text temp=new Text();
        for (int i = 0; i < poolKnockoutStatusGridPane.getRowCount(); i++) {
            temp = (Text) poolKnockoutStatusGridPane.getChildren().get(i*3 +2);
            if (!temp.getText().equals("Done")) {
                value = false;
            }

        }

        if (value) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/MatchScheduleSetup.FXML"));
            Parent newWindow = loader.load();

            MatchScheduleSetupController mss = loader.getController();
            mss.setTournament(tournament);

            Scene newScene = new Scene(newWindow);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(newScene);
            window.show();
        }

    }

}
