package org.gradle.api.experimental.jvm;

import org.gradle.api.Plugin;
import org.gradle.api.experimental.java.StandaloneJavaApplicationPlugin;
import org.gradle.api.experimental.java.StandaloneJavaLibraryPlugin;
import org.gradle.api.experimental.java.checkstyle.CheckstyleSoftwareFeaturePlugin;
import org.gradle.api.initialization.Settings;
import org.gradle.features.annotations.RegistersProjectFeatures;

@SuppressWarnings("UnstableApiUsage")
@RegistersProjectFeatures({
        StandaloneJavaApplicationPlugin.class,
        StandaloneJavaLibraryPlugin.class,
        StandaloneJvmLibraryPlugin.class,
        StandaloneJvmApplicationPlugin.class,
        CheckstyleSoftwareFeaturePlugin.class
})
public abstract class JvmEcosystemPlugin implements Plugin<Settings> {
    @Override
    public void apply(Settings target) {
        target.getPlugins().apply(JvmEcosystemConventionsPlugin.class);
    }
}
