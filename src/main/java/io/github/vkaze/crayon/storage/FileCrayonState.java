package io.github.vkaze.crayon.storage;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.serviceContainer.NonInjectable;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.XMap;
import io.github.vkaze.crayon.Crayon;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(Service.Level.PROJECT)
@State(name = "FileCrayonState", storages = @Storage(value = "crayons.xml"))
public final class FileCrayonState implements PersistentStateComponent<FileCrayonState> {
    @XMap(entryTagName = "file", keyAttributeName = "path", valueAttributeName = "color")
    public final Map<String, Crayon> files;
    @XMap(entryTagName = "dir", keyAttributeName = "path", valueAttributeName = "color")
    public final Map<String, Crayon> dirs;

    @NonInjectable
    public FileCrayonState() {
        files = new HashMap<>();
        dirs = new HashMap<>();
    }

    public static FileCrayonState getInstance(Project project) {
        FileCrayonState persisted = project.getService(FileCrayonState.class);
        return persisted != null ? persisted : new FileCrayonState();
    }

    @Override
    public FileCrayonState getState() {
        return this;
    }

    @Override
    public void loadState(@NonNull FileCrayonState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public boolean addFile(VirtualFile file, Crayon color) {
        if (!file.isValid()) {
            return false;
        }
        if (!file.isInLocalFileSystem()) {
            return false;
        }
        if (file.isDirectory()) {
            dirs.put(file.getPath(), color);
        } else {
            files.put(file.getPath(), color);
        }
        return true;
    }

    public void removeFiles(List<String> paths) {
        for (String path : paths) {
            removeFile(path);
        }
    }

    public boolean removeFile(String path) {
        boolean removed = false;
        removed |= files.remove(path) != null;
        removed |= dirs.remove(path) != null;
        return removed;
    }

    public @Nullable Crayon getCrayon(VirtualFile file) {
        VirtualFile dir;
        if (!file.isDirectory()) {
            Crayon crayon = files.get(file.getPath());
            if (crayon != null) {
                return crayon;
            } else {
                dir = file.getParent();
            }
        } else {
            dir = file;
        }
        while (dir != null) {
            Crayon dirCrayon = dirs.get(dir.getPath());
            if (dirCrayon != null) {
                return dirCrayon;
            }
            dir = dir.getParent();
        }
        return null;
    }
}
