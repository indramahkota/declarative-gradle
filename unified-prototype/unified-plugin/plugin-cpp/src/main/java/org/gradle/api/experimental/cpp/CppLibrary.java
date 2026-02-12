package org.gradle.api.experimental.cpp;

import org.gradle.api.experimental.common.HasLibraryDependencies;
import org.gradle.features.binding.Definition;

@SuppressWarnings("UnstableApiUsage")
public interface CppLibrary extends HasCppTarget, HasLibraryDependencies, Definition<CppLibraryBuildModel> {
}
