package org.gradle.api.experimental.android.test;

import com.android.build.api.dsl.TestExtension;
import org.gradle.features.binding.BuildModel;

@SuppressWarnings("UnstableApiUsage")
public interface AndroidTestBuildModel extends BuildModel {
    public TestExtension getTestExtension();
}
