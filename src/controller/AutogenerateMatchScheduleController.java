package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class AutogenerateMatchScheduleController {
    @FXML
    GridPane matchScheduleGridPane;

    @FXML
    void initialize() {
        GridPane test = new GridPane();

        // --- Venstre
        VBox vBox1 = new VBox();
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
        matchScheduleGridPane.setMargin(hBox, new Insets(0, 0, 0, 30));
        matchScheduleGridPane.setGridLinesVisible(true);



    }


}
