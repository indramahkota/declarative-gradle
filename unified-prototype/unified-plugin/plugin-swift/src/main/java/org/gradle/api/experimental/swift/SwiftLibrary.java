package org.gradle.api.experimental.swift;

import org.gradle.api.experimental.common.HasLibraryDependencies;
import org.gradle.features.binding.Definition;

@SuppressWarnings("UnstableApiUsage")
public interface SwiftLibrary extends HasSwiftTarget, HasLibraryDependencies, Definition<SwiftLibraryBuildModel> {
}
