package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import tournament.Team;
import tournament.Tournament;
import tournament.pool.Pool;
import tournament.pool.bracket.KnockoutBracket;

public class VerifyFinalStageController {

    Tournament tournament;

    @FXML
    GridPane poolKnockoutStatusGridPane;

    @FXML
    GridPane poolStatusGridPane;

    @FXML
    Text poolNameText;

    @FXML
    Text amountOfTeamsText;

    @FXML
    ComboBox amountOfGroupsComboBox;

    @FXML
    ComboBox matchesPrGroupsComboBox;

    String poolClicked;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        drawPoolKnockoutStatusGridPane();
    }

    @FXML
    private void mouseClicked(MouseEvent e) {
        for(Node node : poolKnockoutStatusGridPane.getChildren())
            node.setStyle("-fx-font-weight: normal;");
        Text poolClickedText = (Text) poolKnockoutStatusGridPane.getChildren().get((int) Math.floor(e.getY() / 36) * 3);

        poolClickedText.setStyle("-fx-font-weight: bold;");

        poolClicked = poolClickedText.getText();

        //drawPoolKnockoutStatusGridPane();
    }

    void drawPoolKnockoutStatusGridPane() {
        poolKnockoutStatusGridPane.getChildren().remove(0, poolKnockoutStatusGridPane.getChildren().size());
        for ( Pool pool : tournament.getPoolList() ) {
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
            GridPane.setMargin(poolName, new Insets(10,0,10,0));
            poolKnockoutStatusGridPane.addRow(poolKnockoutStatusGridPane.getRowCount(), poolName, knockoutType, status);
        }
        poolKnockoutStatusGridPane.setGridLinesVisible(false);
        poolKnockoutStatusGridPane.setGridLinesVisible(true);
    }


}
