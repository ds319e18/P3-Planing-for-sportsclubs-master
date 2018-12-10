package tournament.matchschedule.GraphicalObjects;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class
ProgressBox extends VBox {
    private ArrayList<VBox> vBoxList = new ArrayList<>();
    private final int stepNumber;

    public ProgressBox(int stepNumber) {
        this.stepNumber = stepNumber;

        vBoxList.add(new VBox(new Text("Turneringsopsætning")));
        vBoxList.add(new VBox(new Text("Tilføjelse af hold")));
        vBoxList.add(new VBox(new Text("Oprettelse af grupper")));
        vBoxList.add(new VBox(new Text("Godkendelse af grupper")));
        vBoxList.add(new VBox(new Text("Oprettelse af slutspil")));
        vBoxList.add(new VBox(new Text("Godkendelse af slutspil")));
        vBoxList.add(new VBox(new Text("Oprettelse af kampprogram")));
        vBoxList.add(new VBox(new Text("Godkendelse af kampprogram")));

        for (int i = 0; i < vBoxList.size(); i++) {
            setStyleOfBox(vBoxList.get(i), i + 1);
        }

        this.highlightProgressBox();
    }

    private void setStyleOfBox(VBox vBox, int stepNumber) {
        Text text1 = (Text) vBox.getChildren().get(0);
        text1.setFont(new Font(18));
        text1.setWrappingWidth(260);
        text1.setTextAlignment(TextAlignment.CENTER);

        Text text2 = new Text("trin " + stepNumber);
        text2.setFont(new Font(14));
        text2.setWrappingWidth(250);
        text2.setTextAlignment(TextAlignment.CENTER);
        vBox.getChildren().addAll(text2);

        vBox.setStyle("-fx-min-width: 260;"+"-fx-min-height: 58;"+ "-fx-border-width: 2;" + "-fx-border-color: white;" +
                "-fx-background-color: #DCDCDC;");

        this.getChildren().add(vBox);
    }

    private void highlightProgressBox() {
        VBox vBox = (VBox) this.getChildren().get(stepNumber);
        Text text1 = (Text) vBox.getChildren().get(0);
        Text text2 = (Text) vBox.getChildren().get(1);
        text1.setFill(Color.BLACK);
        text2.setFill(Color.BLACK);
        vBox.setStyle("-fx-min-width: 260;"+"-fx-min-height: 58;"+ "-fx-border-width: 2;" + "-fx-border-color: white;" +
                "-fx-background-color: #AFAFAF;");

    }
}
