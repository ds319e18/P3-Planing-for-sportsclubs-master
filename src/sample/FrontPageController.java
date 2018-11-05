package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class FrontPageController {
    @FXML
    GridPane gp;

    @FXML
    Button loginBtn;

    @FXML
    public void initialize() {
        gp.setVgap(10);
        for (int i = 0; i < 10; i++) { // Iterates through a list of tournament-objects.
            Text txt = new Text("Tournament name " + i);
            Text status = new Text("ACTIVE");
            Text date = new Text("date: 26/2/2008\ndate: 27/2/2009");
            Button btn = new Button("View");
            gp.addRow(i, txt, status, date, btn);
        }
    }
}
