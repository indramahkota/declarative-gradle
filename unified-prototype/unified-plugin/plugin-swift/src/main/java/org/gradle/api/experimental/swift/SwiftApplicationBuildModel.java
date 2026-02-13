package org.gradle.api.experimental.swift;

import org.gradle.api.experimental.common.HasCliExecutables;
import org.gradle.features.binding.BuildModel;
import org.gradle.language.swift.SwiftComponent;

@SuppressWarnings("UnstableApiUsage")
public interface SwiftApplicationBuildModel extends BuildModel, HasCliExecutables {
    SwiftComponent getSwiftComponent();
}
