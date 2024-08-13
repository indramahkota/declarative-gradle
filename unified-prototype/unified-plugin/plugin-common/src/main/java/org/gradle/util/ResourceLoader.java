package org.gradle.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Static util class containing methods for loading resources from the classpath.
 */
public final class ResourceLoader {
    /**
     * Recursively extracts the contents of a directory in a jar file on the classpath to a specified directory.
     *
     * @param relativePath path to the source directory within the jar file
     * @param destDir target directory to extract the contents to
     * @throws IOException if an I/O error occurs
     */
    public void extractResourcesFromJar(String relativePath, File destDir) throws IOException {
        URL jarDirURL = ResourceLoader.class.getClassLoader().getResource(relativePath);
        if (jarDirURL == null) {
            throw new IllegalArgumentException("Directory: '" + relativePath + "' not found on classpath.");
        }
        JarFile jarFile = ((JarURLConnection) jarDirURL.openConnection()).getJarFile();
        System.out.println("Using URL: " + jarDirURL);

        Iterator<JarEntry> iterator = jarFile.entries().asIterator();
        while (iterator.hasNext()) {
            JarEntry entry = iterator.next();
            String entryName = entry.getName();

            if (entryName.startsWith(relativePath)) {
                String entrySuffix = entryName.substring(relativePath.length());
                System.out.println("Entry: " + entry + " name: " + entryName + " suffix: " + entrySuffix);
                File destFile = new File(destDir, entrySuffix);
                System.out.println("Dest file: " + destFile);

                if (entry.isDirectory()) {
                    System.out.println("Is directory");
                    FileUtils.forceMkdir(destFile);
                    System.out.println("Created directory");
                } else {
                    System.out.println("Is file");
                    try (InputStream is = jarFile.getInputStream(entry);
                         FileOutputStream fos = new FileOutputStream(destFile)) {
                        IOUtils.copy(is, fos);
                    }
                    System.out.println("Copied file");
                }
            }
        }
    }
}