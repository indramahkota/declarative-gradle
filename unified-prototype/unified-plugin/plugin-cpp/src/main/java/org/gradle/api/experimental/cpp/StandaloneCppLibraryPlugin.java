package org.gradle.api.experimental.cpp;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.experimental.cpp.internal.DefaultCppLibraryBuildModel;
import org.gradle.api.plugins.PluginManager;
import org.gradle.features.annotations.BindsProjectType;
import org.gradle.features.binding.ProjectTypeBinding;
import org.gradle.features.binding.ProjectTypeBindingBuilder;
import org.gradle.language.cpp.plugins.CppLibraryPlugin;

import javax.inject.Inject;

@SuppressWarnings("UnstableApiUsage")
@BindsProjectType(StandaloneCppLibraryPlugin.Binding.class)
public abstract class StandaloneCppLibraryPlugin implements Plugin<Project> {
    public static final String CPP_LIBRARY = "cppLibrary";

    static class Binding implements ProjectTypeBinding {
        @Override
        public void bind(ProjectTypeBindingBuilder builder) {
            builder.bindProjectType(CPP_LIBRARY, CppLibrary.class, (context, definition, buildModel) -> {
                Services services = context.getObjectFactory().newInstance(Services.class);
                services.getPluginManager().apply(CppLibraryPlugin.class);

                ((DefaultCppLibraryBuildModel) buildModel).setCppLibrary(services.getProject().getExtensions().getByType(org.gradle.language.cpp.CppLibrary.class));

                linkDefinitionToPlugin(services.getProject(), definition, buildModel);
            })
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
            .withBuildModelImplementationType(DefaultCppLibraryBuildModel.class);
        }

        private void linkDefinitionToPlugin(Project project, CppLibrary library, CppLibraryBuildModel buildModel) {
            org.gradle.language.cpp.CppLibrary model = buildModel.getCppLibrary();

            model.getImplementationDependencies().getDependencies().addAllLater(library.getDependencies().getImplementation().getDependencies());
            model.getApiDependencies().getDependencies().addAllLater(library.getDependencies().getApi().getDependencies());

            project.getComponents().withType(org.gradle.language.cpp.CppLibrary.class).configureEach(libraryComponent ->
                libraryComponent.getBinaries().configureEach(binary -> {
                    binary.getCompileTask().get().getCompilerArgs().add(library.getCppVersion().map(v -> "--std=" + v));
                })
            );
        }

        interface Services {
            @Inject
            PluginManager getPluginManager();

            @Inject
            Project getProject();
        }
    }

    @Override
    public void apply(Project target) { }
}
