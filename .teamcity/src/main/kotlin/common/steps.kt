package common

import jetbrains.buildServer.configs.kotlin.buildSteps.GradleBuildStep

private val JDK_INSTALLATIONS = listOf(11, 17, 21)

fun configuredGradle(init: GradleBuildStep.() -> Unit) = GradleBuildStep {
    useGradleWrapper = true
    buildFile = "" // Let Gradle detect the build script
    enableStacktrace = true
    gradleParams = "-Porg.gradle.java.installations.fromEnv=${JDK_INSTALLATIONS.joinToString(",") { j -> "JDK$j" }}"

    init()
}

fun GradleBuildStep.addGradleParam(value: String) {
    gradleParams = when {
        gradleParams.isNullOrEmpty() -> value
        else -> "$gradleParams $value"
    }
}
