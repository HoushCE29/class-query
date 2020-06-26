package dev.houshce29.classquery.engine.extract;

import dev.houshce29.classquery.internal.Util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Extractor that simply extracts from the local filesystem.
 */
public class SimpleExtractor extends AbstractExtractor {

    public SimpleExtractor(ClassLoader loader) {
        super(loader);
    }

    @Override
    public boolean canExtract(URL url) {
        try {
            url.toURI();
            return true;
        }
        catch (URISyntaxException ex) {
            return false;
        }
    }

    @Override
    public Set<Class> extract(URL url, String basePackage) {
        try {
            return scan(new File(url.toURI()), basePackage);
        }
        catch (URISyntaxException ex) {
            return new HashSet<>();
        }
    }

    private Set<Class> scan(File file, String javaPackage) {
        // Weird... either a JAR slipped through or this file got removed
        if (!file.exists()) {
            return new HashSet<>();
        }
        Set<Class> classes = new HashSet<>();
        String fileName = file.getName();
        // MyFileName.class
        if (fileName.endsWith(CLASS_EXTENSION)) {
            String fullyQualifiedName =
                    javaPackage + PACKAGE_SEPARATOR + fileName.substring(0, fileName.lastIndexOf(CLASS_EXTENSION));
            resolve(fullyQualifiedName).ifPresent(classes::add);
        }
        // If a directory, dig into it and append to java package
        else if (file.isDirectory()) {
            String nextPackage = javaPackage + PACKAGE_SEPARATOR + fileName;
            for (File child : Util.toList(file.listFiles())) {
                classes.addAll(scan(child, nextPackage));
            }
        }
        return classes;
    }
}
