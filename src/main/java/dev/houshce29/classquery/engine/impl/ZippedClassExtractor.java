package dev.houshce29.classquery.engine.impl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Extractor that extracts from a ZIP-based filesystem (e.g. zips and jars).
 */
public class ZippedClassExtractor extends AbstractExtractor {
    private static final String FILE_PROTOCOL = "file:";
    private static final String JAR_SEPARATOR = ".jar!/";
    private static final String ZIP_SEPARATOR = ".zip!/";

    public ZippedClassExtractor(ClassLoader loader) {
        super(loader);
    }

    @Override
    public boolean canExtract(URL url) {
        String file = url.getFile();
        return file.startsWith(FILE_PROTOCOL) &&
                (file.contains(JAR_SEPARATOR) || file.contains(ZIP_SEPARATOR));
    }

    @Override
    public Set<Class> extract(URL url, String basePackage) {
        String normalized = url.getFile();
        // This should chop off the sub-filesystem portion:
        // file:/D:/dev jars/classquery.jar!/dev/houshce29/targetpackage -> file:/D:/dev%20jars/classquery.jar
        normalized = normalized.substring(0, normalized.indexOf("!")).replaceAll("[ ]", "%20");
        Optional<URI> uri = createUri(normalized);
        return uri.map(URI::getSchemeSpecificPart)
                .map(File::new)
                .map(zip -> extract(zip, basePackage.replaceAll("[.]", "/")))
                .orElse(Collections.emptySet());
    }

    private Set<Class> extract(File file, String packagePath) {
        Set<Class> classes = new HashSet<>();
        // Closeable resource
        try (ZipFile zip = new ZipFile(file)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            // Loop through the entries
            while (entries.hasMoreElements()) {
                String entry = entries.nextElement().getName();
                // Must match the package path and be a class file
                if (entry.startsWith(packagePath) && entry.endsWith(CLASS_EXTENSION)) {
                    String fullyQualifiedName = entry.replaceAll("/", PACKAGE_SEPARATOR)
                            .substring(0, entry.lastIndexOf(CLASS_EXTENSION));
                    resolve(fullyQualifiedName).ifPresent(classes::add);
                }
            }
        }
        catch (IOException ex) {
            // Swallow
        }
        return classes;
    }

    private Optional<URI> createUri(String uriString) {
        try {
            return Optional.of(new URI(uriString));
        }
        catch (URISyntaxException ex) {
            return Optional.empty();
        }
    }
}
