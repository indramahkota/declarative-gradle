package org.gradle.api.experimental.swift;

import org.gradle.api.experimental.common.HasApplicationDependencies;
import org.gradle.features.binding.Definition;

@SuppressWarnings("UnstableApiUsage")
public interface SwiftApplication extends HasSwiftTarget, HasApplicationDependencies, Definition<SwiftApplicationBuildModel> {
}
