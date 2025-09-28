package io.github.vkaze.crayon.mocks;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightVirtualFile;

public class MockVirtualFile extends LightVirtualFile {
    private final String name;
    private final boolean valid;
    private final boolean local;
    private final @Nullable MockVirtualDirectory parent;

    protected MockVirtualFile(String name, boolean valid, boolean local, @Nullable MockVirtualDirectory parent) {
        this.name = name;
        this.valid = valid;
        this.local = local;
        this.parent = parent;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public boolean isInLocalFileSystem() {
        return local;
    }

    @Override
    public @NotNull String getPath() {
        String path = null;
        if (parent != null) {
            path = parent.getPath();
        }
        path = path == null ? "" : path + "/";
        return path + name;
    }

    @Override
    public @Nullable VirtualFile getParent() {
        return parent;
    }
}
