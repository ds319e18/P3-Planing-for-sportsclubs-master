package view.GraphicalObjects;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Function;

public class ActionButtonTableCell<E> extends TableCell<E, Button> {
    private final Button button;

    public ActionButtonTableCell(Function <E, E> function) {
        button = new Button("Fjern");

        this.button.setOnAction( event -> {
            function.apply(getCurrentItem());
        });
        button.setMinWidth(100);
        button.setMaxWidth(100);
    }

    public E getCurrentItem() {
        return this.getTableView().getItems().get(this.getIndex());
    }

    public static <E> Callback<TableColumn<E, Button>, TableCell<E, Button>> forTableColumn(Function <E, E> function) {
        return p -> new ActionButtonTableCell<E>(function);
    }

    @Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(button);
        }
    }
}
