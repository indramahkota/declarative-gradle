package org.gradle.api.experimental.buildinit;

import java.util.Arrays;
import java.util.List;

import org.gradle.buildinit.projectspecs.InitProjectGenerator;
import org.gradle.buildinit.projectspecs.InitProjectSpec;
import org.gradle.buildinit.projectspecs.InitProjectSource;

/**
 * A {@link InitProjectSource} of project specifications for JVM projects.
 */
@SuppressWarnings("UnstableApiUsage")
public final class JVMProjectSource implements InitProjectSource {
    @Override
    public List<InitProjectSpec> getProjectSpecs() {
        return Arrays.asList(
            new StaticProjectSpec("java-library", "Declarative Java Library Project"),
            new StaticProjectSpec("java-application", "Declarative Java Application Project")
        );
    }

    @Override
    public InitProjectGenerator getProjectGenerator() {
        return new StaticProjectGenerator();
    }
}
