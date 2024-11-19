package io.github.vkaze.crayon.ui.menu;

import com.intellij.openapi.actionSystem.ActionUpdateThreadAware;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public interface CrayonAction extends ActionUpdateThreadAware {
    default @Nullable VirtualFile getFile(AnActionEvent event) {
        return event.getData(CommonDataKeys.VIRTUAL_FILE);
    }

    @Contract(pure = true, value = "null -> false")
    default boolean isEnabled(@Nullable VirtualFile file) {
        if (file == null) {
            return false;
        }
        if (!file.isInLocalFileSystem()) {
            return false;
        }
        return !file.isDirectory();
    }
}
