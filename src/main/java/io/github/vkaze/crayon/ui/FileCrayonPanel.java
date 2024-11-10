package io.github.vkaze.crayon.ui;

import com.intellij.ide.ui.UISettings;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.ToolbarDecorator;
import io.github.vkaze.crayon.MsgBundle;
import io.github.vkaze.crayon.storage.FileCrayonState;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class FileCrayonPanel extends JPanel {
    private static final Logger log = Logger.getInstance(FileCrayonPanel.class);
    private final FileCrayonTable crayonTable;

    public FileCrayonPanel(FileCrayonState crayonState) {
        setLayout(new BorderLayout());

        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createHorizontalGlue());
        add(topPanel, BorderLayout.NORTH);

        final JPanel mainPanel = new JPanel(new GridLayout(1, 1));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

        crayonTable = new FileCrayonTable(crayonState);

        final JPanel panel = ToolbarDecorator.createDecorator(crayonTable)
                .disableAddAction()
                .disableUpDownActions()
                .createPanel();
        mainPanel.add(panel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        crayonTable.getEmptyText().setText(MsgBundle.message("plugin.crayon.table.empty"));
    }

    public boolean isModified() {
        log.info("Checking if the table was modified");
        return crayonTable.isModified();
    }

    public void apply() {
        if (isModified()) {
            log.info("Applying changes to the table");
            crayonTable.apply();
            UISettings.getInstance().fireUISettingsChanged();
        }
    }

    public void reset() {
        if (isModified()) {
            log.info("Resetting the table");
            crayonTable.reset();
            UISettings.getInstance().fireUISettingsChanged();
        }
    }
}
