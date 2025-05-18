package ReflectionUtils;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.lang.reflect.Field;
import java.util.List;


public class ReflectionUtils {

    /**
     * Incarca datele dintr-o lista generica intr-un TableView folosind reflecție
     *
     * @param table TableView-ul în care se incarca datele
     * @param data lista de obiecte
     * @param type tipul clasei obiectelor
     * @param <T> tipul generic
     */
    public static <T> void loadTable(TableView<T> table, List<T> data, Class<T> type) {
        int columnCount = table.getColumns().size();
        table.getColumns().clear();

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            TableColumn<T, Object> column = new TableColumn<>(field.getName());
            column.setCellValueFactory(cellData -> {
                try {
                    Object value = field.get(cellData.getValue());
                    return new ReadOnlyObjectWrapper<>(value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            });
            column.setPrefWidth((double) 700 /columnCount);
            table.getColumns().add(column);
        }

        table.setItems(FXCollections.observableList(data));
    }
}
