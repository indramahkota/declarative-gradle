package org.gradle.api.experimental.common;

import org.gradle.features.registration.TaskRegistrar;

public class CliExecutablesSupport {
    public static void configureRunTasks(TaskRegistrar tasks, HasCliExecutables executables) {
        tasks.register("runAll", task -> task.dependsOn(executables.getRunTasks()));
    }
}
