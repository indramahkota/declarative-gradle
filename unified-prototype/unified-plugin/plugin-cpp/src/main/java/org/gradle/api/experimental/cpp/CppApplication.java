package org.gradle.api.experimental.cpp;

import org.gradle.api.experimental.common.HasApplicationDependencies;
import org.gradle.features.binding.Definition;

@SuppressWarnings("UnstableApiUsage")
public interface CppApplication extends HasCppTarget, HasApplicationDependencies, Definition<CppApplicationBuildModel> {
}
