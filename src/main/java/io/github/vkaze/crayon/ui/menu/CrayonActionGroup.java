package io.github.vkaze.crayon.ui.menu;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.vkaze.crayon.Crayon;
import io.github.vkaze.crayon.MsgBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class CrayonActionGroup extends ActionGroup implements CrayonAction {
    private static final AnAction[] ACTIONS;

    static {
        Stream<AnAction> actionStream = Arrays.stream(Crayon.values())
                .sorted(Comparator.comparing(Crayon::name))
                .map(crayon -> {
                    String title = MsgBundle.message("plugin.crayon.action.add.title", crayon.getColorName());
                    String description = MsgBundle.message("plugin.crayon.action.add.description", crayon.getColorName());
                    return new AbstractAddCrayonAction(title, description, crayon.getColorIcon()) {
                        @Override
                        protected Crayon getCrayon() {
                            return crayon;
                        }
                    };
                });
        String cleanTitle = MsgBundle.message("plugin.crayon.action.clean.title");
        String cleanDescription = MsgBundle.message("plugin.crayon.action.clean.description");
        Stream<CleanCrayonAction> clearActionStream = Stream.of(new CleanCrayonAction(cleanTitle, cleanDescription, null));
        ACTIONS = Stream.concat(actionStream, clearActionStream).toList().toArray(AnAction[]::new);
    }

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent anActionEvent) {
        return ACTIONS;
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        VirtualFile file = getFile(event);
        boolean enabled = isEnabled(file);
        event.getPresentation().setEnabledAndVisible(enabled);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
