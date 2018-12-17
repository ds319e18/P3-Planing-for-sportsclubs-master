package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import tournament.Match;
import tournament.Result;
import tournament.Team;
import tournament.Tournament;
import tournament.matchschedule.Field;
import tournament.matchschedule.MatchDay;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalTime;

public class UpdateTournamentController {

    @FXML
    private ImageView logo01;
    @FXML
    private ImageView logo02;
    @FXML
    private ImageView logo03;
    @FXML
    private ImageView logo04;

    private String logo1 = null;
    private String logo2 = null;
    private String logo3 = null;
    private String logo4 = null;

    @FXML
    private Label tournamentNameLabel;

    @FXML
    private Label matchDayDateLabel;

    @FXML
    private TableView<Match> matchTableView;

    private MatchDay matchDay;
    private Tournament tournament;

    void setMatchDay(MatchDay matchDay, Tournament tournament) {
        this.matchDay = matchDay;
        this.tournament = tournament;
        tournamentNameLabel.setText(tournament.getName());
        matchDayDateLabel.setText(matchDay.getName());
        setMatchTableView();
        addMatchesInTable();
    }

    private void setMatchTableView() {
        matchTableView.setEditable(true);

        TableColumn<Match, String> matchNameColumn = new TableColumn<>("Kamp");
        matchNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Match, LocalTime> timeColumn = new TableColumn<>("Tid");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        TableColumn<Match, Team> firstTeamColumn = new TableColumn<>("Hjemmehold");
        firstTeamColumn.setCellValueFactory(new PropertyValueFactory<>("firstTeam"));

        TableColumn<Match, Team> secondTeamColumn = new TableColumn<>("Udehold");
        secondTeamColumn.setCellValueFactory(new PropertyValueFactory<>("secondTeam"));

        TableColumn<Match, Result> resultColumn = new TableColumn<>("Resultat");
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        resultColumn.setStyle("-fx-alignment: CENTER");
        resultColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Result object) {
                return object.toString();
            }

            @Override
            public Result fromString(String string) {
                // split string at -
                String[] resultString = string.split("-");
                return new Result(Integer.parseInt(resultString[0]), Integer.parseInt(resultString[1]));
            }
        }));

        resultColumn.setOnEditCommit(event -> changeResultCell(event));

        TableColumn<Match, Field> fieldColumn = new TableColumn<>("Bane");
        fieldColumn.setCellValueFactory(new PropertyValueFactory<>("field"));

        setWidthOfColumn(matchNameColumn);
        setWidthOfColumn(timeColumn);
        setWidthOfColumn(firstTeamColumn);
        setWidthOfColumn(secondTeamColumn);
        setWidthOfColumn(resultColumn);
        setWidthOfColumn(fieldColumn);

        matchTableView.getColumns().addAll(matchNameColumn, timeColumn, firstTeamColumn, secondTeamColumn, resultColumn, fieldColumn);
    }

    private void setWidthOfColumn(TableColumn tableColumn) {
        tableColumn.setMinWidth(132);
        tableColumn.setMaxWidth(132);
    }

    private void addMatchesInTable() {
        matchTableView.getItems().addAll(matchDay.getMatches());
    }

    @FXML
    private void changeResultCell(TableColumn.CellEditEvent editEvent) {
        Match matchSelected = matchTableView.getSelectionModel().getSelectedItem();
        String[] resultString = editEvent.getNewValue().toString().split("-");
        matchSelected.getResult().setFirstTeamScore(Integer.parseInt(resultString[0]));
        matchSelected.getResult().setSecondTeamScore(Integer.parseInt(resultString[1]));
    }

    @FXML
    public void setBackButtonPressed(ActionEvent event) throws IOException{
        Parent newWindow = FXMLLoader.load(getClass().getResource("../view/AdminPage.FXML"));

        Scene newScene = new Scene(newWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newScene);
        window.show();
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
