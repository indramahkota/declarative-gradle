package org.gradle.api.experimental.cpp;

import org.gradle.api.experimental.common.HasCliExecutables;
import org.gradle.features.binding.BuildModel;
import org.gradle.language.cpp.CppComponent;

@SuppressWarnings("UnstableApiUsage")
public interface CppApplicationBuildModel extends HasCliExecutables, BuildModel {
    CppComponent getCppComponent();
}
