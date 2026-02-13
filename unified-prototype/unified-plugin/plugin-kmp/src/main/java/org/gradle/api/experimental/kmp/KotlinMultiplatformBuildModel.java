package org.gradle.api.experimental.kmp;

import org.gradle.api.experimental.common.HasCliExecutables;
import org.gradle.api.experimental.common.HasGroupAndVersion;
import org.gradle.features.binding.BuildModel;
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension;

@SuppressWarnings("UnstableApiUsage")
public interface KotlinMultiplatformBuildModel extends BuildModel, HasGroupAndVersion, HasCliExecutables {
    KotlinMultiplatformExtension getKotlinMultiplatformExtension();
}
