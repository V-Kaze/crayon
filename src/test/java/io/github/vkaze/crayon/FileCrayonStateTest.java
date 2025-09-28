package io.github.vkaze.crayon;

import com.intellij.openapi.vfs.VirtualFile;
import io.github.vkaze.crayon.mocks.MockVirtualDirectory;
import io.github.vkaze.crayon.mocks.MockVirtualFS;
import io.github.vkaze.crayon.mocks.MockVirtualFile;
import io.github.vkaze.crayon.storage.FileCrayonState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

public class FileCrayonStateTest {

    private static Stream<Arguments> crayonsWithFiles() {
        return Stream.of(
                        "Test.txt",
                        "Space in between.java",
                        "日本語.sql",
                        "without extension",
                        "aA1!@#$%^&*()_+-=,.\\|<>?;':\"[]{}.sql"
                ).map(MockVirtualFS::localValidFile)
                .flatMap(fileName ->
                        Arrays.stream(Crayon.values())
                                .map(crayon -> Arguments.of(fileName, crayon))
                );
    }

    @ParameterizedTest
    @EnumSource(Crayon.class)
    void localValidFile(Crayon crayon) {
        VirtualFile virtualFile = MockVirtualFS.localValidFile("test.txt");
        FileCrayonState crayonState = new FileCrayonState();
        boolean added = crayonState.addFile(virtualFile, crayon);
        Assertions.assertTrue(added, "File wasn't added");
        Assertions.assertEquals(crayon, crayonState.getCrayon(virtualFile));
        boolean removedFile = crayonState.removeFile(virtualFile);
        Assertions.assertTrue(removedFile, "File wasn't removed");
        Assertions.assertNull(crayonState.getCrayon(virtualFile), "File should have been removed");
    }

    @ParameterizedTest
    @EnumSource(Crayon.class)
    void localInvalidFile(Crayon crayon) {
        VirtualFile virtualFile = MockVirtualFS.localInvalidFile("test.txt");
        FileCrayonState crayonState = new FileCrayonState();
        boolean added = crayonState.addFile(virtualFile, crayon);
        Assertions.assertFalse(added, "File shouldn't be added as it's invalid");
        Assertions.assertFalse(crayonState.removeFile(virtualFile), "File wasn't added in the first place");
    }

    @ParameterizedTest
    @EnumSource(Crayon.class)
    void nonLocalValidFile(Crayon crayon) {
        VirtualFile virtualFile = MockVirtualFS.nonLocalValidFile("test.txt");
        FileCrayonState crayonState = new FileCrayonState();
        boolean added = crayonState.addFile(virtualFile, crayon);
        Assertions.assertFalse(added, "File shouldn't be added as it's invalid");
        Assertions.assertFalse(crayonState.removeFile(virtualFile), "File wasn't added in the first place");
    }

    @ParameterizedTest
    @EnumSource(Crayon.class)
    void localValidDirectory(Crayon crayon) {
        VirtualFile virtualDirectory = MockVirtualFS.localValidDirectory("test");
        FileCrayonState crayonState = new FileCrayonState();
        boolean added = crayonState.addFile(virtualDirectory, crayon);
        Assertions.assertTrue(added, "Directory wasn't added");
        Assertions.assertEquals(crayon, crayonState.getCrayon(virtualDirectory));
        boolean removedDirectory = crayonState.removeFile(virtualDirectory);
        Assertions.assertTrue(removedDirectory, "Directory wasn't removed");
        Assertions.assertNull(crayonState.getCrayon(virtualDirectory), "Directory should have been removed");
    }

    @ParameterizedTest
    @EnumSource(Crayon.class)
    void localInvalidDirectory(Crayon crayon) {
        VirtualFile virtualDirectory = MockVirtualFS.localInvalidDirectory("test");
        FileCrayonState crayonState = new FileCrayonState();
        boolean added = crayonState.addFile(virtualDirectory, crayon);
        Assertions.assertFalse(added, "Directory shouldn't be added as it's invalid");
        Assertions.assertFalse(crayonState.removeFile(virtualDirectory), "Directory wasn't added in the first place");
    }

    @ParameterizedTest
    @EnumSource(Crayon.class)
    void nonLocalValidDirectory(Crayon crayon) {
        VirtualFile virtualDirectory = MockVirtualFS.nonLocalValidDirectory("test");
        FileCrayonState crayonState = new FileCrayonState();
        boolean added = crayonState.addFile(virtualDirectory, crayon);
        Assertions.assertFalse(added, "Directory shouldn't be added as it's invalid");
        Assertions.assertFalse(crayonState.removeFile(virtualDirectory), "Directory wasn't added in the first place");
    }

    @ParameterizedTest
    @EnumSource(Crayon.class)
    void crayonFromParentDirectory(Crayon crayon) {
        MockVirtualDirectory parentDirectory = MockVirtualFS.randomDirectory();
        FileCrayonState crayonState = new FileCrayonState();
        boolean added = crayonState.addFile(parentDirectory, crayon);
        Assertions.assertTrue(added, "Directory wasn't added");
        MockVirtualFile virtualFile = MockVirtualFS.randomFile(parentDirectory);
        Assertions.assertEquals(crayon, crayonState.getCrayon(virtualFile));
        Assertions.assertFalse(crayonState.removeFile(virtualFile), "Used parent's crayon, so the file should not exist");
    }

    @ParameterizedTest
    @MethodSource("crayonsWithFiles")
    void crayonWithFile(VirtualFile file, Crayon crayon) {
        FileCrayonState crayonState = new FileCrayonState();
        boolean added = crayonState.addFile(file, crayon);
        Assertions.assertTrue(added, "File wasn't added");
        Assertions.assertEquals(crayon, crayonState.getCrayon(file));
        boolean removedFile = crayonState.removeFile(file);
        Assertions.assertTrue(removedFile, "File wasn't removed");
        Assertions.assertNull(crayonState.getCrayon(file), "File should have been removed");
    }
}
