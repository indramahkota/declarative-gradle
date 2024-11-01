package org.gradle.api.experimental.kmp

import org.gradle.integtests.fixtures.AbstractBuildInitSpecification
import org.gradle.testkit.runner.GradleRunner
import org.junit.Ignore

@Ignore
class KotlinApplicationInitProjectSpec extends AbstractBuildInitSpecification {
    @Override
    protected String getEcosystemPluginId() {
        return "org.gradle.experimental.kmp-ecosystem"
    }

    @Override
    protected String getProjectSpecType() {
        return "kotlin-application"
    }

    @Override
    protected void validateBuild() {
        result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments(":app:run")
                .forwardOutput()
                .build()

        assert result.output.contains("Hello World!")
    }

    @Override
    protected void canBuildGeneratedProject() {
        result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments("build")
                .forwardOutput()
                .build()
    }
}
