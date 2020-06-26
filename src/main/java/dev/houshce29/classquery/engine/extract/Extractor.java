package dev.houshce29.classquery.engine.extract;

import java.net.URL;
import java.util.Set;

/**
 * Object that extracts one or more classes.
 */
public interface Extractor {
    String PACKAGE_SEPARATOR = ".";
    String CLASS_EXTENSION = ".class";

    /**
     * Returns true if this extractor can extract from the URL.
     * @param url URL to check.
     * @return true if extraction is possible.
     */
    boolean canExtract(URL url);

    /**
     * Extracts the classes from the URL.
     * @param url URl to extract from.
     * @param basePackage Base Java package this URL came from.
     * @return Set of classes.
     */
    Set<Class> extract(URL url, String basePackage);
}
