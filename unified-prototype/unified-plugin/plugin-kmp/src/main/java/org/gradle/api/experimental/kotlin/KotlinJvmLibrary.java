package org.gradle.api.experimental.kotlin;

import org.gradle.api.experimental.common.HasLibraryDependencies;
import org.gradle.api.experimental.jvm.HasJavaTarget;
import org.gradle.api.experimental.kotlin.testing.Testing;
import org.gradle.api.tasks.Nested;
import org.gradle.features.binding.Definition;

/**
 * A library implemented using Kotlin and that targets a single JVM version.
 */
@SuppressWarnings("UnstableApiUsage")
public interface KotlinJvmLibrary extends HasJavaTarget, HasLibraryDependencies, Definition<KotlinJvmLibraryBuildModel> {
    @Nested
    Testing getTesting();
}
