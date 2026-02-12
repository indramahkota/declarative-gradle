package org.gradle.api.experimental.java;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.experimental.common.CliExecutablesSupport;
import org.gradle.api.experimental.jvm.DefaultJavaApplicationBuildModel;
import org.gradle.api.experimental.jvm.DefaultJavaBuildModel;
import org.gradle.api.experimental.jvm.JavaApplicationBuildModel;
import org.gradle.api.experimental.jvm.internal.JvmPluginSupport;
import org.gradle.api.plugins.ApplicationPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.plugins.PluginManager;
import org.gradle.api.plugins.jvm.JvmTestSuite;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.features.annotations.BindsProjectType;
import org.gradle.features.binding.ProjectTypeBinding;
import org.gradle.features.binding.ProjectTypeBindingBuilder;
import org.gradle.features.registration.TaskRegistrar;
import org.gradle.jvm.toolchain.JavaToolchainService;
import org.gradle.testing.base.TestingExtension;

import javax.inject.Inject;

import static org.gradle.api.plugins.JvmTestSuitePlugin.DEFAULT_TEST_SUITE_NAME;

/**
 * Creates a declarative {@link JavaApplication} DSL model, applies the official Java application plugin,
 * and links the declarative model to the official plugin.
 */
@SuppressWarnings("UnstableApiUsage")
@BindsProjectType(StandaloneJavaApplicationPlugin.Binding.class)
public abstract class StandaloneJavaApplicationPlugin implements Plugin<Project> {
    public static final String JAVA_APPLICATION = "javaApplication";

    @Override
    public void apply(Project project) {
        project.getPluginManager().withPlugin("org.gradle.java",
                appliedPlugin -> project.getExtensions().getByType(TestingExtension.class).getSuites().withType(JvmTestSuite.class).named(DEFAULT_TEST_SUITE_NAME).configure(JvmTestSuite::useJUnitJupiter));
    }

    static class Binding implements ProjectTypeBinding {
        @Override
        public void bind(ProjectTypeBindingBuilder builder) {
            builder.bindProjectType(JAVA_APPLICATION, JavaApplication.class,
                    (context, definition, buildModel) -> {
                        Services services = context.getObjectFactory().newInstance(Services.class);
                        services.getPluginManager().apply(ApplicationPlugin.class);
                        CliExecutablesSupport.configureRunTasks(services.getTaskRegistrar(), buildModel);
                        ((DefaultJavaBuildModel) buildModel).setJavaPluginExtension(
                                services.getProject().getExtensions().getByType(JavaPluginExtension.class)
                        );
                        ((DefaultJavaApplicationBuildModel) buildModel).setJavaApplicationExtension(
                                services.getProject().getExtensions().getByType(org.gradle.api.plugins.JavaApplication.class)
                        );

                        context.getObjectFactory().newInstance(ModelToPluginLinker.class).link(
                                definition,
                                buildModel,
                                services.getProject().getConfigurations(),
                                services.getProject().getTasks()
                        );
                    }
            )
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
            .withBuildModelImplementationType(DefaultJavaApplicationBuildModel.class);
        }

        interface Services {
            @Inject
            PluginManager getPluginManager();

            @Inject
            TaskRegistrar getTaskRegistrar();

            @Inject
            Project getProject();
        }
    }

    static abstract class ModelToPluginLinker {
        @Inject
        public ModelToPluginLinker() {
        }

        @Inject
        protected abstract JavaToolchainService getJavaToolchainService();

        private void link(JavaApplication definition, JavaApplicationBuildModel buildModel, ConfigurationContainer configurations, TaskContainer tasks) {
            JvmPluginSupport.linkJavaVersion(definition, buildModel.getJavaPluginExtension());
            JvmPluginSupport.linkApplicationMainClass(definition, buildModel.getJavaApplicationExtension());
            JvmPluginSupport.linkMainSourceSourceSetDependencies(definition.getDependencies(), buildModel.getJavaPluginExtension(), configurations);
            JvmPluginSupport.linkTestJavaVersion(definition.getTesting(), getJavaToolchainService(), tasks);
            JvmPluginSupport.linkTestSourceSourceSetDependencies(definition.getTesting().getDependencies(), buildModel.getJavaPluginExtension(), configurations);

            buildModel.getRunTasks().add(tasks.named("run"));
        }
    }
}
