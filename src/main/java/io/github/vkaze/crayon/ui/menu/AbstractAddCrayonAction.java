package io.github.vkaze.crayon.ui.menu;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.NlsActions;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.vkaze.crayon.Crayon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public abstract class AbstractAddCrayonAction extends AnAction implements CrayonAction {
    private static final Logger log = Logger.getInstance(AbstractAddCrayonAction.class);

    public AbstractAddCrayonAction() {
        super();
    }

    public AbstractAddCrayonAction(@Nullable @NlsActions.ActionText String text, @Nullable @NlsActions.ActionDescription String description, @Nullable Icon icon) {
        super(text, description, icon);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        VirtualFile file = getFile(event);
        if (file == null) {
            log.warn("AddCrayonAction was triggered by an event that doesn't have a virtual file attach. Event: {}");
            return;
        }
        // TODO: Add selected file to the storage
        // Using the event, create and show a dialog
        Project currentProject = event.getProject();
        Crayon crayon = getCrayon();
        String message = "Coloring " + file + " using " + crayon + " crayon!";
        String title = event.getPresentation().getDescription();
        Messages.showMessageDialog(currentProject, message, title, Messages.getInformationIcon());
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    abstract protected Crayon getCrayon();
}
