package org.gradle.api.experimental.cpp;

import org.gradle.api.Plugin;
import org.gradle.api.initialization.Settings;
import org.gradle.features.annotations.RegistersProjectFeatures;

@RegistersProjectFeatures({StandaloneCppLibraryPlugin.class, StandaloneCppApplicationPlugin.class})
public class CppEcosystemPlugin implements Plugin<Settings> {
    @Override
    public void apply(Settings target) {
    }
}
