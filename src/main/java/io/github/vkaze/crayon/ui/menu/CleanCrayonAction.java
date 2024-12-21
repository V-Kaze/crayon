package io.github.vkaze.crayon.ui.menu;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsActions;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.vkaze.crayon.storage.FileCrayonState;
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
        Project currentProject = event.getProject();
        if (currentProject == null) {
            return;
        }
        FileCrayonState fileCrayonState = FileCrayonState.getInstance(currentProject);
        VirtualFile[] files = getFiles(event);
        boolean modified = false;
        for (VirtualFile file : files) {
            modified |= fileCrayonState.removeFile(file.getPath());
        }
        if (modified) {
            ProjectView.getInstance(currentProject).refresh();
        }
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
