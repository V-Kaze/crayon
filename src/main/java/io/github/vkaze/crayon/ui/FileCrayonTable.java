package io.github.vkaze.crayon.ui;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.JBColor;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.EditableModel;
import com.intellij.util.ui.EmptyIcon;
import io.github.vkaze.crayon.MsgBundle;
import io.github.vkaze.crayon.storage.Crayon;
import io.github.vkaze.crayon.storage.FileCrayonState;
import io.github.vkaze.crayon.storage.TableRow;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import static com.michaelbaranov.microba.gradienteditor.DefaultGradientEditorModel.COLOR_COLUMN;

public abstract class FileCrayonTable extends JBTable {
    private static final Logger log = Logger.getInstance(FileCrayonTable.class);
    private static final int PATH_COLUMN = 0;
    private static final int CRAYON_COLUMN = 1;
    private final FileCrayonState state;

    public FileCrayonTable(@NonNull FileCrayonState state) {
        super(new ModelAdapter(state));
        this.state = state;

        setStriped(true);
        setAutoResizeMode(AUTO_RESIZE_LAST_COLUMN);

        TableColumnModel columnModel = getColumnModel();
        TableColumn nameColumn = columnModel.getColumn(PATH_COLUMN);
        nameColumn.setCellRenderer(new DefaultTableCellRenderer());
        TableColumn colorColumn = columnModel.getColumn(COLOR_COLUMN);
        colorColumn.setCellRenderer(new ColorCellRenderer());
    }

    @Override
    public ModelAdapter getModel() {
        return (ModelAdapter) super.getModel();
    }

    public boolean isModified() {
        log.info("Checking if the data was modified");
        return !getModel().getRemoved().isEmpty();
    }

    public void apply() {
        if (isModified()) {
            log.info("Applying changes");
            apply(getModel().getRemoved());
            reset();
        }
    }

    public void reset() {
        if (isModified()) {
            log.info("Resetting changes");
            getModel().reset(state);
        }
    }

    protected abstract void apply(final List<String> removed);

    public static class ModelAdapter extends AbstractTableModel implements EditableModel {
        private List<TableRow> rows;
        private List<String> removed;

        private ModelAdapter(FileCrayonState state) {
            reset(state);
        }

        @Override
        public String getColumnName(int column) {
            return column == PATH_COLUMN ? MsgBundle.message("plugin.crayon.table.path")
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

    private static class ColorCellRenderer extends JLabel implements TableCellRenderer {
        @Nullable
        private JBColor iconColor;

        private ColorCellRenderer() {
            setOpaque(true);
            setIcon(EmptyIcon.ICON_16);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Crayon crayon) {
                iconColor = crayon.getColor();
                setText(crayon.getColorName());
            }
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (iconColor != null) {
                final Icon icon = getIcon();
                final int width = icon.getIconWidth();
                final int height = icon.getIconHeight();

                final Color old = g.getColor();

                g.setColor(iconColor);
                g.fillRect(0, 0, width, height);

                g.setColor(old);
            }
        }
    }
}
