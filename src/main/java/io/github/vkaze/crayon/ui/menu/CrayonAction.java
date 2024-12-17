package io.github.vkaze.crayon.ui.menu;

import com.intellij.openapi.actionSystem.ActionUpdateThreadAware;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;

public interface CrayonAction extends ActionUpdateThreadAware {
    VirtualFile[] EMPTY_ARRAY = new VirtualFile[0];

    default VirtualFile[] getFiles(AnActionEvent event) {
        VirtualFile[] files = event.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        if (files == null) {
            files = EMPTY_ARRAY;
        }
        return files;
    }
}
