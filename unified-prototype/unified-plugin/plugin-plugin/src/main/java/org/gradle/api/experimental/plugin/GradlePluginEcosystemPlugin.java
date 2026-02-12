package org.gradle.api.experimental.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.initialization.Settings;
import org.gradle.features.annotations.RegistersProjectFeatures;

@SuppressWarnings("UnstableApiUsage")
@RegistersProjectFeatures({JavaGradlePluginPlugin.class})
public abstract class GradlePluginEcosystemPlugin implements Plugin<Settings> {
    @Override
    public void apply(Settings settings) {}
}
