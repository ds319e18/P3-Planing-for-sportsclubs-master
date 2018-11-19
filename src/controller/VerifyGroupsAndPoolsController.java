package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import tournament.Team;
import tournament.Tournament;
import tournament.pool.Group;
import tournament.pool.Pool;

public class VerifyGroupsAndPoolsController {
    Tournament tournament;

    @FXML
    GridPane poolStatusGridPane;

    @FXML
    GridPane groupsGridPane;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        System.out.println(tournament.getPoolList().get(0).getGroupBracket().getAmountOfGroups());
        for (Group group : tournament.getPoolList().get(0).getGroupBracket().getGroups())
            System.out.println(group.getTeamList());
        setPoolStatusGridPane();
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

        Text poolClicked = (Text) poolStatusGridPane.getChildren().get((int) Math.floor(e.getY() / 36) * 2 + 1);
        poolClicked.setStyle("-fx-font-weight: bold;");

        drawGridPane(poolClicked.getText());
    }

    @FXML
    void drawGridPane(String poolClicked) {
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
            Text groupNumberText = new Text("Group" + i);
            groupNumberText.setStyle("-fx-font-weight: bold;");
            gridPane.add(groupNumberText, 0,0);

            int rowCount = 1;
            for (Team team : group.getTeamList()) {
                gridPane.add(new Text(team.getName()), 0, rowCount++);
            }

            gridPane.setGridLinesVisible(false);
            gridPane.setGridLinesVisible(true);

            groupsGridPane.addColumn(groupsGridPane.getColumnCount(), gridPane);
        }
    }
}
