package io.github.vkaze.crayon.mocks;

import java.util.concurrent.ThreadLocalRandom;

import org.jspecify.annotations.Nullable;

import com.intellij.openapi.vfs.VirtualFile;

public class MockVirtualFS {
    private MockVirtualFS() {
    }

    public static MockVirtualFile localValidFile(String name) {
        return new MockVirtualFile(name, true, true, randomDirectory());
    }

    public static MockVirtualFile localInvalidFile(String name) {
        return new MockVirtualFile(name, false, true, randomDirectory());
    }

    public static MockVirtualFile nonLocalValidFile(String name) {
        return new MockVirtualFile(name, true, false, randomDirectory());
    }

    public static MockVirtualDirectory localValidDirectory(String name) {
        return new MockVirtualDirectory(name, true, true, randomDirectory());
    }

    public static MockVirtualDirectory localInvalidDirectory(String name) {
        return new MockVirtualDirectory(name, false, true, randomDirectory());
    }

    public static MockVirtualDirectory nonLocalValidDirectory(String name) {
        return new MockVirtualDirectory(name, true, false, randomDirectory());
    }

    public static MockVirtualFile randomFile(@Nullable MockVirtualDirectory parent) {
        return new MockVirtualFile(String.valueOf(ThreadLocalRandom.current().nextInt()), true, true, parent);
    }

    public static VirtualFile fromStringPath(String path) {
        return new MockVirtualFile(path, true, true, null);
    }

    public static MockVirtualDirectory randomDirectory() {
        int depth = ThreadLocalRandom.current().nextInt(1, 5);
        MockVirtualDirectory directory = null;
        for (int i = 0; i < depth; i++) {
            directory = new MockVirtualDirectory(String.valueOf(i), true, true, directory);
        }
        return directory;
    }
}
