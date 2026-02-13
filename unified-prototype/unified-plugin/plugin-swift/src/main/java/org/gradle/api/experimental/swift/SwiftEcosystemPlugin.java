package org.gradle.api.experimental.swift;

import org.gradle.api.Plugin;
import org.gradle.api.initialization.Settings;
import org.gradle.features.annotations.RegistersProjectFeatures;

@SuppressWarnings("UnstableApiUsage")
@RegistersProjectFeatures({
        StandaloneSwiftLibraryPlugin.class,
        StandaloneSwiftApplicationPlugin.class})
public class SwiftEcosystemPlugin implements Plugin<Settings> {
    @Override
    public void apply(Settings target) {
    }
}
