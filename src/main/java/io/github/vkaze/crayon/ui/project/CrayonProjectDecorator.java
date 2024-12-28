package io.github.vkaze.crayon.ui.project;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ProjectViewNodeDecorator;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.vkaze.crayon.Crayon;
import io.github.vkaze.crayon.storage.FileCrayonState;

public class CrayonProjectDecorator implements ProjectViewNodeDecorator {
    private static final Logger log = Logger.getInstance(CrayonProjectDecorator.class);

    @Override
    public void decorate(ProjectViewNode<?> projectViewNode, PresentationData presentationData) {
        Project project = projectViewNode.getProject();
        if (project == null) {
            return;
        }
        VirtualFile virtualFile = projectViewNode.getVirtualFile();
        if (virtualFile == null || !virtualFile.isValid()) {
            return;
        }
        FileCrayonState fileCrayonState = FileCrayonState.getInstance(project);
        String path = virtualFile.getPath();
        Crayon crayon = fileCrayonState.getCrayon(virtualFile);
        if (crayon == null) {
            return;
        }
        presentationData.setBackground(crayon.getColor());
    }
}
