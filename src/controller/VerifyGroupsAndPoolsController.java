package controller;

import exceptions.NotAllTeamsAreVerified;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import database.DAO.GroupBracketDAO;
import database.DAO.GroupDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import tournament.Team;
import tournament.Tournament;
import tournament.matchschedule.GraphicalObjects.ProgressBox;
import tournament.pool.Group;
import tournament.pool.Pool;

import java.io.IOException;

public class VerifyGroupsAndPoolsController {
    private Tournament tournament;
    private boolean isBeingEdited = false;
    private Text team1, team2;
    private final int stepNumber = 3;

    @FXML
    private VBox progressBox;

    @FXML
    private GridPane groupsGridPane;

    @FXML
    private TableView<Pool> poolTableView;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        progressBox.getChildren().add(new ProgressBox(stepNumber));
        setPoolTableView();
    }

    private void setPoolTableView() {
        TableColumn<Pool, String> poolNameColumn = new TableColumn<>("Puljenavn");
        poolNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        poolNameColumn.setMinWidth(128);
        poolNameColumn.setMaxWidth(128);

        TableColumn<Pool, String> poolStatusColumn = new TableColumn<>("Status");
        poolStatusColumn.setCellValueFactory(new PropertyValueFactory<>("groupsVerificationStatus"));
        poolStatusColumn.setMaxWidth(128);
        poolStatusColumn.setMinWidth(128);

        poolTableView.getColumns().addAll(poolNameColumn, poolStatusColumn);
        //add pools to tableView
        addPoolsInTableView();
    }

    private void addPoolsInTableView() {
        poolTableView.getItems().addAll(tournament.getPoolList());

        //handle row selection for each pool in tableView
        poolTableView.setRowFactory( table -> {
            TableRow<Pool> row = new TableRow<>();
            row.setOnMouseClicked(event -> handleRowSelection());
            return row;
        });
    }

    private void handleRowSelection() {
        drawGroupsGridPane();
    }

    @FXML
    void drawGroupsGridPane() {
        Pool selectedPool = poolTableView.getSelectionModel().getSelectedItem();

        groupsGridPane.getChildren().remove(0, groupsGridPane.getChildren().size());
        groupsGridPane.setVgap(30);
        groupsGridPane.setHgap(30);

        for (Group group : selectedPool.getGroupBracket().getGroups()) {
            GridPane gridPane = new GridPane();

            int groupNumber = Integer.parseInt(group.getName().substring(7, 8));
            Text groupNumberText = new Text(group.getName() + "  ");
            groupNumberText.setStyle("-fx-font-weight: bold;");
            gridPane.add(groupNumberText, 0,0);

            int rowCount = 1;
            for (Team team : group.getTeamList()) {
                gridPane.add(new Text("  " + team.getName() + "  "), 0, rowCount++);
            }

            gridPane.setGridLinesVisible(false);
            gridPane.setGridLinesVisible(true);

            groupsGridPane.add(gridPane, groupNumber % 4, (int)(Math.floor(groupNumber / 4)));
        }
    }

    @FXML
    private void verifyButtonPressed() {
        Pool selectedPool = poolTableView.getSelectionModel().getSelectedItem();
        if (selectedPool != null) {
            selectedPool.setGroupsVerificationStatus("Færdig");
        }

        poolTableView.getItems().clear();
        addPoolsInTableView();
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

    private void checkAllPoolsAreVerified() {
        for (Pool pool : poolTableView.getItems()) {
            if (pool.getGroupsVerificationStatus().equals("Ikke færdig")) {
                throw new NotAllTeamsAreVerified("grupperne");
            }
        }
    }

    @FXML
    public void nextButtonClicked(ActionEvent event) throws IOException {
        try {
            checkAllPoolsAreVerified();
            // DAO for group and groupbracket
            //GroupDAO groupSQL = new GroupDAO();
            //GroupBracketDAO groupBracketSQL = new GroupBracketDAO();

            // Inserting groups and groupbracketin database
            //groupSQL.insertGroup(tournament);
            //groupBracketSQL.insertGroupBracket(tournament);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/CreatingFinalStage.FXML"));
            Parent newWindow = loader.load();

            CreatingFinalStageController atc = loader.getController();
            atc.setTournament(tournament);

            Scene newScene = new Scene(newWindow);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(newScene);
            window.show();
        } catch (NotAllTeamsAreVerified e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, e.getMessage());
            warning.setHeaderText("Manglende godkendelse fejl");
            warning.setTitle("Fejl");
            warning.showAndWait();
        }
    }
}
