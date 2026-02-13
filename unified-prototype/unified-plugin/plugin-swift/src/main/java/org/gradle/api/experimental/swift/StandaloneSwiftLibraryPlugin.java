package org.gradle.api.experimental.swift;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.experimental.swift.internal.DefaultSwiftLibraryBuildModel;
import org.gradle.api.experimental.swift.internal.SwiftPluginSupport;
import org.gradle.api.plugins.PluginManager;
import org.gradle.features.annotations.BindsProjectType;
import org.gradle.features.binding.ProjectTypeBinding;
import org.gradle.features.binding.ProjectTypeBindingBuilder;
import org.gradle.language.swift.plugins.SwiftLibraryPlugin;

import javax.inject.Inject;

@SuppressWarnings("UnstableApiUsage")
@BindsProjectType(StandaloneSwiftLibraryPlugin.Binding.class)
public abstract class StandaloneSwiftLibraryPlugin implements Plugin<Project> {

    public static final String SWIFT_LIBRARY = "swiftLibrary";

    static class Binding implements ProjectTypeBinding {
        @Override
        public void bind(ProjectTypeBindingBuilder builder) {
            builder.bindProjectType(SWIFT_LIBRARY, SwiftLibrary.class, (context, definition, buildModel) -> {
                Services services = context.getObjectFactory().newInstance(Services.class);
                services.getPluginManager().apply(SwiftLibraryPlugin.class);

                ((DefaultSwiftLibraryBuildModel) buildModel).setSwiftLibrary(services.getProject().getExtensions().getByType(org.gradle.language.swift.SwiftLibrary.class));

                linkDefinitionToPlugin(definition, buildModel);
            })
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
            .withBuildModelImplementationType(DefaultSwiftLibraryBuildModel.class);
        }

        private void linkDefinitionToPlugin(SwiftLibrary definition, SwiftLibraryBuildModel buildModel) {
            org.gradle.language.swift.SwiftLibrary model = buildModel.getSwiftLibrary();
            SwiftPluginSupport.linkSwiftVersion(definition, model);

            model.getImplementationDependencies().getDependencies().addAllLater(definition.getDependencies().getImplementation().getDependencies());
            model.getApiDependencies().getDependencies().addAllLater(definition.getDependencies().getApi().getDependencies());
        }

        interface Services {
            @Inject
            PluginManager getPluginManager();

            @Inject
            Project getProject();
        }
    }

    @Override
    public void apply(Project project) { }
}
