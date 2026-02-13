package org.gradle.api.experimental.java.checkstyle;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.features.binding.Definition;

public interface CheckstyleDefinition extends Definition<CheckstyleBuildModel> {
    Property<String> getCheckstyleVersion();

    DirectoryProperty getConfigDirectory();

    RegularFileProperty getConfigFile();
}
