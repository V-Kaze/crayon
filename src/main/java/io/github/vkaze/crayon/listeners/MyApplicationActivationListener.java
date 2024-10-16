package io.github.vkaze.crayon.listeners;

import com.intellij.openapi.application.ApplicationActivationListener;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.wm.IdeFrame;
import org.jetbrains.annotations.NotNull;

public final class MyApplicationActivationListener implements ApplicationActivationListener {
    private static final Logger log = Logger.getInstance(MyApplicationActivationListener.class);

    @Override
    public void applicationActivated(@NotNull IdeFrame ideFrame) {
        log.warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.");
    }
}
