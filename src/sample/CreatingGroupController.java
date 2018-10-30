package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.List;

import javafx.fxml.FXML;

public class CreatingGroupController {

    @FXML
    private ComboBox groupNrCombobax;

    public void initialize(){
        groupNrCombobax.setItems(groupNrList);
    }

    @FXML
    private ObservableList<String> groupNrList = FXCollections.observableArrayList(
            "1", "2","3","4","5","6"
    );

}
