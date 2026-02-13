package org.gradle.api.experimental.plugin;

import org.gradle.features.binding.BuildModel;
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension;

@SuppressWarnings("UnstableApiUsage")
public interface JavaGradlePluginBuildModel extends BuildModel {
    GradlePluginDevelopmentExtension getDevelopmentExtension();
}
