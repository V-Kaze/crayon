package io.github.vkaze.crayon.ui.settings;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import io.github.vkaze.crayon.MsgBundle;
import io.github.vkaze.crayon.storage.FileCrayonState;
import org.jspecify.annotations.Nullable;

import javax.swing.JComponent;

public class FileCrayonConfigurable implements Configurable {
    private static final Logger log = Logger.getInstance(FileCrayonConfigurable.class);
    private final Project project;
    private FileCrayonPanel fileCrayonPanel;

    public FileCrayonConfigurable(final Project project) {
        this.project = project;
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return MsgBundle.message("plugin.crayon.display.name");
    }

    @Override
    public @Nullable JComponent createComponent() {
        if (fileCrayonPanel == null) {
            fileCrayonPanel = new FileCrayonPanel(FileCrayonState.getInstance(project));
        }

        return fileCrayonPanel;
    }

    @Override
    public boolean isModified() {
        log.info("Checking if the panel was modified");
        return fileCrayonPanel != null && fileCrayonPanel.isModified();
    }

    @Override
    public void apply() {
        if (fileCrayonPanel != null) {
            log.info("Applying changes to the panel");
            fileCrayonPanel.apply();
        }
    }

    @Override
    public void reset() {
        if (fileCrayonPanel != null) {
            log.info("Resetting the panel");
            fileCrayonPanel.reset();
        }
    }

    @Override
    public void disposeUIResources() {
        if (fileCrayonPanel != null) {
            log.info("Disposing the panel");
            fileCrayonPanel = null;
        }
    }
}
