package org.gradle.api.experimental.android.library;

import com.android.build.api.dsl.LibraryExtension;
import org.gradle.features.binding.BuildModel;

@SuppressWarnings("UnstableApiUsage")
public interface AndroidLibraryBuildModel extends BuildModel {
    LibraryExtension getLibraryExtension();
}
