package io.github.vkaze.crayon.ui.settings.table;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.table.JBTable;
import io.github.vkaze.crayon.storage.FileCrayonState;
import io.github.vkaze.crayon.ui.settings.ColumnConstants;
import org.jspecify.annotations.NonNull;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.List;

public class FileCrayonTable extends JBTable {
    private static final Logger log = Logger.getInstance(FileCrayonTable.class);
    private final FileCrayonState state;

    public FileCrayonTable(@NonNull FileCrayonState state) {
        super(new ModelAdapter(state));
        this.state = state;

        setStriped(true);
        setAutoResizeMode(AUTO_RESIZE_LAST_COLUMN);

        TableColumnModel columnModel = getColumnModel();
        TableColumn nameColumn = columnModel.getColumn(ColumnConstants.PATH_COLUMN);
        nameColumn.setCellRenderer(new DefaultTableCellRenderer());
        TableColumn colorColumn = columnModel.getColumn(ColumnConstants.CRAYON_COLUMN);
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

    public void apply(List<String> removed) {
        state.removeFiles(removed);
    }
}
