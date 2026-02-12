package org.gradle.api.experimental.java;

import org.gradle.api.experimental.jvm.HasJavaTarget;
import org.gradle.api.experimental.jvm.HasJvmApplication;
import org.gradle.api.experimental.jvm.JavaApplicationBuildModel;
import org.gradle.api.experimental.jvm.extensions.testing.Testing;
import org.gradle.api.tasks.Nested;
import org.gradle.features.binding.Definition;

/**
 * An application implemented using a single version of Java.
 */
@SuppressWarnings("UnstableApiUsage")
public interface JavaApplication extends HasJavaTarget, HasJvmApplication, Definition<JavaApplicationBuildModel> {
    @Nested
    Testing getTesting();
}
