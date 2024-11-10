package io.github.vkaze.crayon.ui.table;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.ui.EditableModel;
import io.github.vkaze.crayon.MsgBundle;
import io.github.vkaze.crayon.storage.FileCrayonState;
import io.github.vkaze.crayon.storage.TableRow;
import io.github.vkaze.crayon.ui.ColumnConstants;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

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
        rows = state.toTableRows();
        removed = new ArrayList<>();
    }
}