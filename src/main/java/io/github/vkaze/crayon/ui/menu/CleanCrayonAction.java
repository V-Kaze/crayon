package io.github.vkaze.crayon.ui.menu;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.NlsActions;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class CleanCrayonAction extends AnAction implements CrayonAction {
    private static final Logger log = Logger.getInstance(AbstractAddCrayonAction.class);

    public CleanCrayonAction() {
        super();
    }

    public CleanCrayonAction(@Nullable @NlsActions.ActionText String text, @Nullable @NlsActions.ActionDescription String description, @Nullable Icon icon) {
        super(text, description, icon);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        VirtualFile file = getFile(event);
        if (file == null) {
            log.warn("CleanCrayonAction was triggered by an event that doesn't have a virtual file attach. Event: " + event);
            return;
        }
        // TODO: Remove selected file from the storage
        // Using the event, create and show a dialog
        Project currentProject = event.getProject();
        String message = "Cleaning " + file + " from crayons!";
        String title = event.getPresentation().getDescription();
        Messages.showMessageDialog(currentProject, message, title, Messages.getInformationIcon());
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        // TODO: Check if the file is colored by a crayon
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
