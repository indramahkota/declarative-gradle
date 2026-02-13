package org.gradle.api.experimental.java;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.experimental.jvm.DefaultJavaBuildModel;
import org.gradle.api.experimental.jvm.JavaBuildModel;
import org.gradle.api.experimental.jvm.internal.JvmPluginSupport;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.plugins.PluginManager;
import org.gradle.api.plugins.jvm.JvmTestSuite;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.features.annotations.BindsProjectType;
import org.gradle.features.binding.ProjectTypeBinding;
import org.gradle.features.binding.ProjectTypeBindingBuilder;
import org.gradle.jvm.toolchain.JavaToolchainService;
import org.gradle.testing.base.TestingExtension;

import javax.inject.Inject;

import static org.gradle.api.plugins.JvmTestSuitePlugin.DEFAULT_TEST_SUITE_NAME;

/**
 * Creates a declarative {@link JavaLibrary} DSL model, applies the official Java library plugin,
 * and links the declarative model to the official plugin.
 */
@SuppressWarnings("UnstableApiUsage")
@BindsProjectType(StandaloneJavaLibraryPlugin.Binding.class)
public abstract class StandaloneJavaLibraryPlugin implements Plugin<Project> {
    public static final String JAVA_LIBRARY = "javaLibrary";

    @Override
    public void apply(Project project) {
        project.getPluginManager().withPlugin("org.gradle.java",
            appliedPlugin -> project.getExtensions().getByType(TestingExtension.class).getSuites().withType(JvmTestSuite.class).named(DEFAULT_TEST_SUITE_NAME).configure(JvmTestSuite::useJUnitJupiter));
    }

    public abstract static class Binding implements ProjectTypeBinding {
        @Override
        public void bind(ProjectTypeBindingBuilder builder) {
            builder.bindProjectType(JAVA_LIBRARY, JavaLibrary.class,
                    (context, definition, buildModel) -> {
                        Services services = context.getObjectFactory().newInstance(Services.class);
                        services.getPluginManager().apply(JavaLibraryPlugin.class);
                        ((DefaultJavaBuildModel) buildModel).setJavaPluginExtension(services.getProject().getExtensions().getByType(JavaPluginExtension.class));

                        context.getObjectFactory().newInstance(ModelToPluginLinker.class).link(
                                definition,
                                buildModel,
                                services.getPluginManager(),
                                services.getProject().getConfigurations(),
                                services.getProject().getTasks()
                        );
                    }
            )
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
            .withBuildModelImplementationType(DefaultJavaBuildModel.class);
        }

        static abstract class ModelToPluginLinker {
            @Inject
            public ModelToPluginLinker() {
            }

            @Inject
            protected abstract JavaToolchainService getJavaToolchainService();

            private void link(JavaLibrary definition, JavaBuildModel buildModel, PluginManager pluginManager, ConfigurationContainer configurations, TaskContainer tasks) {
                pluginManager.withPlugin("java", plugin -> {
                    JvmPluginSupport.linkJavaVersion(definition, buildModel.getJavaPluginExtension());
                    JvmPluginSupport.linkMainSourceSourceSetDependencies(definition.getDependencies(), buildModel.getJavaPluginExtension(), configurations);
                    JvmPluginSupport.linkTestJavaVersion(definition.getTesting(), getJavaToolchainService(), tasks);
                    JvmPluginSupport.linkTestSourceSourceSetDependencies(definition.getTesting().getDependencies(), buildModel.getJavaPluginExtension(), configurations);
                });
            }
        }

        interface Services {
            @Inject
            PluginManager getPluginManager();

            @Inject
            Project getProject();
        }
    }
}
