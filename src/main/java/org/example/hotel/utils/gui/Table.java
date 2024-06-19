package org.example.hotel.utils.gui;

import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Table {
    public static final int TABLE_WIDTH = 1100;
    public static final int TABLE_HEIGHT = 520;

    public static <T> int getColumnCount(Class<T> entity) {
        return entity.getDeclaredFields().length;
    }

    public static int calcCellWidth(int tableWidth, int columnCount) {
        return tableWidth / columnCount;
    }

    public static <T> TableColumn<T, Object> createColumn(String text, int cellWidth, Function<T, ?> value) {
        TableColumn<T, Object> tableColumn = new TableColumn<>(text);

        tableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(value.apply(cellData.getValue())));
        tableColumn.setPrefWidth(cellWidth);
        tableColumn.setResizable(false);

        return tableColumn;
    }

    public static <T> TableColumn<T, ?>[] createColumns(Map<String, Function<T, ?>> params, int cellWidth) {
        int size = params.size();
        TableColumn<T, ?>[] columns = new TableColumn[size];

        int i = 0;
        for (Map.Entry<String, Function<T, ?>> entry : params.entrySet()) {
            columns[i] = Table.createColumn(entry.getKey(), cellWidth, entry.getValue());
            i++;
        }

        return columns;
    }

    public static TableColumn<Object[], ?>[] createColumns(List<String> columnNames) {
        int size = columnNames.size();
        int cellWidth = Table.calcCellWidth(Table.TABLE_WIDTH, size);
        TableColumn<Object[], ?>[] columns = new TableColumn[size];

        for (int i = 0; i < size; i++) {
            int index = i;
            columns[i] = createColumn(columnNames.get(index), cellWidth, data -> data[index]);
        }

        return columns;
    }
}
