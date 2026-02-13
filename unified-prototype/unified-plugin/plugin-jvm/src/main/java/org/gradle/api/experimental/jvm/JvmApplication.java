package org.gradle.api.experimental.jvm;

import org.gradle.features.binding.Definition;

/**
 * An application that runs on the JVM and that is implemented using one or more versions of Java.
 */
@SuppressWarnings("UnstableApiUsage")
public interface JvmApplication extends HasJavaTargets, HasJvmApplication, Definition<JavaApplicationBuildModel> {
}
