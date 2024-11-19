package io.github.vkaze.crayon.storage;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.serviceContainer.NonInjectable;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Property;
import com.intellij.util.xmlb.annotations.XMap;
import io.github.vkaze.crayon.Crayon;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(Service.Level.PROJECT)
@State(name = "FileCrayonState", storages = @Storage(value = "crayons.xml"))
public final class FileCrayonState implements PersistentStateComponent<FileCrayonState> {
    @Property(surroundWithTag = false)
    @XMap(entryTagName = "coloredFile", keyAttributeName = "path", valueAttributeName = "color")
    public final Map<String, Crayon> files;

    @NonInjectable
    public FileCrayonState() {
        files = new HashMap<>();
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

    public void addFile(String path, Crayon color) {
        files.put(path, color);
    }

    public void removeFiles(List<String> paths) {
        for (String path : paths) {
            files.remove(path);
        }
    }
}
