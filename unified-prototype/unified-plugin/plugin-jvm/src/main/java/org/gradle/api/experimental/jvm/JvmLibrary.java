package org.gradle.api.experimental.jvm;

import org.gradle.api.experimental.common.HasLibraryDependencies;
import org.gradle.features.binding.Definition;

/**
 * A library that runs on the JVM and that is implemented using one or more versions of Java.
 */
@SuppressWarnings("UnstableApiUsage")
public interface JvmLibrary extends HasJavaTargets, HasLibraryDependencies, Definition<JavaBuildModel> {
}
