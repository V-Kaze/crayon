package io.github.vkaze.crayon.toolWindow;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import io.github.vkaze.crayon.MsgBundle;
import io.github.vkaze.crayon.services.MyProjectService;
import org.jspecify.annotations.NonNull;

import javax.swing.*;

public class MyToolWindowFactory implements ToolWindowFactory {
    private static final Logger log = Logger.getInstance(MyToolWindowFactory.class);

    public MyToolWindowFactory() {
        log.warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.");
    }

    @Override
    public void createToolWindowContent(@NonNull Project project, @NonNull ToolWindow toolWindow) {
        MyToolWindow myToolWindow = new MyToolWindow(toolWindow);
        Content content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false);
        toolWindow.getContentManager().addContent(content);
    }

    public static class MyToolWindow {
        private final MyProjectService service;

        public MyToolWindow(ToolWindow toolWindow) {
            this.service = toolWindow.getProject().getService(MyProjectService.class);
        }

        public JBPanel<JBPanel<?>> getContent() {
            JBLabel label = new JBLabel(MsgBundle.message("randomLabel", "?"));
            JButton button = new JButton(MsgBundle.message("shuffle"));
            button.addActionListener(event -> label.setText(MsgBundle.message("randomLabel", service.getRandomNumber())));
            JBPanel<JBPanel<?>> panel = new JBPanel<>();
            panel.add(label);
            panel.add(button);
            return panel;
        }
    }
}
