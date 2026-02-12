package org.gradle.api.experimental.swift;

import org.gradle.features.binding.BuildModel;

@SuppressWarnings("UnstableApiUsage")
public interface SwiftLibraryBuildModel extends BuildModel {
    org.gradle.language.swift.SwiftLibrary getSwiftLibrary();
}
