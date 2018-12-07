package tournament.matchschedule.GraphicalObjects;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.function.Function;

public class DeleteButtonTableCell<E> extends TableCell<E, Button> {
    private final Button button;

    public DeleteButtonTableCell(Function <E, E> function) {
        button = new Button("Fjern");

        this.button.setOnAction( event -> {
            function.apply(getCurrentItem());
        });
        button.setMinWidth(100);
        button.setMaxWidth(100);
    }

    public E getCurrentItem() {
        return (E) this.getTableView().getItems().get(this.getIndex());
    }

    public static <E> Callback<TableColumn<E, Button>, TableCell<E, Button>> forTableColumn(Function <E, E> function) {
        return p -> new DeleteButtonTableCell<E>(function);
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
