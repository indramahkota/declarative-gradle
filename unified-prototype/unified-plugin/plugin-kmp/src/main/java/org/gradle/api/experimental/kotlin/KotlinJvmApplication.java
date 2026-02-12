package org.gradle.api.experimental.kotlin;

import org.gradle.api.experimental.jvm.HasJavaTarget;
import org.gradle.api.experimental.jvm.HasJvmApplication;
import org.gradle.api.experimental.kotlin.testing.Testing;
import org.gradle.api.tasks.Nested;
import org.gradle.features.binding.Definition;

/**
 * An application implemented using Kotlin and that targets a single JVM version.
 */
@SuppressWarnings("UnstableApiUsage")
public interface KotlinJvmApplication extends HasJavaTarget, HasJvmApplication, Definition<KotlinJvmApplicationBuildModel> {
    @Nested
    Testing getTesting();
}
