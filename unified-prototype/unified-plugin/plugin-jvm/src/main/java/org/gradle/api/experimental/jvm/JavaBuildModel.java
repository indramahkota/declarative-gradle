package org.gradle.api.experimental.jvm;

import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.features.binding.BuildModel;

@SuppressWarnings("UnstableApiUsage")
public interface JavaBuildModel extends BuildModel {
    JavaPluginExtension getJavaPluginExtension();
}
