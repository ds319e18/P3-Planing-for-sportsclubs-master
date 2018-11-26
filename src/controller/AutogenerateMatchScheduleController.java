package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import tournament.Match;
import tournament.Tournament;
import tournament.matchschedule.MatchDay;
import tournament.pool.Pool;

public class AutogenerateMatchScheduleController {
    Tournament tournament;

    @FXML
    ScrollPane matchScheduleScrollPane;

    @FXML
    ComboBox comboBox;

    GridPane matchScheduleGridPane;

    void setTournament(Tournament tournament) {
        this.tournament = tournament;
        initilalize();
    }

    @FXML
    void drawMatchScheduleGridPane() {
        matchScheduleGridPane = new GridPane();
        matchScheduleGridPane.setMaxWidth(tournament.getFieldList().size() * 300);
        matchScheduleGridPane.setPrefWidth(tournament.getFieldList().size() * 300);
        matchScheduleGridPane.setMinWidth(tournament.getFieldList().size() * 300);

        MatchDay matchDay = tournament.getMatchSchedule().getMatchDays().get(Integer.parseInt(comboBox.getValue().toString()));
        System.out.println(matchDay.toString()); //TODO FJERN

        for (int i = 0; i < tournament.getFieldList().size(); i++) {
            matchScheduleGridPane.add(new Text("Bane " + (i + 1)), i, 0);
        }

        for (Match match : matchDay.getMatches()) {
            System.out.println(match.getFirstTeam() + " " + match.getSecondTeam() + " " + match.getDuration());//TODO FJERN



            matchScheduleGridPane.add(new Text(""), Integer.parseInt(match.getField().getName().substring(5)), 0);
        }


        matchScheduleScrollPane.setContent(matchScheduleGridPane);
        matchScheduleGridPane.setVisible(true);
    }

    @FXML
    void initilalize() {
        ObservableList observableList = FXCollections.observableArrayList();
        System.out.println(tournament.getMatchSchedule().getMatchDays().size());
        for (int i = 0; i < tournament.getMatchSchedule().getMatchDays().size(); i++)
            observableList.add("" + i);
        comboBox.setItems(observableList);


        GridPane test = new GridPane();

        // --- Venstre
        /*VBox vBox1 = new VBox();
        vBox1.setStyle("-fx-border-color: BLACK;");

        Text text11 = new Text("Kamp 1");
        Text text12 = new Text("12:00 - 12:20");
        text11.setWrappingWidth(80);
        text12.setWrappingWidth(80);
        text11.setTextAlignment(TextAlignment.CENTER);
        text12.setTextAlignment(TextAlignment.CENTER);

        vBox1.getChildren().addAll(text11, text12);

        // ---
        HBox hBox = new HBox();

        VBox vBox2 = new VBox();
        //vBox2.setStyle("-fx-border-color: RED;");

        Text text21 = new Text("U9 - C");
        Text text22 = new Text("Field 6");
        text21.setWrappingWidth(80);
        text22.setWrappingWidth(80);
        text21.setTextAlignment(TextAlignment.CENTER);
        text22.setTextAlignment(TextAlignment.CENTER);
        vBox2.getChildren().addAll(text21, text22);

        VBox vBox3 = new VBox();
        //vBox3.setStyle("-fx-border-color: BLUE;");

        Text text31 = new Text("Jetsmark IF 5 -");
        Text text32 = new Text("Jetsmark IF 6");
        text31.setWrappingWidth(107);
        text32.setWrappingWidth(107);
        text31.setTextAlignment(TextAlignment.CENTER);
        text32.setTextAlignment(TextAlignment.CENTER);
        vBox3.getChildren().addAll(text31, text32);

        hBox.getChildren().addAll(vBox2, vBox3);
        hBox.setStyle("-fx-border-color: BLACK;");

        test.add(vBox1, 0, 0);
        test.add(hBox, 1, 0);

        matchScheduleGridPane.add(test, 0, 0);
        matchScheduleGridPane.setMargin(hBox, new Insets(0, 0, 0, 30));*/
    }


}
