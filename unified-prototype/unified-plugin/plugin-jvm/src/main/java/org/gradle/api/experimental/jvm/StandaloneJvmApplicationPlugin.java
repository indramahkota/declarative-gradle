package org.gradle.api.experimental.jvm;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.dsl.DependencyFactory;
import org.gradle.api.experimental.common.CliExecutablesSupport;
import org.gradle.api.experimental.jvm.internal.JvmPluginSupport;
import org.gradle.api.plugins.ApplicationPlugin;
import org.gradle.api.plugins.JavaApplication;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.plugins.PluginManager;
import org.gradle.api.plugins.internal.JavaPluginHelper;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.features.annotations.BindsProjectType;
import org.gradle.features.binding.ProjectTypeBinding;
import org.gradle.features.binding.ProjectTypeBindingBuilder;
import org.gradle.features.file.ProjectFeatureLayout;
import org.gradle.features.registration.TaskRegistrar;
import org.gradle.jvm.toolchain.JavaToolchainService;

import javax.inject.Inject;

/**
 * Creates a declarative {@link JvmApplication} DSL model, applies the official Jvm plugin,
 * and links the declarative model to the official plugin.
 */
@SuppressWarnings("UnstableApiUsage")
@BindsProjectType(StandaloneJvmApplicationPlugin.Binding.class)
public abstract class StandaloneJvmApplicationPlugin implements Plugin<Project> {

    public static final String JVM_APPLICATION = "jvmApplication";

    @Override
    public void apply(Project project) {

    }

    static class Binding implements ProjectTypeBinding {
        @Override
        public void bind(ProjectTypeBindingBuilder builder) {
            builder.bindProjectType(JVM_APPLICATION, JvmApplication.class,
                    (context, definition, buildModel) -> {
                        Services services = context.getObjectFactory().newInstance(Services.class);
                        services.getPluginManager().apply(ApplicationPlugin.class);
                        CliExecutablesSupport.configureRunTasks(services.getTaskRegistrar(), buildModel);
                        ((DefaultJavaApplicationBuildModel) buildModel).setJavaPluginExtension(
                                services.getProject().getExtensions().getByType(JavaPluginExtension.class)
                        );
                        ((DefaultJavaApplicationBuildModel) buildModel).setJavaApplicationExtension(
                                services.getProject().getExtensions().getByType(JavaApplication.class)
                        );

                        context.getObjectFactory().newInstance(ModelToPluginLinker.class).link(
                                definition,
                                buildModel,
                                services.getProject().getConfigurations(),
                                services.getProject().getTasks(),
                                services.getProjectFeatureLayout(),
                                services.getProviderFactory(),
                                services.getDependencyFactory(),
                                JavaPluginHelper.getJavaComponent(services.getProject()).getMainFeature().getSourceSet()
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
            ProjectFeatureLayout getProjectFeatureLayout();

            @Inject
            ProviderFactory getProviderFactory();

            @Inject
            DependencyFactory getDependencyFactory();

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

        private void link(
                JvmApplication dslModel,
                JavaApplicationBuildModel buildModel,
                ConfigurationContainer configurations,
                TaskContainer tasks,
                ProjectFeatureLayout projectLayout,
                ProviderFactory providers,
                DependencyFactory dependencyFactory,
                SourceSet commonSources) {

            JvmPluginSupport.setupCommonSourceSet(commonSources, projectLayout);
            JvmPluginSupport.linkSourceSetToDependencies(commonSources, dslModel.getDependencies(), configurations);

            JvmPluginSupport.linkJavaVersion(dslModel, buildModel.getJavaPluginExtension(), providers);
            JvmPluginSupport.linkApplicationMainClass(dslModel, buildModel.getJavaApplicationExtension());
            dslModel.getTargets().getStore().withType(JavaTarget.class).all(target -> {
                SourceSet sourceSet = JvmPluginSupport.createTargetSourceSet(target, commonSources, getJavaToolchainService(), buildModel.getJavaPluginExtension(), tasks, configurations, dependencyFactory);

                // Link dependencies to DSL
                JvmPluginSupport.linkSourceSetToDependencies(sourceSet, target.getDependencies(), configurations);

                // Create a run task
                TaskProvider<JavaExec> runTask = tasks.register(sourceSet.getTaskName("run", null), JavaExec.class, task -> {
                    task.getMainClass().set(dslModel.getMainClass());
                    task.getJvmArguments().set(dslModel.getJvmArguments());
                    task.setClasspath(sourceSet.getRuntimeClasspath());
                });
                buildModel.getRunTasks().add(runTask);
            });
        }
    }
}
