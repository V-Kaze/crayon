package io.github.vkaze.crayon.storage;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.serviceContainer.NonInjectable;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.MapAnnotation;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(Service.Level.PROJECT)
@State(name = "FileCrayonState", storages = @Storage(value = "crayons.xml"))
public final class FileCrayonState implements PersistentStateComponent<FileCrayonState> {
    private static final Logger log = Logger.getInstance(FileCrayonState.class);
    @MapAnnotation
    private final Map<String, CrayonColor> files;

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

    public void addFile(String path, CrayonColor color) {
        files.put(path, color);
    }

    public void removeFiles(List<String> paths) {
        for (String path : paths) {
            files.remove(path);
        }
    }

    public List<TableRow> toTableRows() {
        List<TableRow> tableRows = new ArrayList<>(files.size());
        for (Map.Entry<String, CrayonColor> entry : files.entrySet()) {
            tableRows.add(new TableRow(entry.getKey(), entry.getValue()));
        }
        return tableRows;
    }
}
