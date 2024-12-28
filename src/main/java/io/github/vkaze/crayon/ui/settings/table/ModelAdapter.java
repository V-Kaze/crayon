package io.github.vkaze.crayon.ui.settings.table;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.ui.EditableModel;
import io.github.vkaze.crayon.Crayon;
import io.github.vkaze.crayon.MsgBundle;
import io.github.vkaze.crayon.storage.FileCrayonState;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ModelAdapter extends AbstractTableModel implements EditableModel {
    private static final Logger log = Logger.getInstance(ModelAdapter.class);
    private List<TableRow> rows;
    private List<String> removed;

    public ModelAdapter(FileCrayonState state) {
        reset(state);
    }

    @Override
    public String getColumnName(int column) {
        return column == ColumnConstants.PATH_COLUMN ? MsgBundle.message("plugin.crayon.table.path")
                : MsgBundle.message("plugin.crayon.table.crayon");
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TableRow tableRow = rows.get(rowIndex);
        return columnIndex == 0 ? tableRow.path() : tableRow.color();
    }

    @Override
    public void addRow() {
    }

    @Override
    public void removeRow(int index) {
        TableRow removedRow = rows.remove(index);
        removed.add(removedRow.path());
        fireTableRowsDeleted(index, index);
    }

    @Override
    public void exchangeRows(int oldIndex, int newIndex) {
    }

    @Override
    public boolean canExchangeRows(int oldIndex, int newIndex) {
        return false;
    }

    public List<String> getRemoved() {
        return removed;
    }

    public void reset(FileCrayonState state) {
        log.info("Resetting rows");
        rows = convertTableRows(state);
        removed = new ArrayList<>();
    }

    private List<TableRow> convertTableRows(FileCrayonState state) {
        List<TableRow> tableRows = new ArrayList<>(state.dirs.size() + state.files.size());
        for (Map.Entry<String, Crayon> entry : state.dirs.entrySet()) {
            tableRows.add(new TableRow(entry.getKey(), entry.getValue()));
        }
        for (Map.Entry<String, Crayon> entry : state.files.entrySet()) {
            tableRows.add(new TableRow(entry.getKey(), entry.getValue()));
        }
        Collections.sort(tableRows);
        return tableRows;
    }
}