package org.gradle.api.experimental.plugin;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Nested;
import org.gradle.features.binding.Definition;

/**
 * The public DSL interface for a declarative Gradle Plugin.
 */
@SuppressWarnings("UnstableApiUsage")
public interface JavaGradlePlugin extends Definition<JavaGradlePluginBuildModel> {
    Property<String> getDescription();

    @Nested
    JavaGradlePluginDependencies getDependencies();

    NamedDomainObjectContainer<PluginRegistration> getRegisters();
}
