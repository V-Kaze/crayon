package io.github.vkaze.crayon.services;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import io.github.vkaze.crayon.MsgBundle;

import java.util.concurrent.ThreadLocalRandom;

@Service(Service.Level.PROJECT)
public final class MyProjectService {
    private static final Logger log = Logger.getInstance(MyProjectService.class);

    public MyProjectService(Project project) {
        log.info(MsgBundle.message("projectService", project.getName()));
        log.warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.");
    }

    public int getRandomNumber() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }
}
