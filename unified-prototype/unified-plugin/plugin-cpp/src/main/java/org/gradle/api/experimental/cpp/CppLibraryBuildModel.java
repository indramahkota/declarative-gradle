package org.gradle.api.experimental.cpp;

import org.gradle.features.binding.BuildModel;
import org.gradle.language.cpp.CppLibrary;

@SuppressWarnings("UnstableApiUsage")
public interface CppLibraryBuildModel extends BuildModel {
    CppLibrary getCppLibrary();
}
