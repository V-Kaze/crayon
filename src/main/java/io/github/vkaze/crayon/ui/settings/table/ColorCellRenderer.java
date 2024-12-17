package io.github.vkaze.crayon.ui.settings.table;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.ui.EmptyIcon;
import io.github.vkaze.crayon.Crayon;

import javax.swing.table.DefaultTableCellRenderer;

public class ColorCellRenderer extends DefaultTableCellRenderer {
    private static final Logger log = Logger.getInstance(ColorCellRenderer.class);

    public ColorCellRenderer() {
        setIcon(EmptyIcon.ICON_16);
    }

    @Override
    protected void setValue(Object value) {
        if (value instanceof Crayon crayon) {
            setIcon(crayon.getColorIcon());
        } else {
            log.warn("Unexpected value: " + value);
        }
    }
}
