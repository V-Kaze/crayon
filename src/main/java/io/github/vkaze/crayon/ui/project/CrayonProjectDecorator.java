package io.github.vkaze.crayon.ui.project;

import org.jetbrains.annotations.NotNull;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ProjectViewNodeDecorator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import io.github.vkaze.crayon.Crayon;
import io.github.vkaze.crayon.storage.FileCrayonState;

public class CrayonProjectDecorator implements ProjectViewNodeDecorator {
    @Override
    public void decorate(ProjectViewNode<?> projectViewNode, @NotNull PresentationData presentationData) {
        Project project = projectViewNode.getProject();
        if (project == null) {
            return;
        }
        VirtualFile virtualFile = projectViewNode.getVirtualFile();
        if (virtualFile == null || !virtualFile.isValid()) {
            return;
        }
        FileCrayonState fileCrayonState = FileCrayonState.getInstance(project);
        Crayon crayon = fileCrayonState.getCrayon(virtualFile);
        if (crayon == null) {
            return;
        }
        presentationData.setBackground(crayon.getColor());
    }
}
