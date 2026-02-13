package org.gradle.api.experimental.android.application;

import com.android.build.api.dsl.ApplicationExtension;
import org.gradle.features.binding.BuildModel;

@SuppressWarnings("UnstableApiUsage")
public interface AndroidApplicationBuildModel extends BuildModel {
    ApplicationExtension getApplicationExtension();
}
