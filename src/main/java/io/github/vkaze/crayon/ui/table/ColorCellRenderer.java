package io.github.vkaze.crayon.ui.table;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.EmptyIcon;
import io.github.vkaze.crayon.storage.Crayon;
import org.jspecify.annotations.Nullable;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

public class ColorCellRenderer extends JLabel implements TableCellRenderer {
    @Nullable
    private JBColor iconColor;

    public ColorCellRenderer() {
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
